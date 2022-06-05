package home.fifteen.dictionary.controller;


import home.fifteen.dictionary.dictionary.getters.DictionaryGetter;
import home.fifteen.dictionary.dictionary.Sources;
import home.fifteen.dictionary.task.TaskBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class ChooserController implements Initializable , SceneSwitcher{

    @FXML
    VBox sourceListView;
    @FXML
    VBox onlineSourceListView;
    @FXML
    Button internetSource;
    @FXML
    Button download;
    @FXML
    Label onlineSourceHeader;
    @FXML
    SplitPane mainSplit;

    private Initializable mainWindowController;
    private Scene main;
    private final Map<CheckBox , DictionaryGetter> sources = new TreeMap<>(Comparator.comparing(Labeled::getText)) ;
    private final Map<CheckBox , DictionaryGetter> sourcesOnline = new TreeMap<>(Comparator.comparing(Labeled::getText)) ;
    private TaskBuilder taskBuilder ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        onlineSourceHeader.setVisible(false);
        download.setDisable(true);
        mainSplit.setDividerPositions(0.9);

        sources.clear();
        sourceListView.getChildren().clear();
        readOfflineSources();

    }


    @FXML
    public void switchScene(ActionEvent event){

        getSelectedSource();

        Node    node = (Node) event.getSource();
        Stage window = (Stage)( node.getScene().getWindow() );
        window.setScene(main);
        mainWindowController.initialize(null,null);
    }

    @FXML
    public void getOnlineSources(ActionEvent event){
        onlineSourceHeader.setVisible(true);
        mainSplit.setDividerPositions(0.4);

        readOnlineSources();
    }

    @FXML void download(ActionEvent event){

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




    private void readOfflineSources(){

        log.info("Sources : " + Sources.getGetters().toString());
        for(DictionaryGetter getter : Sources.getGetters()){
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(true);
            checkBox.setText(getter.getDictionary().getNameForList());
            sources.put(checkBox , getter);

            checkBox.setOnAction((ae)->getSelectedSource());
            log.fine(getter.getDictionary().toString());
        }
        sourceListView.getChildren().addAll(sources.keySet());

        log.info( String.valueOf(sourceListView.getChildren().size()));
    }

    private void readOnlineSources(){

        Sources.initOnline();

        log.info("Sources : " + Sources.getOnlineGetters().toString());
        for(DictionaryGetter getter : Sources.getOnlineGetters()){
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(true);
            checkBox.setText(getter.getDictionary().getNameForList());
            sourcesOnline.put(checkBox , getter);

            checkBox.setOnAction((ae)->getSelectedSource());
            log.fine(getter.getDictionary().toString());
        }
        onlineSourceListView.getChildren().addAll(sourcesOnline.keySet());

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

        if(getters.size()==0){
            for(CheckBox checkBox : sources.keySet()){
                getters.add(sources.get(checkBox));
                checkBox.setSelected(true);
                break;
            }
        }

        taskBuilder.setGetters(getters);
        log.fine(taskBuilder.toString());
    }
}
