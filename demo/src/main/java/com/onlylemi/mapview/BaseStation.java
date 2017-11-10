package com.onlylemi.mapview;

/**
 * Created by admin on 2017/11/5.
 */

public class BaseStation {
    private String name;
    private String coord;
    public BaseStation(){

    }
    public BaseStation(String name,String coord){
        this.name=name;
        this.coord=coord;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    public void setCoord(String coord){
        this.coord=coord;
    }

    public String getCoord(){
        return this.coord;
    }

    @Override
    public String toString(){
        return "name="+this.name+",coord="+this.coord;
    }
}
