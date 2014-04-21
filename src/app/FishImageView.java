package app;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import models.Fish;
import models.Link;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by michal on 17/03/14.
 */
public class FishImageView extends ImageView{
    protected List<Link> sourceLinks = new ArrayList<>();
    protected List<Link> targetLinks = new ArrayList<>();
    private final double FITSIZE = 25;
    public String mode;
    private Fish fish;
    private boolean localVar;

    private class Delta {
        double x, y;
    }

    public FishImageView(Fish fish) {
        this.fish = fish;
        this.setImage(fish.getImage());
        setFitSize();
    }

    public FishImageView(Fish fish, double x, double y) {
        this.fish = fish;
        this.setImage(fish.getImage());
        this.setX(x);
        this.setY(y);
        setFitSize();
    }
    public FishImageView(FishImageView another){
        this.fish = another.getFish();
        this.setImage(another.getFish().getImage());
        this.setX(another.getX());
        this.setY(another.getY());
        this.setLayoutX(another.getLayoutX());
        this.setLayoutY(another.getLayoutY());
        //
        this.setScaleX(another.getScaleX());
        this.setScaleY(another.getScaleY());
        //
        setFitSize();
        for(Link l:another.sourceLinks){
            Link link = new Link(l);
            this.sourceLinks.add(link);
        }
        for(Link l:another.targetLinks){
            Link link = new Link(l);
            this.targetLinks.add(link);
        }
    }

    public void setMoveMode() {
        clearEventHandler();
        this.mode = "move";
        final Delta dragDelta = new Delta();
        final FishImageView imageView = this;

        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = imageView.getLayoutX() - mouseEvent.getSceneX();
                dragDelta.y = imageView.getLayoutY() - mouseEvent.getSceneY();
                imageView.setCursor(javafx.scene.Cursor.MOVE);
                mouseEvent.consume();
            }
        });
        imageView.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                imageView.setCursor(javafx.scene.Cursor.HAND);

                Point2D p;
                for (Link l : sourceLinks) {
                    p = getGlobalCoords(imageView);
                    l.setStartX(p.getX());
                    l.setStartY(p.getY());
                }
                for (Link l : targetLinks) {
                    p = getGlobalCoords(imageView);
                    l.setEndX(p.getX());
                    l.setEndY(p.getY());
                }
                mouseEvent.consume();
            }
        });
        imageView.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                imageView.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                imageView.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
                mouseEvent.consume();
            }
        });
        imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                imageView.setCursor(javafx.scene.Cursor.HAND);
                mouseEvent.consume();
            }
        });
    }

    public void setLinkMode() {
        clearEventHandler();
        this.mode = "link";
        final FishImageView imageView = this;

        this.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = imageView.startDragAndDrop(TransferMode.LINK);
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                db.setContent(content);

                imageView.setScaleX(1.1);
                imageView.setScaleY(1.1);

                event.consume();
            }
        });
        this.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.LINK);
                imageView.setScaleX(1.1);
                imageView.setScaleY(1.1);
                event.consume();
            }
        });

        this.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (imageView != dragEvent.getGestureSource()) {
                    imageView.setScaleX(1.0);
                    imageView.setScaleY(1.0);
                }
            }
        });
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                FishImageView srcView = (FishImageView) event.getGestureSource();
                FishImageView trgView = (FishImageView) event.getTarget();
                trgView.setScaleX(1.0);
                trgView.setScaleY(1.0);

                if (!(srcView instanceof LocalVarView)) {
                    srcView.setScaleX(1.0);
                    srcView.setScaleY(1.0);
                }

                setupLink(srcView, trgView);
                event.consume();
            }
        });
    }


    private void setupLink(FishImageView srcView, FishImageView trgView) {
        Fish source = srcView.getFish();
        Fish target = trgView.getFish();

        if (source.canBeLinked(target)) {
            Point2D p1 = getGlobalCoords(srcView);
            Point2D p2 = getGlobalCoords(trgView);

            Pane assignRefsPane = (Pane) this.getParent();
            for (int i = 0; i < srcView.sourceLinks.size(); i++) {
                Link l = srcView.sourceLinks.get(i);
                boolean linked = l.linkedToType(target);
                if (linked) {
                    assignRefsPane.getChildren().remove(l);
                    l.destroy();
                }
            }
            if (srcView instanceof LocalVarView && source.getClass() != target.getClass()) {
                System.out.println("cannot link these");
            } else {
                Link link = new Link(p1, p2, srcView, trgView);
                srcView.sourceLinks.add(link);
                trgView.targetLinks.add(link);
                assignRefsPane.getChildren().add(link);
                link.toBack();
            }
        }
    }

    public void setUnlinkMode() {
        for (Link l : targetLinks) {
            l.unlinkModeOn();
        }
    }

    public void clearEventHandler() {
        for (Link l : sourceLinks) {
            l.unlinkModeOff();
        }
        this.setCursor(Cursor.DEFAULT);
        this.setOnMousePressed(null);
        this.setOnMouseDragged(null);
        this.setOnMouseReleased(null);
        this.setOnDragDetected(null);
        this.setOnMouseEntered(null);
    }

    protected Point2D getGlobalCoords(FishImageView fishImageView) {
        int offset = (fishImageView instanceof LocalVarView) ? 15 : 28;
        double x = (fishImageView.getBoundsInLocal().getMinX() + fishImageView.getBoundsInLocal().getMaxX()) / 2;
        double y = ((fishImageView.getBoundsInLocal().getMinY() + fishImageView.getBoundsInLocal().getMaxY()) / 2) - offset;

        return fishImageView.localToScene(x, y);
    }

    public boolean containsFish(Fish fish) {
        return this.fish == fish;
    }

    public void removeLink(Link link) {
        sourceLinks.remove(link);
        targetLinks.remove(link);
    }

    public Fish getFish() {
        return this.fish;
    }

    protected void setAsLocalVar() {
        this.localVar = true;
    }

    private void setFitSize() {
        this.setFitWidth(FITSIZE + 5);
        this.setFitHeight(FITSIZE);
    }
}
