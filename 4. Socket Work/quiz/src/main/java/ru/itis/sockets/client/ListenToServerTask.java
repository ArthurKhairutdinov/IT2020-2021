package ru.itis.sockets.client;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import ru.itis.controllers.MainController;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class ListenToServerTask extends Task<Void> {
    private BufferedReader fromServer;
    private MainController controller;
    private String playerInitialName;
    private int playerHaveNames;
    private Boolean state;
    private Integer answerNum;

    public ListenToServerTask(BufferedReader fromServer, MainController controller) {
        this.fromServer = fromServer;
        this.controller = controller;
        this.state = true;
        this.playerHaveNames = 0;
    }

    @Override
    protected Void call() throws Exception {
        while (state) {
            try {
                String messageFromServer = fromServer.readLine();
                if (messageFromServer != null) {
                    if (messageFromServer.startsWith("name;") || messageFromServer.startsWith("opponent;")) {
                        playerHaveNames++;
                        if (messageFromServer.startsWith("name;")) {
                            playerInitialName = messageFromServer.split(";")[2];
                            Platform.runLater(() -> {
                                controller.nameInput.setDisable(true);
                                controller.nameInput.setVisible(false);
                                controller.button.setDisable(true);
                                controller.playerName.setText(messageFromServer.split(";")[1]);
                                controller.playerName.setVisible(true);
                            });
                        } else {
                            Platform.runLater(() -> {
                                controller.oppName.setText(messageFromServer.split(";")[1]);
                                controller.oppName.setVisible(true);
                            });
                        }
                        if (playerHaveNames == 2) {
                            Platform.runLater(() -> {
                                controller.questionPane.setDisable(false);
                                controller.questionPane.setVisible(true);
                                controller.button.setOnAction(event -> controller.sendAnswer(answerNum));
                                controller.button.setDisable(false);
                                controller.player.sendMessage("?");
                            });
                        }
                    } else if (messageFromServer.startsWith("true") || messageFromServer.startsWith("false")) {
                        String[] messageArray = messageFromServer.split(";");
                        Integer answerNum = Integer.parseInt(messageArray[1]) - 1;
                        try {
                            Platform.runLater(() -> {
                                if (messageArray[2].equals(playerInitialName)) controller.playerMark.setVisible(true);
                                else controller.oppMark.setVisible(true);
                                controller.button.setDisable(true);
                            });
                            Thread.sleep(1000);
                            if (Boolean.valueOf(messageArray[0])) {
                                Platform.runLater(() ->  {
                                    RadioButton rb = (RadioButton)controller.answerPane.getChildren().get(answerNum);
                                    rb.setStyle("-fx-background-color: green");
                                    rb.setSelected(false);
                                    rb.setDisable(true);
                                });
                                if (messageArray[2].equals(playerInitialName)) Platform.runLater(() -> {
                                        Integer score = Integer.parseInt(controller.playerScore.getText());
                                        score += 500;
                                        controller.playerScore.setText(score.toString());
                                        controller.playerMark.setVisible(false);
                                    });
                                else Platform.runLater(() -> {
                                    Integer score = Integer.parseInt(controller.oppScore.getText());
                                    score += 500;
                                    controller.oppScore.setText(score.toString());
                                    controller.oppMark.setVisible(false);
                                });
                                Thread.sleep(2000);
                                Platform.runLater(() -> {
                                    controller.button.setDisable(false);
                                    List<Node> rbs = controller.answerPane.getChildren();
                                    for (Node node : rbs) {
                                        node.setDisable(false);
                                        node.setStyle("");
                                    }
                                    controller.player.sendMessage("?");
                                });
                            } else {
                                Platform.runLater(() ->  {
                                    RadioButton rb = (RadioButton)controller.answerPane.getChildren().get(answerNum);
                                    rb.setStyle("-fx-background-color: red");
                                    rb.setSelected(false);
                                    rb.setDisable(true);
                                });
                                if (messageArray[2].equals(playerInitialName)) Platform.runLater(() -> {
                                    Integer score = Integer.parseInt(controller.playerScore.getText());
                                    score -= 200;
                                    controller.playerScore.setText(score.toString());
                                    controller.playerMark.setVisible(false);
                                });
                                else Platform.runLater(() -> {
                                    Integer score = Integer.parseInt(controller.oppScore.getText());
                                    score -= 200;
                                    controller.oppScore.setText(score.toString());
                                    controller.oppMark.setVisible(false);
                                });
                                Thread.sleep(2000);
                                Platform.runLater(() -> controller.button.setDisable(false));
                            }
                        } catch (InterruptedException e) {}
                    } else if (messageFromServer.equals("end")) {
                        Platform.runLater(() -> controller.answerPane.setVisible(false));
                        if (Integer.parseInt(controller.playerScore.getText()) > Integer.parseInt(controller.oppScore.getText())) {
                            Platform.runLater(() -> controller.questionText.setText(controller.playerName.getText() + " is a winner!"));
                        } else if (Integer.parseInt(controller.playerScore.getText()) < Integer.parseInt(controller.oppScore.getText())) {
                            Platform.runLater(() -> controller.questionText.setText(controller.oppName.getText() + " is a winner!"));
                        } else {
                            Platform.runLater(() -> controller.questionText.setText("Draw!"));
                        }
                        state = false;
                        Platform.runLater(() -> controller.closeGame());
                    } else {
                        String[] questionArray = messageFromServer.split(";");
                        String question = questionArray[0];
                        answerNum = Integer.parseInt(questionArray[5]);

                        Platform.runLater(() -> {
                            controller.questionText.setText(question);

                            controller.answer1.setText(questionArray[1]);
                            controller.answer2.setText(questionArray[2]);
                            controller.answer3.setText(questionArray[3]);
                            controller.answer4.setText(questionArray[4]);
                        });
                    }
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        return null;
    }
}
