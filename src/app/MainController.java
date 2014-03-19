package app;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by michal on 16/03/14.
 */
public class MainController implements Initializable {
    private MainApp app;


    public void drawAllFish(Event event){
        app.assignRefController.drawAllFish();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initializing the parent controller");
    }

    public void setApp(MainApp app) {
        this.app = app;
    }

}
