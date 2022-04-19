package home.fifteen.dictionary.controller;


import home.fifteen.dictionary.dictionary.DictionaryGetter;
import home.fifteen.dictionary.dictionary.Sources;
import home.fifteen.dictionary.task.TaskBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class ChooserController implements Initializable , SceneSwitcher{

    @FXML
    VBox sourceListView;

    private Initializable mainWindowController;
    private Scene main;
    private final Map<CheckBox , DictionaryGetter> sources = new HashMap<>() ;
    private TaskBuilder taskBuilder ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sources.clear();
        sourceListView.getChildren().clear();
        readAllSources();

    }


    @FXML
    public void switchScene(ActionEvent event){

        getSelectedSource();
//        ((SceneSwitcher)mainWindowController).setTaskBuilder(taskBuilder);

        Node    node = (Node) event.getSource();
        Stage window = (Stage)( node.getScene().getWindow() );
        window.setScene(main);
        mainWindowController.initialize(null,null);
    }

    @Override
    public void setSecondaryController(Initializable mainWindowController){
        this.mainWindowController = mainWindowController;
    }

    @Override
    public void setTaskBuilder(TaskBuilder taskBuilder) {
        this.taskBuilder = taskBuilder;
        log.info(taskBuilder.toString());
    }

    @Override
    public void setSecondaryScene(Scene scene){
        main = scene;
    }


    private void readAllSources(){

        Sources.init();
        log.info("Sources : " + Sources.getGetters().toString());
        for(DictionaryGetter getter : Sources.getGetters()){
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(true);
            checkBox.setText(getter.getDictionary().getNameForList());
            sources.put(checkBox , getter);
            sourceListView.getChildren().add(checkBox);
            log.fine(getter.getDictionary().toString());
        }

        log.info( String.valueOf(sourceListView.getChildren().size()));
    }

    private void getSelectedSource(){
        Set<DictionaryGetter> getters = new HashSet<>();


        for(CheckBox checkBox : sources.keySet()){

            if(checkBox.isSelected()){
                getters.add(sources.get(checkBox));
                log.info(checkBox.getText());
            }
        }
        log.info("getters number : " + getters.size());
        log.fine(taskBuilder.toString());
        taskBuilder.setGetters(getters);
        log.fine(taskBuilder.toString());
    }
}
