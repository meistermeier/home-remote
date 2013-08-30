package com.meistermeier.homeremote.command;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Some build in commands that don't depend on a external lib and other systems, connections etc.
 *
 * @author Gerrit Meier
 */
public class BuildInCommand implements Command {

    private static final String NAME = "BuildInCommand";

    private static final String NETWORK_KEYWORD = "system";
    private static final String XMPP_KEYWORD = "system";
    private static final String VOICE_KEYWORD = "system";

    private static final String OPTION_STATUS = "status";

    private static final String OPTION_IP = "ip";

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
    public String[] getOptions() {
        return new String[]{OPTION_STATUS};
    }

    @Override
    public String execute(String args) {
        String cleanedArgs = cleanArgsFromKeywords(args);
        switch (cleanedArgs) {
            case OPTION_STATUS:
                return getStatus();
            case OPTION_IP:
                try {
                    return getIP();
                } catch (UnknownHostException e) {
                    return "could not retrieve my ip address.";
                } catch (SocketException e) {
                    return "could not retrieve my ip address.";
                }
            default:
                return "unknown option\n" + getHelp();
        }
    }

    /**
     * Retrieves the current status and help for control and commands registered in the application.
     */
    protected String getStatus() {
        StringBuilder statusBuilder = new StringBuilder();

        for (Command command : registry.getCommandSet()) {
            statusBuilder.append(command.getInfo()).append("\n");
        }

        return statusBuilder.toString();
    }

    /**
     * Returns the current IP address of the device. Helpful in combination with the auto connected xmpp client
     * to find the device in your local network.
     */
    protected String getIP() throws UnknownHostException, SocketException {
        StringBuilder hostAddressBuilder = new StringBuilder();

        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            if (!networkInterface.isUp() || networkInterface.isLoopback() || networkInterface.isVirtual()) {
                continue;
            }

            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                hostAddressBuilder.append(inetAddress.getHostAddress()).append("\n");
            }
        }
        return hostAddressBuilder.toString();
    }

}
