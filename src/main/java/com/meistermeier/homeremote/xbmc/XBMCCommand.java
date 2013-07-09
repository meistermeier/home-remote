package com.meistermeier.homeremote.xbmc;

import com.meistermeier.homeremote.command.Command;
import com.meistermeier.homeremote.command.Option;

/**
 * @author Gerrit Meier
 */
public class XbmcCommand implements Command {

    private static final String[] ACTIVATE_COMMANDS = {"movie", "video player"};

    private final Option[] options;

    public XbmcCommand(Option[] options) {
        this.options = options;
    }

    @Override
    public String[] getActivateCommands() {
        return ACTIVATE_COMMANDS;
    }

    @Override
    public Option[] getOptions() {
        return options;
    }
}
