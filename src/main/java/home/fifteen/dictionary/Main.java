package home.fifteen.dictionary;

import home.fifteen.dictionary.controller.SceneSwitcher;
import home.fifteen.dictionary.dictionary.*;
import home.fifteen.dictionary.dictionary.getters.DictionaryGetter;
import home.fifteen.dictionary.task.TaskBuilder;
import home.fifteen.dictionary.utils.EncoderBase64;
import home.fifteen.dictionary.utils.Settings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main extends Application  {

    private final static Logger LOGGER = Logger.getLogger(ClassLoader.class.getName());

    private final static String FXML = "MainWindow.fxml";
    private final static String CHOOSER = "ChooseDictionary.fxml";


    public static void main(String[] args) {

        try {
            LogManager.getLogManager().readConfiguration(
                    ClassLoader.getSystemResourceAsStream("logging.properties"));

        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e);
        }


        if(args.length>1 && args[0].equals("-encode")){
            encodeDictionary(args[1]);
        }

//        Task task = new Task();
//
//        Sources.init();
//        for(DictionaryGetter getter : Sources.getGetters()){
//            task.addDictionary(getter.getDictionary());
//            log.fine(getter.getDictionary().toString());
//        }
//
//        for(int i=0;i<10;i++) {
//            task.prepareTask();
//            log.info(task.toString());
//        }

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load( ClassLoader.getSystemResourceAsStream(FXML) );
            Scene main = new Scene(root);
            SceneSwitcher controller = loader.getController();


            Sources.init();
            TaskBuilder taskBuilder = new TaskBuilder(Sources.getGetters());
//            final String FILE_NAME_SERIALIZABLE = Settings.FILE_NAME_SERIALIZABLE.getProperty();
//            File file = new File(FILE_NAME_SERIALIZABLE);
//            if(file.exists()){
//                try {
//                    FileInputStream fileInputStream = new FileInputStream(FILE_NAME_SERIALIZABLE);
//                    ObjectInputStream ois = new ObjectInputStream(fileInputStream);
//                    Set<DictionaryGetter> serializedGetters = new HashSet<>();
//                    serializedGetters.addAll((HashSet<DictionaryGetter>) ois.readObject());
//                    ois.close();
//                    LOGGER.info("have been read");
//                    Set<DictionaryGetter> getters = new HashSet<>();
//                    for (DictionaryGetter serializedGetter : serializedGetters) {
//                        for(DictionaryGetter sourceGetter : Sources.getGetters()){
//                            if(serializedGetter.getID().equals(sourceGetter.getID())){
//                                getters.add(sourceGetter);
//                            }
//                        }
//                    }
//                    taskBuilder = new TaskBuilder(getters);
//
//                }catch (ClassNotFoundException e) {
//                    LOGGER.severe("Can not get access to " + FILE_NAME_SERIALIZABLE);
//                    taskBuilder = new TaskBuilder(Sources.getGetters());
//                }
//            }

            taskBuilder.init();

            loader = new FXMLLoader();
            root = loader.load( ClassLoader.getSystemResourceAsStream(CHOOSER) );
            Scene dictionaryChoose = new Scene(root);
            SceneSwitcher chooserController = loader.getController();

            controller.setSecondaryController( (Initializable) chooserController);
            controller.setSecondaryScene( dictionaryChoose );
            controller.setTaskBuilder(taskBuilder);
            chooserController.setSecondaryController( (Initializable) controller);
            chooserController.setSecondaryScene( main );
            chooserController.setTaskBuilder(taskBuilder);

            primaryStage.setResizable(false);
            primaryStage.setScene(main);
            primaryStage.getIcons().add(
                    new Image(
                            Objects.requireNonNull(
                                    ClassLoader.getSystemResourceAsStream("icons8-book-64.png")
                            )
                    )
            );




        primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void encodeDictionary(String fileName) {

        File file = new File(fileName);

        boolean factor = file.isFile() && file.getName().equals(fileName);

        if(factor) {
            EncoderBase64 encoder = new EncoderBase64(fileName);
            encoder.initFileInfo();
            encoder.encode();

        }else{
            String string = String.format(
                    "\nWrong File Name : %s\nPlease specify file name from working directory." ,
                    fileName
            );
            LOGGER.severe( string );
        }

        System.exit(0);
    }

    public static Logger getLogger(){
        return LOGGER;
    }

}
