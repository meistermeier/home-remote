package com.meistermeier.homeremote.command;

import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;

/**
 * Registry for all commands in the system.
 *
 * @author Gerrit Meier
 */
public class CommandRegistry {
    private final static Logger LOG = LoggerFactory.getLogger(CommandRegistry.class);

    private final Set<Command> commandSet = Sets.newHashSet();


    /**
     * register command to be available by keyword call
     *
     * @param command - command to be registered
     */
    public void register(Command command) {
        commandSet.add(command);
        LOG.debug("Added {} command.", command);
    }

    /**
     * @return all registered commands
     */
    public Set<Command> getCommandSet() {
        return commandSet;
    }

    /**
     * Tries to find a command for the given network keyword.
     *
     * @param keyword - network keyword of a command
     * @return matching command if exists or Optional.empty
     */
    public Optional<Command> getNetworkCommand(String keyword) {
        return commandSet.stream().filter(element -> StringUtils.startsWith(keyword, element.getNetworkKeyword())).findAny();
    }

    /**
     * Tries to find a command for the given xmpp keyword.
     *
     * @param keyword - xmpp keyword of a command
     * @return matching command if exists or Optional.empty
     */
    public Optional<Command> getXmppCommand(String keyword) {
        return commandSet.stream().filter(element -> StringUtils.startsWith(keyword, element.getXmppKeyword())).findAny();
    }

}
