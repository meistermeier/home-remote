package com.meistermeier.homeremote.command;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @author Gerrit Meier
 */
public abstract class AbstractCommand implements Command {

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
