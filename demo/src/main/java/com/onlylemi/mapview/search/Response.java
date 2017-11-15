package com.onlylemi.mapview.search;

public class Response {

    public int responseStatus;
    public String responseDetails;
    public Data responseData;

    public static class Data {
        public SearchResult results[];
        public SearchCursor cursor;
    }
}
