package com.meistermeier.homeremote.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Gerrit Meier
 */
public class NetworkControl extends Thread {

    private final static Logger LOG = LoggerFactory.getLogger(NetworkControl.class);
    private ServerSocket serverSocket;

    public NetworkControl() {
        try {
            serverSocket = new ServerSocket(1337);
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
                while (!disconnected) {

                    InputStream inputStream = socket.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                    String command;
                    while (!(command = br.readLine()).equals("quit")) {
                        LOG.debug(command);
                    }

                    if (command.equals("quit")) {
                        disconnected = true;
                    }

                }

                LOG.debug("closed connection to client");
                socket.close();

            } catch (IOException e) {
                LOG.error("Something went wrong while talking with the client", e);
            }
        }
    }
}
