package home.fifteen.dictionary.controller;

import home.fifteen.dictionary.dictionary.DictionaryGetter;
import home.fifteen.dictionary.dictionary.Sources;
import home.fifteen.dictionary.task.Task;
import home.fifteen.dictionary.task.TaskBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable , SceneSwitcher {


    @FXML
    private Label question;
    @FXML
    private Button answer1;
    @FXML
    private Button answer2;
    @FXML
    private Button answer3;
    @FXML
    private Button answer4;
    @FXML
    private Button answer5;
    @FXML
    private Label rightInaRow;
    @FXML
    private Label rightAnswers;

    private Task task;
    private TaskBuilder taskBuilder ;
    private Initializable chooserController;
    private Scene dictionaryChooser;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(taskBuilder!=null){
            makeTask();

        }


    }


    @FXML
    public void switchScene(ActionEvent event){

        Node    node = (Node) event.getSource();
        Stage window = (Stage)( node.getScene().getWindow() );
        chooserController.initialize(null,null);
        window.setScene(dictionaryChooser);

    }

    @FXML
    public void checkAnswer(ActionEvent event){
        String answer = ((Button)event.getSource()).getText();
        task.checkAnswer(answer);

        rightAnswers.setText( String.valueOf(task.getCorrectAnswers()));
        rightInaRow.setText(String.valueOf(task.getCorrectAnswersInARow()));

        makeTask();
    }

    @Override
    public void setSecondaryScene(Scene scene) {
        dictionaryChooser = scene;
    }

    public void setSecondaryController(Initializable chooserController){
        this.chooserController = chooserController;
    }

    @Override
    public void setTaskBuilder(TaskBuilder taskBuilder) {
        this.taskBuilder = taskBuilder;
        log.info(taskBuilder.toString());

        makeTask();
    }


    private void makeTask(){
        task = this.taskBuilder.getTask();
        task.prepareTask();
        log.info(task.toString());

        question.setText(task.getTask());
        answer1.setText(task.getAnswers().get(0));
        answer2.setText(task.getAnswers().get(1));
        answer3.setText(task.getAnswers().get(2));
        answer4.setText(task.getAnswers().get(3));
        answer5.setText(task.getAnswers().get(4));

        rightAnswers.setText( String.valueOf(task.getCorrectAnswers()));
        rightInaRow.setText(String.valueOf(task.getCorrectAnswersInARow()));
    }
}
