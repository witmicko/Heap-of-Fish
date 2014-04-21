package models;

/**
 * Created by michal on 16/03/14.
 */
public class BlueFish extends Fish {
    BlueFish myFriend;
    YellowFish myLunch;

    public BlueFish() {
        super("blue");
    }

    @Override
    public boolean addFish(Fish fish) {
        if (fish instanceof BlueFish) {
            myFriend = (BlueFish) fish;
            return true;
        } else if (fish instanceof YellowFish) {
            myLunch = (YellowFish) fish;
            return true;
        } else return false;
    }

    @Override
    public boolean removeFish(Fish fish) {
        if (fish instanceof BlueFish) {
            myFriend = null;
            return true;
        } else if (fish instanceof YellowFish) {
            myLunch = null;
            return true;
        } else return false;
    }

    @Override
    public boolean canBeLinked(Fish fish) {
        if (fish instanceof YellowFish) return true;
        else if (fish instanceof BlueFish) return true;
        else return false;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\n\t" + "Friend: " + myFriend
                + "\n\t" + "Lunch : " + myLunch;
    }


}
