package com.onlylemi.mapview.dagger;

import com.onlylemi.mapview.BLEMapActivity;
import com.onlylemi.mapview.search.SearchController;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by admin on 2017/11/15.
 */
@Singleton
@Component(modules = {LocationSearchModule.class})
public interface BLEMapComponent {
    void inject(BLEMapActivity bleMapActivity);
    SearchController getSearch();
}
