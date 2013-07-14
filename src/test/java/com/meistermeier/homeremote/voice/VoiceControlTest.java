package com.meistermeier.homeremote.voice;

import com.meistermeier.homeremote.voice.command.VoiceCommand;
import com.meistermeier.homeremote.voice.command.VoiceCommandEvaluator;
import com.meistermeier.homeremote.voice.command.VoiceCommandRegistry;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Gerrit Meier
 */
public class VoiceControlTest {

    private VoiceControl voiceControl;
    private VoiceCommand voiceCommandDummy = new DummyVoiceCommand();

    @Before
    public void setUp() throws Exception {

        VoiceCommandRegistry voiceCommandRegistry = new VoiceCommandRegistry();
        voiceCommandRegistry.register(voiceCommandDummy);

        VoiceCommandEvaluator voiceCommandEvaluator = new VoiceCommandEvaluator(voiceCommandRegistry);
        voiceControl = new VoiceControl(voiceCommandEvaluator);

    }

    @Test
    public void testRetrieveVoiceCommand() throws Exception {
        Optional<VoiceCommand> voiceCommand = voiceControl.retrieveVoiceCommand("this");
        assertTrue(voiceCommand.isPresent());
        assertEquals(voiceCommandDummy, voiceCommand.get());
    }

    @Test
    public void testEvaluateRecognizedCommand() throws Exception {
        Optional<VoiceCommand> voiceCommand = voiceControl.evaluateRecognizedCommand(new String[]{"this", "is", "a", "test"});
        assertTrue(voiceCommand.isPresent());
        assertEquals(voiceCommandDummy, voiceCommand.get());
    }

    @Test
    public void testName() throws Exception {
        boolean result = voiceControl.processComand(new String[]{"is", "a", "test"}, Optional.of(voiceCommandDummy));
        assertTrue(result);
    }

    class DummyVoiceCommand implements VoiceCommand {
        @Override
        public ApplicationContext getApplicationContext() {
            return null;
        }

        @Override
        public String[] getActivateCommands() {
            return new String[]{"this"};
        }

        @Override
        public boolean evaluateOptions(String[] options) {
            return true;
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        }
    }
}
