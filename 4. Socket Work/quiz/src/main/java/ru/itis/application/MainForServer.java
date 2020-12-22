package ru.itis.application;

import ru.itis.sockets.server.EchoServerSocket;

public class MainForServer {
    public static void main(String[] args) {
        EchoServerSocket serverSocket = new EchoServerSocket();
        serverSocket.start(7777);
    }
}
