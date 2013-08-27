package com.meistermeier.homeremote.command.elro;

import com.pi4j.io.gpio.*;

/**
 * @author Gerrit Meier
 */
public class ElroControl {
    final GpioController gpio = GpioFactory.getInstance();
    private final GpioPinDigitalOutput output;

    public ElroControl(Elro elro) {
        Pin gpioPin = elro.getDataPin();
        output = gpio.provisionDigitalOutputPin(gpioPin, "data pin", PinState.LOW);
    }

    public void switchOn() {
        gpio.pulse(100, output);
    }
}
