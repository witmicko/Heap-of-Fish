package models;

/**
 * Created by michal on 16/03/14.
 */
public class RedFish extends Fish {
    RedFish myFriend;
    BlueFish myLunch;
    YellowFish mySnack;

    public RedFish() {
        super("red");
    }

    @Override
    public boolean addFish(Fish fish) {
        if (fish instanceof RedFish){
            myFriend = (RedFish)fish;
            return true;
        }
        else if (fish instanceof BlueFish){
            myLunch = (BlueFish)fish;
            return true;
        }else if (fish instanceof YellowFish){
            mySnack = (YellowFish)fish;
            return true;
        }
        else return false;
    }

    @Override
    public boolean removeFish(Fish fish) {
        if (fish instanceof RedFish){
            myFriend = null;
            return true;
        }
        else if (fish instanceof BlueFish){
            myLunch = null;
            return true;
        }else if (fish instanceof YellowFish){
            mySnack = null;
            return true;
        }
        else return false;
    }
}
