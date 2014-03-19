package models;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Created by michal on 17/03/14.
 */
public class FishImageView extends ImageView {
    private final double FITSIZE = 25;
    public String mode;
    private class Delta {double x, y;}

    private Fish fish;
    private double x;
    private double y;
    public FishImageView(Fish fish) {
        this.fish = fish;
        this.setImage(fish.getImage());
        setFitSize();
    }

    public FishImageView(Fish fish, double x, double y) {
        this.fish = fish;
        this.setImage(fish.getImage());
        this.x = x;
        this.y = y;
        setFitSize();
    }

    private void setFitSize() {
        this.setFitWidth(FITSIZE+5);
        this.setFitHeight(FITSIZE);
    }


    public void setMoveMode(){
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
        this.mode  = "link";
        final Delta dragDelta = new Delta();
        final FishImageView imageView = this;
        final Path path = new Path();
        path.setStrokeWidth(1);
        path.setStroke(Color.BLACK);

        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                path.getElements().clear();
                path.getElements().add(new MoveTo(mouseEvent.getX(), mouseEvent.getY()));

                imageView.setCursor(Cursor.CROSSHAIR);
                mouseEvent.consume();
            }
        });

        imageView.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                path.getElements().add(new LineTo(mouseEvent.getX(), mouseEvent.getY()));
                mouseEvent.consume();
            }
        });
        imageView.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                imageView.getParent().getChildrenUnmodifiable().add(path);

                imageView.setCursor(javafx.scene.Cursor.HAND);
                mouseEvent.consume();
            }
        });

//        imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//
//                imageView.setCursor(javafx.scene.Cursor.HAND);
//                mouseEvent.consume();
//            }
//        });



    }

    public void clearEventHandler(){
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEvent.consume();
            }
        });

        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEvent.consume();
            }
        });
        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEvent.consume();
            }
        });

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEvent.consume();
            }
        });

    }

    public void setUnlinkMode() {

    }
}
