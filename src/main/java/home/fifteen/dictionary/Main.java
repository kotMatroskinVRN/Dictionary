package home.fifteen.dictionary;

import home.fifteen.dictionary.dictionary.*;
import home.fifteen.dictionary.dictionary.Sources;
import home.fifteen.dictionary.task.Task;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main extends Application  {

    private final static Logger log = Logger.getLogger(ClassLoader.class.getName());

    private final static String FXML = "MainWindow.fxml";
    private final static String CHOOSER = "ChooseDictionary.fxml";

    private static Parent root , chooser ;


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

        Stage stage   = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        root = loader.load( ClassLoader.getSystemResourceAsStream(FXML) );
        loader = new FXMLLoader();
        chooser = loader.load( ClassLoader.getSystemResourceAsStream(CHOOSER) );



        Scene scene = new Scene(root);

        stage.setResizable(false);
        stage.setScene(scene);
//        stage.getIcons().add(
//                new Image(
//                        Objects.requireNonNull(
//                                ClassLoader.getSystemResourceAsStream("icon/SeaBattle_32x32.PNG")
//                        )
//                )
//        );




        stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void encodeDictionary(String fileName) {

        File file = new File(fileName);

        boolean factor = file.isFile() && file.getName().equals(fileName);

        if(factor) {
            EncoderBase64 encoder = new EncoderBase64(fileName);
            encoder.encode();

        }else{
            String string = String.format(
                    "\nWrong File Name : %s\nPlease specify file name from working directory." ,
                    fileName
            );
            log.severe( string );
        }

        System.exit(0);
    }

    public static Logger getLog(){
        return log;
    }

    public static Parent getRoot() {
        return root;
    }

    public static Parent getChooser() {
        return chooser;
    }

    public static String getChooserLink(){
        return CHOOSER;
    }

    public static String getMainLink(){
        return FXML;
    }

}
