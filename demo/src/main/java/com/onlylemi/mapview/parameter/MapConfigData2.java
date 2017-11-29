package com.onlylemi.mapview.parameter;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/11/28.
 */

public class MapConfigData2 {
    //工厂机柜坐标,一共40个 地图大小440*850
    public static List<PointF> getFactoryMarks() {
        List<PointF> marks = new ArrayList<>();
        //A通道左边标记点
        marks.add(new PointF(149,291));
        marks.add(new PointF(149,342));
        marks.add(new PointF(149,376));
        marks.add(new PointF(149,409));
        marks.add(new PointF(149,438));
        marks.add(new PointF(149,477));
        marks.add(new PointF(149,513));
        //A通道右边标记点
        marks.add(new PointF(305,291));
        marks.add(new PointF(305,342));
        marks.add(new PointF(305,376));
        marks.add(new PointF(305,409));
        marks.add(new PointF(305,438));
        marks.add(new PointF(305,477));
        marks.add(new PointF(305,513));

        //B 通道左边标记点
        marks.add(new PointF(149,678));
        marks.add(new PointF(149,720));
        marks.add(new PointF(149,759));
        marks.add(new PointF(149,783));
        marks.add(new PointF(149,816));
        marks.add(new PointF(149,852));
        marks.add(new PointF(149,890));
        marks.add(new PointF(149,923));
        marks.add(new PointF(149,955));
        marks.add(new PointF(149,991));
        marks.add(new PointF(149,1030));

        //B通道右边标记点
        marks.add(new PointF(305,678));
        marks.add(new PointF(305,720));
        marks.add(new PointF(305,759));
        marks.add(new PointF(305,783));
        marks.add(new PointF(305,816));
        marks.add(new PointF(305,852));
        marks.add(new PointF(305,890));
        marks.add(new PointF(305,923));
        marks.add(new PointF(305,955));
        marks.add(new PointF(305,991));
        marks.add(new PointF(305,1030));
        return marks;
    }

    //工厂机柜所在地点名称
    public static List<String> getFactoryMarksName(){
        List<String> marksName=new ArrayList<>();
        marksName.add("AL1");//A通道左边标记点
        marksName.add("AL2");
        marksName.add("AL3");
        marksName.add("A通道列间空调1");//
        marksName.add("AL5");
        marksName.add("AL6");
        marksName.add("A通道综合机柜1");

        marksName.add("电源列柜");//A通道右边标记点
        marksName.add("蓄电池柜");
        marksName.add("AR3");
        marksName.add("A通道列间空调2");
        marksName.add("AR5");
        marksName.add("AR6");
        marksName.add("A通道综合机柜2");

        //B通道左边标记点
        marksName.add("BL1");
        marksName.add("BL2");
        marksName.add("BL3");
        marksName.add("B通道列间空调1");
        marksName.add("BL5");
        marksName.add("BL6");
        marksName.add("BL7");
        marksName.add("B通道列间空调2");
        marksName.add("BL9");
        marksName.add("BL10");
        marksName.add("BL11");

        //B通道右边标记点
        marksName.add("BR1");
        marksName.add("BR2");
        marksName.add("BR3");
        marksName.add("B通道列间空调3");
        marksName.add("BR5");
        marksName.add("BR6");
        marksName.add("BR7");
        marksName.add("B通道列间空调4");
        marksName.add("BR9");
        marksName.add("BR10");
        marksName.add("BR11");
        return marksName;
    }
    //添加公司机柜地点坐标
    public static List<PointF> getCompanyMarks(){
        List<PointF> companyMarks=new ArrayList<PointF>();
        companyMarks.add(new PointF(668,602));
        companyMarks.add(new PointF(752,602));
        companyMarks.add(new PointF(841,602));// PointF(881,875
        companyMarks.add(new PointF(933,602));//PointF(970,875)
        companyMarks.add(new PointF(1023,602));// PointF(1059,875)
        companyMarks.add(new PointF(1110,602));//PointF(1146,875)
        return companyMarks;
    }

    //添加公司地点名称
    public static List<String> getCompanyMarksName(){
        List<String> companyMarksName=new ArrayList<String>();
        companyMarksName.add("CD500");
        companyMarksName.add("ISR柜1");
        companyMarksName.add("ISR柜2");
        companyMarksName.add("ISR柜3");
        companyMarksName.add("ISR柜4");
        companyMarksName.add("综合布线机柜");
        return companyMarksName;
    }

    public static List<PointF> getCompanyNodesList(){
        List<PointF> companyNodes=new ArrayList<>();
        //公司前台走廊开端
        companyNodes.add(new PointF(205,175)); //0 前台走廊端点
        companyNodes.add(new PointF(205,193)); //1 走廊坐标1
        companyNodes.add(new PointF(205,293)); //2 走廊坐标2
        companyNodes.add(new PointF(205,394)); //3 走廊坐标3
        companyNodes.add(new PointF(205,554)); //4 走廊坐标4
        companyNodes.add(new PointF(205,729)); //5 走廊坐标5
        companyNodes.add(new PointF(205,772)); //6 走廊坐标6  展厅门口中间走廊
        companyNodes.add(new PointF(205,832));//7 展厅门口边沿走廊位置
        companyNodes.add(new PointF(205,994));//8 走廊坐标7
        companyNodes.add(new PointF(205,1117));//9 走廊坐标8
        companyNodes.add(new PointF(205,1305));//10 会议室门口边沿走廊位置
        companyNodes.add(new PointF(205,1353));//11 会议室门口中间
        companyNodes.add(new PointF(205,1641));//12 走廊坐标9
        companyNodes.add(new PointF(205,1760));//13 走廊坐标10

        companyNodes.add(new PointF(342,772)); //14 展厅门口中间位置
        companyNodes.add(new PointF(342,949)); //15 展厅前门墙壁边缘1
        companyNodes.add(new PointF(342,1102));//16 展厅前门墙壁边沿2
        companyNodes.add(new PointF(342,690));//17 展厅门口墙壁边沿3

        companyNodes.add(new PointF(459,772)); //18 展厅门口中间位置
        companyNodes.add(new PointF(459,949)); //19 展厅坐标1
        companyNodes.add(new PointF(459,1102));//20 展厅坐标2 靠近会议室边沿
        companyNodes.add(new PointF(459,690));// 21 展厅坐标3 靠近机柜边缘

        companyNodes.add(new PointF(582,772)); //22 展厅门口中间位置
        companyNodes.add(new PointF(582,949)); //23 展厅前门墙壁边缘1
        companyNodes.add(new PointF(582,1102));//24 展厅前门墙壁边沿2
        companyNodes.add(new PointF(582,690));// 25 展厅门口墙壁边沿3
        companyNodes.add(new PointF(582,608));// 26 展厅机柜边沿坐标
        companyNodes.add(new PointF(582,476));// 27 展厅机柜后面坐标1

        companyNodes.add(new PointF(668,772)); //28 展厅门口中间位置 第一个机柜
        companyNodes.add(new PointF(668,949)); //29 展厅前门墙壁边缘1
        companyNodes.add(new PointF(668,1102));//30 展厅前门墙壁边沿2
        companyNodes.add(new PointF(668,690));// 31 展厅门口墙壁边沿3
        companyNodes.add(new PointF(668,476));// 32 展厅机柜后面坐标2

        companyNodes.add(new PointF(759,772)); //33 展厅门口中间位置 第2个机柜
        companyNodes.add(new PointF(759,949)); //34 展厅前门墙壁边缘1
        companyNodes.add(new PointF(759,1102));//35 展厅前门墙壁边沿2
        companyNodes.add(new PointF(759,690));// 36 展厅门口墙壁边沿3
        companyNodes.add(new PointF(759,476));// 37 展厅机柜后面坐标2

        companyNodes.add(new PointF(832,772)); //38 展厅门口中间位置 第3个机柜
        companyNodes.add(new PointF(832,949)); //39 展厅前门墙壁边缘1
        companyNodes.add(new PointF(832,1102));//40 展厅前门墙壁边沿2
        companyNodes.add(new PointF(832,690));// 41 展厅门口墙壁边沿3
        companyNodes.add(new PointF(832,476));// 42 展厅机柜后面坐标2

        companyNodes.add(new PointF(935,772)); //43 展厅门口中间位置 第4个机柜
        companyNodes.add(new PointF(935,949)); //44 展厅前门墙壁边缘1
        companyNodes.add(new PointF(935,1102));//45 展厅前门墙壁边沿2
        companyNodes.add(new PointF(935,690));// 46 展厅门口墙壁边沿3
        companyNodes.add(new PointF(935,476));// 47 展厅机柜后面坐标2

        companyNodes.add(new PointF(1024,772)); //48 展厅门口中间位置 第5个机柜
        companyNodes.add(new PointF(1024,949)); //49 展厅前门墙壁边缘1
        companyNodes.add(new PointF(1024,1102));//50 展厅前门墙壁边沿2
        companyNodes.add(new PointF(1024,690));// 51 展厅门口墙壁边沿3
        companyNodes.add(new PointF(1024,476));// 52 展厅机柜后面坐标2

        companyNodes.add(new PointF(1112,772)); //53 展厅门口中间位置 第6个机柜
        companyNodes.add(new PointF(1112,949)); //54 展厅前门墙壁边缘1
        companyNodes.add(new PointF(1112,1102));//55 展厅前门墙壁边沿2
        companyNodes.add(new PointF(1112,690));// 56 展厅门口墙壁边沿3
        companyNodes.add(new PointF(1112,476));// 57 展厅机柜后面坐标2

        companyNodes.add(new PointF(1246,772)); //58 展厅门口中间位置
        companyNodes.add(new PointF(1246,949)); //59 展厅前门墙壁边缘1
        companyNodes.add(new PointF(1246,1102));//60 展厅前门墙壁边沿2
        companyNodes.add(new PointF(1246,690));// 61 展厅门口墙壁边沿3
        return companyNodes;
    }

    public static List<PointF> getCompanyNodesContactList(){
        List<PointF> companyNodesContact=new ArrayList<PointF>();
        companyNodesContact.add(new PointF(0,1));
        companyNodesContact.add(new PointF(1,2));
        companyNodesContact.add(new PointF(2,3));
        companyNodesContact.add(new PointF(3,4));
        companyNodesContact.add(new PointF(3,5));
        companyNodesContact.add(new PointF(4,5));

        companyNodesContact.add(new PointF(5,6));
        companyNodesContact.add(new PointF(5,14));
        companyNodesContact.add(new PointF(6,7));
        companyNodesContact.add(new PointF(6,14));
        companyNodesContact.add(new PointF(7,8));
        companyNodesContact.add(new PointF(7,14));
        companyNodesContact.add(new PointF(8,9));
        companyNodesContact.add(new PointF(9,10));
        companyNodesContact.add(new PointF(10,11));
        companyNodesContact.add(new PointF(11,12));
        companyNodesContact.add(new PointF(12,13));

        companyNodesContact.add(new PointF(14,15));
        companyNodesContact.add(new PointF(14,17));
        companyNodesContact.add(new PointF(14,18));

        companyNodesContact.add(new PointF(15,16));
        companyNodesContact.add(new PointF(15,19));

        companyNodesContact.add(new PointF(16,20));
        companyNodesContact.add(new PointF(17,21));

        companyNodesContact.add(new PointF(18,19));
        companyNodesContact.add(new PointF(18,21));
        companyNodesContact.add(new PointF(18,22));

        companyNodesContact.add(new PointF(19,20));
        companyNodesContact.add(new PointF(19,23));

        companyNodesContact.add(new PointF(20,24));
        companyNodesContact.add(new PointF(21,25));

        companyNodesContact.add(new PointF(22,23));
        companyNodesContact.add(new PointF(22,25));
        companyNodesContact.add(new PointF(22,28));

        companyNodesContact.add(new PointF(23,24));
        companyNodesContact.add(new PointF(23,29));

        companyNodesContact.add(new PointF(24,30));
        companyNodesContact.add(new PointF(25,26));
        companyNodesContact.add(new PointF(25,31));

        companyNodesContact.add(new PointF(26,27));
        companyNodesContact.add(new PointF(26,27));

        companyNodesContact.add(new PointF(27,32));

        companyNodesContact.add(new PointF(28,29));
        companyNodesContact.add(new PointF(28,31));
        companyNodesContact.add(new PointF(28,33));

        companyNodesContact.add(new PointF(29,30));
        companyNodesContact.add(new PointF(29,34));

        companyNodesContact.add(new PointF(30,35));
        companyNodesContact.add(new PointF(31,36));
        companyNodesContact.add(new PointF(32,37));
        companyNodesContact.add(new PointF(33,34));
        companyNodesContact.add(new PointF(33,36));
        companyNodesContact.add(new PointF(33,38));
        companyNodesContact.add(new PointF(34,35));
        companyNodesContact.add(new PointF(34,39));
        companyNodesContact.add(new PointF(35,40));
        companyNodesContact.add(new PointF(36,41));
        companyNodesContact.add(new PointF(37,42));

        companyNodesContact.add(new PointF(38,39));
        companyNodesContact.add(new PointF(38,41));
        companyNodesContact.add(new PointF(38,43));
        companyNodesContact.add(new PointF(39,40));
        companyNodesContact.add(new PointF(39,44));
        companyNodesContact.add(new PointF(40,45));
        companyNodesContact.add(new PointF(41,46));
        companyNodesContact.add(new PointF(42,47));

        companyNodesContact.add(new PointF(43,44));
        companyNodesContact.add(new PointF(43,46));
        companyNodesContact.add(new PointF(43,48));
        companyNodesContact.add(new PointF(44,45));
        companyNodesContact.add(new PointF(44,49));
        companyNodesContact.add(new PointF(45,50));
        companyNodesContact.add(new PointF(46,51));
        companyNodesContact.add(new PointF(47,52));

        companyNodesContact.add(new PointF(48,49));
        companyNodesContact.add(new PointF(48,51));
        companyNodesContact.add(new PointF(48,53));
        companyNodesContact.add(new PointF(49,50));
        companyNodesContact.add(new PointF(49,54));
        companyNodesContact.add(new PointF(50,55));
        companyNodesContact.add(new PointF(51,56));
        companyNodesContact.add(new PointF(52,57));

        companyNodesContact.add(new PointF(53,54));
        companyNodesContact.add(new PointF(53,56));
        companyNodesContact.add(new PointF(53,58));
        companyNodesContact.add(new PointF(54,55));
        companyNodesContact.add(new PointF(54,59));
        companyNodesContact.add(new PointF(55,60));
        companyNodesContact.add(new PointF(56,61));

        companyNodesContact.add(new PointF(58,59));
        companyNodesContact.add(new PointF(58,61));
        companyNodesContact.add(new PointF(59,60));

        return companyNodesContact;
    }


    public static List<PointF> getFctoryNodesList(){
        List<PointF> factoryNodes=new ArrayList<>();
        factoryNodes.add(new PointF(56,208)); //0 机房左上角坐标
        factoryNodes.add(new PointF(149,208)); //1 机房上走廊中间坐标1 与A通道左侧相邻
        factoryNodes.add(new PointF(228,208)); //2 机房上走廊中间坐标4,与A通道门口相对
        factoryNodes.add(new PointF(305,208)); //3 机房上走廊中间坐标3与A通道右侧相邻
        factoryNodes.add(new PointF(405,208)); //4 机房右上角坐标
        factoryNodes.add(new PointF(457,208)); //5 机房门口坐标
        factoryNodes.add(new PointF(504,208));// 6 机房门外坐标

        factoryNodes.add(new PointF(56,265));// 7 A通道门口第1行坐标1
        factoryNodes.add(new PointF(232,265));//8 A通道门口坐标
        factoryNodes.add(new PointF(405,265)); //9 A通道右走廊坐标

        factoryNodes.add(new PointF(56,291));// 10 A通道门口第2行坐标1
        factoryNodes.add(new PointF(232,291));//11 A通道门口坐标
        factoryNodes.add(new PointF(405,291)); //12 A通道右走廊坐标

        factoryNodes.add(new PointF(56,342));// 13 A通道门口第3行坐标1
        factoryNodes.add(new PointF(232,342));//14 A通道门口坐标
        factoryNodes.add(new PointF(405,342)); //15 A通道右走廊坐标

        factoryNodes.add(new PointF(56,376));// 16 A通道门口第4行坐标1
        factoryNodes.add(new PointF(232,376));//17 A通道门口坐标
        factoryNodes.add(new PointF(405,376)); //18 A通道右走廊坐标

        factoryNodes.add(new PointF(56,409));// 19 A通道门口第5行坐标1
        factoryNodes.add(new PointF(232,409));//20 A通道门口坐标
        factoryNodes.add(new PointF(405,409)); //21 A通道右走廊坐标

        factoryNodes.add(new PointF(56,438));// 22 A通道门口第一行坐标1
        factoryNodes.add(new PointF(232,438));//23 A通道门口坐标
        factoryNodes.add(new PointF(405,438)); //24 A通道右走廊坐标

        factoryNodes.add(new PointF(56,477));// 25 A通道门口第一行坐标1
        factoryNodes.add(new PointF(232,477));//26 A通道门口坐标
        factoryNodes.add(new PointF(405,477)); //27 A通道右走廊坐标

        factoryNodes.add(new PointF(56,513));// 28 A通道门口第一行坐标1
        factoryNodes.add(new PointF(232,513));//29 A通道门口坐标
        factoryNodes.add(new PointF(405,513)); //30 A通道右走廊坐标
        //过道5个坐标
        factoryNodes.add(new PointF(56,585));// 31 过道坐标1
        factoryNodes.add(new PointF(149,585));//32 过道坐标2
        factoryNodes.add(new PointF(232,585));//33 过道坐标3
        factoryNodes.add(new PointF(305,585));//34 过道坐标4
        factoryNodes.add(new PointF(405,585)); //35 过道坐标5

        factoryNodes.add(new PointF(56,678));// 36 B通道门口第1行坐标1
        factoryNodes.add(new PointF(232,678));//37 B通道门口坐标
        factoryNodes.add(new PointF(405,678)); //38 B通道右走廊坐标

        factoryNodes.add(new PointF(56,720));// 39 B通道门口第2行坐标1
        factoryNodes.add(new PointF(232,720));//40 B通道门口坐标
        factoryNodes.add(new PointF(405,720)); //41 B通道右走廊坐标

        factoryNodes.add(new PointF(56,759));// 42 B通道门口第3行坐标1
        factoryNodes.add(new PointF(232,759));//43 B通道门口坐标
        factoryNodes.add(new PointF(405,759)); //44 B通道右走廊坐标

        factoryNodes.add(new PointF(56,783));// 45 B通道门口第4行坐标1
        factoryNodes.add(new PointF(232,783));//46 B通道门口坐标
        factoryNodes.add(new PointF(405,783)); //47 B通道右走廊坐标

        factoryNodes.add(new PointF(56,816));// 48 B通道门口第5行坐标1
        factoryNodes.add(new PointF(232,816));//49 B通道门口坐标
        factoryNodes.add(new PointF(405,816)); //50 B通道右走廊坐标

        factoryNodes.add(new PointF(56,852));// 51 B通道门口第6行坐标1
        factoryNodes.add(new PointF(232,852));//52 B通道门口坐标
        factoryNodes.add(new PointF(405,852)); //53 B通道右走廊坐标

        factoryNodes.add(new PointF(56,890));// 54 B通道门口第7行坐标1
        factoryNodes.add(new PointF(232,890));//55 B通道门口坐标
        factoryNodes.add(new PointF(405,890));//56 B通道右走廊坐标

        factoryNodes.add(new PointF(56,923));// 57 B通道门口第8行坐标1
        factoryNodes.add(new PointF(232,923));//58 B通道门口坐标
        factoryNodes.add(new PointF(405,923)); //59 B通道右走廊坐标

        factoryNodes.add(new PointF(56,955));// 60 B通道门口第9行坐标1
        factoryNodes.add(new PointF(232,955));//61 B通道门口坐标
        factoryNodes.add(new PointF(405,955)); //62 B通道右走廊坐标

        factoryNodes.add(new PointF(56,991));// 63 B通道门口第10行坐标1
        factoryNodes.add(new PointF(232,991));//64 B通道门口坐标
        factoryNodes.add(new PointF(405,991)); //65 B通道右走廊坐标

        factoryNodes.add(new PointF(56,1030));// 66 B通道门口第11行坐标1
        factoryNodes.add(new PointF(232,1030));//67 B通道门口坐标
        factoryNodes.add(new PointF(405,1030));//68 B通道右走廊坐标

        factoryNodes.add(new PointF(56,1112));// 69 下走廊1
        factoryNodes.add(new PointF(149,1112));//70 过道坐标2
        factoryNodes.add(new PointF(232,1112));//71 过道坐标3
        factoryNodes.add(new PointF(305,1112));//72 过道坐标4
        factoryNodes.add(new PointF(405,1112)); //73 过道坐标5

        return factoryNodes;
    }

    public static List<PointF> getFactoryNodesContactList(){
        List<PointF> factoryNodesContact=new ArrayList<PointF>();
        factoryNodesContact.add(new PointF(0,1));
        factoryNodesContact.add(new PointF(0,7));
        factoryNodesContact.add(new PointF(1,2));
        factoryNodesContact.add(new PointF(2,3));
        factoryNodesContact.add(new PointF(2,8));
        factoryNodesContact.add(new PointF(3,4));
        factoryNodesContact.add(new PointF(4,5));
        factoryNodesContact.add(new PointF(4,9));
        factoryNodesContact.add(new PointF(5,6));


        factoryNodesContact.add(new PointF(7,10));
        factoryNodesContact.add(new PointF(8,11));
        factoryNodesContact.add(new PointF(9,12));


        factoryNodesContact.add(new PointF(10,13));
        factoryNodesContact.add(new PointF(11,14));
        factoryNodesContact.add(new PointF(12,15));

        factoryNodesContact.add(new PointF(13,16));
        factoryNodesContact.add(new PointF(14,17));
        factoryNodesContact.add(new PointF(15,18));

        factoryNodesContact.add(new PointF(10,13));
        factoryNodesContact.add(new PointF(11,14));
        factoryNodesContact.add(new PointF(12,15));

        factoryNodesContact.add(new PointF(13,16));
        factoryNodesContact.add(new PointF(14,17));
        factoryNodesContact.add(new PointF(15,18));

        factoryNodesContact.add(new PointF(16,19));
        factoryNodesContact.add(new PointF(17,20));
        factoryNodesContact.add(new PointF(18,21));

        factoryNodesContact.add(new PointF(19,22));
        factoryNodesContact.add(new PointF(20,23));
        factoryNodesContact.add(new PointF(21,24));

        factoryNodesContact.add(new PointF(22,25));
        factoryNodesContact.add(new PointF(23,26));
        factoryNodesContact.add(new PointF(24,27));

        factoryNodesContact.add(new PointF(25,28));
        factoryNodesContact.add(new PointF(26,29));
        factoryNodesContact.add(new PointF(27,30));

        factoryNodesContact.add(new PointF(28,31));
        factoryNodesContact.add(new PointF(29,33));
        factoryNodesContact.add(new PointF(30,35));
        factoryNodesContact.add(new PointF(31,32));
        factoryNodesContact.add(new PointF(31,36));

        factoryNodesContact.add(new PointF(32,33));
        factoryNodesContact.add(new PointF(33,34));
        factoryNodesContact.add(new PointF(33,37));
        factoryNodesContact.add(new PointF(34,35));
        factoryNodesContact.add(new PointF(35,38));

        factoryNodesContact.add(new PointF(36,39));
        factoryNodesContact.add(new PointF(37,40));
        factoryNodesContact.add(new PointF(38,41));

        factoryNodesContact.add(new PointF(39,42));
        factoryNodesContact.add(new PointF(40,42));
        factoryNodesContact.add(new PointF(41,44));

        factoryNodesContact.add(new PointF(42,45));
        factoryNodesContact.add(new PointF(43,46));
        factoryNodesContact.add(new PointF(44,47));

        factoryNodesContact.add(new PointF(45,48));
        factoryNodesContact.add(new PointF(46,49));
        factoryNodesContact.add(new PointF(47,50));

        factoryNodesContact.add(new PointF(48,51));
        factoryNodesContact.add(new PointF(49,52));
        factoryNodesContact.add(new PointF(50,53));

        factoryNodesContact.add(new PointF(51,54));
        factoryNodesContact.add(new PointF(52,55));
        factoryNodesContact.add(new PointF(53,56));

        factoryNodesContact.add(new PointF(54,57));
        factoryNodesContact.add(new PointF(55,58));
        factoryNodesContact.add(new PointF(56,59));

        factoryNodesContact.add(new PointF(57,60));
        factoryNodesContact.add(new PointF(58,61));
        factoryNodesContact.add(new PointF(59,62));

        factoryNodesContact.add(new PointF(60,63));
        factoryNodesContact.add(new PointF(61,64));
        factoryNodesContact.add(new PointF(62,65));

        factoryNodesContact.add(new PointF(63,66));
        factoryNodesContact.add(new PointF(64,67));
        factoryNodesContact.add(new PointF(65,68));

        factoryNodesContact.add(new PointF(66,69));
        factoryNodesContact.add(new PointF(67,71));
        factoryNodesContact.add(new PointF(68,73));
        factoryNodesContact.add(new PointF(69,70));
        factoryNodesContact.add(new PointF(70,71));
        factoryNodesContact.add(new PointF(71,72));
        factoryNodesContact.add(new PointF(72,73));
        return factoryNodesContact;
    }
}
