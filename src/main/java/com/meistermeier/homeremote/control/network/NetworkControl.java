package com.meistermeier.homeremote.control.network;

import com.google.common.collect.Sets;
import com.meistermeier.homeremote.command.Command;
import com.meistermeier.homeremote.command.CommandRegistry;
import com.meistermeier.homeremote.control.Control;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.Set;

/**
 * Simple network support to control application (e.g. telnet)
 *
 * @author Gerrit Meier
 */
public class NetworkControl implements Control {

    private final static Logger LOG = LoggerFactory.getLogger(NetworkControl.class);

    private ServerSocket serverSocket;
    private final CommandRegistry commandRegistry;
    private final Set<Thread> clientThreadSet = Sets.newHashSet();
    private ServerThread serverThread;

    public NetworkControl(CommandRegistry commandRegistry, int port) {
        this.commandRegistry = commandRegistry;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            LOG.error("Could not acquire port to listen for incoming commands", e);
            throw new RuntimeException("Could not acquire port to listen for incoming commands", e);
        }
    }

    public void start() {
        serverThread = new ServerThread();
        serverThread.start();
    }

    @Override
    public void shutdown() {
        serverThread.shutdown();
    }

    class ServerThread extends Thread {
        boolean running = true;

        @Override
        public void run() {
            while (running) {
                try {
                    Socket socket = serverSocket.accept();
                    Thread clientThread = new Thread(new NetworkControlHandler(socket));
                    clientThread.start();
                    clientThreadSet.add(clientThread);
                } catch (IOException e) {
                    LOG.error("An error occurs while accepting connections", e);
                }
            }
        }

        public void shutdown() {
            for (Thread thread : clientThreadSet) {
                thread.interrupt();
            }
            running = false;
            interrupt();
        }
    }


    class NetworkControlHandler implements Runnable {
        private final Socket socket;

        NetworkControlHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                boolean disconnected = false;
                InputStream inputStream = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
                LOG.info("opened connection to client {}", socket);
                while (!disconnected) {
                    String commandInput;
                    while (!(commandInput = br.readLine()).equals("quit")) {
                        Optional<Command> commandOptional = commandRegistry.getNetworkCommand(commandInput);
                        if (commandOptional.isPresent()) {
                            Command command = commandOptional.get();
                            String result = command.execute(commandInput);

                            writer.write(result);
                            writer.write("\n");
                            writer.flush();

                        } else {
                            StringBuilder helpResponse = new StringBuilder("could not find any matching command\nAvailable commands are:\n");
                            for (Command command : commandRegistry.getCommandSet()) {
                                helpResponse.append(command.getNetworkKeyword()).append("\n");
                            }
                            writer.write(helpResponse.toString());
                            writer.flush();
                        }
                    }

                    if (commandInput.equals("quit")) {
                        disconnected = true;
                    }
                }

                LOG.info("closed connection to client {}", socket);
                writer.close();
                inputStream.close();
                socket.close();

            } catch (IOException e) {
                LOG.error("Something went wrong while talking with the client", e);
            }
        }
    }
}
