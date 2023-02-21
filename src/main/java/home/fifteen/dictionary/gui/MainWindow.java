package home.fifteen.dictionary.gui;

import home.fifteen.dictionary.controller.SceneSwitcher;
import home.fifteen.dictionary.dictionary.Sources;
import home.fifteen.dictionary.task.TaskBuilder;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainWindow {

    private static MainWindow mainWindow;
    private final SceneSwitcher controller;
    private final Scene scene;



    private MainWindow() {
        String fxml = "MainWindow.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = new Pane();
        try {
            root = loader.load( ClassLoader.getSystemResourceAsStream(fxml) );

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        scene = new Scene(root);
        controller = loader.getController();

        Sources.init();

        TaskBuilder taskBuilder = new TaskBuilder(Sources.getGetters());
        taskBuilder.init();

        controller.setTaskBuilder(taskBuilder);
        ((Initializable)controller).initialize(null,null);


    }

    public static synchronized MainWindow getInstance(){
        if(mainWindow == null){
            mainWindow = new MainWindow();
        }
        return mainWindow;
    }

    public SceneSwitcher getController() {
        return controller;
    }

    public Scene getScene() {
        return scene;
    }
}
