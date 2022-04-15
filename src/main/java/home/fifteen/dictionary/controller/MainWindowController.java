package home.fifteen.dictionary.controller;

import home.fifteen.dictionary.Main;
import home.fifteen.dictionary.dictionary.DictionaryGetter;
import home.fifteen.dictionary.dictionary.Sources;
import home.fifteen.dictionary.task.Task;
import home.fifteen.dictionary.task.TaskBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MainWindowController implements Initializable {

    private final Logger log = Main.getLog();

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        task = new Task();
        Sources.init();
        log.info(Sources.getGetters().toString());

        for(DictionaryGetter getter : Sources.getGetters()){
            task.addDictionary(getter.getDictionary());
            log.fine(getter.getDictionary().toString());
        }

        task.prepareTask();
        log.info(task.toString());

        question.setText(task.getTask());
        answer1.setText(task.getAnswers().get(0));
        answer2.setText(task.getAnswers().get(1));
        answer3.setText(task.getAnswers().get(2));
        answer4.setText(task.getAnswers().get(3));
        answer5.setText(task.getAnswers().get(4));

    }


    @FXML
    public void switchScene(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load( ClassLoader.getSystemResourceAsStream(Main.getChooserLink()) );

            Node    node = (Node) event.getSource();
            Stage window = (Stage)( node.getScene().getWindow() );
            Scene  scene = new Scene( root );
            window.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
