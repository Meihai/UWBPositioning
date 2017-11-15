package com.onlylemi.mapview.dagger;

import com.onlylemi.mapview.search.ILocationSearch;
import com.onlylemi.mapview.search.LocationSearchController;
import com.onlylemi.mapview.search.LocationSearchImpl;
import com.onlylemi.mapview.search.SearchController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by admin on 2017/11/14.
 */
@Module
public class LocationSearchModule {
    @Provides
    @Singleton
    ILocationSearch provideLocationSearch() {
        return new LocationSearchImpl();
    }
    @Provides
    SearchController provideSearchController(LocationSearchController searchController) {
        return searchController;
    }
}
