package com.meistermeier.homeremote.command.netio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * @author Gerrit Meier
 */
public class NetioPowerSwitchControl {

    private final static Logger LOG = LoggerFactory.getLogger(NetioPowerSwitchControl.class);

    private final NetioPowerSwitch netioPowerSwitch;

    enum Message {
        SWITCH_ON,
        SWITCH_OFF,
        STATUS
    }

    public NetioPowerSwitchControl(NetioPowerSwitch netioPowerSwitch) {
        this.netioPowerSwitch = netioPowerSwitch;
    }

    public String switchOn(int unit) {
        try {
            return sendMessage(Message.SWITCH_ON, unit);
        } catch (IOException e) {
            LOG.error("Could not send switch on message", e);
            return "Could not set status";
        }
    }

    public String switchOff(int unit) {
        try {
            return sendMessage(Message.SWITCH_OFF, unit);
        } catch (IOException e) {
            LOG.error("Could not send switch off message", e);
            return "Could not set status";
        }
    }

    public String status() {
        try {
            return sendMessage(Message.STATUS, null);
        } catch (IOException e) {
            LOG.error("Could not get switch status", e);
            return "Could not get status";
        }
    }

    protected String sendMessage(Message message, Integer unit) throws IOException {
        Socket socket = new Socket(netioPowerSwitch.getAddress(), netioPowerSwitch.getPort());
        OutputStream outputStream = socket.getOutputStream();
        String loginString = "login " + netioPowerSwitch.getUser() + " " + netioPowerSwitch.getPassword() + "\n";
        outputStream.write(loginString.getBytes());
        outputStream.flush();

        switch (message) {
            case SWITCH_ON:
                return sendSwitchOnCommand(socket, unit);
            case SWITCH_OFF:
                return sendSwitchOffCommand(socket, unit);
            case STATUS:
                return sendStatusCommand(socket);
            default:
                return "Unknown message command";
        }
    }

    private String sendStatusCommand(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(("port list").getBytes());
        outputStream.flush();

        return getAnswer(socket);
    }

    private String sendSwitchOnCommand(Socket socket, int unit) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(("port " + unit + " 1").getBytes());
        outputStream.flush();

        return getAnswer(socket);
    }

    private String sendSwitchOffCommand(Socket socket, int unit) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(("port " + unit + " 0").getBytes());
        outputStream.flush();

        return getAnswer(socket);
    }

    private String getAnswer(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        return br.readLine();
    }
}
