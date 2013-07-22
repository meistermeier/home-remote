package com.meistermeier.homeremote.command;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

/**
 * @author Gerrit Meier
 */
public class CommandRegistry {
    private final static Logger LOG = LoggerFactory.getLogger(CommandRegistry.class);

    private final Map<String, Command> commandMap = Maps.newHashMap();

    public boolean register(Command command) {
        if (commandMap.values().contains(command)) {
            LOG.warn("Command {} already registered. Skipping.", command);
            return false;
        }

        for (String activationCommand : command.getActivationCommands()) {
            commandMap.put(activationCommand, command);
        }

        return true;
    }

    public Optional<Command> getCommand(String commandString) {
        Optional<Command> commandOptional = Optional.empty();
        for (String key : commandMap.keySet()) {
            if (StringUtils.indexOf(commandString, key) > -1) {
                return Optional.of(commandMap.get(key));
            }
        }
        return commandOptional;
    }
}
