package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import models.Fish;

import java.net.URL;
import java.util.*;

/**
 * Created by michal on 16/03/14.
 */
final public class GarbageCollectController implements Initializable {
    private MainApp app;

    @FXML Pane garbCollPane;
    private List<FishImageView> fishImageViews = new ArrayList<>(20);
    private List<Link> links = new ArrayList<>();
    private LocalVarView[] locVars = new LocalVarView[4];
    private boolean initialMark = false;
    private boolean foundOrphans = false;


    @FXML
    public void copyFish() {
        initialMark = foundOrphans = false;
        garbCollPane.getChildren().removeAll(fishImageViews);
        fishImageViews = app.assignRefController.getAllImages();
        for (FishImageView fishView : fishImageViews) {
            garbCollPane.getChildren().add(fishView);
            for (Link l : fishView.targetLinks) {
                links.add(l);
                garbCollPane.getChildren().add(l);
                l.toBack();
            }
        }
    }

    @FXML
    public void step() {
        while (true) {
            if (!initialMark) {
                mark();
                Collections.sort(fishImageViews, new FishImageView.X_ORDER());
                break;
            }
            if (!foundOrphans) {

                findOrphans();
                break;
            }
        }

    }

    private void findOrphans() {
        step:
        {
            for (FishImageView f : fishImageViews) {
                if (f.connectedToLocVar(f)) {
                    f.setImage("black");
                    f.setMarked(true);
//                    break step;
                }
            }
            List<FishImageView> unmarked = new ArrayList<>();
            for (FishImageView f : fishImageViews) {
                if (!f.getMarked()) {
                    unmarked.add(f);
                }
            }
            fishImageViews.removeAll(unmarked);
            garbCollPane.getChildren().removeAll(unmarked);
            for (FishImageView f : fishImageViews) {
                f.setImage(f.getFish().getImage());
            }
        }



    }

    private void mark() {
        for (FishImageView f : fishImageViews) {
            f.setImage("white");
        }
        initialMark = true;
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
