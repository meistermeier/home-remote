package com.meistermeier.homeremote.command.power;

/**
 * @author Gerrit Meier
 */
public class PowerSwitch {
    private final String address;
    private final int port;
    private final String user;
    private final String password;

    public PowerSwitch(String address, int port, String user, String password) {
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
