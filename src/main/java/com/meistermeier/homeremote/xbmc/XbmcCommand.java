package com.meistermeier.homeremote.xbmc;

import com.meistermeier.homeremote.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Gerrit Meier
 */
public class XbmcCommand extends AbstractCommand {

    private final static Logger LOG = LoggerFactory.getLogger(XbmcCommand.class);

    private static final String[] ACTIVATE_COMMANDS = {"video player", "movie"};

    private static final String OPTION_PLAY = "play";
    private static final String OPTION_PAUSE = "pause";
    private static final String OPTION_STOP = "stop";

    private final XbmcControl xbmcControl;

    public XbmcCommand(XbmcControl xbmcControl) {
        this.xbmcControl = xbmcControl;
    }

    @Override
    public String[] getActivationCommands() {
        return ACTIVATE_COMMANDS;
    }

    @Override
    public String evaluateAndExectue(String command) {
        String options = removeActivationString(command);
        switch (options) {
            case OPTION_PLAY:
                return xbmcControl.playPause();
            case OPTION_PAUSE:
                return xbmcControl.playPause();
            case OPTION_STOP:
                return xbmcControl.stopMovie();
            default:
                LOG.info("que? {} is not an option.", options);
                return "unknown command " + options;
        }
    }

}
