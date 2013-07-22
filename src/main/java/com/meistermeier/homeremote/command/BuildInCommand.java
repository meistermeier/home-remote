package com.meistermeier.homeremote.command;

import org.apache.commons.lang.StringUtils;

/**
 * @author Gerrit Meier
 */
public class BuildInCommand extends AbstractCommand {

    private final String[] ACTIVATION_COMMANDS = {"remote"};

    private final String VERSION = "version";
    private final String STATUS = "status";

    @Override
    public String[] getActivationCommands() {
        return ACTIVATION_COMMANDS;
    }

    @Override
    public String evaluateAndExectue(String options) {
        String cleanedOptions = removeActivationString(options);
        switch (cleanedOptions) {
            case VERSION:
                return "0.0.1";
            case STATUS:
                return "Everything alright";
            default:
                return StringUtils.EMPTY;
        }
    }
}
