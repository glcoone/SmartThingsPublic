/**
 *  Raspberry Pi
 *
 *  Copyright 2014 Nicholas Wilde
 *
 *  Monitor your Raspberry Pi using SmartThings and WebIOPi <https://code.google.com/p/webiopi/>
 *
 *  Companion WebIOPi python script can be found here:
 *  <https://github.com/nicholaswilde/smartthings/blob/master/device-types/raspberry-pi/raspberrypi.py>
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
 */
 
import groovy.json.JsonSlurper
//import java.io.*

preferences {
        input("ip", "string", title:"IP Address", description: "192.168.169.1", required: true, displayDuringSetup: true)
        input("port", "string", title:"Port", description: "8000", defaultValue: 8000 , required: true, displayDuringSetup: true)
        input("username", "string", title:"Username", description: "webiopi", required: true, displayDuringSetup: true)
        input("password", "password", title:"Password", description: "Password", required: true, displayDuringSetup: true)
}

metadata {
	definition (name: "Raspberry Pii - Garage Door", namespace: "Garnets", author: "Garnet Coone") {
		capability "Polling"
		capability "Refresh"
        capability "Switch"
        //capability "Sensor"
        //capability "Actuator"
        capability "Lock"
		capability "Door Control"
		capability "Contact Sensor"

        // Add Capability for 2 Garage Doors that can: Open, Close, Turn Light On/Off, and LOCKout wireless remostes
        command "open1"
        command "close1"
        command "on1"
        command "off1"
        command "lock1"
        command "unlock1"
        command "open2"
        command "close2"
        command "on2"
        command "off2"
        command "lock2"
        command "unlock2"
        command "subscribeAction"
}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
        // Create 3 GD Tiles.  One as the summary and the other 2 for the physical doors.  
        // Intially used nextstate (Ex: nextState:"closing")), but will use web remote's return value to determine state
        //     (Color references: #ff3300,#bc2323=red d04e00=dark orange, #ffa81e=orange, #ffcc33,#f1d801=yellow, 44b621=green
		standardTile("allGD", "device.doorControl", width: 1, height: 1, canChangeBackground: true, canChangeIcon: true) {
			state("unknown", label:'${name}', action:"refresh.refresh", icon:"st.doors.garage.garage-open", backgroundColor:"#d04e00")
			state("closed",  label:'${name}', action:"refresh.refresh", icon:"st.doors.garage.garage-closed", backgroundColor:"#79b821")
            state("open",    label:'${name}', action:"refresh.refresh", icon:"st.doors.garage.garage-open", backgroundColor:"#ffa81e")
			state("opening", label:'${name}', icon:"st.doors.garage.garage-opening", backgroundColor:"#ffe71e")
			state("closing", label:'${name}', icon:"st.doors.garage.garage-closing", backgroundColor:"#ffe71e")
			state "moving",  label:'${name}', icon:"st.motion.motion.active", action: "refresh.refresh", backgroundColor: "#ffdd00"
		}
        standardTile("All_GD_Status", "device.contactSensor") {
        	state "open", label: '${name}'
			state "closed",  label: '${name}'
		}
        // In ST ach GD needs it's own state (Ex: closed1).  I tried using start state names, but ST would change it for all 3 tiles.
        standardTile("largeGD", "device.mydoor", canChangeBackground: true) {
			state("unknown1", label:"unknown large", action:"refresh.refresh", icon:"st.doors.garage.garage-open", backgroundColor:"#d04e00")
			state("closed1",  label:"closed large",  action:"open1", icon:"st.doors.garage.garage-closed", backgroundColor:"#79b821", nextState:"opening1")
			state("open1",    label:"open large",    action:"close1", icon:"st.doors.garage.garage-open", backgroundColor:"#ffa81e", nextState:"closing1")
			state("opening1", label:"opening large", icon:"st.doors.garage.garage-opening", backgroundColor:"#ffe71e")
			state("closing1", label:"closing large", icon:"st.doors.garage.garage-closing", backgroundColor:"#ffe71e")
		}
        standardTile("smallGD", "device.mydoor", canChangeBackground: true) {
			state("unknown2", label:"unknown small", action:"refresh.refresh", icon:"st.doors.garage.garage-open", backgroundColor:"#d04e00")
			state("closed2",  label:"closed small",  action:"open2", icon:"st.doors.garage.garage-closed", backgroundColor:"#79b821",nextState:"opening2")
			state("open2",    label:"open small",    action:"close2", icon:"st.doors.garage.garage-open", backgroundColor:"#ffa81e", nextState:"closing2")
			state("opening2", label:"opening small", icon:"st.doors.garage.garage-opening", backgroundColor:"#ffe71e")
			state("closing2", label:"closing small", icon:"st.doors.garage.garage-closing", backgroundColor:"#ffe71e")
		}
		standardTile("open", "device.door", inactiveLabel: false, decoration: "flat") {
			state "default", label:'open all', action:"door control.open", icon:"st.doors.garage.garage-opening"
		}
		standardTile("close", "device.door", inactiveLabel: false, decoration: "flat") {
			state "default", label:'close all', action:"door control.close", icon:"st.doors.garage.garage-closing"
		}

        standardTile("largeGD_light", "device.switch1", canChangeBackground: true) {
			state "off1", label: '${name}', action: "on1",  icon: "st.switches.light.off", backgroundColor: "#ffffff"//, nextState: "on"
			state "on1",  label: '${name}',  action: "off1", icon: "st.switches.light.on",  backgroundColor: "#79b821"//, nextState: "off"
		}
		standardTile("smallGD_light", "device.switch2", canChangeBackground: true) {
			state "off2", label: '${name}', action: "on2",  icon: "st.switches.light.off", backgroundColor: "#ffffff"//, nextState: "on"
			state "on2",  label: '${name}',  action: "off2", icon: "st.switches.light.on",  backgroundColor: "#79b821"//, nextState: "off"
		}

        standardTile("largeGD_lock", "device.switch4", canChangeBackground: true) {
        	state "unlocked1", label: '${name}',  action: "lock1", icon: "st.locks.lock.unlocked",  backgroundColor: "#79b821", nextState: "locked"
			state "locked1",   label: '${name}', action: "unlock1",  icon: "st.locks.lock.locked", backgroundColor: "#ffffff", nextState: "unlocked"
		}
        standardTile("smallGD_lock", "device.switch3", canChangeBackground: true) {
			state "unlocked2", label: '${name}',  action: "lock2", icon: "st.locks.lock.unlocked",  backgroundColor: "#79b821", nextState: "locked"
            state "locked2",   label: '${name}', action: "unlock2",  icon: "st.locks.lock.locked", backgroundColor: "#ffffff", nextState: "unlocked"

		}
        
        standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat") {
        	state "default", action:"refresh.refresh", icon: "st.secondary.refresh"
        }
        
        main "allGD"
        details(["allGD", "open", "close", "largeGD", "largeGD_light", "largeGD_lock", "smallGD", "smallGD_light", "smallGD_lock", "refresh"])

    }
}

// ------------------------------------------------------------------

def installed() {
	log.debug "Installing Raspberry Pi Garage Door!!!!!!!!"
    state.DeviceID = 0
    subscribeAction("/path/of/event")
}

def updated() {
	log.debug "Updating MyQ Garage Door!!!!!!!!"    
    state.DeviceID = 0
    poll()
    subscribeAction("/path/of/event")
}

def parseEventData(Map results){
    log.debug "@@@@@@@@@@@@@@@@@@@@@@ parseEventData results = ${results}"
    results.each { name, value ->
        log.debug "parseEventData ${name}, ${value}"
        //Parse events and optionally create SmartThings events
    }
}


// parse events into attributes
def parse(String description) {
    def map = [:]
    def descMap = parseDescriptionAsMap(description)
    //log.debug descMap
    def body = new String(descMap["body"].decodeBase64())
    //log.debug "parse() - body: ${body}"
    def slurper = new JsonSlurper()
    def result = slurper.parseText(body)
    log.debug "Raspberry Pii - Garage Door-parse() - result: ${result}"
	//if (result){
    //	log.debug "Computer is up"
   	//	sendEvent(name: "cpuStat", value: "on")
    //}
    
    if (result.containsKey("GD1_status")) {
        log.debug state.gd1_state
        state.gd1_state = result.GD1_status
        //log.debug state.gd1_state
        sendEvent(name: "largeGD", value: result.GD1_status, isStateChange: true, display: true, descriptionText: "Large door is $result.GD1_status")
        //log.debug "GD1_status: ${state.GD1_status}"
    }
    if (result.containsKey("GD2_status")) {
    	log.debug "GD2_status: ${result.GD2_status}"
        state.gd2_state = result.GD2_status
        //log.debug state.gd2_state
        sendEvent(name: "smallGD", value: result.GD2_status, isStateChange: true, display: true, descriptionText: "Small door is $result.GD2_status")
        //log.debug "GD2_status: ${state.GD2_status}"
    }
       
    // Summary Status:  Show worse case.  Either one open, opening, closing, and only show closed if both closed
    if (result.containsKey("GD1_status") && result.containsKey("GD2_status")) {
    	if (result.GD1_status == "closed1" && result.GD2_status == "closed2") {
            log.debug "Setting allGD to closed"
			sendEvent(name: "allGD", value: "closed", isStateChange: true, display: true, descriptionText: "Both doors are closed")
            sendEvent(name: "All_GD_Status", value: "closed")
        }
        else if (result.GD1_status == "open1" || result.GD2_status == "open2") {
			sendEvent(name: "allGD", value: "open", isStateChange: true, display: true, descriptionText: "At least one door open")
            sendEvent(name: "All_GD_Status", value: "open")
        }
        else if (result.GD1_status == "opening1" || result.GD2_status == "opening2") {
			sendEvent(name: "allGD", value: "opening", isStateChange: true, display: true, descriptionText: "At least one door opening")
            sendEvent(name: "All_GD_Status", value: "open")
        }
        else if (result.GD1_status == "closing1" || result.GD2_status == "closing2") {
			sendEvent(name: "allGD", value: "closing", isStateChange: true, display: true, descriptionText: "At least one door closing")
            sendEvent(name: "All_GD_Status", value: "closed")
        }
        else {
			sendEvent(name: "allGD", value: "unknown", isStateChange: true, display: true, descriptionText: "Large Door had invalid data")
            sendEvent(name: "All_GD_Status", value: "unknown")
        }
    }
    
    if (result.containsKey("GD1_light")) {
    	log.debug "GD1_light: ${result.GD1_light}"
        if (result.GD1_light == "on") {
            sendEvent(name: "largeGD_light", value: "on1", isStateChange: true, display: true)
            log.debug "setting largeGD_light to on1"
        }
        else
            sendEvent(name: "largeGD_light", value: "off1", isStateChange: true, display: true)
        log.debug "GD1_light state= ${state.largeGD_light}"
    }
    if (result.containsKey("GD2_light")) {
    	log.debug "GD2_light: ${result.GD2_light}"
        if (result.GD2_light == "on")
            sendEvent(name: "smallGD_light", value: "on2", isStateChange: true, display: true)
        else
            sendEvent(name: "smallGD_light", value: "off2", isStateChange: true, display: true)
        //sendEvent(name: "smallGD_light", value: result.GD2_light)
    }

    if (result.containsKey("GD1_lock")) {
    	log.debug "GD1_lock: ${result.GD1_lock}"
        if (result.GD1_lock == "locked")
            sendEvent(name: "largeGD_lock", value: "locked1", isStateChange: true, display: true)
        else
            sendEvent(name: "largeGD_lock", value: "unlocked1", isStateChange: true, display: true)
        log.debug "GD1_lock state= ${state.largeGD_lock}"
    }
    if (result.containsKey("GD2_lock")) {
    	log.debug "GD2_lock: ${result.GD2_lock}"
        if (result.GD2_lock == "locked")
            sendEvent(name: "smallGD_lock", value: "locked2", isStateChange: true, display: true)
        else
            sendEvent(name: "smallGD_lock", value: "unlocked2", isStateChange: true, display: true)
    }
        
    if (result.containsKey("test_msg")) {
    	log.debug "details: ${result.test_msg}"
    }
    //sendEvent(name: "largeGD", value: "open large", isStateChange: true, display: true, descriptionText: "Door is $result.GD_status")
    //sendEvent(name: "smallGD", value: "open small", isStateChange: true, display: true, descriptionText: "Door is $result.GD_status")
    //sendEvent(name: "toggle", value: "open")


}

// handle commands
def poll() {
	log.debug "Executing 'poll'"
    //sendEvent(name: "switch", value: "off")
    sendEvent(name: "cpuStat", value: "off")
    getRPiData()
}

def refresh() {
	//sendEvent(name: "cpuStat", value: "off")
	log.debug "Executing 'refresh'"
    //getRPiData()
    def uri = "/macros/getDetails"
    postAction(uri)
}

def getDetails(){
	log.debug "Details was pressed"
    sendEvent(name: "cpuStat", value: "off")
    log.debug "Executing 'getDetails'"
    def uri = "/macros/getDetails"
    postAction(uri)
}


// Get CPU percentage reading
private getRPiData() {
	def uri = "/macros/getStatus"
    postAction(uri)
}


def open()
{
	log.debug "Opening All Doors"
    sendEvent(name: "allGD", value: "opening")
    def uri = "/macros/open_all"
    postAction(uri)
    
	//log.debug "Opening Door"
    //def dInitStatus
    //def dCurrentStatus = "opening"
    //getDoorStatus() { status -> dInitStatus = status }
	//if (dInitStatus == "opening" || dInitStatus == "open" || dInitStatus == "moving") { return }
	//setDoorState("opening", true)
    //openDoor()
	//while (dCurrentStatus == "opening")
    //{
	//	sleepForDuration(1000) {       
    //    	getDoorStatus(dInitStatus) { status -> dCurrentStatus = status }
    //    }
    //}
	//// Contact Sensor
	//setContactSensorState("open")      	    
	//log.debug "Final Door Status: $dCurrentStatus"
	//setDoorState(dCurrentStatus, true)
}

def open1()
{
	log.debug "Opening Large Garage Door"
    //sendEvent(name: "largeGD", value: "opening1", isStateChange: true, display: true)
    //sendEvent(name: "allGD",   value: "opening", isStateChange: true, display: true)

    def uri = "/macros/open1"
    postAction(uri)
}

def open2()
{
	log.debug "Opening Small Garage Door"
    //sendEvent(name: "smallGD", value: "opening2", isStateChange: true, display: true)
    //sendEvent(name: "allGD",   value: "opening", isStateChange: true, display: true)
    def uri = "/macros/open2"
    postAction(uri)
}


def close()
{
	log.debug "Closing All Doors"
    sendEvent(name: "toggle", value: "closing")
    def uri = "/macros/close_all"
    postAction(uri)
}

def close1()
{
	log.debug "Closing Large Garage Door"
    sendEvent(name: "largeGD", value: "closing1")
    sendEvent(name: "allGD",  value: "closing", isStateChange: true, display: true)
    def uri = "/macros/close1"
    postAction(uri)
}

def close2()
{
	log.debug "Closing Small Garage Door. State=$state.gd2_state"

    sendEvent(name: "smallGD", value: "closing2")
    sendEvent(name: "allGD",  value: "closing", isStateChange: true, display: true)
    def uri = "/macros/close2"
    postAction(uri)
    
    //def dInitStatus
    //def dCurrentStatus = "closing"
	//while (state.gd2_state != "closed")
    //{
    //    log.debug "Waiting for GD to close for 1 sec"
	//	//delayAction(1000)
    //    log.debug state.gd2_state
	//	sleepForDuration(1000)
    //    getRPiData()
    //}
    
    //}
	//// Contact Sensor
	//setContactSensorState("open")      	    
	//log.debug "Final Door Status: $dCurrentStatus"
	//setDoorState(dCurrentStatus, true)
}


def on1()
{
	log.debug "onLight1 Garage Door"
    //sendEvent(name: "light_largeGD", value: "on")
    def uri = "/macros/onLight1"
    postAction(uri)
}

def off1()
{
	log.debug "offLight1 Garage Door"
    //sendEvent(name: "light_largeGD", value: "off")
    def uri = "/macros/offLight1"
    postAction(uri)
}

def on2()
{
	log.debug "onLight2 Garage Door"
    //sendEvent(name: "light_largeGD", value: "on")
    def uri = "/macros/onLight2"
    postAction(uri)
    
    //delayAction(5000)
    def urii = "/macros/sleepfor5sec"
    postAction(urii)
    
    urii = "/macros/sleepfor1sec"
    //def i
    //for (i=0; i<6; i++) {
        postAction(urii)
    //}
}

def off2()
{
	log.debug "offLight2 Garage Door"
    //sendEvent(name: "light_largeGD", value: "off")
    def uri = "/macros/offLight2"
    postAction(uri)
}


def lock1()
{
	log.debug "lock1 Garage Door"
    def uri = "/macros/lock1"
    postAction(uri)
}

def unlock1()
{
	log.debug "unlock1 Garage Door"
    def uri = "/macros/unlock1"
    postAction(uri)
}

def lock2()
{
	log.debug "lock2 Garage Door"
    def uri = "/macros/lock2"
    postAction(uri)
}

def unlock2()
{
	log.debug "unlock2 Garage Door"
    def uri = "/macros/unlock2"
    postAction(uri)
}


// Adding Switch methods on() and Off() so that IFTT can control Doors
def on()
{
    //log.debug "on() activated. Calling open() - All"
    //open()
    log.debug "on() activated. Calling open1() - Main GD"
    open1()
}

def off()
{
    log.debug "off() activated. Calling off() - All"
    close()
}

def private subscribeAction(path, callbackPath="") {
    log.debug "1st line of subscribeAction"
    return
    def address = device.hub.getDataValue("localIP") + ":" + device.hub.getDataValue("localSrvPortTCP")
    def parts = device.deviceNetworkId.split(":")
    def ip = convertHexToIP(parts[0])
    def port = convertHexToInt(parts[1])
    ip = ip + ":" + port
    log.debug "In subscribeAction(${path}, ${callbackbackPath}). address=${address}, parts=${parts}, ip=${ip}"

    def result = new physicalgraph.device.HubAction(
        method: "SUBSCRIBE",
        path: path,
        headers: [
            HOST: ip,
            CALLBACK: "<http://${address}/notify$callbackPath>",
            NT: "upnp:event",
            TIMEOUT: "Second-3600"])
    result
}


// ------------------------------------------------------------------

private postAction(uri){
  setDeviceNetworkId(ip,port)  
  
  def userpass = encodeCredentials(username, password)
  
  def headers = getHeader(userpass)
  
  def hubAction = new physicalgraph.device.HubAction(
    method: "POST",
    path: uri,
    headers: headers
  )//,delayAction(1000), refresh()]
  log.debug("Executing hubAction " + uri + " on " + getHostAddress())
  //log.debug hubAction
  hubAction    
}

// ------------------------------------------------------------------
// Helper methods
// ------------------------------------------------------------------

def sleepForDuration(duration, callback = {})
{
	// I'm sorry!

	def dTotalSleep = 0
	def dStart = new Date().getTime()
    
    while (dTotalSleep <= duration)
    {            
		try { httpGet("http://australia.gov.au/404") { } } catch (e) { }
        
        dTotalSleep = (new Date().getTime() - dStart)
    }

    //log.debug "Slept ${dTotalSleep}ms"

	callback(dTotalSleep)
}


def parseDescriptionAsMap(description) {
	description.split(",").inject([:]) { map, param ->
		def nameAndValue = param.split(":")
		map += [(nameAndValue[0].trim()):nameAndValue[1].trim()]
	}
}

private encodeCredentials(username, password){
	//log.debug "Encoding credentials"
	def userpassascii = "${username}:${password}"
    def userpass = "Basic " + userpassascii.encodeAsBase64().toString()
    //log.debug "ASCII credentials are ${userpassascii}"
    //log.debug "Credentials are ${userpass}"
    return userpass
}

private getHeader(userpass){
	//log.debug "Getting headers"
    def headers = [:]
    headers.put("HOST", getHostAddress())
    headers.put("Authorization", userpass)
    //log.debug "Headers are ${headers}"
    return headers
}

private delayAction(long time) {
	new physicalgraph.device.HubAction("delay $time")
}

private setDeviceNetworkId(ip,port){
  	def iphex = convertIPtoHex(ip)
  	def porthex = convertPortToHex(port)
  	device.deviceNetworkId = "$iphex:$porthex"
  	log.debug "Device Network Id set to ${iphex}:${porthex}"
}

private getHostAddress() {
	return "${ip}:${port}"
}

private String convertIPtoHex(ipAddress) { 
    String hex = ipAddress.tokenize( '.' ).collect {  String.format( '%02x', it.toInteger() ) }.join()
    return hex

}

private String convertPortToHex(port) {
	String hexport = port.toString().format( '%04x', port.toInteger() )
    return hexport
}
