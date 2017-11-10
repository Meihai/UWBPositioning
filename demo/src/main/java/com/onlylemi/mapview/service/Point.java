package com.onlylemi.mapview.service;

/**
 * Created by admin on 2017/10/31.
 */

public class Point {
    private double x;
    private double y;

    public Point(double x,double y){
        this.x=x;
        this.y=y;
    }

    public double getX(){
        return this.x;
    }

    public void setX(double x){
        this.x=x;
    }

    public double getY(){
        return this.y;
    }

    public void setY(double y){
        this.y=y;
    }
}
