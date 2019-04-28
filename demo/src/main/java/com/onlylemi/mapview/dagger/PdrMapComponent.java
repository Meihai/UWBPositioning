package com.onlylemi.mapview.dagger;

import com.onlylemi.mapview.BLEMapActivity;
import com.onlylemi.mapview.PdrMapActivity;
import com.onlylemi.mapview.search.SearchController;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by admin on 2017/12/16.
 */
@Singleton
@Component(modules = {LocationSearchModule.class})
public interface PdrMapComponent {
    void inject(PdrMapActivity pdrMapActivity);
    SearchController getSearch();
}
