package gc;

import models.Fish;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal on 17/03/14.
 */
public class ObjectPool {
    List<Fish>objectPoolList;

    public ObjectPool(int size){
        objectPoolList = new ArrayList<Fish>(size);
        for (int i = 0; i < size; i++){
            objectPoolList.add(null);
        }
    }
}
