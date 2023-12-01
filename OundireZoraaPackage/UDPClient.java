package OundireZoraaPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * The UDPClient class represents a UDP client that sends messages to a server.
 */
public class UDPClient {

    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int serverPort;

    /**
     * Constructs a UDPClient instance with the specified server host and port.
     *
     * @param serverHost The hostname or IP address of the server.
     * @param serverPort The port number on which the server is listening.
     */
    public UDPClient(String serverHost, int serverPort) {
        try {
            this.socket = new DatagramSocket();
            this.serverAddress = InetAddress.getByName(serverHost);
            this.serverPort = serverPort;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends messages entered by the user to the server.
     *
     * @throws IOException If an I/O error occurs while reading user input or sending messages.
     */
    public void sendMessages() throws IOException {
        try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("UDP Client is ready. Enter your messages:");

            String userInput;
            while ((userInput = console.readLine()) != null) {
                singleMessage(userInput);
            }
        }
    }

    /**
     * Sends a single message to the server.
     *
     * @param message The message to be sent.
     * @throws IOException If an I/O error occurs while sending the message.
     */
    private void singleMessage(String message) throws IOException {
        byte[] sendData = message.getBytes("UTF-8");
        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        socket.send(packet);
    }

    /**
     * The main method to start the UDP client. It takes a server host and port as command-line arguments.
     *
     * @param args Command-line arguments. Provide the server host and port to connect to.
     * @throws IOException If an I/O error occurs while creating or running the client.
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java UDPClient <serverHost> <serverPort>");
            return;
        }

        String serverHost = args[0];
        int serverPort = Integer.parseInt(args[1]);

        UDPClient client = new UDPClient(serverHost, serverPort);
        client.sendMessages();
    }
}
