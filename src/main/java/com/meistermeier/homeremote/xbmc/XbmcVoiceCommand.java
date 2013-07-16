package com.meistermeier.homeremote.xbmc;

import com.meistermeier.homeremote.voice.command.AbstractVoiceCommand;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Gerrit Meier
 */
public class XbmcVoiceCommand extends AbstractVoiceCommand {

    private final static Logger LOG = LoggerFactory.getLogger(XbmcVoiceCommand.class);

    private static final String[] ACTIVATE_COMMANDS = {"video player", "movie"};

    private static final String OPTION_PLAY = "play";
    private static final String OPTION_PAUSE = "pause";
    private static final String OPTION_STOP = "stop";

    private final XbmcControl xbmcControl;

    public XbmcVoiceCommand(XbmcControl xbmcControl) {
        this.xbmcControl = xbmcControl;
    }

    @Override
    public String[] getActivationCommands() {
        return ACTIVATE_COMMANDS;
    }

    @Override
    public boolean evaluateOptions(String command) {
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
                return false;
        }
    }

}
