package app;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import models.Fish;

import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by michal on 16/03/14.
 */
final public class AssignReferencesController implements Initializable {
    private MainApp app;

    private enum Mode {MOVE, LINK, UNLINK}

    private Mode mode = Mode.MOVE;
    @FXML Pane assignRefsPane;
    @FXML ToggleGroup modeToggle;
    List<FishImageView> fishImageViews = new ArrayList<>(20);

    public void drawAllFish() {
        List<Fish> fishList = app.heap.getHandlePoolList();

        if (fishImageViews.size() != fishList.size()) {
            for (Fish f : fishList) {
                boolean drawn = false;
                for (FishImageView fishView : fishImageViews) {
                    if (fishView.containsFish(f)) drawn = true;
                }
                if (!drawn && f!=null) {
                    FishImageView fishImageView = initImage(f);
                    fishImageViews.add(fishImageView);
                    assignRefsPane.getChildren().add(fishImageView);
                }
            }
        }
    }

    private FishImageView initImage(Fish fish) {
        final FishImageView imageView = new FishImageView(fish);
        switch (mode) {
            case MOVE:
                imageView.setMoveMode();
                break;
            case LINK:
                imageView.setLinkMode();
                break;
            case UNLINK:
                imageView.setUnlinkModeOn();
                break;
        }
        imageView.setX(Math.random() * app.scene.getWidth());
        imageView.setY(Math.random() * 300);
        return imageView;
    }


    @FXML
    public void changeMode(MouseEvent event) {
        String selectedToggle = ((RadioButton) event.getSource()).getId();
        for (FishImageView f : fishImageViews) {
            switch (selectedToggle) {
                case "move":
                    mode = Mode.MOVE;
                    f.setMoveMode();
                    f.setUnlinkModeOff();
                    break;
                case "link":
                    mode = Mode.LINK;
                    f.setLinkMode();
                    f.setUnlinkModeOff();
                    break;
                case "unlink":
                    mode = Mode.UNLINK;
                    f.setUnlinkModeOn();
                    break;
            }
        }
    }

    public void setApp(MainApp app) {
        this.app = app;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initializing the assignRefs controller");
    }
}
