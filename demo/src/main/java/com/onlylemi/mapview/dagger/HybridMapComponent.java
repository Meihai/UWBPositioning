package com.onlylemi.mapview.dagger;

import com.onlylemi.mapview.HybridMapActivity;
import com.onlylemi.mapview.search.SearchController;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by admin on 2017/12/20.
 */


@Singleton
@Component(modules = {LocationSearchModule.class})
public interface HybridMapComponent {
    void inject(HybridMapActivity hybridMapActivity);
    SearchController getSearch();
}
