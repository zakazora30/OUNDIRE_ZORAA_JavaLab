# OUNDIRE_ZORAA_JavaLab

This project is a simple demonstration of two types of communication between clients using both UDP and TCP protocols. It consists of four main classes: `UDPServer`, `UDPClient`, `TCPMultiServer`, and `TCPClient`.

## Table of Contents

- [Project Description](#project-description)
- [Main Classes](#main-classes)
  - [1. UDPServer](#1-udpserver)
  - [2. UDPClient](#2-udpclient)
  - [3. TCPMultiServer](#3-tcpmultiserver)
  - [4. TCPClient](#4-tcpclient)
- [Getting Started](#getting-started)
- [Usage](#usage)

## Project Description

You can consider this project as a chat application that enables communication between clients using both UDP and TCP protocols. 
- In a first time, it's a simple communication between the UDPServer & the UDPClient using UDP. 
- In a second time, Clients can send and receive messages in a chat-like interface using TCP.

## Main Classes

### 1. UDPServer

The `UDPServer` class represents the server side for handling UDP communication. It listens for incoming messages and displays them on the console.

### 2. UDPClient

The `UDPClient` class is responsible for sending messages to the server using UDP. It reads user input from the console and transmits the messages to the server.

### 3. TCPMultiServer

The `TCPMultiServer` class handles multiple client connections using the TCP protocol. It echoes messages to all connected clients and displays client information.

### 4. TCPClient

The `TCPClient` class is a client with a graphical user interface (GUI) for interacting with the chat application. It allows users to enter a username and send messages in a chat window.

## Getting Started

To run the chat application, follow these steps:

1. Clone the repository: `git clone https://github.com/zakazora30/OUNDIRE_ZORAA_JavaLab.git`
2. Navigate to the project directory: `cd your-repo`
3. Compile the Java files: `javac OundireZoraaPackage/*.java`
4. Run the desired class using `java` command (e.g., `java OundireZoraaPackage.UDPServer 8080). Note that you should use the same port for the Server and the Client.

## Usage

### UDP Communication

To start the UDP communication:

#### UDPServer

``bash:
java OundireZoraaPackage.UDPServer 8080

#### UDPClient

``bash:
java OundireZoraaPackage.UDPClient localhost 8080

### TCP Communication

To start the UDP communication 'Chat Application):

#### TCPServer

``bash:
java OundireZoraaPackage.TCPMultiServer 8080

#### TCPClient

``bash:
java OundireZoraaPackage.TCPClient localhost 8080

