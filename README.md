# Home Remote
Version: 0.something-alpha  
This is so much WIP ;)
## About
**Short description:** This project is about controlling stuff with other stuff.

**Long description:** At this time the home remote project defines the following inputs and outputs:
### inputs
- voice
### outputs
- [XBMC][1]

## Compile
Since there is no Sphinx stuff available in the official Maven Repository, you got to take care of yourself and download [CMU Sphinx][2] manually.
If you use maven to compile the sources at the command line you have to define the sphinx parameter.

e.g. `mvn -Dsphinx=path/to/sphinx/lib clean package`

## Configuration

## Planned endpoints
### inputs
- REST (or something similar :P)
### outputs
- [NETIO-230B (Power Distribution Unit with network support)][3]
- 433 Mhz Radio Transmitter (for wireless power outlets)

[1]: http://xbmc.org/ "XBMC"
[2]: http://sourceforge.net/projects/cmusphinx/files/sphinx4/1.0%20beta6/ "sphinx at sourceforge"
[3]: http://www.koukaam.se/showproduct.php?article_id=1502