package home.fifteen.dictionary;

import home.fifteen.dictionary.dictionary.Sources;
import home.fifteen.dictionary.gui.DictionaryChooser;
import home.fifteen.dictionary.gui.MainWindow;
import home.fifteen.dictionary.utils.commandline.CommandLineOptions;
import home.fifteen.dictionary.utils.lastrun.LastRunReader;
import home.fifteen.dictionary.utils.lastrun.LastRunWriter;
import home.fifteen.dictionary.utils.lastrun.ObjectInputStreamReader;
import home.fifteen.dictionary.utils.lastrun.ObjectOutputStreamWriter;
import home.fifteen.dictionary.utils.logger.ColorfulLogger;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application  {

    private final static ColorfulLogger LOGGER = ColorfulLogger.getLogger();


    public static void main(String[] args) {

        CommandLineOptions commandLineOptions = new CommandLineOptions();
        commandLineOptions.init(args);

        Sources.init();



        MainWindow.getInstance(); //init main scene
        DictionaryChooser.getInstance(); // init dictionary chooser scene

        LastRunReader lastRunReader = new ObjectInputStreamReader();
        if(lastRunReader.checkSource()){
            lastRunReader.read();
        }

        LastRunWriter lastRunWriter = new ObjectOutputStreamWriter();
        lastRunWriter.write(
                MainWindow.getInstance().getController().getTaskBuilder().getGetters()
        );





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
