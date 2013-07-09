package com.meistermeier.homeremote.command;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Gerrit Meier
 */
public class CommandRegistry {
    private final static Logger LOG = LoggerFactory.getLogger(CommandRegistry.class);

    private final List<Command> commandList = Lists.newArrayList();

    public boolean register(Command command) {
        if (commandList.contains(command)) {
            LOG.warn("Command {} already registered. Skipping.", command);
            return false;
        }
        return commandList.add(command);
    }

    public List<Command> getCommandList() {
        return commandList;
    }
}
