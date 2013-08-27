package com.meistermeier.homeremote.command.elro;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

/**
 * @author Gerrit Meier
 */
public class Elro {
    private Pin dataPin = RaspiPin.GPIO_17;

    public Pin getDataPin() {
        return dataPin;
    }
}
