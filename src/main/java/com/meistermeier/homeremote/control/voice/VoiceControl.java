package com.meistermeier.homeremote.control.voice;

/**
 * @author Gerrit Meier
 */
public class VoiceControl extends Thread {

    /*private final static Logger LOG = LoggerFactory.getLogger(VoiceControl.class);

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
    }*/

}
