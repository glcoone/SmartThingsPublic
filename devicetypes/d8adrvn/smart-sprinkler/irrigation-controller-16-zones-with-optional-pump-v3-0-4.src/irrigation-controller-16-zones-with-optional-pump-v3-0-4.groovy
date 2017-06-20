/**
 *  Irrigation Controller 16 Zones w/Optional Pump
 *
 *
 *  This SmartThings Device Type Code Works With Arduino Irrigation Controller available at https://github.com/d8adrvn/smart_sprinkler
 *  
 *
 *	Creates connected irrigation controller
 *  Author: Stan Dotson (stan@dotson.info) and Matthew Nichols (matt@nichols.name)
 *  Copyright 2015 Stan Dotson and Matthew Nichols
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 */
 
 // for the UI
preferences {
	input "ip", "text", title: "Arduino IP Address", description: "ip", required: true, displayDuringSetup: true
	input "port", "text", title: "Arduino Port", description: "port", required: true, displayDuringSetup: true
	input "mac", "text", title: "Arduino MAC Addr", description: "mac", required: true, displayDuringSetup: true
    input("PercentTimer", "text", title: "Percent Run (50-150)", description: "Percent Run Time", required: false, defaultValue: "100")
    input("testTimer", "text", title: "Test All Zones Time (1-9)", description: "Test All Time", required: false, defaultValue: "1")
    input("oneTimer", "text", title: "Zone One", description: "Zone One Time", required: false, defaultValue: "1")
    input("twoTimer", "text", title: "Zone Two", description: "Zone Two Time", required: false, defaultValue: "1")
    input("threeTimer", "text", title: "Zone Three", description: "Zone Three Time", required: false, defaultValue: "1")
    input("fourTimer", "text", title: "Zone Four", description: "Zone Four Time", required: false, defaultValue: "1")
    input("fiveTimer", "text", title: "Zone Five", description: "Zone Five Time", required: false, defaultValue: "1")
    input("sixTimer", "text", title: "Zone Six", description: "Zone Six Time", required: false, defaultValue: "1")
    input("sevenTimer", "text", title: "Zone Seven", description: "Zone Seven Time", required: false, defaultValue: "1")
    input("eightTimer", "text", title: "Zone Eight", description: "Zone Eight Time", required: false, defaultValue: "1")
    input("nineTimer", "text", title: "Zone Nine", description: "Zone Nine Time", required: false, defaultValue: "1")
    input("tenTimer", "text", title: "Zone Ten", description: "Zone Ten Time", required: false, defaultValue: "1")
    input("elevenTimer", "text", title: "Zone Eleven", description: "Zone Eleven Time", required: false, defaultValue: "1")
    input("twelveTimer", "text", title: "Zone Twelve", description: "Zone Twelve Time", required: false, defaultValue: "1")
    input("thirteenTimer", "text", title: "Zone Thirteen", description: "Zone Thirteen Time", required: false, defaultValue: "1")
    input("fourteenTimer", "text", title: "Zone Fourteen", description: "Zone Fourteen Time", required: false, defaultValue: "1")
    input("fifteenTimer", "text", title: "Zone Fifteen", description: "Zone Fifteen Time", required: false, defaultValue: "1")
    input("sixteenTimer", "text", title: "Zone Sixteen", description: "Zone Sixteen Time", required: false, defaultValue: "1")    
}

metadata {
    definition (name: "Irrigation Controller 16 Zones with Optional Pump v3.0.4", version: "3.0.4", author: "stan@dotson.info", namespace: "d8adrvn/smart_sprinkler") {
        
        
        capability "Switch"
        command "OnWithZoneTimes"
        command "RelayOn1"
        command "RelayOn1For"
        command "RelayOff1"
        command "RelayOn2"
        command "RelayOn2For"
        command "RelayOff2"
        command "RelayOn3"
        command "RelayOn3For"
        command "RelayOff3"
        command "RelayOn4"
        command "RelayOn4For"
        command "RelayOff4"
        command "RelayOn5"
        command "RelayOn5For"
        command "RelayOff5"
        command "RelayOn6"
        command "RelayOn6For"
        command "RelayOff6"
        command "RelayOn7"
        command "RelayOn7For"
        command "RelayOff7"
        command "RelayOn8"
        command "RelayOn8For"
        command "RelayOff8"
        command "RelayOn9"
        command "RelayOn9For"
        command "RelayOff9"
        command "RelayOn10"
        command "RelayOn10For"
        command "RelayOff10"
        command "RelayOn11"
        command "RelayOn11For"
        command "RelayOff11"
        command "RelayOn12"
        command "RelayOn12For"
        command "RelayOff12"
        command "RelayOn13"
        command "RelayOn13For"
        command "RelayOff13"
        command "RelayOn14"
        command "RelayOn14For"
        command "RelayOff14"
        command "RelayOn15"
        command "RelayOn15For"
        command "RelayOff15"
        command "RelayOn16"
        command "RelayOn16For"
        command "RelayOff16"
		command "rainDelayed"
        command "update" 
        command "enablePump"
        command "disablePump"
        command "onPump"
        command "offPump"
        command "noEffect"
        command "skip"
        command "expedite"
        command "onHold"
        command "warning"
        command "testAllZones"
        attribute "effect", "string"
    }

    simulator {
          }

    tiles {
        standardTile("allZonesTile", "device.switch", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "off", label: 'Start', action: "switch.on", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "starting"
            state "on", label: 'Running', action: "switch.off", icon: "st.Health & Wellness.health7", backgroundColor: "#53a7c0", nextState: "stopping"
            state "starting", label: 'Starting...', action: "switch.off", icon: "st.Health & Wellness.health7", backgroundColor: "#53a7c0"
            state "stopping", label: 'Stopping...', action: "switch.off", icon: "st.Health & Wellness.health7", backgroundColor: "#53a7c0"
            state "rainDelayed", label: 'Rain Delay', action: "switch.off", icon: "st.Weather.weather10", backgroundColor: "#fff000", nextState: "off"
        	state "warning", label: 'Issue',  icon: "st.Health & Wellness.health7", backgroundColor: "#ff000f", nextState: "off"

        }
        standardTile("zoneOneTile", "device.zoneOne", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "o1", label: 'One', action: "RelayOn1", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff",nextState: "sending1"
            state "sending1", label: 'sending', action: "RelayOff1", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "q1", label: 'One', action: "RelayOff1",icon: "st.Outdoor.outdoor12", backgroundColor: "#c0a353", nextState: "sending1"
            state "r1", label: 'One', action: "RelayOff1",icon: "st.Outdoor.outdoor12", backgroundColor: "#53a7c0", nextState: "sending1"
        }
        standardTile("zoneTwoTile", "device.zoneTwo", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "o2", label: 'Two', action: "RelayOn2", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "sending2"
            state "sending2", label: 'sending', action: "RelayOff2", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "q2", label: 'Two', action: "RelayOff2",icon: "st.Outdoor.outdoor12", backgroundColor: "#c0a353", nextState: "sending2"
            state "r2", label: 'Two', action: "RelayOff2",icon: "st.Outdoor.outdoor12", backgroundColor: "#53a7c0", nextState: "sending2"
        }
        standardTile("zoneThreeTile", "device.zoneThree", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "o3", label: 'Three', action: "RelayOn3", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "sending3"
            state "sending3", label: 'sending', action: "RelayOff3", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "q3", label: 'Three', action: "RelayOff3",icon: "st.Outdoor.outdoor12", backgroundColor: "#c0a353", nextState: "sending3"
            state "r3", label: 'Three', action: "RelayOff3",icon: "st.Outdoor.outdoor12", backgroundColor: "#53a7c0", nextState: "sending3"
        }
        standardTile("zoneFourTile", "device.zoneFour", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "o4", label: 'Four', action: "RelayOn4", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "sending4"
            state "sending4", label: 'sending', action: "RelayOff4", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "q4", label: 'Four', action: "RelayOff4",icon: "st.Outdoor.outdoor12", backgroundColor: "#c0a353", nextState: "sending4"
            state "r4", label: 'Four', action: "RelayOff4",icon: "st.Outdoor.outdoor12", backgroundColor: "#53a7c0", nextState: "sending4"
        }
        standardTile("zoneFiveTile", "device.zoneFive", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "o5", label: 'Five', action: "RelayOn5", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "sending5"
            state "sending5", label: 'sending', action: "RelayOff5", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "q5", label: 'Five', action: "RelayOff5",icon: "st.Outdoor.outdoor12", backgroundColor: "#c0a353", nextState: "sending5"
            state "r5", label: 'Five', action: "RelayOff5",icon: "st.Outdoor.outdoor12", backgroundColor: "#53a7c0", nextState: "sending5"
        }
        standardTile("zoneSixTile", "device.zoneSix", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "o6", label: 'Six', action: "RelayOn6", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "sending6"
            state "sending6", label: 'sending', action: "RelayOff6", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "q6", label: 'Six', action: "RelayOff6",icon: "st.Outdoor.outdoor12", backgroundColor: "#c0a353", nextState: "sending6"
            state "r6", label: 'Six', action: "RelayOff6",icon: "st.Outdoor.outdoor12", backgroundColor: "#53a7c0", nextState: "sending6"
        }
        standardTile("zoneSevenTile", "device.zoneSeven", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "o7", label: 'Seven', action: "RelayOn7", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "sending7"
            state "sending7", label: 'sending', action: "RelayOff7", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "q7", label: 'Seven', action: "RelayOff7",icon: "st.Outdoor.outdoor12", backgroundColor: "#c0a353", nextState: "sending7"
            state "r7", label: 'Seven', action: "RelayOff7",icon: "st.Outdoor.outdoor12", backgroundColor: "#53a7c0", nextState: "sending7"
        }
        standardTile("zoneEightTile", "device.zoneEight", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "o8", label: 'Eight', action: "RelayOn8", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "sending8"
            state "sending8", label: 'sending', action: "RelayOff8", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "q8", label: 'Eight', action: "RelayOff8",icon: "st.Outdoor.outdoor12", backgroundColor: "#c0a353", nextState: "sending8"
            state "r8", label: 'Eight', action: "RelayOff8",icon: "st.Outdoor.outdoor12", backgroundColor: "#53a7c0", nextState: "sending8"
        }
        standardTile("zoneNineTile", "device.zoneNine", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "o9", label: 'Nine', action: "RelayOn9", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "sending9"
            state "sending9", label: 'sending', action: "RelayOff9", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "q9", label: 'Nine', action: "RelayOff9",icon: "st.Outdoor.outdoor12", backgroundColor: "#c0a353", nextState: "sending9"
            state "r9", label: 'Nine', action: "RelayOff9",icon: "st.Outdoor.outdoor12", backgroundColor: "#53a7c0", nextState: "sending9"
        }       
        standardTile("zoneTenTile", "device.zoneTen", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "o10", label: 'Ten', action: "RelayOn10", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "sending10"
            state "sending10", label: 'sending', action: "RelayOff10", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "q10", label: 'Ten', action: "RelayOff10",icon: "st.Outdoor.outdoor12", backgroundColor: "#c0a353", nextState: "sending10"
            state "r10", label: 'Ten', action: "RelayOff10",icon: "st.Outdoor.outdoor12", backgroundColor: "#53a7c0", nextState: "sending10"
        } 
        standardTile("zoneElevenTile", "device.zoneEleven", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "o11", label: 'Eleven', action: "RelayOn11", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "sending11"
            state "sending11", label: 'sending', action: "RelayOff11", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "q11", label: 'Eleven', action: "RelayOff11",icon: "st.Outdoor.outdoor12", backgroundColor: "#c0a353", nextState: "sending11"
            state "r11", label: 'Eleven', action: "RelayOff11",icon: "st.Outdoor.outdoor12", backgroundColor: "#53a7c0", nextState: "sending11"
        } 
        standardTile("zoneTwelveTile", "device.zoneTwelve", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "o12", label: 'Twelve', action: "RelayOn12", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "sending12"
            state "sending12", label: 'sending', action: "RelayOff12", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "q12", label: 'Twelve', action: "RelayOff12",icon: "st.Outdoor.outdoor12", backgroundColor: "#c0a353", nextState: "sending12"
            state "r12", label: 'Twelve', action: "RelayOff12",icon: "st.Outdoor.outdoor12", backgroundColor: "#53a7c0", nextState: "sending12"
        } 
        standardTile("zoneThirteenTile", "device.zoneThirteen", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "o13", label: 'Thirteen', action: "RelayOn13", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "sending13"
            state "sending13", label: 'sending', action: "RelayOff13", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "q13", label: 'Thirteen', action: "RelayOff13",icon: "st.Outdoor.outdoor12", backgroundColor: "#c0a353", nextState: "sending13"
            state "r13", label: 'Thirteen', action: "RelayOff13",icon: "st.Outdoor.outdoor12", backgroundColor: "#53a7c0", nextState: "sending13"
        } 
        standardTile("zoneFourteenTile", "device.zoneFourteen", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "o14", label: 'Fourteen', action: "RelayOn14", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "sending14"
            state "sending14", label: 'sending', action: "RelayOff14", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "q14", label: 'Fourteen', action: "RelayOff14",icon: "st.Outdoor.outdoor12", backgroundColor: "#c0a353", nextState: "sending14"
            state "r14", label: 'Fourteen', action: "RelayOff14",icon: "st.Outdoor.outdoor12", backgroundColor: "#53a7c0", nextState: "sending14"
        }

        standardTile("zoneFifteenTile", "device.zoneFifteen", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "o15", label: 'Fifteen', action: "RelayOn15", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "sending15"
            state "sending15", label: 'sending', action: "RelayOff15", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "q15", label: 'Fifteen', action: "RelayOff15",icon: "st.Outdoor.outdoor12", backgroundColor: "#c0a353", nextState: "sending15"
            state "r15", label: 'Fifteen', action: "RelayOf15",icon: "st.Outdoor.outdoor12", backgroundColor: "#53a7c0", nextState: "sending15"
        } 
        standardTile("zoneSixteenTile", "device.zoneSixteen", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "o16", label: 'Sixteen', action: "RelayOn16", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "sending16"
            state "sending16", label: 'sending', action: "RelayOff16", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "q16", label: 'Sixteen', action: "RelayOff16",icon: "st.Outdoor.outdoor12", backgroundColor: "#c0a353", nextState: "sending16"
            state "r16", label: 'Sixteen', action: "RelayOff16",icon: "st.Outdoor.outdoor12", backgroundColor: "#53a7c0", nextState: "sending16"
            state "havePump", label: 'Sixteen', action: "disablePump", icon: "st.custom.buttons.subtract-icon", backgroundColor: "#ffffff"
        } 
      
        standardTile("pumpTile", "device.pump", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "noPump", label: 'Pump', action: "enablePump", icon: "st.custom.buttons.subtract-icon", backgroundColor: "#ffffff",nextState: "enablingPump"
            state "offPump", label: 'Pump', action: "onPump", icon: "st.valves.water.closed", backgroundColor: "#ffffff", nextState: "sendingPump"
            state "enablingPump", label: 'sending', action: "disablePump", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "disablingPump", label: 'sending', action: "disablePump", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
            state "onPump", label: 'Pump', action: "offPump",icon: "st.valves.water.open", backgroundColor: "#53a7c0", nextState: "sendingPump"
            state "sendingPump", label: 'sending', action: "offPump", icon: "st.Health & Wellness.health7", backgroundColor: "#cccccc"
        }
        	
     	standardTile("scheduleEffect", "device.effect", width: 1, height: 1) {
            state("noEffect", label: "Normal", action: "skip", icon: "st.Office.office7", backgroundColor: "#ffffff")
            state("skip", label: "Skip 1X", action: "expedite", icon: "st.Office.office7", backgroundColor: "#c0a353")
            state("expedite", label: "Expedite", action: "onHold", icon: "st.Office.office7", backgroundColor: "#53a7c0")
            state("onHold", label: "Pause", action: "noEffect", icon: "st.Office.office7", backgroundColor: "#bc2323")
        }
        standardTile("runTestTile", "device.runTest", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
            state "offRunTest", label: 'Start Test',   action: "testAllZones", icon: "st.Outdoor.outdoor12", backgroundColor: "#ffffff", nextState: "startingTest"
            state "startingTest", label: 'Starting...', action: "switch.off", icon: "st.Health & Wellness.health7", backgroundColor: "#53a7c0"
            state "onRunTest",  label: 'Running', action: "switch.off", icon: "st.Health & Wellness.health7", backgroundColor: "#53a7c0", nextState: "stoppingTest"
            state "stoppingTest", label: 'Stopping...', action: "switch.off", icon: "st.Health & Wellness.health7", backgroundColor: "#53a7c0"
        }
        
        standardTile("refreshTile", "device.refresh", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true, decoration: "flat") {
            state "ok", label: "", action: "update", icon: "st.secondary.refresh", backgroundColor: "#ffffff"
        }
        main "allZonesTile"
        details(["zoneOneTile","zoneTwoTile","zoneThreeTile","zoneFourTile","zoneFiveTile","zoneSixTile","zoneSevenTile","zoneEightTile", "zoneNineTile", "zoneTenTile", "zoneElevenTile", "zoneTwelveTile", "zoneThirteenTile", "zoneFourteenTile", "zoneFifteenTile", "zoneSixteenTile", "zoneSeventeenTile", "zoneEighteenTile", "zoneNineteenTile", "zoneTwentyTile", "zoneTwentyoneTile", "zoneTwentytwoTile", "zoneTwentythreeTile", "zoneTwentyfourTile", "scheduleEffect", "runTestTile", "pumpTile", "refreshTile"])
    }
}

// parse events into attributes to create events
def parse(String description) {
	log.debug "Parsing '${description}'"  /////GC commented out originally
	def msg = parseLanMessage(description)
	def headerString = msg.header

	if (!headerString) {
		log.debug "headerstring was null for some reason :("    /////GC commented out originally
    }

    def value = msg.body
    log.debug "Parsing '${value}'"
	
	if (value == 'null'  || value == "" || value?.contains("ping") || value?.trim()?.length() == 0 ) {  
        // Do nothing
        return
    }
    if (value != "havePump" && value != "noPump" && value != "pumpRemoved") {
		String delims = ","
		String[] tokens = value?.split(delims)
        for (int x=0; x<tokens?.length; x++) {
           
           def displayed = tokens[x] && tokens[x] != "ping"  //evaluates whether to display message
           def name = tokens[x] in ["r1", "q1", "o1"] ? "zoneOne"
            : tokens[x] in ["r2", "q2", "o2"] ? "zoneTwo"
            : tokens[x] in ["r3", "q3", "o3"] ? "zoneThree"
            : tokens[x] in ["r4", "q4", "o4"] ? "zoneFour"
            : tokens[x] in ["r5", "q5", "o5"] ? "zoneFive"
            : tokens[x] in ["r6", "q6", "o6"] ? "zoneSix"
            : tokens[x] in ["r7", "q7", "o7"] ? "zoneSeven"
            : tokens[x] in ["r8", "q8", "o8"] ? "zoneEight"
            : tokens[x] in ["r9", "q9", "o9"] ? "zoneNine"
            : tokens[x] in ["r10", "q10", "o10"] ? "zoneTen"
            : tokens[x] in ["r11", "q11", "o11"] ? "zoneEleven"
            : tokens[x] in ["r12", "q12", "o12"] ? "zoneTwelve"
            : tokens[x] in ["r13", "q13", "o13"] ? "zoneThirteen"
            : tokens[x] in ["r14", "q14", "o14"] ? "zoneFourteen"
            : tokens[x] in ["r15", "q15", "o15"] ? "zoneFifteen"
            : tokens[x] in ["r16", "q16", "o16"] ? "zoneSixteen"
            : tokens[x] in ["onPump", "offPump"] ? "pump"
            : tokens[x] in ["ok"] ? "refresh" : null
			
            //manage and display events
            def currentVal = device?.currentValue(name)
            def isDisplayed = true
            def isPhysical = true
            
            //manage which events are displayed in log
			if (tokens[x]?.contains("q")) {
				isDisplayed = false
                isPhysical = false
            } 
            
            if (tokens[x]?.contains("o") && currentVal?.contains("q")) {
				isDisplayed = false
            	isPhysical = false
            }
			
            //send an event if there is a state change
			if (currentVal != tokens[x]) {
				def result = createEvent(name: name, value: tokens[x], displayed: isDisplayed, isStateChange: true, isPhysical: isPhysical)
            	log.debug "Parse returned ${result?.descriptionText}"
            	sendEvent(result)
            }
        }
    }
    if (value == "pumpAdded") {
    	//send an event if there is a state change
        if (device.currentValue("zoneSixteen") != "havePump" && device.currentValue("pump") != "offPump") {
    		sendEvent (name:"zoneSixteen", value:"havePump", displayed: true, isStateChange: true, isPhysical: true)
        	sendEvent (name:"pump", value:"offPump", displayed: true, isStateChange: true, isPhysical: true)
    	}
    }
    if (value == "pumpRemoved") {
    	//send event if there is a state change
        if (device.currentValue("pump") != "noPump") {
    		sendEvent (name:"pump", value:"noPump", displayed: true, isStateChange: true, isPhysical: true)
    	}
    }
    if(anyZoneOn()) {
        //manages the state of the overall system.  Overall state is "on" if any zone is on
        //set displayed to false; does not need to be logged in mobile app
        if(device.currentValue("switch") != "on") {
        	sendEvent (name: "switch", value: "on", descriptionText: "Irrigation System Is On", displayed: false)  //displayed default is false to minimize logging
            }
    } else if (device.currentValue("switch") != "rainDelayed") {
        if(device.currentValue("switch") != "off") {
        	sendEvent (name: "switch", value: "off", descriptionText: "Irrigation System Is Off", displayed: false)  //displayed default is false to minimize logging
       	}
    }
}

private getHostAddress() {
    def ip = settings.ip
    def port = settings.port

	log.debug "Using ip: ${ip} and port: ${port} for device: ${device.id}"
    return ip + ":" + port
}


def sendEthernet(message) {
	log.debug "Executing 'sendEthernet' ${message}"
	new physicalgraph.device.HubAction(
    	method: "POST",
    	path: "/${message}?",
    	headers: [ HOST: "${getHostAddress()}" ]
	)
}

def anyZoneOn() {
    if(device.currentValue("zoneOne") in ["r1","q1"]) return true;
    if(device.currentValue("zoneTwo") in ["r2","q2"]) return true;
    if(device.currentValue("zoneThree") in ["r3","q3"]) return true;
    if(device.currentValue("zoneFour") in ["r4","q4"]) return true;
    if(device.currentValue("zoneFive") in ["r5","q5"]) return true;
    if(device.currentValue("zoneSix") in ["r6","q6"]) return true;
    if(device.currentValue("zoneSeven") in ["r7","q7"]) return true;
    if(device.currentValue("zoneEight") in ["r8","q8"]) return true;
    if(device.currentValue("zoneNine") in ["r9","q9"]) return true;
    if(device.currentValue("zoneTen") in ["r10","q10"]) return true;
    if(device.currentValue("zoneEleven") in ["r11","q11"]) return true;
    if(device.currentValue("zoneTwelve") in ["r12","q12"]) return true;
    if(device.currentValue("zoneThirteen") in ["r13","q13"]) return true;
    if(device.currentValue("zoneFourteen") in ["r14","q14"]) return true;
    if(device.currentValue("zoneFifteen") in ["r15","q15"]) return true;
    if(device.currentValue("zoneSixteen") in ["r16","q16"]) return true;
    
    false;
}

// handle commands
def RelayOn1() {
    log.info "Executing 'on,1'"
    sendEthernet("on,1,${oneTimer}")
}

def RelayOn1For(value) {
    value = checkTime(value)
    log.info "Executing 'on,1,$value'"
    sendEthernet("on,1,${value}")
}

def RelayOff1() {
    log.info "Executing 'off,1'"
    sendEthernet("off,1")
}

def RelayOn2() {
    log.info "Executing 'on,2'"
    sendEthernet("on,2,${twoTimer}")
}

def RelayOn2For(value) {
    value = checkTime(value)
    log.info "Executing 'on,2,$value'"
    sendEthernet("on,2,${value}")
}

def RelayOff2() {
    log.info "Executing 'off,2'"
    sendEthernet("off,2")
}

def RelayOn3() {
    log.info "Executing 'on,3'"
    sendEthernet("on,3,${threeTimer}")
}

def RelayOn3For(value) {
    value = checkTime(value)
    log.info "Executing 'on,3,$value'"
    sendEthernet("on,3,${value}")
}

def RelayOff3() {
    log.info "Executing 'off,3'"
    sendEthernet("off,3")
}

def RelayOn4() {
    log.info "Executing 'on,4'"
    sendEthernet("on,4,${fourTimer}")
}

def RelayOn4For(value) {
    value = checkTime(value)
    log.info "Executing 'on,4,$value'"
    sendEthernet("on,4,${value}")
}

def RelayOff4() {
    log.info "Executing 'off,4'"
    sendEthernet("off,4")
}

def RelayOn5() {
    log.info "Executing 'on,5'"
    sendEthernet("on,5,${fiveTimer}")
}

def RelayOn5For(value) {
    value = checkTime(value)
    log.info "Executing 'on,5,$value'"
    sendEthernet("on,5,${value}")
}

def RelayOff5() {
    log.info "Executing 'off,5'"
    sendEthernet("off,5")
}

def RelayOn6() {
    log.info "Executing 'on,6'"
    sendEthernet("on,6,${sixTimer}")
}

def RelayOn6For(value) {
    value = checkTime(value)
    log.info "Executing 'on,6,$value'"
    sendEthernet("on,6,${value}")
}

def RelayOff6() {
    log.info "Executing 'off,6'"
    sendEthernet("off,6")
}

def RelayOn7() {
    log.info "Executing 'on,7'"
    sendEthernet("on,7,${sevenTimer}")
}

def RelayOn7For(value) {
    value = checkTime(value)
    log.info "Executing 'on,7,$value'"
    sendEthernet("on,7,${value}")
}

def RelayOff7() {
    log.info "Executing 'off,7'"
    sendEthernet("off,7")
}

def RelayOn8() {
    log.info "Executing 'on,8'"
    sendEthernet("on,8,${eightTimer}")
}

def RelayOn8For(value) {
    value = checkTime(value)
    log.info "Executing 'on,8,$value'"
    sendEthernet("on,8,${value}")
}

def RelayOff8() {
    log.info "Executing 'off,8'"
    sendEthernet("off,8")
}

// GC 4/23/17:  Many of the sendEthernet's below were sending wrong timer, so fixed
def RelayOn9() {
    log.info "Executing 'on,9'"
    sendEthernet("on,9,${nineTimer}")
}

def RelayOn9For(value) {
    value = checkTime(value)
    log.info "Executing 'on,9,$value'"
    sendEthernet("on,9,${value}")
}

def RelayOff9() {
    log.info "Executing 'off,9'"
    sendEthernet("off,9")
}

def RelayOn10() {
    log.info "Executing 'on,10'"
    sendEthernet("on,10,${tenTimer}")
}

def RelayOn10For(value) {
    value = checkTime(value)
    log.info "Executing 'on,10,$value'"
    sendEthernet("on,10,${value}")
}

def RelayOff10() {
    log.info "Executing 'off,10'"
    sendEthernet("off,10")
}

def RelayOn11() {
    log.info "Executing 'on,11'"
    sendEthernet("on,11,${elevenTimer}")
}

def RelayOn11For(value) {
    value = checkTime(value)
    log.info "Executing 'on,11,$value'"
    sendEthernet("on,11,${value}")
}

def RelayOff11() {
    log.info "Executing 'off,11'"
    sendEthernet("off,11")
}

def RelayOn12() {
    log.info "Executing 'on,12'"
    sendEthernet("on,12,${twelveTimer}")
}

def RelayOn12For(value) {
    value = checkTime(value)
    log.info "Executing 'on,12,$value'"
    sendEthernet("on,12,${value}")
}

def RelayOff12() {
    log.info "Executing 'off,12'"
    sendEthernet("off,12")
}

def RelayOn13() {
    log.info "Executing 'on,13'"
    sendEthernet("on,13,${thirteenTimer}")
}

def RelayOn13For(value) {
    value = checkTime(value)
    log.info "Executing 'on,13,$value'"
    sendEthernet("on,13,${value}")
}

def RelayOff13() {
    log.info "Executing 'off,13'"
    sendEthernet("off,13")
}

def RelayOn14() {
    log.info "Executing 'on,14'"
    sendEthernet("on,14,${fourteenTimer}")
}

def RelayOn14For(value) {
    value = checkTime(value)
    log.info "Executing 'on,14,$value'"
    sendEthernet("on,14,${value}")
}

def RelayOff14() {
    log.info "Executing 'off,14'"
    sendEthernet("off,14")
}

def RelayOn15() {
    log.info "Executing 'on,15'"
    sendEthernet("on,15,${fifteenTimer}")
}

def RelayOn15For(value) {
    value = checkTime(value)
    log.info "Executing 'on,15,$value'"
    sendEthernet("on,15,${value}")
}

def RelayOff15() {
    log.info "Executing 'off,15'"
    sendEthernet("off,15")
}

def RelayOn16() {
    log.info "Executing 'on,16'"
    sendEthernet("on,16,${sixteenTimer}")
}

def RelayOn16For(value) {
    value = checkTime(value)
    log.info "Executing 'on,16,$value'"
    sendEthernet("on,16,${value}")
}

def RelayOff16() {
    log.info "Executing 'off,16'"
    sendEthernet("off,16")
}



def on() {
    log.info "Executing 'allOn'"
    sendEthernet("allOn,${oneTimer ?: 0},${twoTimer ?: 0},${threeTimer ?: 0},${fourTimer ?: 0},${fiveTimer ?: 0},${sixTimer ?: 0},${sevenTimer ?: 0},${eightTimer ?: 0},${nineTimer ?: 0},${tenTimer ?: 0},${elevenTimer ?: 0},${twelveTimer ?: 0},${thirteenTimer ?: 0},${fourteenTimer ?: 0},${fifteenTimer ?: 0},${sixteenTimer ?: 0}")
}

def OnWithZoneTimes(value, test2) {
    log.info "Executing 'allOn' with zone times [$value], [$test2]"
	
    def evt = createEvent(name: "switch", value: "starting", displayed: true)
    sendEvent(evt)
    
    def zoneTimes = [:]
    for(z in value.split(",")) {
    	def parts = z.split(":")
        zoneTimes[parts[0].toInteger()] = parts[1]
        log.info("Zone ${parts[0].toInteger()} on for ${parts[1]} minutes")
    }
    sendEthernet("allOn,${checkTime(zoneTimes[1]) ?: 0},${checkTime(zoneTimes[2]) ?: 0},${checkTime(zoneTimes[3]) ?: 0},${checkTime(zoneTimes[4]) ?: 0},${checkTime(zoneTimes[5]) ?: 0},${checkTime(zoneTimes[6]) ?: 0},${checkTime(zoneTimes[7]) ?: 0},${checkTime(zoneTimes[8]) ?: 0},${checkTime(zoneTimes[9]) ?: 0},${checkTime(zoneTimes[10]) ?: 0},${checkTime(zoneTimes[11]) ?: 0},${checkTime(zoneTimes[12]) ?: 0},${checkTime(zoneTimes[13]) ?: 0},${checkTime(zoneTimes[14]) ?: 0},${checkTime(zoneTimes[15]) ?: 0},${checkTime(zoneTimes[16]) ?: 0}")
}

def testAllZones() {
	def testTimerStr = settings.testTimer
    def int testTimer
	log.info "Executing 'testAllZones'"
    //def evt = createEvent(name: "switch", value: "starting", displayed: true)
    //sendEvent(evt)
    if (testTimerStr.isNumber()) {
    	testTimer = testTimerStr.toInteger()
    }
    else {
    	testTimer = 1
    }
    if (testTimer < 1 || testTimer > 10) {
    	log.info("Test Timer ${testTimer} out of Range 1-10 minutes.  Reseting to 1.")
        testTimer = 1
    }
    def zoneTimes = ""
    for(int z = 1; z <= 16; z++) {
        zoneTimes += ",${testTimer}"
    }
    log.info("Zone ${testTimer} on for {${zoneTimes}} minutes")
    //switches.OnWithZoneTimes(zoneTimes.join(","))
        
    def evt2 = createEvent(name: "runTestTile", value: "onRunTest", displayed: true)
    sendEvent(evt2)
    
    sendEthernet("allOn${zoneTimes}");
}

def off() {
    log.info "Executing 'allOff'"
    sendEthernet("allOff")
}

def checkTime(t) {
	def time = (t ?: 0).toInteger()
    time > 60 ? 60 : time
}

def update() {
    log.info "Execting refresh"
    sendEthernet("update")
}

def rainDelayed() {
    log.info "rain delayed"
    if(device.currentValue("switch") != "on") {
        sendEvent (name:"switch", value:"rainDelayed", displayed: true)
    }
    else {
    	sendEvent (name: "switch", value: "rainDelayed, but system running?", descriptionText: "Irrigation System Is On", displayed: true)
    }
}

def warning() {
    log.info "Warning: Programmed Irrigation Did Not Start"
    if(device.currentValue("switch") != "on") {
        sendEvent (name:"switch", value:"warning", displayed: true)
        log.debug "Set switch to warning-Issue"
    }
}

def enablePump() {
	log.info "Pump Enabled"
        sendEthernet("pump,3")  //pump is queued and ready to turn on when zone is activated
}
def disablePump() {
	log.info "Pump Disabled"
        sendEthernet("pump,0")  //remove pump from system, reactivate Zone8
}
def onPump() {
	log.info "Pump On"
    sendEthernet("pump,2")
    }
def offPump() {
	log.info "Pump Enabled"
        sendEthernet("pump,1")  //pump returned to queue state to turn on when zone turns on
        }
        
def push() {
	log.info "advance to next zone"
    sendEthernet("advance")  //turn off currently running zone and advance to next
    }
   
// four commands that over-ride the SmartApp

// skip one scheduled watering
def	skip() {
    def evt = createEvent(name: "effect", value: "skip", displayed: true)
    log.info("Sending: $evt")
    sendEvent(evt)
}
// over-ride rain delay and water even if it rains
def	expedite() {
    def evt = createEvent(name: "effect", value: "expedite", displayed: true)
    log.info("Sending: $evt")
    sendEvent(evt)
}

// schedule operates normally
def	noEffect() {
    def evt = createEvent(name: "effect", value: "noEffect", displayed: true)
    log.info("Sending: $evt")
    sendEvent(evt)
}

// turn schedule off indefinitely
def	onHold() {
    def evt = createEvent(name: "effect", value: "onHold", displayed: true)
    log.info("Sending: $evt")
    sendEvent(evt)
}

def updateDeviceNetworkID() {
	log.debug "Executing 'updateDeviceNetworkID'"
    if(device.deviceNetworkId!=mac) {
    	log.debug "setting deviceNetworkID = ${mac}"
        device.setDeviceNetworkId("${mac}")
	}
}

def updated() {
	if (!state.updatedLastRanAt || now() >= state.updatedLastRanAt + 5000) {
		state.updatedLastRanAt = now()
		log.debug("Executing 'updated() ' $numButtons")
    	runIn(3, updateDeviceNetworkID)
        sendEvent(name: "numberOfButtons", value: numButtons)
	}
	else {
		log.trace "updated(): Ran within last 5 seconds so aborting."
	}
}