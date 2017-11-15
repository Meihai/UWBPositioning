package com.onlylemi.mapview.parameter;

import android.graphics.PointF;

import java.util.HashMap;

/**
 * Created by admin on 2017/11/15.
 */

public class LocationSet {
    public static HashMap<String,PointF> locationMap=new HashMap();
    static{
        //工厂相关位置标记点
        //A通道左边标记点
        locationMap.put("AL1",new PointF(50, 122));
        locationMap.put("AL2",new PointF(50,160));
        locationMap.put("AL3",new PointF(50,198));
        //A通道中间过道标记点
        locationMap.put("AP1",new PointF(81,122));
        locationMap.put("AP2",new PointF(81,160));
        locationMap.put("AP3",new PointF(81,198));
        //A通道右边标记点
        locationMap.put("AR1",new PointF(110,122));
        locationMap.put("AR2",new PointF(110,160));
        locationMap.put("AR3",new PointF(110,198));

        //过道标记点
        locationMap.put("P1",new PointF(50,238));
        locationMap.put("P2",new PointF(81,238));
        locationMap.put("P3",new PointF(110,238));

        //B 通道左边标记点
        locationMap.put("BL1",new PointF(50,270));
        locationMap.put("BL2",new PointF(50,308));
        locationMap.put("BL3",new PointF(50,346));
        locationMap.put("BL4",new PointF(50,384));
        //B通道中间过道标记点
        locationMap.put("BP1",new PointF(81,270));
        locationMap.put("BP2",new PointF(81,308));
        locationMap.put("BP3",new PointF(81,346));
        locationMap.put("BP4",new PointF(81,384));
        //B通道右边标记点
        locationMap.put("BR1",new PointF(110,270));
        locationMap.put("BR2",new PointF(110,308));
        locationMap.put("BR3",new PointF(110,346));
        locationMap.put("BR4",new PointF(110,384));
        
    }
}
