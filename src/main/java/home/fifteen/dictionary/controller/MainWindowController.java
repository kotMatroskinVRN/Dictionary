package home.fifteen.dictionary.controller;

import home.fifteen.dictionary.gui.CorrectAnswerView;
import home.fifteen.dictionary.gui.DictionaryChooser;
import home.fifteen.dictionary.gui.FontForQuestion;
import home.fifteen.dictionary.task.Task;
import home.fifteen.dictionary.task.TaskBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable , SceneSwitcher {


    @FXML
    private Text question;
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
    @FXML
    private HBox bottomPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    Tooltip tooltip;

    private Task task;
    private TaskBuilder taskBuilder ;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(taskBuilder!=null){
            makeTask();
            tooltip.setText(taskBuilder.getGettersToolTip());
        }

    }


    @FXML
    public void switchScene(ActionEvent event){

        Node    node = (Node) event.getSource();
        Stage window = (Stage)( node.getScene().getWindow() );
        Initializable initializable = (Initializable) DictionaryChooser.getInstance().getController();
        initializable.initialize(null,null);
        window.setScene(DictionaryChooser.getInstance().getScene());

    }

    @FXML
    public void checkAnswer(ActionEvent event){
        String answer = ((Button)event.getSource()).getText();
        boolean isCorrect = task.checkAnswer(answer);

        rightAnswers.setText( String.valueOf(task.getCorrectAnswers()));
        rightInaRow.setText(String.valueOf(task.getCorrectAnswersInARow()));

        String text = task.getTask().getWord() + " = " + task.getCorrectAnswer();

        makeTask();

        if(!isCorrect){
            log.printVerbose(text);
            showCorrectAnswer(text);
        }


    }




    @Override
    public void setTaskBuilder(TaskBuilder taskBuilder) {
        this.taskBuilder = taskBuilder;
        tooltip.setText(taskBuilder.getGettersToolTip());
        log.printVerbose(taskBuilder.toString());

        makeTask();
    }


    private void makeTask(){

        task = this.taskBuilder.getTask();
        task.prepareTask();
        log.printVerbose(task.toString());


        question.setText(task.getTask().getWord());
        answer1.setText(task.getAnswers().get(0));
        answer2.setText(task.getAnswers().get(1));
        answer3.setText(task.getAnswers().get(2));
        answer4.setText(task.getAnswers().get(3));
        answer5.setText(task.getAnswers().get(4));

        borderPane.setBottom(bottomPane);
        rightAnswers.setText( String.valueOf(task.getCorrectAnswers()));
        rightInaRow.setText(String.valueOf(task.getCorrectAnswersInARow()));

        computeQuestionTextSize();

    }

    private void computeQuestionTextSize() {

        FontForQuestion adaptiveFont = new FontForQuestion(question.getFont());
        adaptiveFont.adjustSize(question.getText().length());

        question.applyCss();
        question.setFont( adaptiveFont.getFont() );

    }

    private void showCorrectAnswer(String text){
        CorrectAnswerView answerView = new CorrectAnswerView(text);
        borderPane.setBottom(answerView);
    }
}
