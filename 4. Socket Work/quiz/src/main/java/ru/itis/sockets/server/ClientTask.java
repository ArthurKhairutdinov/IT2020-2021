package ru.itis.sockets.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ClientTask implements Runnable {
    private Thread t;
    private BufferedReader fromClient;
    private PrintWriter backToClient;
    private PrintWriter forward;
    private boolean gotName;
    private boolean state;
    private Queue<String> questions;

    ClientTask(String name, BufferedReader fromClient, PrintWriter backToClient, PrintWriter forward, Queue<String> questions) {
        t = new Thread(this, name);
        this.fromClient = fromClient;
        this.backToClient = backToClient;
        this.forward = forward;
        this.state = true;
        this.gotName = false;
        this.questions = questions;
        t.start();
    }

    @Override
    public void run() {
        try {
            while (state) {
                String messageFromClient = fromClient.readLine();
                if (messageFromClient != null) {
                    try {
                        Integer parsedMessage = Integer.parseInt(messageFromClient);
                        Integer answer = parsedMessage / 10;
                        Integer rightAnswer = parsedMessage % 10;
                        String message = "" + answer + ";" + t.getName();
                        if (answer == rightAnswer) message = "true;" + message;
                        else message = "false;" + message;
                        forward.println(message);
                        backToClient.println(message);
                    } catch (NumberFormatException e) {
                        if (messageFromClient.startsWith("?") && t.getName().equals("1")) {
                            String question = !questions.isEmpty() ? questions.poll() : "end";
                            forward.println(question);
                            backToClient.println(question);
                        } else if (!gotName) {
                            gotName = true;
                            backToClient.println("name;" + messageFromClient + ";" + t.getName());
                            forward.println("opponent;" + messageFromClient);
                        } else if (messageFromClient.equals("close")) {
                            state = false;
                        }
                    }
                }
                Thread.sleep(10);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

