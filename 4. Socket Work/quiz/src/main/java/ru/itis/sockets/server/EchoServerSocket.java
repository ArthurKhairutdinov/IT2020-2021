package ru.itis.sockets.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class EchoServerSocket {

    public void start(int port) {
        try {
            ServerSocket socket = new ServerSocket(port);

            BufferedReader qstReader = new BufferedReader(new InputStreamReader(
                            EchoServerSocket.class.getResourceAsStream("/txt/questions.txt")));
            LinkedList<String> initialQuestions = new LinkedList<>();
            String str = qstReader.readLine();
            while (str != null) {
                initialQuestions.add(str);
                str = qstReader.readLine();
            }
            qstReader.close();

            LinkedList<String> questions = new LinkedList<>();
            for (String question : initialQuestions) {
                String[] questionArray = question.split(";");
                Random random = new Random();
                Integer rightAnswer = random.nextInt(4);
                String[] answers = new String[4];
                answers[rightAnswer] = questionArray[1];

                LinkedList<String> wrongAnswers = new LinkedList<>();
                for (int i = 2; i < questionArray.length; i++) {
                    wrongAnswers.add(questionArray[i]);
                }
                Collections.shuffle(wrongAnswers);

                for (int i = 0; i < answers.length; i++) {
                    if (i != rightAnswer) answers[i] = wrongAnswers.poll();
                }

                question = questionArray[0] + ";";
                for (int i = 0; i < answers.length; i++) {
                    question += answers[i] + ";";
                }
                question += String.valueOf(rightAnswer + 1);
                questions.add(question);
            }
            Collections.shuffle(questions);

            Socket firstPlayer = socket.accept();
            Socket secondPlayer = socket.accept();

            ClientTask firstPlayerTask = new ClientTask("1",
                    new BufferedReader(new InputStreamReader(firstPlayer.getInputStream())),
                    new PrintWriter(firstPlayer.getOutputStream(), true),
                    new PrintWriter(secondPlayer.getOutputStream(), true),
                    questions);

            ClientTask secondPlayerTask = new ClientTask("2",
                    new BufferedReader(new InputStreamReader(secondPlayer.getInputStream())),
                    new PrintWriter(secondPlayer.getOutputStream(), true),
                    new PrintWriter(firstPlayer.getOutputStream(), true),
                    questions);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
