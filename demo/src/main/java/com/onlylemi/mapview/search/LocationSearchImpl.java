package com.onlylemi.mapview.search;

import com.onlylemi.mapview.parameter.LocationSet;
import com.onlylemi.mapview.utils.ResponseCode;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by admin on 2017/11/14.
 */

public class LocationSearchImpl implements ILocationSearch {
    @Override
   public Observable<Response> search(final String query){
        Observable<Response> responseObservable=Observable.create(new Observable.OnSubscribe<Response>(){
            @Override
            public void call(Subscriber<? super Response> subscriber){
                String[] resultKey=searchPlace(query);
                if(resultKey.length>0){
                    Response queryResponse=new Response();
                    queryResponse.responseStatus= ResponseCode.RESPONSE_SUCCESS;
                    queryResponse.responseDetails="";
                    SearchResult[] searchResultArr=new SearchResult[resultKey.length];
                    for (int i=0;i<resultKey.length;i++){
                        searchResultArr[i]=new SearchResult(resultKey[i]);
                    }
                    queryResponse.responseData=new Response.Data();
                    queryResponse.responseData.results=searchResultArr;
                    subscriber.onNext(queryResponse);
                }else{
                    Response queryResponse=new Response();
                    queryResponse.responseStatus= ResponseCode.RESPONSE_FAIL;
                    queryResponse.responseDetails="在地图上未找到该位置";
                    subscriber.onNext(queryResponse);
                }
            }
        });
        return responseObservable;
    }

    private String[] searchPlace(final String query){
        ArrayList<String> findKeyList=new ArrayList<String>();
        String[] keys= LocationSet.locationMap.keySet().toArray(new String[0]);
        Pattern pattern=ruleBuild(query.toLowerCase());
        for(int i=0;i<keys.length;i++){
            Matcher matcher=pattern.matcher(keys[i].toLowerCase());
            if(matcher.find()){
                findKeyList.add(keys[i]);
            }
        }
        return findKeyList.toArray(new String[0]);
    }

    //构建模糊查找表达式
    public Pattern ruleBuild(String matchKey){
        StringBuilder ruleStringBuilder=new StringBuilder();
        ruleStringBuilder.append("^.*");
        ruleStringBuilder.append(matchKey);
        ruleStringBuilder.append(".*$");
        return Pattern.compile(ruleStringBuilder.toString(),Pattern.DOTALL);
    }
}
