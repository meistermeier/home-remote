package com.meistermeier.homeremote.network;

import org.apache.commons.lang.StringUtils;

/**
 * @author Gerrit Meier
 */
public interface NetworkCommand {

    default boolean isRegisteredFor(String command) {
        if(StringUtils.isNotBlank(command)) {
            return (command.split(" ")[0]).equals(getMainCommand());
        }
        return false;
    }

    String getMainCommand();

    boolean exectueCommand(String command);
}
