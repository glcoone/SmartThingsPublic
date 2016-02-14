/**
 *  Garage Door Device Type - Garage_Doors.groovy
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 * 
 *  Change History:
 *
 *    Date        Who            What
 *    ----        ---            ----
 *    2016-01-06  Garnet Coone  Original Creation - Refactor of my Raspberry Pi implimentation using ST_Anything_Doors
 *                                                  in order to switch to Arduino and ST Shield
 *    2016-01-24  Garnet Coone  Advanced Control -  Added Summary Tile that shows rollup status.  Added virtual tiles for
 *													switch and contact sensor for use with IFTT and SmartTiles.
 *
 */
 
import groovy.json.JsonSlurper

metadata {
	definition (name: "Garage_Doors", namespace: "Garnets", author: "Garnet Coone") {
		capability "Polling"
		capability "Refresh"
    	capability "Switch"   
    	capability "Lock"
		capability "Door Control"
		capability "Contact Sensor"
    	//capability "Sensor"
    	//capability "Actuator"
        
        //attribute "garageSideDoor", "string"

        // Add Capability for 2 Garage Doors that can: Open, Close, Turn Light On/Off, and LOCKout wireless remostes
        command "pushMainGD"
        command "pushSmallGD"
        command "closeAll"
        command "mainGDLightOn"
        command "smallGDLightOn"
        command "mainGDLightOff"
        command "smallGDLightOff"
        command "lock1"
        command "lock2"
        command "unlock1"
        command "unlock2"
        command "subscribeAction"
        
	}
    
    simulator {
        status "on":  "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A6F6E"
        status "off": "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A6F6666"

        // reply messages
        reply "raw 0x0 { 00 00 0a 0a 6f 6e }": "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A6F6E"
        reply "raw 0x0 { 00 00 0a 0a 6f 66 66 }": "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A6F6666"
    }


	tiles {
        // Create 3 GD Tiles.  One as the summary and the other 2 for the physical doors.  
        // Intially used nextstate (Ex: nextState:"closing")), but will use web remote's return value to determine state
        //     (Color references: #ff3300,#bc2323=red d04e00=dark orange, #ffa81e=orange, #ffcc33,#f1d801=yellow, 44b621=green
		standardTile("allGD", "device.doorControl", width: 1, height: 1, canChangeBackground: true, canChangeIcon: true) {
			state("unknown", label:'${name}', action:"refresh.refresh", icon:"st.doors.garage.garage-open", backgroundColor:"#d04e00")
			state("closed",  label:'${name}', action:"refresh.refresh", icon:"st.doors.garage.garage-closed", backgroundColor:"#79b821")
          	state("open",    label:'${name}', action:"refresh.refresh", icon:"st.doors.garage.garage-open", backgroundColor:"#ffa81e")
			state("opening", label:'${name}', action:"refresh.refresh", icon:"st.doors.garage.garage-opening", backgroundColor:"#ffe71e")
			state("closing", label:'${name}', action:"refresh.refresh", icon:"st.doors.garage.garage-closing", backgroundColor:"#ffe71e")
			state("moving",  label:'${name}', action:"refresh.refresh", icon:"st.motion.motion.active", backgroundColor: "#ffdd00")
		}
        // In ST each GD needs it's own state (Ex: closed1).  I tried using the same state names, but ST would change it for all 3 tiles.  Maybe this
        // has been fixed by ST.  This is also used with IFTT, and it only works with ST built-in types.
        standardTile("mainGD", "device.mainGD", canChangeBackground: true) {
			state("unknown", label:"unknown Main", action:"refresh.refresh", icon:"st.doors.garage.garage-open", backgroundColor:"#d04e00")
			state("closed",  label:"closed Main",  action:"pushMainGD", icon:"st.doors.garage.garage-closed", backgroundColor:"#79b821", nextState:"opening")
			state("open",    label:"open Main",    action:"pushMainGD", icon:"st.doors.garage.garage-open", backgroundColor:"#ffa81e", nextState:"closing")
			state("opening", label:"opening Main", icon:"st.doors.garage.garage-opening", backgroundColor:"#ffe71e")
			state("closing", label:"closing Main", icon:"st.doors.garage.garage-closing", backgroundColor:"#ffe71e")
		}
        standardTile("smallGD", "device.smallGD", canChangeBackground: true) {
			state("unknown", label:"unknown Small", action:"refresh.refresh", icon:"st.doors.garage.garage-open", backgroundColor:"#d04e00")
			state("closed",  label:"closed Small",  action:"pushSmallGD", icon:"st.doors.garage.garage-closed", backgroundColor:"#79b821",nextState:"opening")
			state("open",    label:"open Small",    action:"pushSmallGD", icon:"st.doors.garage.garage-open", backgroundColor:"#ffa81e", nextState:"closing")
			state("opening", label:"opening Small", icon:"st.doors.garage.garage-opening", backgroundColor:"#ffe71e")
			state("closing", label:"closing Small", icon:"st.doors.garage.garage-closing", backgroundColor:"#ffe71e")
		}
		standardTile("open", "device.door", inactiveLabel: false, decoration: "flat") {
			state "default", label:'Open Main', action:"door control.open", icon:"st.doors.garage.garage-opening"
		}
		standardTile("close", "device.door", inactiveLabel: false, decoration: "flat") {
			state "default", label:'Close Main', action:"door control.close", icon:"st.doors.garage.garage-closing"
		}
    	standardTile("closeAll", "device.door", inactiveLabel: false, decoration: "flat") {
			state "default", label:'Close All', action:"closeAll", icon:"st.doors.garage.garage-closing"
		}

        standardTile("mainGD_Light", "device.mainGD_Light", canChangeBackground: true) {
			state "off", label: '${name}', action: "mainGDLightOn",  icon: "st.switches.light.off", backgroundColor: "#ffffff"//, nextState: "on"
			state "on",  label: '${name}', action: "mainGDLightOFF", icon: "st.switches.light.on",  backgroundColor: "#79b821"//, nextState: "off"
		}
	    standardTile("smallGD_Light", "device.smallGD_Light", canChangeBackground: true) {
			state "off", label: '${name}', action: "smallGDLightOn",  icon: "st.switches.light.off", backgroundColor: "#ffffff"//, nextState: "on"
			state "on",  label: '${name}', action: "smallGDLightOff", icon: "st.switches.light.on",  backgroundColor: "#79b821"//, nextState: "off"
		}

        standardTile("mainGD_Lock", "device.mainGD_Lock", canChangeBackground: true) {
        	state "unlocked", label: '${name}', action: "lock1",   icon: "st.locks.lock.unlocked",  backgroundColor: "#79b821", nextState: "locked"
			state "locked",   label: '${name}', action: "unlock1", icon: "st.locks.lock.locked", backgroundColor: "#ffffff", nextState: "unlocked"
		}
        standardTile("smallGD_Lock", "device.smallGD_Lock", canChangeBackground: true) {
			state "unlocked", label: '${name}', action: "lock2",   icon: "st.locks.lock.unlocked",  backgroundColor: "#79b821", nextState: "locked"
            state "locked",   label: '${name}', action: "unlock2", icon: "st.locks.lock.locked", backgroundColor: "#ffffff", nextState: "unlocked"
		}
        standardTile("garageSideDoor", "device.garageSideDoor", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
			state("open", label:'${name}', icon:"st.contact.contact.open", backgroundColor:"#ffa81e")
			state("closed", label:'${name}', icon:"st.contact.contact.closed", backgroundColor:"#79b821")
 		}
        standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat") {
        		state "default", action:"refresh.refresh", icon: "st.secondary.refresh"
        }
        
        ///// Virtual Tiles for built-in capabilities used by IFTT and SmartTiles. /////
		standardTile("switch", "device.switch") {
			state "off", label: '${name}', action: "switch.on", icon: "st.switches.light.off", backgroundColor: "#ffffff"
			state "on", label: '${name}', action: "switch.off", icon: "st.switches.light.on", backgroundColor: "#79b821"
		}
        standardTile("contact", "device.contact", inactiveLabel: false) {
			state "open", label: "DingDong!", icon: "st.security.alarm.alarm", backgroundColor: "#ffa81e"
			state "closed", label: " ", icon: "st.security.alarm.clear", backgroundColor: "#79b821"
		}
        standardTile("lock", "device.lock", inactiveLabel: false) {
			state "unlocked", backgroundColor: "#79b821"
			state "locked", backgroundColor: "#ffa81e"
		}
        
        main("mainGD")
        details("open", "close", "closeAll", "mainGD", "mainGD_Light", "mainGD_Lock", "smallGD", "smallGD_Light", "smallGD_Lock", "garageSideDoor", "refresh")
        //details(["allGD", "open", "close", "mainGD", "mainGD_Light", "mainGD_Lock", "smallGD", "smallGD_Light", "smallGD_Lock", "garageSideDoor", "refresh"])
        //details("allGD", "open", "close", "mainGD", "mainGD_Light", "mainGD_Lock", "smallGD", "smallGD_Light", "smallGD_Lock", "garageSideDoor", "refresh")
    }
}

def installed() {
	log.debug "Installing Garage Door!!!!!!!!"
    state.mainGD = "closed"
    state.smallGD = "closed"
}

//Map parse(String description) {
def parse(String description) {
    log.debug("Entering parse() - " + zigbee.getEvent(description))
    //def result = zigbee.getEvent(description)
    //if(result) {
      //  sendEvent(result)
    //} else {
    //    // zigbee.getEvent was unable to parse description. Description must be parsed manually.
    //}
    def msg = zigbee.parse(description)?.text
    log.debug "--Parse msg = '${msg}'"
    if (msg == 'ping'){
        return msg
	}
    
    def parts = msg.split(" ")
    def name  = parts.length>0?parts[0].trim():null
    def value = parts.length>1?parts[1].trim():null
    log.debug "--- parts = ${parts}; name = ${name}; value = ${value}"
    
    name = value != "ping" ? name : null
    //log.debug "--- parse result = ${result}"
    def result = createEvent(name: name, value: value)
  
    if (name == "mainGD") {
        if (state.mainGD != value) {
        	state.mainGD = value
            if (state.mainGD == "closed" && state.smallGD == "closed") {
				sendEvent(name: "contact", value: "closed")
                sendEvent(name: "allGD",   value: "closed")
            }
            else {
            	sendEvent(name: "contact", value: "open")
            	sendEvent(name: "allGD",   value: "open")
            }
            ///// Also update ST built-in Switch /////
            if (state.mainGD == "open") {
            	sendEvent(name: "switch", value: "on")
            }
            else {
            	sendEvent(name: "switch", value: "off")
            }
     	}
        log.debug "***** mailGD_Light found in parse string - " + state.mainGD
    }
    if (name == "smallGD") {
        if (state.smallGD != value) {
        	state.smallGD = value
            if (state.mainGD == "closed" && state.smallGD == "closed") {
				sendEvent(name: "contact", value: "closed")
                sendEvent(name: "allGD",   value: "closed")
            }
            else {
            	sendEvent(name: "contact", value: "open")
                sendEvent(name: "allGD",   value: "open")
            }
     	}
        log.debug "***** smallGD_Light found in parse string - " + state.smallGD
    }
    //sendEvent(name: "contact", value: "closed")
    return result
    
    
    def map = [:]
    def descMap = zigbee.parseDescriptionAsMap(description)
    log.debug("-- descMap = ${descMap}")
    def body = new String(descMap["body"].decodeBase64())
    //def body = new String(descMap["result"].decodeBase64())
    log.debug "-- parse() - body: ${body}"
    def slurper = new JsonSlurper()
    //def result = slurper.parseText(body)
    //log.debug "-- result: ${result}"
	//if (result){
    //	log.debug "Computer is up"
   	//	sendEvent(name: "cpuStat", value: "on")
    //}
}

def pushMainGD() {
    log.debug "Executing 'pushMainGD' = 'mainGD on'"
    zigbee.smartShield(text: "mainGD on").format()
}
def pushSmallGD() {
    log.debug "Executing 'pushSmallGD' = 'smallGD on'"
    zigbee.smartShield(text: "smallGD on").format()
}

def open() {  ///// Open Main GD //////
    log.debug "Executing 'open()' = 'mainGD on'"
    ///// Guard to only open the door if it is closed /////
    if (state.mainGD == "closed" || state.mainGD == "closing") {
    	sendEvent(name: "allGD",   value: "opening", isStateChange: true, display: true)
    	sendEvent(name: "mainGD",  value: "opening", isStateChange: true, display: true)
  		pushMainGD()
    }
}
def close() {  ///// Close Main GD /////
    log.debug "Executing 'close' = 'mainGD on'"
    if (state.mainGD == "open" || state.mainGD == "opening") {  ///// Guard /////
    	sendEvent(name: "allGD",   value: "closing", isStateChange: true, display: true)
    	sendEvent(name: "mainGD",  value: "closing", isStateChange: true, display: true)
    	//zigbee.smartShield(text: "mainGD off").format()
        pushMainGD()
    }
}
def closeAll() {  ///// Close both Doors /////
    log.debug "***** Executing 'closeAll' = 'closeall'"
    //// Guard for closing both Doors w/ delay between cmds as Arduino can't handle without a little dalay /////
    if ((state.mainGD == "open" || state.mainGD == "opening") && (state.smallGD == "open" || state.smallGD == "opening")) {  
    	sendEvent(name: "allGD",   value: "closing", isStateChange: true, display: true)
    	sendEvent(name: "mainGD",  value: "closing", isStateChange: true, display: true)
        sendEvent(name: "smallGD", value: "closing", isStateChange: true, display: true)
        delayBetween([
            zigbee.smartShield(text: "mainGD on").format(),
            zigbee.smartShield(text: "smallGD on").format()
            ], 1000
        )
    }
    else if (state.mainGD == "open" || state.mainGD == "opening") {  ///// Guard /////
    	sendEvent(name: "allGD",   value: "closing", isStateChange: true, display: true)
    	sendEvent(name: "mainGD",  value: "closing", isStateChange: true, display: true)
        pushMainGD()
    }
    else if (state.smallGD == "open" || state.smallGD == "opening") {  ///// Guard /////
    	sendEvent(name: "allGD",   value: "closing", isStateChange: true, display: true)
    	sendEvent(name: "smallGD",  value: "closing", isStateChange: true, display: true)
        pushSmallGD()
    }
}

def mainGDLightOn() {
    log.debug "Executing 'mainGDLightOn' = 'mainGD_Light on'"

    log.debug "--- " + device.currentState("door")
    log.debug "--- " + device.latestState("lock")
    log.debug "--- state.smallGD=${state.smallGD}"

    // Gets the current value for the given attribute
    // Return type will vary depending on the device
    log.debug "--- " + device.currentValue("door")
    log.debug "--- " + device.latestValue("lock")
    //zigbee.smartShield(text: "mainGD_Light on").format()
    sendEvent(name: "smallGD_Light", value: "on", isStateChange: true, display: true) 
}

def smallGDLightOn() {
    log.debug "Executing 'smallGDLightOn' = 'smallGD_Light on'"
    //zigbee.smartShield(text: "smallGD_Light on").format()
 ///VERIFY then delete 1/25
    sendEvent(name: "smallGD_Light", value: "on", isStateChange: true, display: true) 
}

def mainGDLightOff() {
    log.debug "Executing 'mainGDLightOff' = 'mainGD_Light off'"
    zigbee.smartShield(text: "mainGD_Light off").format()
}
def smallGDLightOff() {
    log.debug "Executing 'smallGDLightOff' = 'smallGD_Light off'"
    zigbee.smartShield(text: "smallGD_Light off").format()
}

def lock1() {
    log.debug "Executing 'lock1' = 'mainGD_Lock on'"
    zigbee.smartShield(text: "mainGD_Lock on").format()
}
def lock2() {
    log.debug "Executing 'lock2' = 'smallGD_Lock on'"
    zigbee.smartShield(text: "smallGD_Lock on").format()
}

def unlock1() {
    log.debug "Executing 'unlock1' = 'mainGD_Lock off'"
    zigbee.smartShield(text: "mainGD_Lock on").format()
}
def unlock2() {
    log.debug "Executing 'unlock2' = 'smallGD_Lock off'"
    zigbee.smartShield(text: "smallGD_Lock off").format()
}

///// Virtual control of Main GD using Switch type.  This allows control w/ IFTT, Alexa, etc. /////
def on() {  // Use On switch to Open GD
    log.debug "Executing 'on()'"
    open()
}
def off() {   // Use off switch to Close all GD
    log.debug "Executing 'off()'"
    close()
}
///// Virtual control using Lock type  This allows IFTT, Alexa, etc. lock/unlock /////
def cmds = []
	cmds << "delay 1000"

def lock() {
    log.debug "Executing 'lock()'"
    lock1()
    //sleep(500)
    cmds
    lock2()
    sendEvent(name: "lock", value: "locked") 
    //zigbee.smartShield(text: "mainGD on").format()
}
def unlock() {
    log.debug "Executing 'unlock()'"
    unlock1()
    //sleep(500)
    unlock2()
    sendEvent(name: "lock", value: "unlocked") 
    //zigbee.smartShield(text: "mainGD off").format()
}

def poll() {
	//temporarily implement poll() to issue a configure() command to send the polling interval settings to the arduino
	configure()
}

def configure() {
	log.debug "Executing 'configure'"
    //return;
	log.debug "--  temphumid sample rate = " + temphumidSampleRate
	[
        zigbee.smartShield(text: "temphumid " + temphumidSampleRate).format()
    ]
}