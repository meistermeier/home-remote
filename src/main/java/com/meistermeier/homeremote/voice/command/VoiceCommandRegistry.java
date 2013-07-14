package com.meistermeier.homeremote.voice.command;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Gerrit Meier
 */
public class VoiceCommandRegistry {
    private final static Logger LOG = LoggerFactory.getLogger(VoiceCommandRegistry.class);

    private final List<VoiceCommand> voiceCommandList = Lists.newArrayList();

    public boolean register(VoiceCommand voiceCommand) {
        if (voiceCommandList.contains(voiceCommand)) {
            LOG.warn("VoiceCommand {} already registered. Skipping.", voiceCommand);
            return false;
        }
        return voiceCommandList.add(voiceCommand);
    }

    public List<VoiceCommand> getVoiceCommandList() {
        return voiceCommandList;
    }
}
