package com.meistermeier.homeremote.command.xbmc;

import com.meistermeier.homeremote.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Gerrit Meier
 */
public class XbmcCommand implements Command {

    private static final String NAME = "XBMC";

    private static final String VOICE_KEYWORD = "media player";
    private static final String NETWORK_KEYWORD = "xbmc";
    private static final String XMPP_KEYWORD = "xbmc";

    private static final String OPTION_PLAY = "play";
    private static final String OPTION_PAUSE = "pause";
    private static final String OPTION_STOP = "stop";

    private final XbmcControl xbmcControl;

    public XbmcCommand(XbmcControl xbmcControl) {
        this.xbmcControl = xbmcControl;
    }

    @Override
    public String getName() {
        return NAME;
    }


    @Override
    public String getNetworkKeyword() {
        return NETWORK_KEYWORD;
    }

    @Override
    public String getVoiceKeyword() {
        return VOICE_KEYWORD;
    }

    @Override
    public String getXmppKeyword() {
        return XMPP_KEYWORD;
    }

    @Override
    public String[] getOptions() {
        return new String[]{OPTION_PAUSE, OPTION_PLAY, OPTION_STOP};
    }

    @Override
    public String execute(String args) {
        String cleanedArgs = cleanArgsFromKeywords(args);
        switch (cleanedArgs) {
            case OPTION_PAUSE:
                return xbmcControl.pause();
            case OPTION_PLAY:
                return xbmcControl.play();
            case OPTION_STOP:
                return xbmcControl.stopMovie();
            default:
                return "unknown option\n" + getHelp();
        }
    }
}
