package OundireZoraaPackage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * The UDPServer class represents a UDP server that listens for datagrams
 * from clients and processes the received data.
 */
public class UDPServer {

    private int port;
    private DatagramSocket serverSocket;
    static int size = 1024;

    /**
     * Constructs a UDPServer instance with the specified port.
     *
     * @param port The port number on which the server will listen for datagrams.
     * @throws SocketException If an error occurs while creating the DatagramSocket.
     */
    public UDPServer(int port) throws SocketException {
        this.port = port;
        this.serverSocket = new DatagramSocket(port);
    }

    /**
     * Launches the UDP server, continuously listening for incoming datagrams
     * and processing the received data.
     *
     * @throws IOException If an I/O error occurs while receiving datagrams.
     */
    public void launch() throws IOException {
        byte[] buffer = new byte[size];
        System.out.println("UDP Server is listening on port " + port);

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            serverSocket.receive(packet);
            processReceivedData(packet);
        }
    }

    /**
     * Processes the data received in a DatagramPacket and prints the information.
     *
     * @param packet The DatagramPacket containing the received data.
     * @throws UnsupportedEncodingException If the specified encoding is not supported.
     */
    private void processReceivedData(DatagramPacket packet) throws UnsupportedEncodingException {
        String clientAddress = packet.getAddress().getHostAddress();
        String receivedData = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
        System.out.println("Received from " + clientAddress + ": " + receivedData);
    }

    /**
     * The main method to start the UDP server. It takes a port number as a command-line argument.
     *
     * @param args Command-line arguments. Provide the port number to run the server on.
     * @throws IOException If an I/O error occurs while creating or running the server.
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            int port = Integer.parseInt(args[0]);
            UDPServer server = null;
            try {
                server = new UDPServer(port);
            } catch (SocketException e) {
                System.err.println("Error: The specified port is already in use.");
                return;
            }
            server.launch();
        } else {
            System.out.println("Usage: java UDPServer <port>");
        }
    }

    /**
     * Returns a string representation of the UDPServer object.
     *
     * @return A string representation of the object, including the port number.
     */
    public String toString() {
        return "UDPServer{port=" + port + "}";
    }
}
