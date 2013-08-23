package com.meistermeier.homeremote.command;

/**
 * @author Gerrit Meier
 */
public class BuildInCommand implements Command {

    private static final String NAME = "BuildInCommand";

    private static final String NETWORK_KEYWORD = "system";
    private static final String XMPP_KEYWORD = "system";
    private static final String VOICE_KEYWORD = "system";

    private static final String STATUS = "status";

    private final CommandRegistry registry;

    public BuildInCommand(CommandRegistry registry) {
        this.registry = registry;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getNetworkKeyword() {
        return NETWORK_KEYWORD;
    }

    @Override
    public String getXmppKeyword() {
        return XMPP_KEYWORD;
    }

    @Override
    public String getVoiceKeyword() {
        return VOICE_KEYWORD;
    }

    @Override
    public String[] getCommands() {
        return new String[]{STATUS};
    }

    @Override
    public String execute(String args) {
        String cleanedArgs = cleanArgsFromKeywords(args);
        switch (cleanedArgs) {
            case STATUS:
                return getStatus();
            default:
                return "unknown option\n" + getHelp();
        }
    }

    protected String getStatus() {
        StringBuilder statusBuilder = new StringBuilder();

        for (Command command : registry.getCommandSet()) {
            statusBuilder.append(command.getInfo()).append("\n");
        }

        return statusBuilder.toString();
    }


}
