package com.meistermeier.homeremote.network;

import com.meistermeier.homeremote.command.Command;
import com.meistermeier.homeremote.command.CommandRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

/**
 * @author Gerrit Meier
 */
public class NetworkControl extends Thread {

    private final static Logger LOG = LoggerFactory.getLogger(NetworkControl.class);

    private ServerSocket serverSocket;
    private final CommandRegistry commandRegistry;

    public NetworkControl(CommandRegistry commandRegistry, int port) {
        this.commandRegistry = commandRegistry;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            LOG.error("Could not acquire port to listen for incomcing commands", e);

        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(new NetworkControlHandler(socket)).start();
            } catch (IOException e) {
                LOG.error("An error occurs while accepting connections", e);
            }
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
                        Optional<Command> commandOptional = commandRegistry.getCommand(commandInput);
                        if (commandOptional.isPresent()) {
                            Command command = commandOptional.get();
                            String result = command.evaluateAndExectue(commandInput);

                            writer.write(result);
                            writer.write("\n");
                            writer.flush();

                        }
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(commandInput);
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
