package app;

import models.Fish;

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
        super(another);
    }

    public void setLocalVar(FishImageView fishImageView) {
        this.localVar = fishImageView;
    }


}
