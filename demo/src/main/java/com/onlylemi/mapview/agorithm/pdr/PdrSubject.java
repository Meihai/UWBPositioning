package com.onlylemi.mapview.agorithm.pdr;

/**
 * Created by admin on 2017/12/17.
 */

public interface PdrSubject {
    public void registerObserver(PdrObserver o);

    public void removeObserver(PdrObserver o);

    public void notifyObservers();
}
