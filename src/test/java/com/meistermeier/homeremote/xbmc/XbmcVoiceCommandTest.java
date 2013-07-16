package com.meistermeier.homeremote.xbmc;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Gerrit Meier
 */
public class XbmcVoiceCommandTest {

    XbmcVoiceCommand voiceCommand = new XbmcVoiceCommand(new XbmcControl(new Xbmc("dummyurl")));

    @Test
    public void testIsRegisteredFor() throws Exception {
        assertTrue(voiceCommand.isRegisteredFor("movie"));
        assertTrue(voiceCommand.isRegisteredFor("video player"));

        assertFalse(voiceCommand.isRegisteredFor("videoplayer"));
    }

    @Test
    public void testGetActivationCommands() throws Exception {
        String[] activationCommands = voiceCommand.getActivationCommands();

        assertEquals(2, activationCommands.length);
        assertArrayEquals(new String[]{"video player", "movie"}, activationCommands);
    }

    @Test
    public void testRemoveAcitvationCommands() throws Exception {
        String option = voiceCommand.removeActivationString("video player start");
        assertEquals("start", option);

        option = voiceCommand.removeActivationString("movie start");
        assertEquals("start", option);

        option = voiceCommand.removeActivationString("unknown command start");
        assertEquals("unknown command start", option);

        option = voiceCommand.removeActivationString(null);
        assertEquals("", option);
    }
}
