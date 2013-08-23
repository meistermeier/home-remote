package com.meistermeier.homeremote.command;

import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;

/**
 * @author Gerrit Meier
 */
public class CommandRegistry {
    private final static Logger LOG = LoggerFactory.getLogger(CommandRegistry.class);

    private final Set<Command> commandSet = Sets.newHashSet();

    public void register(Command command) {
        commandSet.add(command);
        LOG.debug("Added {} command.", command);
    }

    public Set<Command> getCommandSet() {
        return commandSet;
    }

    public Optional<Command> getNetworkCommand(String keyword) {
        for (Command command : commandSet) {
            if (StringUtils.startsWith(keyword, command.getNetworkKeyword())) {
                return Optional.of(command);
            }
        }

        return Optional.empty();
    }

}
