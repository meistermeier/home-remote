package com.meistermeier.homeremote.voice.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Gerrit Meier
 */
public interface VoiceCommand extends ApplicationContextAware {

    final static Logger LOG = LoggerFactory.getLogger(VoiceCommand.class);

    default void registerCommand() {
        String[] possibleVoiceCommandRegistryBeans = getApplicationContext().getBeanNamesForType(VoiceCommandRegistry.class);
        if (possibleVoiceCommandRegistryBeans.length == 1) {
            VoiceCommandRegistry voiceCommandRegistry = (VoiceCommandRegistry) getApplicationContext().getBean(possibleVoiceCommandRegistryBeans[0]);
            voiceCommandRegistry.register(this);
            LOG.info("registered {} in voice commands.", getClass());
        } else {
            LOG.info("no voice support enabled.");
        }
    }

    ApplicationContext getApplicationContext();

    String[] getActivateCommands();

    boolean evaluateOptions(String[] options);

}
