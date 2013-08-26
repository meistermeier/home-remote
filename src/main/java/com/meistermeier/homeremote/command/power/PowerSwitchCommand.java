package com.meistermeier.homeremote.command.power;

import com.meistermeier.homeremote.command.Command;

/**
 * Wrapper for my PD Unit
 * Not in use by now
 *
 * @author Gerrit Meier
 */
public class PowerSwitchCommand implements Command {

    private final String OPTION_ON = "on";
    private final String OPTION_OFF = "off";
    private final String OPTION_STATUS = "status";
    private final PowerSwitchControl powerSwitchControl;


    public PowerSwitchCommand(PowerSwitchControl powerSwitchControl) {
        this.powerSwitchControl = powerSwitchControl;
    }

    @Override
    public String getName() {
        return "PowerSwitch";
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
    public String[] getCommands() {
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
                return powerSwitchControl.switchOn(unit);
            case OPTION_OFF:
                return powerSwitchControl.switchOff(unit);
            case OPTION_STATUS:
                return powerSwitchControl.status();
            default:
                return "unknown control argument (" + arguments[0] + ")\n" + getHelp();

        }

    }
}
