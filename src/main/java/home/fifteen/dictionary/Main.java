package home.fifteen.dictionary;

import home.fifteen.dictionary.controller.SceneSwitcher;
import home.fifteen.dictionary.dictionary.Sources;
import home.fifteen.dictionary.task.TaskBuilder;
import home.fifteen.dictionary.utils.commandline.CommandLineOptions;
import home.fifteen.dictionary.utils.logger.ColorfulLogger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application  {

    private final static ColorfulLogger LOGGER = ColorfulLogger.getLogger();
    private final static String FXML = "MainWindow.fxml";
    private final static String CHOOSER = "ChooseDictionary.fxml";


    public static void main(String[] args) {



        CommandLineOptions commandLineOptions = new CommandLineOptions();
        commandLineOptions.init(args);

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





}
