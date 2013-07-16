package com.meistermeier.homeremote.voice.command;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @author Gerrit Meier
 */
public abstract class AbstractVoiceCommand implements VoiceCommand {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
