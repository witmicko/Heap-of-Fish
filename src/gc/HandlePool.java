package gc;

import models.Fish;
import models.RedFish;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by michal on 16/03/14.
 */
public class HandlePool {
    List <Fish>fishList = new ArrayList<>();

    public HandlePool(int size) {
        fishList = new ArrayList<>(size);
        for (int i = 0; i < size; i++){
            fishList.add(null);
        }
    }
    public void clear(){
        int size = fishList.size();
        fishList.clear();
        for (int i = 0; i < size; i++){
            fishList.add(null);
        }
    }
}
