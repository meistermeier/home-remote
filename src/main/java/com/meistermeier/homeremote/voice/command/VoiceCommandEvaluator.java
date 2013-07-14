package com.meistermeier.homeremote.voice.command;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author Gerrit Meier
 */
public class VoiceCommandEvaluator {
    private final VoiceCommandRegistry voiceCommandRegistry;

    public VoiceCommandEvaluator(VoiceCommandRegistry voiceCommandRegistry) {
        this.voiceCommandRegistry = voiceCommandRegistry;
    }

    public Optional<VoiceCommand> evaluateInput(String command) {
        if (StringUtils.isBlank(command)) {
            return Optional.empty();
        }

        return getCommand(command);
    }

    private Optional<VoiceCommand> getCommand(String command) {
        List<VoiceCommand> voiceCommandList = voiceCommandRegistry.getVoiceCommandList();
        for (VoiceCommand voiceCommand : voiceCommandList) {
            String[] activateCommands = voiceCommand.getActivateCommands();
            for (String activateCommand : activateCommands) {
                if (command.equals(activateCommand)) {
                    return Optional.of(voiceCommand);
                }
            }
        }
        return Optional.empty();
    }
}
