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
- your voice (currently under heavy refactoring) (by [CMU Sphinx][2])
- XMPP / jabber (by [smack][3])
- simple telnet

#### outputs
- [XBMC][1]
- [NETIO-230B (PDU with network support)][4]
- 433 Mhz Radio Transmitter (to control elro power outlets)

## Compile
Since there is no Sphinx stuff available in the official Maven Repository, you got to take care of yourself and download [CMU Sphinx][2] manually.
If you use maven to compile the sources at the command line you have to define the sphinx parameter.
Also maven central is missing an up-to-date smack version. It is provided the same way like the sphinx lib before.

e.g. `mvn -Dsphinx=path/to/sphinx/lib -Dsmack=path/to/smack/lib clean package`

## Start
_(documentation is a steady wip)_

## Configuration
_(documentation in progress)_

There is a default application.properties which gives you an outline for the available config parameter. You may (and should) provide an `.home_remote` config
file in your home directory. Windows users may replace the path in the application.properties (key _custom.properties.path_) file for their needs.

[1]: http://xbmc.org/ "XBMC"
[2]: http://sourceforge.net/projects/cmusphinx/files/sphinx4/1.0%20beta6/ "sphinx at sourceforge"
[3]: http://www.igniterealtime.org/projects/smack "smack project site"
[4]: http://www.koukaam.se/showproduct.php?article_id=1502