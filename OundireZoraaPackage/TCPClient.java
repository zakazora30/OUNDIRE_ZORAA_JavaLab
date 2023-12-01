package OundireZoraaPackage;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * The TCPClientWithGUI class represents a simple chat client with a graphical user interface (GUI)
 * that allows users to connect to a server, enter a username, and send/receive chat messages.
 */
public class TCPClient {
    private Socket socket;
    private String serverHost;
    private int serverPort;
    private String username;
    private JTextArea chatArea;
    private JTextField inputField;

    /**
     * Constructs a TCPClientWithGUI instance with the specified server host and port.
     *
     * @param serverHost The host address of the server.
     * @param serverPort The port number on which the server is listening.
     * @throws IOException If an I/O error occurs while creating the socket.
     */
    public TCPClient(String serverHost, int serverPort) throws IOException {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.socket = new Socket(serverHost, serverPort);
    }

    /**
     * Starts the chat client with the graphical user interface.
     *
     * @throws IOException If an I/O error occurs during communication with the server.
     */
    public void start() throws IOException {
        System.out.println("Connected to server at " + serverHost + ":" + serverPort);

        // Create GUI
        JFrame frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        inputField = new JTextField();
        JButton sendButton = new JButton("Send");
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        BufferedReader serverResponseReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStream out = socket.getOutputStream();

        // Get username from the user only if it's not set
        if (username == null) {
            String initialPrompt = serverResponseReader.readLine();
            username = JOptionPane.showInputDialog(null, initialPrompt);
            out.write(username.getBytes("UTF-8"));
            out.flush();
            serverResponseReader.readLine();
        }

        // Create a separate thread to continuously read and display messages from the server
        Thread serverResponseThread = new Thread(() -> {
            try {
                while (true) {
                    String serverResponse = serverResponseReader.readLine();
                    if (serverResponse == null) {
                    }
                    appendToChat(serverResponse);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serverResponseThread.start();

        // Action listener for the Send button
        sendButton.addActionListener(e -> {
            try {
                String userInput = inputField.getText();
                if (!userInput.isEmpty()) {
                    out.write(userInput.getBytes("UTF-8"));
                    out.flush();
                    inputField.setText("");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Appends a message to the chat area in a thread-safe manner.
     *
     * @param message The message to be appended.
     */
    private void appendToChat(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
        });
    }

    /**
     * The main method to start the TCP chat client with GUI. It takes a server host and port as command-line arguments.
     *
     * @param args Command-line arguments. Provide the server host and port to connect to.
     * @throws IOException If an I/O error occurs while creating or running the client.
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java TCPClientWithGUI <serverHost> <serverPort>");
            return;
        }

        String serverHost = args[0];
        int serverPort = Integer.parseInt(args[1]);

        new TCPClient(serverHost, serverPort).start();
    }
}
