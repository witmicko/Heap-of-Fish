package app;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import models.Fish;

/**
 * Created by michal on 18/04/2014.
 */
public class Link extends Line {
//    private Fish source;
//    private Fish target;
    private FishImageView sourceView;
    private FishImageView targetView;

//    public Link(Point2D p1, Point2D p2, Fish source, Fish target,FishImageView srcView,FishImageView trgView){
    public Link(Point2D p1, Point2D p2,FishImageView srcView,FishImageView trgView){
        this.sourceView = srcView;
        this.targetView = trgView;

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
        link(sourceView,targetView);
    }

    public Link(Link another){
        this.sourceView = another.sourceView;
        this.targetView = another.targetView;
        this.setStartX(another.getStartX());
        this.setStartY(another.getStartY());
        this.setEndX(another.getEndX());
        this.setEndY(another.getEndY());
        this.setStrokeWidth(2.0);
    }
    public boolean link(FishImageView sourceView, FishImageView targetView){
        Fish source = sourceView.getFish();
        Fish target = targetView.getFish();
        if (source.addFish(target)){
//            this.source = source;
            this.sourceView = sourceView;
//            this.target = target;
            this.targetView = targetView;
            return true;
        }else{
            return false;
        }

    }

    public void unlink(){
//        source.removeFish(target);
//        source = null;
//        target = null;
    }

    public boolean linkedToType(Fish fish){
        return targetView.getFish().getClass() == fish.getClass();
    }

    public void unlinkModeOn(){
            this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(event);
                Link l = (Link)event.getTarget();
                Pane assignRefsPane = (Pane)l.getParent();
                assignRefsPane.getChildren().remove(l);
                l.unlink();
                sourceView.removeLink(l);
                targetView.removeLink(l);
            }
        });
    }
    public void unlinkModeOff(){
        this.setOnMouseClicked(null);
    }

    public void destroy(){
        sourceView.getFish().removeFish(this.targetView.getFish());
        sourceView.removeLink(this);
        targetView.removeLink(this);
    }
    public boolean linked(Fish fish){
        return sourceView.getFish().equals(fish) || targetView.getFish().equals(fish);
    }

    public boolean localVarSource(){
        return this.sourceView instanceof LocalVarView;
    }

    public FishImageView getSourceView(){
        return sourceView;
    }
}
