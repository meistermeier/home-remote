package com.meistermeier.homeremote.command.xbmc;

/**
 * @author Gerrit Meier
 */
public class Xbmc {

    private final String url;

    public Xbmc(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
