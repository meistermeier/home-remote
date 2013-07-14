package com.meistermeier.homeremote.xbmc;

import com.meistermeier.homeremote.voice.command.VoiceCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * @author Gerrit Meier
 */
public class XbmcVoiceCommand implements VoiceCommand {

    private final static Logger LOG = LoggerFactory.getLogger(XbmcVoiceCommand.class);

    private static final String[] ACTIVATE_COMMANDS = {"movie", "video player"};

    private static final String OPTION_PLAY = "play";
    private static final String OPTION_PAUSE = "pause";
    private static final String OPTION_STOP = "stop";

    private final XbmcControl xbmcControl;

    private ApplicationContext applicationContext;

    public XbmcVoiceCommand(XbmcControl xbmcControl) {
        this.xbmcControl = xbmcControl;
    }

    @Override
    public String[] getActivateCommands() {
        return ACTIVATE_COMMANDS;
    }

    @Override
    public boolean evaluateOptions(String[] options) {
        if (options.length != 1) {
            LOG.info("don't know what to do with " + Arrays.toString(options));
            return false;
        }

        switch (options[0]) {
            case OPTION_PLAY:
                return xbmcControl.playPause();
            case OPTION_PAUSE:
                return xbmcControl.playPause();
            case OPTION_STOP:
                return xbmcControl.stopMovie();
            default:
                LOG.info("que? {} is not an option.", options[0]);
                return false;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
