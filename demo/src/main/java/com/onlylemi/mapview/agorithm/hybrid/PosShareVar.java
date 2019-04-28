package com.onlylemi.mapview.agorithm.hybrid;

/**
 * Created by admin on 2017/12/20.
 */

public class PosShareVar {
    private volatile static double posx=0;
    private volatile static double posy=0;
    private volatile static boolean updateFlag=false;
    private volatile static long updateTime; //坐标的更新时间


    public double getPosx(){
        return posx;
    }

    public void setPosx(double posx){
        PosShareVar.posx=posx;
    }

    public double getPosy(){
        return posy;
    }

    public void setPosy(double posy){
        PosShareVar.posy=posy;
    }

    public boolean getUpdateFlag(){
        return updateFlag;
    }

    public void setUpdateFlag(boolean updateFlag){
        PosShareVar.updateFlag=updateFlag;
    }

    public void setUpdateTime(long updateTime){
        PosShareVar.updateTime=updateTime;
    }

    public long getUpdateTime(){
        return updateTime;
    }


}
