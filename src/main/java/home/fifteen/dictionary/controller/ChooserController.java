package home.fifteen.dictionary.controller;

import home.fifteen.dictionary.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooserController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    public void switchScene(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load( ClassLoader.getSystemResourceAsStream(Main.getMainLink()) );

            Node    node = (Node) event.getSource();
            Stage window = (Stage)( node.getScene().getWindow() );
            Scene  scene = new Scene( root );
            window.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
