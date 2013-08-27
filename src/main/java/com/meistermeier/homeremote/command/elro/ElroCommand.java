package com.meistermeier.homeremote.command.elro;

import com.meistermeier.homeremote.command.Command;

/**
 * @author Gerrit Meier
 */
public class ElroCommand implements Command {

    private static final String NAME = "Elro";
    private static final String NETWORK_KEYWORD = "elro";
    private static final String XMPP_KEYWORD = "elro";
    private static final String VOICE_KEYWORD = "switch";

    private static final String OPTION_ON = "on";
    private static final String OPTION_OFF = "off";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getNetworkKeyword() {
        return NETWORK_KEYWORD;
    }

    @Override
    public String getXmppKeyword() {
        return XMPP_KEYWORD;
    }

    @Override
    public String getVoiceKeyword() {
        return VOICE_KEYWORD;
    }

    @Override
    public String[] getOptions() {
        return new String[]{OPTION_ON + "<unit>", OPTION_OFF + "<unit>"};  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String execute(String args) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
