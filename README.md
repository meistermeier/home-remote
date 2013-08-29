# Home Remote
Version: 0.something-alpha  
This is so much WIP I cannot even tell if you can name the version alpha ;)

## About
### Short description
This project is about controlling stuff (manually) with other stuff.

### Long description
**Home Remote is no home automation tool (yet not any sensors supported)**

At this time the home remote project defines the following inputs and outputs:

#### inputs
- your voice (currently unavailable due to heavy refactoring) (by [CMU Sphinx][2])
- XMPP / jabber (by [smack][3])
- simple telnet

#### outputs
- [XBMC][1]
- [NETIO-230B (PDU with network support)][4]

## Compile
**Note: This app is designed for JDK8 to get the best out of my pi :)**
~~Since there is no Sphinx stuff nor up-to-date smack version available in the official Maven Repository, you got to take care of yourself and download [CMU Sphinx][2]
respectively [smack][3] manually.
If you use maven to compile the sources at the command line you must define the sphinx and smack lib parameter.~~
**gradle docs are missing..do your best**


## Configuration
_(documentation in progress)_

There is a default stub application.properties file which gives you an outline for the available config parameter. You may (and should) provide an `.home_remote` config
file in your home directory. Windows users may replace the path in the application.properties (key _custom.properties.path_) file for their needs.

## How to run
_(documentation is a steady wip)_

## What's next
### Elro power outlets (433 Mhz Radio Transmitter)
First tests on the raspberry pi where successful. Still in integration phase. (Check out the elro branch for bleeding edge stuff)

### Re-enable voice support
Bring back the voice support to control your home Star Trek style. But also avoid movies to pause or stop themselves by voice recognition (still a huge problem).

### Sensor support
This will be the next milestone. Support various sensors to bring make this application a home automation system. It's also necessary to create some kind of DSL
 to map sensors to actions (commands).


[1]: http://xbmc.org/ "XBMC"
[2]: http://sourceforge.net/projects/cmusphinx/files/sphinx4/1.0%20beta6/ "sphinx at sourceforge"
[3]: http://www.igniterealtime.org/projects/smack "smack project site"
[4]: http://www.koukaam.se/showproduct.php?article_id=1502