package home.fifteen.dictionary;

import home.fifteen.dictionary.dictionary.Sources;
import home.fifteen.dictionary.dictionary.getters.DictionaryGetter;
import home.fifteen.dictionary.gui.DictionaryChooser;
import home.fifteen.dictionary.gui.MainWindow;
import home.fifteen.dictionary.task.TaskBuilder;
import home.fifteen.dictionary.utils.Settings;
import home.fifteen.dictionary.utils.commandline.CommandLineOptions;
import home.fifteen.dictionary.utils.logger.ColorfulLogger;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Main extends Application  {

    private final static ColorfulLogger LOGGER = ColorfulLogger.getLogger();


    public static void main(String[] args) {

        CommandLineOptions commandLineOptions = new CommandLineOptions();
        commandLineOptions.init(args);

        Sources.init();

        MainWindow.getInstance();
        DictionaryChooser.getInstance();

//        final String FILE_NAME_SERIALIZABLE = Settings.FILE_NAME_SERIALIZABLE.getProperty();
//        File file = new File(FILE_NAME_SERIALIZABLE);
//
//        if(file.exists()) {
//            try(
//                    FileInputStream fileInputStream = new FileInputStream(FILE_NAME_SERIALIZABLE);
//                    ObjectInputStream ois = new ObjectInputStream(fileInputStream)
//                    )
//            {
//                                Set<DictionaryGetter> serializedGetters = new HashSet<>();
//                serializedGetters.addAll((HashSet<DictionaryGetter>) ois.readObject());
//                ois.close();
//                LOGGER.printInfo("have been read");
//                Set<DictionaryGetter> getters = new HashSet<>();
//                for (DictionaryGetter serializedGetter : serializedGetters) {
//                    for (DictionaryGetter sourceGetter : Sources.getGetters()) {
//                        if (serializedGetter.getID().equals(sourceGetter.getID())) {
//                            getters.add(sourceGetter);
//                        }
//                    }
//                }
//                TaskBuilder taskBuilder = new TaskBuilder(getters);
//                MainWindow.getInstance().getController().setTaskBuilder(taskBuilder);
//
//
//            } catch (IOException | ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        }


        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setResizable(false);
        primaryStage.setScene(MainWindow.getInstance().getScene());
        primaryStage.getIcons().add(
                new Image(
                        Objects.requireNonNull(
                                ClassLoader.getSystemResourceAsStream("icons8-book-64.png")
                        )
                )
        );

        primaryStage.show();

    }





}
