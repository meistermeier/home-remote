package com.meistermeier.homeremote.command.power;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Gerrit Meier
 */
public class PowerSwitchControl {

    private final PowerSwitch powerSwitch;

    enum Message {
        SWITCH_ON,
        SWITCH_OFF
    }

    public PowerSwitchControl(PowerSwitch powerSwitch) {
        this.powerSwitch = powerSwitch;
    }

    public String switchOn(int unit) {
        return "switched on " + unit;
    }

    public String switchOff(int unit) {
        return "switched off " + unit;
    }

    protected void sendMessage(Message message, int unit) throws IOException {
        Socket socket = new Socket(powerSwitch.getAddress(), powerSwitch.getPort());
        OutputStream outputStream = socket.getOutputStream();
        String loginString = "login " + powerSwitch.getUser() + " " + powerSwitch.getPassword() + "\n";
        outputStream.write(loginString.getBytes());

        String commandString;
        switch(message) {
            case SWITCH_ON:
                commandString = createSwitchCommand(unit);
                break;
        }
        outputStream.write(loginString.getBytes());
    }

    private String createSwitchCommand(int unit) {
        return "port list uuuu";
    }
}
