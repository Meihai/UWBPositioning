package com.onlylemi.mapview.parameter;

import android.graphics.PointF;

import java.util.HashMap;

/**
 * Created by admin on 2017/11/15.
 */

public class LocationSet {
    public static HashMap<String,PointF> locationMap=new HashMap();
    static{
        locationMap.put("AL1",new PointF(147,398));//A通道左边标记点
        locationMap.put("AL2",new PointF(147,453));
        locationMap.put("AL3",new PointF(147,495));
        locationMap.put("A通道列间空调1",new PointF(147,530));//
        locationMap.put("AL5",new PointF(147,564));
        locationMap.put("AL6",new PointF(147,604));
        locationMap.put("A通道综合机柜1",new PointF(147,654));

        locationMap.put("电源列柜",new PointF(327,398));//A通道右边标记点
        locationMap.put("蓄电池柜",new PointF(327,453));
        locationMap.put("AR3",new PointF(327,495));
        locationMap.put("A通道列间空调2",new PointF(327,530));
        locationMap.put("AR5",new PointF(327,564));
        locationMap.put("AR6",new PointF(327,604));
        locationMap.put("A通道综合机柜2",new PointF(327,654));

        //B通道左边标记点
        locationMap.put("BL1",new PointF(147,835));
        locationMap.put("BL2",new PointF(147,879));
        locationMap.put("BL3",new PointF(147,922));
        locationMap.put("B通道列间空调1",new PointF(147,956));
        locationMap.put("BL5",new PointF(147,989));
        locationMap.put("BL6",new PointF(147,1035));
        locationMap.put("BL7",new PointF(147,1075));
        locationMap.put("B通道列间空调2",new PointF(147,1110));
        locationMap.put("BL9",new PointF(147,1144));
        locationMap.put("BL10",new PointF(147,1185));
        locationMap.put("BL11",new PointF(147,1229));

        //B通道右边标记点
        locationMap.put("BR1",new PointF(327,835));
        locationMap.put("BR2",new PointF(327,879));
        locationMap.put("BR3",new PointF(327,922));
        locationMap.put("B通道列间空调3",new PointF(327,956));
        locationMap.put("BR5",new PointF(327,989));
        locationMap.put("BR6",new PointF(327,1035));
        locationMap.put("BR7",new PointF(327,1075));
        locationMap.put("B通道列间空调4",new PointF(327,1110));
        locationMap.put("BR9",new PointF(327,1144));
        locationMap.put("BR10",new PointF(327,1185));
        locationMap.put("BR11",new PointF(327,1229));


//        locationMap.put("AL1",new PointF(151,295));//A通道左边标记点
//        locationMap.put("AL2",new PointF(151,339));
//        locationMap.put("AL3",new PointF(151,378));
//        locationMap.put("A通道列间空调1",new PointF(151,423));//
//        locationMap.put("AL5",new PointF(151,467));
//        locationMap.put("AL6",new PointF(151,511));
//        locationMap.put("A通道综合机柜1",new PointF(151,547));
//
//        locationMap.put("电源列柜",new PointF(379,295));//A通道右边标记点
//        locationMap.put("蓄电池柜",new PointF(379,339));
//        locationMap.put("AR3",new PointF(379,378));
//        locationMap.put("A通道列间空调2",new PointF(379,423));
//        locationMap.put("AR5",new PointF(379,467));
//        locationMap.put("AR6",new PointF(379,511));
//        locationMap.put("A通道综合机柜2",new PointF(379,547));
//
//        //B通道左边标记点
//        locationMap.put("BL1",new PointF(151,655));
//        locationMap.put("BL2",new PointF(151,696));
//        locationMap.put("BL3",new PointF(151,737));
//        locationMap.put("B通道列间空调1",new PointF(151,781));
//        locationMap.put("BL5",new PointF(151,824));
//        locationMap.put("BL6",new PointF(151,871));
//        locationMap.put("BL7",new PointF(151,910));
//        locationMap.put("B通道列间空调2",new PointF(151,953));
//        locationMap.put("BL9",new PointF(151,996));
//        locationMap.put("BL10",new PointF(151,1038));
//        locationMap.put("BL11",new PointF(151,1080));
//
//        //B通道右边标记点
//        locationMap.put("BR1",new PointF(379,655));
//        locationMap.put("BR2",new PointF(379,696));
//        locationMap.put("BR3",new PointF(379,737));
//        locationMap.put("B通道列间空调3",new PointF(379,781));
//        locationMap.put("BR5",new PointF(379,824));
//        locationMap.put("BR6",new PointF(379,871));
//        locationMap.put("BR7",new PointF(379,910));
//        locationMap.put("B通道列间空调4",new PointF(379,953));
//        locationMap.put("BR9",new PointF(379,996));
//        locationMap.put("BR10",new PointF(379,1038));
//        locationMap.put("BR11",new PointF(379,1080));

        //公司标记点
        locationMap.put("CD500",new PointF(668,602));
        locationMap.put("ISR柜1",new PointF(752,602));
        locationMap.put("ISR柜2",new PointF(841,602));
        locationMap.put("ISR柜3",new PointF(933,602));
        locationMap.put("列头柜",new PointF(1023,602));
        locationMap.put("综合布线机柜",new PointF(1110,602));
        
    }
}
