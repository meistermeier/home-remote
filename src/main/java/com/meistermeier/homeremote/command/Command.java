package com.meistermeier.homeremote.command;

import org.apache.commons.lang.StringUtils;

/**
 * @author Gerrit Meier
 */
public interface Command {

    String getName();

    String getNetworkKeyword();

    String getXmppKeyword();

    String getVoiceKeyword();

    String[] getCommands();

    String execute(String args);

    default String cleanArgsFromKeywords(String args) {
        args = args.replace(getNetworkKeyword(), StringUtils.EMPTY);
        args = args.replace(getNetworkKeyword(), StringUtils.EMPTY);
        args = args.replace(getNetworkKeyword(), StringUtils.EMPTY);
        return args.trim();
    }

    default String getHelp() {
        StringBuilder helpBuilder = new StringBuilder("available options:\n");

        for (String command : getCommands()) {
            helpBuilder.append(command).append("\n");
        }

        return helpBuilder.toString();
    }

    default String getInfo() {
        StringBuilder infoBuilder = new StringBuilder();
        infoBuilder.append(getName()).append("\n")
                .append("network trigger: ").append(getNetworkKeyword()).append("\n")
                .append("xmpp trigger: ").append(getXmppKeyword()).append("\n")
                .append("voice trigger: ").append(getVoiceKeyword()).append("\n");

        return infoBuilder.toString();
    }
}
