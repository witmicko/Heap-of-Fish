package app;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import models.Fish;
import models.FishImageView;

import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by michal on 16/03/14.
 */
public class AssignReferencesController implements Initializable {
    private MainApp app;

    private enum Mode {MOVE, LINK, UNLINK}

    ;
    private Mode mode = Mode.MOVE;

    @FXML Pane assignRefsPane;
    @FXML ToggleGroup modeToggle;
    List<FishImageView> fishImageViews = new ArrayList<>(20);

    public void drawAllFish() {
        assignRefsPane.getChildren().removeAll(fishImageViews);
        fishImageViews.clear();
        for (Fish f : app.heap.getHandlePoolList()) {
            if (f != null) {
                fishImageViews.add(initImage(f));
            }
        }
        assignRefsPane.getChildren().addAll(fishImageViews);
    }

    @FXML
    public void changeMode(MouseEvent event) {
        String selectedToggle = ((RadioButton) event.getSource()).getId();
        for (FishImageView f : fishImageViews) {
            switch (selectedToggle) {
                case "move":
                    mode = Mode.MOVE;
                    f.setMoveMode();
                    break;
                case "link":
                    mode = Mode.LINK;
                    setLinkMode();
                    f.clearEventHandler();
//                    f.setLinkMode();
                    break;
                case "unlink":
                    mode = Mode.UNLINK;
                    f.setUnlinkMode();
                    break;
            }
        }
    }

    private void setLinkMode() {
        final Path path = new Path();
        path.setStrokeWidth(1);
        path.setStroke(Color.BLACK);
        assignRefsPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                path.getElements().clear();
                path.getElements().add(new MoveTo(event.getX(), event.getY()));
                System.out.println(event);
            }
        });
        assignRefsPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                path.getElements().add(new LineTo(mouseEvent.getX(), mouseEvent.getY()));
                mouseEvent.consume();
            }
        });
        assignRefsPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                imageView.getParent().getChildrenUnmodifiable().add(path);

//                imageView.setCursor(javafx.scene.Cursor.HAND);
                mouseEvent.consume();
            }
        });
        if (path != null) {
            assignRefsPane.getChildren().add(path);
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
                imageView.setUnlinkMode();
                break;
        }

        imageView.setX(120);
        imageView.setY(Math.random() * 300);
        return imageView;
    }

    public void setApp(MainApp app) {
        this.app = app;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initializing the assignRefs controller");
    }
}
