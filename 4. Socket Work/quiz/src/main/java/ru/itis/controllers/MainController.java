package ru.itis.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import ru.itis.sockets.client.ListenToServerTask;
import ru.itis.sockets.client.SocketClient;
import sun.plugin.javascript.navig.Anchor;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController implements Initializable {

    public SocketClient player;

    public ExecutorService service;

    @FXML
    public Button button;

    @FXML
    public Label playerName;

    @FXML
    public Label oppName;

    @FXML
    public TextField nameInput;

    @FXML
    public Text questionText;

    @FXML
    public Pane questionPane;

    @FXML
    public AnchorPane answerPane;

    public ToggleGroup answers;
    @FXML
    public RadioButton answer1;
    @FXML
    public RadioButton answer2;
    @FXML
    public RadioButton answer3;
    @FXML
    public RadioButton answer4;

    @FXML
    public Label playerScore;

    @FXML
    public Label playerMark;

    @FXML
    public Label oppScore;

    @FXML
    public Label oppMark;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        player = new SocketClient("localhost", 7777);

        Image buttonImage = new Image("/png/button.png", 100, 100, false, true);
        ImageView buttonImageView = new ImageView(buttonImage);
        button.setGraphic(buttonImageView);
        button.setOnAction(event -> player.sendMessage(nameInput.getText()));

        answers = new ToggleGroup();
        answer1.setToggleGroup(answers); answer2.setToggleGroup(answers);
        answer3.setToggleGroup(answers); answer4.setToggleGroup(answers);

        ListenToServerTask listenToServerTask = new ListenToServerTask(player.getFromServer(), this);
        service = Executors.newFixedThreadPool(1);
        service.execute(listenToServerTask);
    }

    public void sendAnswer(Integer rightAnswerNum) {
        Integer answer = 0;
        if (answer1.isSelected()) answer = 1;
        else if (answer2.isSelected()) answer = 2;
        else if (answer3.isSelected()) answer = 3;
        else if (answer4.isSelected()) answer = 4;
        player.sendMessage("" + answer + rightAnswerNum);
    }

    public void closeGame() {
        service.shutdown();
        player.closeConnection();
    }
}
