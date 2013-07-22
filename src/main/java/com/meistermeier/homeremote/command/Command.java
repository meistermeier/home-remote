package com.meistermeier.homeremote.command;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Gerrit Meier
 */
public interface Command extends ApplicationContextAware {
    final static Logger LOG = LoggerFactory.getLogger(Command.class);

    /**
     * default register of command implementation
     */
    default void registerCommand() {
        String[] possibleCommandRegistryBeans = getApplicationContext().getBeanNamesForType(CommandRegistry.class);
        if (possibleCommandRegistryBeans.length == 1) {
            CommandRegistry commandRegistry = (CommandRegistry) getApplicationContext().getBean(possibleCommandRegistryBeans[0]);
            commandRegistry.register(this);
            LOG.info("registered {} in commands.", getClass());
        } else {
            LOG.error("could not register command. There is no such bean for CommandRegistry.");
        }

    }

    ApplicationContext getApplicationContext();

    String[] getActivationCommands();

    default boolean isRegisteredFor(String command) {
        return ArrayUtils.contains(getActivationCommands(), command);
    }

    String evaluateAndExectue(String options);

    default String removeActivationString(String command) {
        if (StringUtils.isBlank(command)) {
            return StringUtils.EMPTY;
        }
        for (String activateCommand : getActivationCommands()) {
            command = command.replace(activateCommand, StringUtils.EMPTY);
        }
        return command.trim();
    }
}
