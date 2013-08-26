package com.meistermeier.homeremote.command.netio;

/**
 * @author Gerrit Meier
 */
public class NetioPowerSwitch {
    private final String address;
    private final int port;
    private final String user;
    private final String password;

    public NetioPowerSwitch(String address, int port, String user, String password) {
        this.address = address;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
