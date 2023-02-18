package home.fifteen.dictionary.controller;

import home.fifteen.dictionary.Main;
import home.fifteen.dictionary.task.TaskBuilder;
import home.fifteen.dictionary.utils.logger.ColorfulLogger;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import java.util.logging.Logger;

public interface SceneSwitcher {

    ColorfulLogger log = ColorfulLogger.getLogger();

//    void setSecondaryScene(Scene scene);
//    void setSecondaryController(Initializable controller);
    void setTaskBuilder(TaskBuilder taskBuilder);
}
