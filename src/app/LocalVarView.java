package app;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import models.Fish;
import models.Link;
import models.RedFish;

/**
 * Created by michal on 20/04/2014.
 */
public class LocalVarView extends FishImageView {
    private FishImageView localVar;

    public LocalVarView(Fish fish) {
        super(fish);
    }

    public LocalVarView(Fish fish, double x, double y) {
        super(fish, x, y);
    }
    public LocalVarView(LocalVarView another){
        super((FishImageView)another);

    }

    public void setLocalVar(FishImageView fishImageView) {
        this.localVar = fishImageView;
    }


}
