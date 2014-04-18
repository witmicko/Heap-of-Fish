package gc;

import models.Fish;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal on 16/03/14.
 */
public class HandlePool {
    List <Fish>fishList;

    public HandlePool(int size) {
        fishList = new ArrayList<>(size);
        for (int i = 0; i < size; i++){
            fishList.add(null);
        }
    }
}
