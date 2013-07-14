package com.meistermeier.homeremote.voice;

import com.meistermeier.homeremote.voice.command.VoiceCommand;
import com.meistermeier.homeremote.voice.command.VoiceCommandEvaluator;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Gerrit Meier
 */
public class VoiceControl {

    private final static Logger LOG = LoggerFactory.getLogger(VoiceControl.class);

    private final Microphone microphone;
    private final Recognizer recognizer;
    private final VoiceCommandEvaluator voiceCommandEvaluator;

    private VoiceControlThread voiceControlThread;

    public VoiceControl(VoiceCommandEvaluator voiceCommandEvaluator) {
        ConfigurationManager cm = new ConfigurationManager(VoiceControl.class.getResource("/homeremote.config.xml"));
        this.voiceCommandEvaluator = voiceCommandEvaluator;
        recognizer = (Recognizer) cm.lookup("recognizer");
        microphone = (Microphone) cm.lookup("microphone");
    }

    public void startListening() {
        setupListening();
        voiceControlThread = new VoiceControlThread(this);
        voiceControlThread.start();
    }

    private void setupListening() {
        recognizer.allocate();
        if (!microphone.startRecording()) {
            LOG.error("Cannot initialize microphone.");
            recognizer.deallocate();
        }
    }

    protected synchronized Optional<VoiceCommand> evaluateRecognizedCommand(String[] commandArgs) {
        String activationCommand = commandArgs[0];
        return retrieveVoiceCommand(activationCommand);
    }

    protected Optional<VoiceCommand> retrieveVoiceCommand(String activationCommand) {
        return voiceCommandEvaluator.evaluateInput(activationCommand);
    }

    protected boolean processComand(String[] commandOptions, Optional<VoiceCommand> voiceCommandOptional) {
        if (voiceCommandOptional.isPresent()) {
            VoiceCommand voiceCommand = voiceCommandOptional.get();
            return voiceCommand.evaluateOptions(commandOptions);
        }
        return false;
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

    class VoiceControlThread extends Thread {

        private final VoiceControl voiceControl;

        VoiceControlThread(VoiceControl voiceControl) {
            this.voiceControl = voiceControl;
            setUncaughtExceptionHandler((t, e) ->
                    LOG.info("voice control listening was shut down")
            );
        }

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
                if (StringUtils.isNotBlank(recognizedString)) {
                    String[] arguments = recognizedString.split(" ");
                    Optional<VoiceCommand> voiceCommand = voiceControl.evaluateRecognizedCommand(arguments);
                    boolean processed = voiceControl.processComand(Arrays.copyOfRange(arguments, 1, arguments.length), voiceCommand);

                    if (!processed && LOG.isDebugEnabled()) {
                        LOG.debug("command {} was not processed.", recognizedString);
                    }
                }
                LOG.debug("understood '{}'", recognizedString);
            }
        }

    }
}
