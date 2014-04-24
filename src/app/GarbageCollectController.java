package app;

import javafx.collections.FXCollections;
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
//    private LocalVarView[] locVars = new LocalVarView[4];
    private boolean initialMark = false;
    private boolean foundOrphans = false;
    private boolean removed = false;
    private int step = 0;


    @FXML
    public void copyFish() {
        initialMark = foundOrphans = removed = false;
        garbCollPane.getChildren().removeAll(fishImageViews);
        List<FishImageView>temp = app.assignRefController.getAllAsList();
        fishImageViews = app.assignRefController.hardCopy(temp);

        for (FishImageView fishView : fishImageViews) {
            garbCollPane.getChildren().add(fishView);
            for (Link l : fishView.targetLinks) {
                links.add(l);
                garbCollPane.getChildren().add(l);
                l.toBack();
            }
        }
        System.out.println();
    }

    @FXML
    public void step() {
        System.out.println(step);
        if (!initialMark) {
            mark();
            Collections.sort(fishImageViews, new FishImageView.X_ORDER());
        } else if (!foundOrphans) {
            findOrphans(step);
            step++;
        } else if (!removed) removeOrphans();

    }

    private void removeOrphans() {
        List<FishImageView> unmarked = new ArrayList<>();
        List<Link> unmarkedLinks = new ArrayList<>();
        for (FishImageView f : fishImageViews) {
            if (!f.getMarked()) {
                unmarkedLinks.addAll(f.targetLinks);
                unmarked.add(f);
            }
        }
        fishImageViews.removeAll(unmarked);
        links.removeAll(unmarkedLinks);
        garbCollPane.getChildren().removeAll(unmarked);
        garbCollPane.getChildren().removeAll(unmarkedLinks);

        List<Fish> fishs = new ArrayList<>();
        for (FishImageView f : fishImageViews) {
            f.setMarked(false);
            if (!(f instanceof LocalVarView)) {
                fishs.add(f.getFish());
            }
            f.setImage(f.getFish().getImage());
        }
        app.allocController.redraw(fishs);

        removed = true;
        step = 0;
    }

    private void findOrphans(int step) {
        if (step < fishImageViews.size()) {
            FishImageView fView = fishImageViews.get(step);
            fView.setImage("grey");
            try {
                if (fView.getMarked() || fView.connectedToLocVar(fView)) {
                    fView.setImage("black");
                    fView.setMarked(true);
                    for (Link link : fView.sourceLinks) {
                        link.getTargetView().setMarked(true);
                    }
                    for (Link link : fView.targetLinks) {
                        link.getSourceView().setMarked(true);
                    }
                }

            } catch (StackOverflowError e) {
                System.out.println();
//                    for (Link link : fView.targetLinks) {
//                        if (link.getSourceView().getMarked()) {
//                            fView.setImage("black");
//                            fView.setMarked(true);
//                            break step;
//                        }
//                    }
//                    for (Link link : fView.sourceLinks) {
//                        if (link.getTargetView().getMarked()) {
//                            fView.setImage("black");
//                            fView.setMarked(true);
//                            break step;
//                        }
//                    }
//                    e.printStackTrace();
            }
            if (step == fishImageViews.size() - 1) foundOrphans = true;
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
        fishImageViews.clear();
        links.clear();
        step = 0;
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
