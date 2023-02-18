package home.fifteen.dictionary.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CorrectAnswerView extends HBox {
    public CorrectAnswerView(String text) {
        super();

        Label label = new Label(text);

        setId("correctAnswer");
        getChildren().add(label);

    }
}
