package com.meistermeier.homeremote.voice;

import com.meistermeier.homeremote.command.Command;
import com.meistermeier.homeremote.command.CommandRegistry;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Gerrit Meier
 */
public class VoiceControl extends Thread {

    private final static Logger LOG = LoggerFactory.getLogger(VoiceControl.class);

    private final Microphone microphone;
    private final Recognizer recognizer;
    private final CommandRegistry commandRegistry;


    public VoiceControl(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;

        ConfigurationManager cm = new ConfigurationManager(VoiceControl.class.getResource("/homeremote.config.xml"));
        recognizer = (Recognizer) cm.lookup("recognizer");
        microphone = (Microphone) cm.lookup("microphone");
    }

    public void startListening() {
        setupListening();
        start();
    }

    private void setupListening() {
        recognizer.allocate();
        if (!microphone.startRecording()) {
            LOG.error("Cannot initialize microphone.");
            recognizer.deallocate();
        }
    }

    protected Optional<Command> evaluateRecognizedCommand(String command) {
        if (StringUtils.isBlank(command)) {
            return Optional.empty();
        }
        return commandRegistry.getCommand(command);
    }

    /*
    No proper way (for me) to give the user hope for a restart :(
    Just die on main application shutdown
    public void stopListening() {
        voiceControlThread.interrupt();
        microphone.clear();
        microphone.getTimer().stop();
        microphone.stopRecording();
        recognizer.deallocate();
    }*/


    @Override
    public void run() {
        LOG.info("start voice control");

        while (true) {
            Result result = recognizer.recognize();

            if (result == null) {
                LOG.warn("got no result");
                continue;
            }

            String recognizedString = result.getBestFinalResultNoFiller();

            if (StringUtils.isBlank(recognizedString)) {
                continue;
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("understood '{}'", recognizedString);
            }

            Optional<Command> voiceCommandOptional = evaluateRecognizedCommand(recognizedString);
            if (voiceCommandOptional.isPresent()) {
                Command command = voiceCommandOptional.get();
                command.evaluateAndExectue(recognizedString);
            }
        }
    }

}
