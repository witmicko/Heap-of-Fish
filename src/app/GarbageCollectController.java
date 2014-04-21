package app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.*;

import java.net.URL;
import java.util.*;

/**
 * Created by michal on 16/03/14.
 */
final public class GarbageCollectController implements Initializable {
    private MainApp app;

    @FXML Pane garbCollPane;
    private List<FishImageView> fishImageViews = new ArrayList<>(20);
    private List<Link>links = new ArrayList<>();
    private LocalVarView[] locVars = new LocalVarView[4];
    private boolean initialMark = false;


    @FXML public void copyFish(){
        garbCollPane.getChildren().removeAll(fishImageViews);
//        garbCollPane.getChildren().addAll(fishImageViews);

        fishImageViews  = app.assignRefController.getAllImages();
        for(FishImageView fishView:fishImageViews){
            garbCollPane.getChildren().add(fishView);
            for(Link l:fishView.targetLinks){
                links.add(l);
                garbCollPane.getChildren().add(l);
                l.toBack();
            }
        }
    }

    @FXML
    public void step() {
        if(!initialMark)mark();
        stepThroughLocVars();
    }

    private void stepThroughLocVars() {



    }

    private void mark() {
        for(FishImageView f:fishImageViews){
            f.setImage(new Image("res\\white_fish.png", true));
        }
    }

    @FXML
    public void reset(ActionEvent event) {
        garbCollPane.getChildren().removeAll(fishImageViews);
        garbCollPane.getChildren().removeAll(links);
        copyFish();
    }

    public void setApp(MainApp app) {
        this.app = app;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initializing the assignRefs controller");
    }
}
