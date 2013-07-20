package com.meistermeier.homeremote.voice;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

/**
 * @author Gerrit Meier
 */
public class VoiceCommandRegistry {
    private final static Logger LOG = LoggerFactory.getLogger(VoiceCommandRegistry.class);

    private final Map<String, VoiceCommand> voiceCommandMap = Maps.newHashMap();

    public boolean register(VoiceCommand voiceCommand) {
        if (voiceCommandMap.values().contains(voiceCommand)) {
            LOG.warn("VoiceCommand {} already registered. Skipping.", voiceCommand);
            return false;
        }

        for (String activationCommand : voiceCommand.getActivationCommands()) {
            voiceCommandMap.put(activationCommand, voiceCommand);
        }

        return true;
    }

    public Optional<VoiceCommand> getVoiceCommand(String commandString) {
        Optional<VoiceCommand> voiceCommandOptional = Optional.empty();
        VoiceCommand voiceCommand = voiceCommandMap.get(commandString);
        if (voiceCommand != null) {
            voiceCommandOptional = Optional.of(voiceCommand);
        }
        return voiceCommandOptional;
    }
}
