package app;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import models.BlueFish;
import models.Fish;
import models.RedFish;
import models.YellowFish;

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
    protected List<FishImageView> fishImageViews = new ArrayList<>(20);
    private LocalVarView[] locVars;


    public void drawAllFish() {
        if (locVars == null) {
            locVars = new LocalVarView[4];
            drawLocalVars();
        }

        assignRefsPane.getChildren().removeAll(fishImageViews);
        assignRefsPane.getChildren().addAll(fishImageViews);
        List<Fish> fishList = app.heap.getHandlePoolList();

//        if (fishImageViews.size() != fishList.size()) {
        for (Fish f : fishList) {
            boolean drawn = false;
            for (FishImageView fishView : fishImageViews) {
                if (fishView.containsFish(f)) drawn = true;
            }
            if (!drawn && f != null) {
                FishImageView fishImageView = initImage(f);
                fishImageViews.add(fishImageView);
                assignRefsPane.getChildren().add(fishImageView);
            }
        }
//        }
    }

    private void drawLocalVars() {
        Fish fish = null;
        for (int i = 1; i <= 3; i++) {
            switch (i) {
                case 1:
                    fish = new RedFish();
                    break;
                case 2:
                    fish = new BlueFish();
                    break;
                case 3:
                    fish = new YellowFish();
                    break;
            }
            LocalVarView view = new LocalVarView(fish, 50, i * 50 + 50);
            locVars[i] = view;
//            view.setAsLocalVar();
            view.setScaleX(1.5);
            view.setScaleY(1.5);
            view.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Dragboard db = view.startDragAndDrop(TransferMode.LINK);
                    ClipboardContent content = new ClipboardContent();
                    content.putString("");
                    db.setContent(content);
                    event.consume();
                }
            });
            view.setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    event.acceptTransferModes(TransferMode.LINK);
                    event.consume();
                }
            });
            Rectangle rec = new Rectangle(50, 40, Color.GRAY);
            rec.setStrokeWidth(3);
            rec.setStroke(Color.BLACK);
            rec.setX(view.getX() - 10);
            rec.setY(view.getY() - 7);
            assignRefsPane.getChildren().add(rec);
            assignRefsPane.getChildren().add(view);
            view.setUnlinkMode();
            rec.toBack();
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

        Random rand = new Random();
        double x = 100 + rand.nextInt((int) app.scene.getWidth() - 200);
        imageView.setX(x);

        double y = rand.nextInt((int) (app.scene.getHeight() - 100));
        imageView.setY(y);
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
                    break;
                case "link":
                    mode = Mode.LINK;
                    f.setLinkMode();
                    break;
                case "unlink":
                    mode = Mode.UNLINK;
                    f.setUnlinkMode();
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

    private void printAll() {
        System.out.println("*******************");
        for (FishImageView fV : fishImageViews) {
            System.out.println(fV.getFish().toString() + " " + fV.getFish().hashCode());
        }
    }

    public List<FishImageView> getAllImages() {
        List<FishImageView> images = new ArrayList<>();
        List<FishImageView> tempList = new ArrayList<>();
        tempList.addAll(fishImageViews);
        tempList.addAll(Arrays.asList(locVars).subList(1, 4));

        Map<FishImageView,FishImageView>oldToNewMap = new HashMap<>();

        for (FishImageView tempView : tempList) {
            FishImageView newView;
            if (tempView instanceof LocalVarView) newView = new LocalVarView((LocalVarView) tempView);
            else newView = new FishImageView(tempView);
            images.add(newView);
            oldToNewMap.put(tempView,newView);
        }

        for(FishImageView f:images){
            for(FishImageView tempView:tempList) {
                for (Link l : f.sourceLinks) {
                    if (l.getTargetView().equals(tempView)) l.setTargetView(oldToNewMap.get(tempView));
                }
                for (Link l : f.targetLinks) {
                    if (l.getSourceView().equals(tempView)) l.setSourceView(oldToNewMap.get(tempView));
                }
            }
        }
            System.out.println();
            return images;
        }
    }
