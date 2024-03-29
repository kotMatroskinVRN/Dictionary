package home.fifteen.dictionary.controller;


import home.fifteen.dictionary.dictionary.DictionaryGetterComparator;
import home.fifteen.dictionary.dictionary.getters.DictionaryGetter;
import home.fifteen.dictionary.dictionary.Sources;
import home.fifteen.dictionary.task.TaskBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
    Label onlineSourceHeader;
    @FXML
    SplitPane mainSplit;
    @FXML
    Tooltip tooltip;

    private Initializable mainWindowController;
    private Scene main;
    private final Map<CheckBox , DictionaryGetter> sources = new TreeMap<>(Comparator.comparing(Labeled::getText)) ;
    private final Map<CheckBox , DictionaryGetter> sourcesOnline = new TreeMap<>(Comparator.comparing(Labeled::getText)) ;
    private TaskBuilder taskBuilder ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        onlineSourceHeader.setVisible(false);
        mainSplit.setDividerPositions(0.9);

        sources.clear();
        sourcesOnline.clear();
        sourceListView.getChildren().clear();
        onlineSourceListView.getChildren().clear();
        readOfflineSources();

        parseTooltip();

    }



    @FXML
    public void switchScene(ActionEvent event){

        getSelectedSource();

        Node    node = (Node) event.getSource();
        Stage window = (Stage)( node.getScene().getWindow() );
        window.setScene(main);
        onlineSourceListView.getChildren().clear();
        mainWindowController.initialize(null,null);
    }

    @FXML
    public void getOnlineSources(ActionEvent event){
        onlineSourceHeader.setVisible(true);
        mainSplit.setDividerPositions(0.4);

        readOnlineSources();
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
            log.info( "Reading Dictionary name from DATA " + getter.getDictionary().getNameForList());
            log.info( "Reading Dictionary name from GUI " + checkBox.getText());
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

            highLightOnlineDictionaries();

            checkBox.setOnAction((ae)->getSelectedSource());
            log.fine(getter.getDictionary().toString());
        }
        onlineSourceListView.getChildren().clear();
        onlineSourceListView.getChildren().addAll(sourcesOnline.keySet());



        log.info( String.valueOf(sourceListView.getChildren().size()));
    }

    private void highLightOnlineDictionaries() {

        for( CheckBox checkBox : sourcesOnline.keySet()){
            DictionaryGetter onlineGetter = sourcesOnline.get(checkBox);
            for( DictionaryGetter offlineGetter : Sources.getGetters()){

                DictionaryGetterComparator comparator =
                        new DictionaryGetterComparator( onlineGetter , offlineGetter );
                comparator.compare();

                if(onlineGetter.isDownloadable()){
                    checkBox.setId("newerDictionary");
                    checkBox.applyCss();
                    checkBox.setSelected(false);
                }
                if(comparator.isEqual()){
                    checkBox.setId("equalDictionary");
                    checkBox.applyCss();
                    checkBox.setSelected(false);
                }else{
                    if(comparator.isSameName() && !comparator.isFirstNewer() ){
                        checkBox.setId("olderDictionary");
                        checkBox.applyCss();
                        checkBox.setSelected(false);
                    }

                }

            }
        }

    }


    private void getSelectedSource(){
        Set<DictionaryGetter> getters = new HashSet<>();


        for(CheckBox checkBox : sources.keySet()){

            if(checkBox.isSelected()){
                getters.add(sources.get(checkBox));
                log.info(checkBox.getText());
            }
        }
        log.info("Offline getters number : " + getters.size());

        for(CheckBox checkBox : sourcesOnline.keySet()){

            if(checkBox.isSelected()){
                DictionaryGetter getter = sourcesOnline.get(checkBox);
                getters.add(getter);
                log.info(checkBox.getText());
            }
        }
        log.info("Online getters number : " + getters.size());

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


    private void parseTooltip() {
        String string = tooltip.getText();
        string = string.replaceAll("\\\\n" , "\n");
        tooltip.setText(string);
    }
}
