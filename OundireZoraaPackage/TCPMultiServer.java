package OundireZoraaPackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The TCPMultiServer class represents a multithreaded TCP server that accepts
 * connections from multiple clients and handles communication between them.
 */
public class TCPMultiServer {

    private ServerSocket serverSocket;
    private int port;
    static int size = 1024;
    private List<ConnectionThread> connectionThreads;

    /**
     * Constructs a TCPMultiServer instance with the specified port.
     *
     * @param port The port number on which the server will listen for client connections.
     * @throws IOException If an I/O error occurs while creating the ServerSocket.
     */
    public TCPMultiServer(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.connectionThreads = new ArrayList<>();
    }

    /**
     * Launches the TCP multiServer, continuously listening for incoming client connections
     * and creating separate threads to handle each connection.
     */
    public void launch() {
        System.out.println("TCP MultiServer is listening on port " + port);

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                ConnectionThread connectionThread = new ConnectionThread(clientSocket);
                connectionThreads.add(connectionThread);
                connectionThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The ConnectionThread class represents a thread responsible for managing one client connection.
     */
    private class ConnectionThread extends Thread {
        private Socket clientSocket;
        private OutputStream out;
        private InputStream in;
        private String clientName;

        /**
         * Constructs a ConnectionThread instance for a given client socket.
         *
         * @param clientSocket The client socket for the connection.
         */
        public ConnectionThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                this.out = clientSocket.getOutputStream();
                this.in = clientSocket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Runs the thread, handling communication with the connected client.
         */
        public void run() {
            byte[] buffer = new byte[size];

            try {
                handleUsernameInput();

                while (true) {
                    int bytesRead = in.read(buffer);
                    if (bytesRead == -1) {
                    }
                    String clientData = new String(buffer, 0, bytesRead);
                    System.out.println("Received from " + clientName + ": " + clientData);

                    broadcast(clientName + ": " + clientData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                    connectionThreads.remove(this); // Remove from the list of active connections
                    broadcast(clientName + " has left the chat.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Handles the input of the client's username.
         *
         * @throws IOException If an I/O error occurs while interacting with the client.
         */
        private void handleUsernameInput() throws IOException {
            byte[] buffer = new byte[size];
            if (clientName == null) {
                sendMessage("Enter your username: ");
                int bytesRead = in.read(buffer);
                if (bytesRead != -1) {
                    clientName = new String(buffer, 0, bytesRead);
                    System.out.println("Client " + clientSocket.getInetAddress() + " set username to: " + clientName);
                    broadcast(clientName + " has joined the chat.");
                }
            }
        }

        /**
         * Sends a message to the connected client.
         *
         * @param message The message to be sent.
         */
        private void sendMessage(String message) {
            try {
                out.write((message + "\n").getBytes());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Broadcasts a message to all connected clients.
     *
     * @param message The message to be broadcasted.
     */
    private synchronized void broadcast(String message) {
        for (ConnectionThread connectionThread : connectionThreads) {
            connectionThread.sendMessage(message);
        }
    }

    /**
     * The main method to start the TCP multiServer. It takes a port number as a command-line argument.
     *
     * @param args Command-line arguments. Provide the port number to run the server on.
     * @throws IOException If an I/O error occurs while creating or running the server.
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            int port = Integer.parseInt(args[0]);
            TCPMultiServer multiServer = new TCPMultiServer(port);
            multiServer.launch();
        } else {
            System.out.println("Usage: java TCPMultiServer <port>");
        }
    }
}
