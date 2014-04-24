package gc;

import models.Fish;

import java.util.Iterator;
import java.util.List;

/**
 * Created by michal on 17/03/14.
 */
public class Heap {
    HandlePool handlePool;
    ObjectPool objectPool;


    public Heap(int size) {
        handlePool = new HandlePool(size);
        objectPool = new ObjectPool(size);
    }

    public List<Fish> getHandlePoolList() {
        return handlePool.fishList;
    }

    public List<Fish> getObjectPoolList() {
        return objectPool.objectPoolList;
    }

    public void addElement(Fish fish) {
        addToHandlePool(fish);
        addToObjectPool(fish);
    }

    public void clearHeap() {
        handlePool.clear();
        objectPool.clear();
    }

    private void addToObjectPool(Fish fish) {
        int fishSize = fish.getClass().getDeclaredFields().length;
        int poolSize = objectPool.objectPoolList.size();
        for (int i = 0; i < poolSize; i++) {
            if (objectPool.objectPoolList.get(i) == null) {
                boolean fits = fitCheck(i, fishSize);
                if (fits) {
                    for (int k = 0; k < fishSize; k++) {
                        objectPool.objectPoolList.set(i + k, fish);
                    }
                    break;
                }
            }
        }
    }

    private boolean fitCheck(int startIndex, int fishSize) {
        if (objectPool.objectPoolList.size() - startIndex < fishSize) return false;
        for (int k = 0; k <= fishSize; k++) {
            if (objectPool.objectPoolList.get(startIndex + k) != null) {
                return false;
            }
        }
        return true;
    }

    private void addToHandlePool(Fish fish) {
        for (int i = 0; i < handlePool.fishList.size(); i++) {
            if (handlePool.fishList.get(i) == null) {
                handlePool.fishList.set(i, fish);
                break;
            }
        }
    }
}
