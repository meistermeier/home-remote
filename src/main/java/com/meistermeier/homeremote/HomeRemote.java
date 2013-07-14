package com.meistermeier.homeremote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Gerrit Meier
 */
public class HomeRemote {

    private final static Logger LOG = LoggerFactory.getLogger(HomeRemote.class);

    public static void main(String[] args) throws Exception {
        new ClassPathXmlApplicationContext("/home-remote.xml");
    }

}
