package com.onlylemi.mapview.parameter;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/11/30.
 */

public class MapConfigData3 {
    //工厂机柜坐标,一共40个 地图大小440*850
    public static List<PointF> getFactoryMarks() {
        List<PointF> marks = new ArrayList<>();
        //A通道左边标记点
        marks.add(new PointF(147,398));
        marks.add(new PointF(147,453));
        marks.add(new PointF(147,495));
        marks.add(new PointF(147,530));
        marks.add(new PointF(147,564));
        marks.add(new PointF(147,604));
        marks.add(new PointF(147,654));
        //A通道右边标记点
        marks.add(new PointF(327,398));
        marks.add(new PointF(327,453));
        marks.add(new PointF(327,495));
        marks.add(new PointF(327,530));
        marks.add(new PointF(327,564));
        marks.add(new PointF(327,604));
        marks.add(new PointF(327,654));

        //B 通道左边标记点
        marks.add(new PointF(147,835));
        marks.add(new PointF(147,879));
        marks.add(new PointF(147,922));
        marks.add(new PointF(147,956));
        marks.add(new PointF(147,989));
        marks.add(new PointF(147,1035));
        marks.add(new PointF(147,1075));
        marks.add(new PointF(147,1110));
        marks.add(new PointF(147,1144));
        marks.add(new PointF(147,1185));
        marks.add(new PointF(147,1229));

        //B通道右边标记点
        marks.add(new PointF(327,835));
        marks.add(new PointF(327,879));
        marks.add(new PointF(327,922));
        marks.add(new PointF(327,956));
        marks.add(new PointF(327,989));
        marks.add(new PointF(327,1035));
        marks.add(new PointF(327,1075));
        marks.add(new PointF(327,1110));
        marks.add(new PointF(327,1144));
        marks.add(new PointF(327,1185));
        marks.add(new PointF(327,1229));
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
        companyMarksName.add("列头柜");
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
        factoryNodes.add(new PointF(58,326)); //0 机房左上角坐标
        factoryNodes.add(new PointF(99,326)); //1 机房上走廊坐标1 机柜边缘
        factoryNodes.add(new PointF(147,326)); //2 机房上走廊A通道左边机柜中间坐标
        factoryNodes.add(new PointF(184,326)); //3 A通道中间边缘
        factoryNodes.add(new PointF(232,326));// 4 中间通道坐标
        factoryNodes.add(new PointF(282,326));//5 中间通道右边边缘
        factoryNodes.add(new PointF(327,326));//6 A通道右边机柜中心位置
        factoryNodes.add(new PointF(370,326));//7 A通道机柜右边边缘位置
        factoryNodes.add(new PointF(436,326));//8 A通道右走廊坐标
        factoryNodes.add(new PointF(505,326));//9 门口坐标
        factoryNodes.add(new PointF(552,326)); //10门口外坐标

        factoryNodes.add(new PointF(232,371)); //11A通道门口坐标

        factoryNodes.add(new PointF(58,398));//12 A通道机柜第1排
        factoryNodes.add(new PointF(232,398));//13
        factoryNodes.add(new PointF(436,398));//14

        factoryNodes.add(new PointF(58,453));//15 A通道机柜第2排
        factoryNodes.add(new PointF(232,453));//16
        factoryNodes.add(new PointF(436,453));//17

        factoryNodes.add(new PointF(58,495));//18 A通道机柜第3排
        factoryNodes.add(new PointF(232,495));//19
        factoryNodes.add(new PointF(436,495));//20

        factoryNodes.add(new PointF(58,530));//21 A通道机柜第4排
        factoryNodes.add(new PointF(232,530));//22
        factoryNodes.add(new PointF(436,530));//23

        factoryNodes.add(new PointF(58,564));//24 A通道机柜第5排
        factoryNodes.add(new PointF(232,564));//25
        factoryNodes.add(new PointF(436,564));//26

        factoryNodes.add(new PointF(58,604));//27 A通道机柜第6排
        factoryNodes.add(new PointF(232,604));//28
        factoryNodes.add(new PointF(436,604));//29

        factoryNodes.add(new PointF(58,654));//30 A通道机柜第7排
        factoryNodes.add(new PointF(232,654));//31
        factoryNodes.add(new PointF(436,654));//32

        factoryNodes.add(new PointF(58,676));//33 过道与A通道交界处
        factoryNodes.add(new PointF(232,676));//34
        factoryNodes.add(new PointF(436,676));//35

        factoryNodes.add(new PointF(58,746)); //36 过道中心位置
        factoryNodes.add(new PointF(99,746)); //37 过道与通道机柜边缘
        factoryNodes.add(new PointF(147,746)); //38 机柜中间坐标
        factoryNodes.add(new PointF(184,746)); //39 机柜边缘
        factoryNodes.add(new PointF(232,746));// 40 中间通道坐标
        factoryNodes.add(new PointF(282,746));// 41中间通道右边边缘
        factoryNodes.add(new PointF(327,746));// 42 A通道右边机柜中心位置
        factoryNodes.add(new PointF(370,746));// 43 A通道机柜右边边缘位置
        factoryNodes.add(new PointF(436,746));// 44 A通道右走廊坐标

        factoryNodes.add(new PointF(58,812));//45 过道与B通道交界处
        factoryNodes.add(new PointF(232,812));//46
        factoryNodes.add(new PointF(436,812));//47

        //B通道机柜坐标
        factoryNodes.add(new PointF(58,835));//48 B通道机柜第1排
        factoryNodes.add(new PointF(232,835));//49
        factoryNodes.add(new PointF(436,835));//50

        factoryNodes.add(new PointF(58,879));//51 B通道机柜第2排
        factoryNodes.add(new PointF(232,879));//52
        factoryNodes.add(new PointF(436,879));//53

        factoryNodes.add(new PointF(58,922));//54 B通道机柜第3排
        factoryNodes.add(new PointF(232,922));//55
        factoryNodes.add(new PointF(436,922));//56

        factoryNodes.add(new PointF(58,956));//57 B通道机柜第4排
        factoryNodes.add(new PointF(232,956));//58
        factoryNodes.add(new PointF(436,956));//59

        factoryNodes.add(new PointF(58,989));//60 B通道机柜第5排
        factoryNodes.add(new PointF(232,989));//61
        factoryNodes.add(new PointF(436,989));//62

        factoryNodes.add(new PointF(58,1035));//63 B通道机柜第6排
        factoryNodes.add(new PointF(232,1035));//64
        factoryNodes.add(new PointF(436,1035));//65

        factoryNodes.add(new PointF(58,1075));//66 B通道机柜第7排
        factoryNodes.add(new PointF(232,1075));//67
        factoryNodes.add(new PointF(436,1075));//68

        factoryNodes.add(new PointF(58,1110));//69 B通道机柜第8排
        factoryNodes.add(new PointF(232,1110));//70
        factoryNodes.add(new PointF(436,1110));//71

        factoryNodes.add(new PointF(58,1144));//72 B通道机柜第9排
        factoryNodes.add(new PointF(232,1144));//73
        factoryNodes.add(new PointF(436,1144));//74

        factoryNodes.add(new PointF(58,1185));//75 B通道机柜第10排
        factoryNodes.add(new PointF(232,1185));//76
        factoryNodes.add(new PointF(436,1185));//77

        factoryNodes.add(new PointF(58,1229));//78 B通道机柜第11排
        factoryNodes.add(new PointF(232,1229));//79
        factoryNodes.add(new PointF(436,1229));//80


        factoryNodes.add(new PointF(58,1246));//81 B通道门口
        factoryNodes.add(new PointF(232,1246));//82
        factoryNodes.add(new PointF(436,1246));//83

        factoryNodes.add(new PointF(58,1318)); //84 过道中心位置
        factoryNodes.add(new PointF(99,1318)); //85 过道与通道机柜边缘
        factoryNodes.add(new PointF(147,1318)); //86 机柜中间坐标
        factoryNodes.add(new PointF(184,1318)); //87 机柜边缘
        factoryNodes.add(new PointF(232,1318));// 88 中间通道坐标
        factoryNodes.add(new PointF(282,1318));// 89中间通道右边边缘
        factoryNodes.add(new PointF(327,1318));// 90 A通道右边机柜中心位置
        factoryNodes.add(new PointF(370,1318));// 91 A通道机柜右边边缘位置
        factoryNodes.add(new PointF(436,1318));// 92 A通道右走廊坐标
        return factoryNodes;
    }

    public static List<PointF> getFactoryNodesContactList(){
        List<PointF> factoryNodesContact=new ArrayList<PointF>();
        factoryNodesContact.add(new PointF(0,1));
        factoryNodesContact.add(new PointF(0,12));
        factoryNodesContact.add(new PointF(1,2));
        factoryNodesContact.add(new PointF(2,3));
        factoryNodesContact.add(new PointF(3,4));
        factoryNodesContact.add(new PointF(4,5));
        factoryNodesContact.add(new PointF(4,11));
        factoryNodesContact.add(new PointF(5,6));
        factoryNodesContact.add(new PointF(6,7));
        factoryNodesContact.add(new PointF(7,8));
        factoryNodesContact.add(new PointF(8,9));
        factoryNodesContact.add(new PointF(8,14));
        factoryNodesContact.add(new PointF(9,10));
        factoryNodesContact.add(new PointF(9,14));
        factoryNodesContact.add(new PointF(11,13));
        //A通道第1排
        factoryNodesContact.add(new PointF(12,15));
        factoryNodesContact.add(new PointF(13,16));
        factoryNodesContact.add(new PointF(14,17));

        //A通道第2排
        factoryNodesContact.add(new PointF(15,18));
        factoryNodesContact.add(new PointF(16,19));
        factoryNodesContact.add(new PointF(17,20));

        //A通道第3排
        factoryNodesContact.add(new PointF(18,21));
        factoryNodesContact.add(new PointF(19,22));
        factoryNodesContact.add(new PointF(20,23));

        //A通道第4排
        factoryNodesContact.add(new PointF(21,24));
        factoryNodesContact.add(new PointF(22,25));
        factoryNodesContact.add(new PointF(23,26));

        //A通道第5排
        factoryNodesContact.add(new PointF(24,27));
        factoryNodesContact.add(new PointF(25,28));
        factoryNodesContact.add(new PointF(26,29));

        //A通道第6排
        factoryNodesContact.add(new PointF(27,30));
        factoryNodesContact.add(new PointF(28,31));
        factoryNodesContact.add(new PointF(29,32));

        //A通道第7排
        factoryNodesContact.add(new PointF(30,33));
        factoryNodesContact.add(new PointF(31,34));
        factoryNodesContact.add(new PointF(32,35));

        //过道
        factoryNodesContact.add(new PointF(33,36));
      //  factoryNodesContact.add(new PointF(34,39));
        factoryNodesContact.add(new PointF(34,40));
      //  factoryNodesContact.add(new PointF(34,41));
        factoryNodesContact.add(new PointF(35,44));
        factoryNodesContact.add(new PointF(36,37));
        factoryNodesContact.add(new PointF(36,45));
        factoryNodesContact.add(new PointF(37,38));
        factoryNodesContact.add(new PointF(38,39));
        factoryNodesContact.add(new PointF(39,40));
        factoryNodesContact.add(new PointF(40,41));
        factoryNodesContact.add(new PointF(40,46));
        factoryNodesContact.add(new PointF(41,42));
        factoryNodesContact.add(new PointF(42,43));
        factoryNodesContact.add(new PointF(43,44));
        factoryNodesContact.add(new PointF(44,47));

        factoryNodesContact.add(new PointF(45,48));
        factoryNodesContact.add(new PointF(46,49));
        factoryNodesContact.add(new PointF(47,50));

        //B通道第1排
        factoryNodesContact.add(new PointF(48,51));
        factoryNodesContact.add(new PointF(49,52));
        factoryNodesContact.add(new PointF(50,53));
        //B通道第2排
        factoryNodesContact.add(new PointF(51,54));
        factoryNodesContact.add(new PointF(52,55));
        factoryNodesContact.add(new PointF(53,56));
        //B通道第3排
        factoryNodesContact.add(new PointF(54,57));
        factoryNodesContact.add(new PointF(55,58));
        factoryNodesContact.add(new PointF(56,59));
        //B通道第4排
        factoryNodesContact.add(new PointF(57,60));
        factoryNodesContact.add(new PointF(58,61));
        factoryNodesContact.add(new PointF(59,62));
        //B通道第5排
        factoryNodesContact.add(new PointF(60,63));
        factoryNodesContact.add(new PointF(61,64));
        factoryNodesContact.add(new PointF(62,65));
        //B通道第6排
        factoryNodesContact.add(new PointF(63,66));
        factoryNodesContact.add(new PointF(64,67));
        factoryNodesContact.add(new PointF(65,68));
        //B通道第7排
        factoryNodesContact.add(new PointF(66,69));
        factoryNodesContact.add(new PointF(67,70));
        factoryNodesContact.add(new PointF(68,71));
        //B通道第8排
        factoryNodesContact.add(new PointF(69,72));
        factoryNodesContact.add(new PointF(70,73));
        factoryNodesContact.add(new PointF(71,74));
        //B通道第9排
        factoryNodesContact.add(new PointF(72,75));
        factoryNodesContact.add(new PointF(73,76));
        factoryNodesContact.add(new PointF(74,77));

        //B通道第10排
        factoryNodesContact.add(new PointF(75,78));
        factoryNodesContact.add(new PointF(76,79));
        factoryNodesContact.add(new PointF(77,80));
        //B通道第11排
        factoryNodesContact.add(new PointF(78,81));
        factoryNodesContact.add(new PointF(79,82));
        factoryNodesContact.add(new PointF(80,83));

        //B通道下走廊
        factoryNodesContact.add(new PointF(81,84));
        factoryNodesContact.add(new PointF(82,86));
        factoryNodesContact.add(new PointF(82,87));
        factoryNodesContact.add(new PointF(82,88));
        factoryNodesContact.add(new PointF(82,89));
        factoryNodesContact.add(new PointF(82,90));
        factoryNodesContact.add(new PointF(83,92));
        factoryNodesContact.add(new PointF(84,85));
        factoryNodesContact.add(new PointF(85,86));
        factoryNodesContact.add(new PointF(86,87));
        factoryNodesContact.add(new PointF(87,88));
        factoryNodesContact.add(new PointF(88,89));
        factoryNodesContact.add(new PointF(89,90));
        factoryNodesContact.add(new PointF(90,91));
        factoryNodesContact.add(new PointF(91,92));

        return factoryNodesContact;
    }
}
