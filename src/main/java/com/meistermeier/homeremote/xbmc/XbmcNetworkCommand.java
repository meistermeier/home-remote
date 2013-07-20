package com.meistermeier.homeremote.xbmc;

import com.meistermeier.homeremote.network.NetworkCommand;
import org.apache.commons.lang.StringUtils;

/**
 * @author Gerrit Meier
 */
public class XbmcNetworkCommand implements NetworkCommand {

    private static final String MAIN_COMMAND = "xbmc";

    @Override
    public String getMainCommand() {
        return MAIN_COMMAND;
    }

    @Override
    public boolean exectueCommand(String command) {
        if (StringUtils.isBlank(command)) {
            return false;
        }

        command = command.replace(MAIN_COMMAND, StringUtils.EMPTY).trim();

        return executeCommandWithOptions(command);

    }

    public boolean executeCommandWithOptions(String command) {
        switch (command) {
            case "play":
                return true;
            case "pause":
                return true;
            case "stop":
                return true;
            default:
                return false;
        }
    }
}
