package models;

import javafx.scene.image.Image;

/**
 * Created by michal on 16/03/14.
 */
public class YellowFish extends Fish {
    YellowFish myFriend;

    public YellowFish() {
        super("yellow");
    }

    @Override
    public boolean addFish(Fish fish) {
        if (fish instanceof YellowFish) {
            myFriend = (YellowFish) fish;
            return true;
        } else return false;
    }

    @Override
    public boolean removeFish(Fish fish) {
        if (fish instanceof YellowFish) {
            myFriend = null;
            return true;
        } else return false;
    }

    @Override
    public boolean canBeLinked(Fish fish) {
        if (fish instanceof YellowFish) return true;
        else return false;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\n\t" + "Friend: " + myFriend;
    }
}
