package com.onlylemi.mapview.search;


import rx.Observable;

/**
 * Created by admin on 2017/11/14.
 */

public interface ILocationSearch {
    Observable<Response> search(String query);
}
