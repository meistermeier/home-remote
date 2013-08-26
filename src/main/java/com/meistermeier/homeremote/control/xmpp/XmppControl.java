package com.meistermeier.homeremote.control.xmpp;

import com.meistermeier.homeremote.command.Command;
import com.meistermeier.homeremote.command.CommandRegistry;
import com.meistermeier.homeremote.control.Control;
import org.apache.commons.lang.StringUtils;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Straight forward implementation for a xmpp client listening for orders.
 *
 * @author Gerrit Meier
 */
public class XmppControl implements Control {

    private static final Logger LOG = LoggerFactory.getLogger(XmppControl.class);

    private static final Presence AVAILABLE_STATUS = new Presence(Presence.Type.available);
    private final String user;
    private final String password;
    private final String host;
    private final int port;
    private final String service;
    private final CommandRegistry commandRegistry;

    private XMPPConnection connection;
    private XmppControl.XmppThread xmppThread;
    private XmppConnectionThread xmppConnectionThread;

    public XmppControl(String user, String password, String host, int port, String service, CommandRegistry commandRegistry) {
        if (StringUtils.isBlank(user) || StringUtils.isBlank(host) || port == 0 || StringUtils.isBlank(service)) {
            throw new RuntimeException("not all params set to connect");
        }
        this.commandRegistry = commandRegistry;

        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;
        this.service = service;

    }


    public void initConnection() {
        xmppConnectionThread = new XmppConnectionThread();
        xmppConnectionThread.start();
    }

    @Override
    public void shutdown() {
        xmppConnectionThread.shutdown();
    }

    private class XmppConnectionThread extends Thread {
        boolean running = true;

        public void connect() throws XMPPException {
            ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(host, port, service);
            connection = new XMPPConnection(connectionConfiguration);

            connection.connect();
            connection.login(user, password);
            connection.sendPacket(AVAILABLE_STATUS);

            xmppThread = new XmppThread();
            xmppThread.run();

        }

        public void shutdown() {
            if (xmppThread != null) {
                xmppThread.shutdown();
                xmppThread.interrupt();
            }
            running = false;
            interrupt();
        }

        @Override
        public void run() {
            boolean connected = true;
            while (running) {
                try {
                    connect();
                    connected = true;
                    LOG.info("successful connected to xmpp server");
                } catch (XMPPException e) {
                    if (connected) {
                        LOG.info("cannot connect to xmpp server: {}", e.getMessage());
                    }
                    connected = false;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // ignore interrupt
                }
            }
        }
    }

    private class XmppThread extends Thread {

        boolean running = true;

        @Override
        public void run() {
            connection.getChatManager().addChatListener((chat, createdLocally) -> {
                if (!createdLocally) {
                    String participant = chat.getParticipant();
                    LOG.info("chat started with {}", participant);
                    chat.addMessageListener((newChat, message) -> {
                        try {
                            String commandInput = message.getBody();
                            Optional<Command> commandOptional = commandRegistry.getNetworkCommand(commandInput);
                            if (commandOptional.isPresent()) {
                                Command command = commandOptional.get();
                                String result = command.execute(commandInput);
                                newChat.sendMessage(result);
                            }
                            if (LOG.isDebugEnabled()) {
                                LOG.debug(commandInput);
                            }

                        } catch (XMPPException e) {
                            LOG.error("could not send message", e);
                        }
                    });
                }
            });

            // leave connection open
            while (running) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // ignore interrupt
                }
            }

            connection.disconnect();

        }

        public void shutdown() {
            running = false;
            interrupt();
        }
    }

}
