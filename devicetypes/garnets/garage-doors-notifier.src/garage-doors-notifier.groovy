/**
 *  Garage Door Left Open - A Virtual Switch and Contact Sensor to notify when GD is Left Open.
 *  Used with SmartRules and IFTT to send notification when Garage Door has been Left Open for x minutes.
 *  My GD is used alot, so I only want IFTT to notify if it has been left open and not every occurence.
 *  Limitation:  This virtual device should really just be a Contact Sensor, but the cancelable/interruptable
 *               delayed action feature in SmartRules only works on a Switch.  Switch = Contact Sensor.
 *               (I also use SmartTiles, only show the Contact Sensor to be consistent door statuses.)
 *  Two SmartRules:  
 *  - Use Condition that GD is "open" with a 5 min Delayed (cancellable) Action of turning on this swith.
 *  - Use Event that GD has "closed" AND Condition that this switch is  "on"
 *    Really, one SmartRule w/ a False rule Action would suffice, but it would trigger every time the door closed.
 *    The second rule only turns this off if it was on which allows for an _optional_ 2nd IFTT notification
 *    the the GD has been finally closed.
 *  (Create a Device instance of this Event Handler and call it something "GD Notifier" or "GD 5 Min Warning") 
 *  IFTT:
 *  -  Trigger: "Switched On",  Device: Choose Device, Notifier Action: '{{SwitchName}}' switched ON at {{SwitchedOnAt}}.
 *  - [Trigger: "Switched Off", Device: Choose Device, Notifier Action: '{{SwitchName}}' switched OFF at {{SwitchedOffAt}}.]
 *
 *  Copyright 2016 Garnet Coone
 *
 */
metadata {
	definition (name: "Garage_Doors Notifier", namespace: "Garnets", author: "Garnet Coone") {
		capability "Switch"
		capability "Contact Sensor"
	}

	simulator {
		// TODO: define status and reply messages here
	}
	tiles {
		//standardTile("switch", "device.switch", width: 2, height: 2) {
		//	state "on", label: '${name}', icon: "st.contact.contact.open", backgroundColor: "#ffa81e"
		//	state "off", label: '${name}', icon: "st.contact.contact.closed", backgroundColor: "#79b821"
		//}
		standardTile("contact", "device.contact", width: 2, height: 2, inactiveLabel: false) {
			state "closed", label: "OK",          icon: "st.security.alarm.clear", backgroundColor: "#79b821"
			state "open",   label: "Still Open!", icon: "st.security.alarm.alarm", backgroundColor: "#ffa81e"
		}
		standardTile("switch", "device.switch") {
			state "off", label: '${name}', action: "switch.on",  icon: "st.switches.light.off", backgroundColor: "#ffffff"
			state "on",  label: '${name}', action: "switch.off", icon: "st.switches.light.on", backgroundColor: "#79b821"
		}

		main "contact"
		details(["contact", "switch"])
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
	// TODO: handle 'contact' attribute

}

def on() {
    log.debug "on() Executing"
    sendEvent(name: "switch",  value: "on",   isStateChange: true, display: true)
    sendEvent(name: "contact", value: "open", isStateChange: true, display: true)
    }
    
def off() {
	log.debug "off() Executing"
    sendEvent(name: "switch",  value: "off",    isStateChange: true, display: true)
    sendEvent(name: "contact", value: "closed", isStateChange: true, display: true)
    }
    