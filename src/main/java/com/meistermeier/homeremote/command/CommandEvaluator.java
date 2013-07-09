package com.meistermeier.homeremote.command;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author Gerrit Meier
 */
public class CommandEvaluator {
    private final CommandRegistry commandRegistry;

    public CommandEvaluator(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    public Optional<Command> evaluateInput(String input) {
        if (StringUtils.isBlank(input)) {
            return Optional.empty();
        }

        return getCommand(input);
    }

    private Optional<Command> getCommand(String input) {
        String[] words = input.split(" ");
        List<Command> commandList = commandRegistry.getCommandList();
        for (Command command : commandList) {
            String[] activateCommands = command.getActivateCommands();
            for (String activateCommand : activateCommands) {
                if (words[0].equals(activateCommand)) {
                    return Optional.of(command);
                }
            }
        }
        return Optional.empty();
    }
}
