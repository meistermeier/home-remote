package com.meistermeier.homeremote.command;

import org.apache.commons.lang.StringUtils;

/**
 * A command might be any system / interface / ... that can receive calls from this application or
 * in other words: That thing you want to control.
 * There are different kind of keywords to be published.<br/>
 * Some guidelines here: You really don't want the user to pronounce xbmc in voice mode or force her to type <i>movie play pause</i>
 * in a chat window (better <i>xmbc play</i>)
 *
 * @author Gerrit Meier
 */
public interface Command {

    /**
     * The name by which the command is known in the system
     * @return command name
     */
    String getName();

    /**
     * @return network keyword to trigger this command
     */
    String getNetworkKeyword();

    /**
     * @return xmpp keyword to trigger this command
     */
    String getXmppKeyword();

    /**
     * @return voice keyword to trigger this command
     */
    String getVoiceKeyword();

    /**
     * Available options in this command.
     *
     * @return option array
     */
    String[] getOptions();

    /**
     * Evaluate and execute the given option
     *
     * @param args the complete argument string (incl. keyword)
     * @return commands answer to be published in the control channel
     */
    String execute(String args);

    /**
     * Removes all keywords from the given input string
     * @param args to be cleaned
     * @return cleaned argument string
     */
    default String cleanArgsFromKeywords(String args) {
        args = args.replace(getNetworkKeyword(), StringUtils.EMPTY);
        args = args.replace(getXmppKeyword(), StringUtils.EMPTY);
        args = args.replace(getVoiceKeyword(), StringUtils.EMPTY);
        return args.trim();
    }

    /**
     * Collects all available options for this control and returns a simple help message.
     *
     * @return a simple generic help
     */
    default String getHelp() {
        StringBuilder helpBuilder = new StringBuilder("available options:\n");

        for (String option : getOptions()) {
            helpBuilder.append(option).append("\n");
        }

        return helpBuilder.toString();
    }

    /**
     * Returns the trigger keywords for this command. Used by the build-in command to give the user
     * a short overview over existing commands and how to trigger them.
     *
     * @return info string how to trigger this command.
     */
    default String getInfo() {
        StringBuilder infoBuilder = new StringBuilder();
        infoBuilder.append(getName()).append("\n")
                .append("network trigger: ").append(getNetworkKeyword()).append("\n")
                .append("xmpp trigger: ").append(getXmppKeyword()).append("\n")
                .append("voice trigger: ").append(getVoiceKeyword()).append("\n");

        return infoBuilder.toString();
    }
}
