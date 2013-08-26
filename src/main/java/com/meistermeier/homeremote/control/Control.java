package com.meistermeier.homeremote.control;

/**
 * Control interface implemented by any control. Mainly used for correct registration
 * and controllable shutdown calls to free resources etc.
 *
 * @author Gerrit Meier
 */
public interface Control {
    void shutdown();
}
