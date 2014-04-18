package models;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 * Created by michal on 18/04/2014.
 */
public class Link extends Line {
    private Fish source;
    private Fish target;

    public Link(Point2D p1, Point2D p2, Fish source, Fish target){
//        link(source,target);

        this.setStartX(p1.getX());
        this.setStartY(p1.getY());

        this.setEndX(p2.getX());
        this.setEndY(p2.getY());

        this.setStrokeWidth(2.0);

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Link l = (Link)event.getTarget();
                l.setStrokeWidth(4);
            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Link l = (Link)event.getTarget();
                l.setStrokeWidth(2.0);
            }
        });
//        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println("line clicked");
//
//                Link l = (Link)event.getTarget();
//                Pane assignRefsPane = (Pane)l.getParent();
//                assignRefsPane.getChildren().remove(l);
//                l.unlink();
//                System.out.println();
//            }
//        });
    }

    public boolean link(Fish source , Fish target){
        if (source.addFish(target)){
            this.source = source;
            this.target = target;
            return true;
        }else{
//            Pane assignRefsPane = (Pane)this.getParent();
//            assignRefsPane.getChildren().remove(this);
            return false;
        }

    }

    public void unlink(){
        source.removeFish(target);
        source = null;
        target = null;
    }

    public boolean linkedToType(Fish fish){
        return source.linkedToType(fish);
    }

    public void unlinkModeOn(){
            this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("line clicked");

                Link l = (Link)event.getTarget();
                Pane assignRefsPane = (Pane)l.getParent();
                assignRefsPane.getChildren().remove(l);
                l.unlink();
                System.out.println();
            }
        });
    }
    public void unlinkModeOff(){
        this.setOnMouseClicked(null);
    }
}
