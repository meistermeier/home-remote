package com.meistermeier.homeremote.command.netio;

import com.meistermeier.homeremote.command.Command;

/**
 * Wrapper for my PD Unit
 * @author Gerrit Meier
 */
public class NetioPowerSwitchCommand implements Command {

    private final String OPTION_ON = "on";
    private final String OPTION_OFF = "off";
    private final String OPTION_STATUS = "status";
    private final NetioPowerSwitchControl netioPowerSwitchControl;


    public NetioPowerSwitchCommand(NetioPowerSwitchControl netioPowerSwitchControl) {
        this.netioPowerSwitchControl = netioPowerSwitchControl;
    }

    @Override
    public String getName() {
        return "NetioPowerSwitch";
    }

    @Override
    public String getNetworkKeyword() {
        return "switch";
    }

    @Override
    public String getXmppKeyword() {
        return "switch";
    }

    @Override
    public String getVoiceKeyword() {
        return "switch";
    }

    @Override
    public String[] getOptions() {
        return new String[]{OPTION_ON + " <unit>", OPTION_OFF + " <unit>", OPTION_STATUS};  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String execute(String args) {
        String cleanedArgs = cleanArgsFromKeywords(args);
        String[] arguments = cleanedArgs.split(" ");
        if (arguments.length > 2) {
            return "unknown command and argument count (" + cleanedArgs + ")\n" + getHelp();
        }
        int unit = -1;
        if (arguments.length == 2) {
            try {
                unit = Integer.parseInt(arguments[1].trim());
            } catch (NumberFormatException e) {
                return "unknown device: " + arguments[1];
            }
        }
        switch (arguments[0].trim()) {
            case OPTION_ON:
                return netioPowerSwitchControl.switchOn(unit);
            case OPTION_OFF:
                return netioPowerSwitchControl.switchOff(unit);
            case OPTION_STATUS:
                return netioPowerSwitchControl.status();
            default:
                return "unknown control argument (" + arguments[0] + ")\n" + getHelp();

        }

    }
}
