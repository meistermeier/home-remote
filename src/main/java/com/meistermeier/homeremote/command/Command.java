package com.meistermeier.homeremote.command;

/**
 * We are not in the webapp world. So I think Command is a nice name ;)
 * @author Gerrit Meier
 */
public interface Command {
    String[] getActivateCommands();

    Option[] getOptions();
}
