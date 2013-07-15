# Home Remote
Version: 0.something-alpha  
This is so much WIP I cannot even tell if you can name it alpha ;)

## About
### Short description
This project is about controlling stuff with other stuff.

### Long description
At this time the home remote project defines the following inputs and outputs:

#### inputs
- voice

#### outputs
- [XBMC][1]

## Compile
Since there is no Sphinx stuff available in the official Maven Repository, you got to take care of yourself and download [CMU Sphinx][2] manually.
If you use maven to compile the sources at the command line you have to define the sphinx parameter.

e.g. `mvn -Dsphinx=path/to/sphinx/lib clean package`

## Configuration
_(documentation in progress)_

There is a default application.properties which gives you an outline for the available config parameter. You may (and should) provide an optional .home_remote config
file in your home directory (currently only tested with *nix environments) if you want to override any settings like xbmc url.

## Planned endpoints
### inputs
- REST: will give the option to implement against an api for other client applications
### outputs (devices at home :)
- [NETIO-230B (PDU with network support)][3]
- 433 Mhz Radio Transmitter (to control simple wireless power outlets)

[1]: http://xbmc.org/ "XBMC"
[2]: http://sourceforge.net/projects/cmusphinx/files/sphinx4/1.0%20beta6/ "sphinx at sourceforge"
[3]: http://www.koukaam.se/showproduct.php?article_id=1502