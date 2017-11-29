package com.onlylemi.mapview.parameter;

import android.graphics.PointF;

import com.onlylemi.mapview.service.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/11/24.
 */

public class MapConfigData {

    //工厂机柜坐标,一共40个 地图大小440*850
    public static List<PointF> getFactoryMarks() {
        List<PointF> marks = new ArrayList<>();
        //A通道左边标记点
        marks.add(new PointF(151,295));
        marks.add(new PointF(151,339));
        marks.add(new PointF(151,378));
        marks.add(new PointF(151,423));
        marks.add(new PointF(151,467));
        marks.add(new PointF(151,511));
        marks.add(new PointF(151,547));
        //A通道右边标记点
        marks.add(new PointF(379,295));
        marks.add(new PointF(379,339));
        marks.add(new PointF(379,378));
        marks.add(new PointF(379,423));
        marks.add(new PointF(379,467));
        marks.add(new PointF(379,511));
        marks.add(new PointF(379,547));

        //B 通道左边标记点
        marks.add(new PointF(151,655));
        marks.add(new PointF(151,696));
        marks.add(new PointF(151,737));
        marks.add(new PointF(151,781));
        marks.add(new PointF(151,824));
        marks.add(new PointF(151,871));
        marks.add(new PointF(151,910));
        marks.add(new PointF(151,953));
        marks.add(new PointF(151,996));
        marks.add(new PointF(151,1038));
        marks.add(new PointF(151,1080));

        //B通道右边标记点
        marks.add(new PointF(379,655));
        marks.add(new PointF(379,696));
        marks.add(new PointF(379,737));
        marks.add(new PointF(379,781));
        marks.add(new PointF(379,824));
        marks.add(new PointF(379,871));
        marks.add(new PointF(379,910));
        marks.add(new PointF(379,953));
        marks.add(new PointF(379,996));
        marks.add(new PointF(379,1038));
        marks.add(new PointF(379,1080));
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
        factoryNodes.add(new PointF(47,246)); //0 机房左上角坐标
        factoryNodes.add(new PointF(94,246)); //1 机房上走廊中间坐标1
        factoryNodes.add(new PointF(152,246)); //2 机房上走廊中间坐标2
        factoryNodes.add(new PointF(211,246)); //3 机房上走廊中间坐标3
        factoryNodes.add(new PointF(266,246)); //4 机房上走廊中间坐标4,与A通道门口相对
        factoryNodes.add(new PointF(266,280)); //5 机房上走廊中间坐标与A通道门口交汇点
        factoryNodes.add(new PointF(327,246)); //6 机房上走廊中间坐标5
        factoryNodes.add(new PointF(367,246)); //7 机房上走廊中间坐标6
        factoryNodes.add(new PointF(439,246)); //8 机房上走廊中间坐标7
        factoryNodes.add(new PointF(492,246)); //9 机房上走廊中间坐标8
        factoryNodes.add(new PointF(559,261)); //10 机房门口坐标
        factoryNodes.add(new PointF(612,261));// 11 机房门外坐标

        factoryNodes.add(new PointF(63,295)); //12 机房第一排机柜左坐标
        factoryNodes.add(new PointF(266,295)); //13 机房第一排机柜中间坐标
        factoryNodes.add(new PointF(492,295)); //14 机房第一排机柜右坐标

        factoryNodes.add(new PointF(63,339)); //15 机房第2排机柜左坐标
        factoryNodes.add(new PointF(266,339)); //16 机房第2排机柜中间坐标
        factoryNodes.add(new PointF(492,339)); //17 机房第2排机柜右坐标

        factoryNodes.add(new PointF(63,378)); //18机房第3排机柜左坐标
        factoryNodes.add(new PointF(266,378)); //19 机房第3排机柜中间坐标
        factoryNodes.add(new PointF(492,378)); //20 机房第3排机柜右坐标

        factoryNodes.add(new PointF(63,423)); //21机房第4排机柜左坐标
        factoryNodes.add(new PointF(266,423)); //22机房第4排机柜中间坐标
        factoryNodes.add(new PointF(492,423)); //23 机房第4排机柜右坐标

        factoryNodes.add(new PointF(63,467)); //24 机房第5排机柜左坐标
        factoryNodes.add(new PointF(266,467)); //25 机房第5排机柜中间坐标
        factoryNodes.add(new PointF(492,467)); //26 机房第5排机柜右坐标

        factoryNodes.add(new PointF(63,511)); //27机房第6排机柜左坐标
        factoryNodes.add(new PointF(266,511)); //28 机房第6排机柜中间坐标
        factoryNodes.add(new PointF(492,511)); //29 机房第6排机柜右坐标

        factoryNodes.add(new PointF(63,547)); //30 机房第7排机柜左坐标
        factoryNodes.add(new PointF(266,547)); //31 机房第7排机柜中间坐标
        factoryNodes.add(new PointF(492,547)); //32 机房第7排机柜右坐标

        factoryNodes.add(new PointF(63,607)); //33 机房中间通道左坐标
        factoryNodes.add(new PointF(266,607)); //34 机房中间通道中间坐标
        factoryNodes.add(new PointF(492,607)); //35 机房中间通道右坐标
        factoryNodes.add(new PointF(160,607)); //36 机房中间通道坐标左中
        factoryNodes.add(new PointF(384,607)); //37机房中间通道右中坐标
        factoryNodes.add(new PointF(266,573)); //38 机房过道与A通道门口交汇处
        factoryNodes.add(new PointF(266,634));//39 机房过道与B通道交汇处坐标

        factoryNodes.add(new PointF(63,655)); //40 机房第8排机柜左坐标
        factoryNodes.add(new PointF(266,655)); //41 机房第8排机柜中间坐标
        factoryNodes.add(new PointF(492,655)); //42 机房第8排机柜右坐标

        factoryNodes.add(new PointF(63,695)); //43 机房第9排机柜左坐标
        factoryNodes.add(new PointF(266,695)); //44 机房第9排机柜中间坐标
        factoryNodes.add(new PointF(492,695)); //45 机房第9排机柜右坐标

        factoryNodes.add(new PointF(63,737)); //46 机房第10排机柜左坐标
        factoryNodes.add(new PointF(266,737)); //47 机房第10排机柜中间坐标
        factoryNodes.add(new PointF(492,737)); //48 机房第10排机柜右坐标

        factoryNodes.add(new PointF(63,781)); //49 机房第11排机柜左坐标
        factoryNodes.add(new PointF(266,781)); //50 机房第11排机柜中间坐标
        factoryNodes.add(new PointF(492,781)); //51 机房第11排机柜右坐标

        factoryNodes.add(new PointF(63,824)); //52 机房第12排机柜左坐标
        factoryNodes.add(new PointF(266,824)); //53 机房第12排机柜中间坐标
        factoryNodes.add(new PointF(492,824)); //54 机房第12排机柜右坐标

        factoryNodes.add(new PointF(63,871)); //55 机房第13排机柜左坐标
        factoryNodes.add(new PointF(266,871)); //56 机房第13排机柜中间坐标
        factoryNodes.add(new PointF(492,871)); //57 机房第13排机柜右坐标

        factoryNodes.add(new PointF(63,910)); //58 机房第14排机柜左坐标
        factoryNodes.add(new PointF(266,910)); //59 机房第14排机柜中间坐标
        factoryNodes.add(new PointF(492,910)); //60 机房第14排机柜右坐标

        factoryNodes.add(new PointF(63,953)); //61 机房第15排机柜左坐标
        factoryNodes.add(new PointF(266,953)); //62 机房第15排机柜中间坐标
        factoryNodes.add(new PointF(492,953)); //63 机房第15排机柜右坐标

        factoryNodes.add(new PointF(63,996)); //64 机房第16排机柜左坐标
        factoryNodes.add(new PointF(266,996)); //65 机房第16排机柜中间坐标
        factoryNodes.add(new PointF(492,996)); //66 机房第16排机柜右坐标

        factoryNodes.add(new PointF(63,1038)); //67 机房第17排机柜左坐标
        factoryNodes.add(new PointF(266,1038)); //68 机房第17排机柜中间坐标
        factoryNodes.add(new PointF(492,1038)); //69 机房第17排机柜右坐标

        factoryNodes.add(new PointF(63,1080)); //70 机房第18排机柜左坐标
        factoryNodes.add(new PointF(266,1080)); //71 机房第18排机柜中间坐标
        factoryNodes.add(new PointF(492,1080)); //72 机房第18排机柜右坐标

        factoryNodes.add(new PointF(47,1120)); //73 机房左下角坐标
        factoryNodes.add(new PointF(94,1120)); //74 机房下走廊中间坐标1
        factoryNodes.add(new PointF(152,1120)); //75 机房下走廊中间坐标2
        factoryNodes.add(new PointF(211,1120)); //76 机房下走廊中间坐标3
        factoryNodes.add(new PointF(266,1120)); //77 机房下走廊中间坐标4,与B通道门口相对
        factoryNodes.add(new PointF(266,1095)); //78 机房下走廊中间坐标与B通道门口交汇点
        factoryNodes.add(new PointF(327,1120)); //79 机房下走廊中间坐标5
        factoryNodes.add(new PointF(367,1120)); //80 机房下走廊中间坐标6
        factoryNodes.add(new PointF(439,1120)); //81 机房下走廊中间坐标7
        factoryNodes.add(new PointF(492,1120)); //82 机房下走廊中间坐标8
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
        factoryNodesContact.add(new PointF(4,6));
        factoryNodesContact.add(new PointF(5,13));
        factoryNodesContact.add(new PointF(6,7));
        factoryNodesContact.add(new PointF(7,8));
        factoryNodesContact.add(new PointF(8,9));
        factoryNodesContact.add(new PointF(9,10));
        factoryNodesContact.add(new PointF(9,14));
        factoryNodesContact.add(new PointF(10,11));
        factoryNodesContact.add(new PointF(10,14));
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
        factoryNodesContact.add(new PointF(29,32));

        factoryNodesContact.add(new PointF(30,33));
        factoryNodesContact.add(new PointF(31,38));
        factoryNodesContact.add(new PointF(32,35));
        factoryNodesContact.add(new PointF(33,36));
        factoryNodesContact.add(new PointF(33,40));

        factoryNodesContact.add(new PointF(34,36));
        factoryNodesContact.add(new PointF(34,37));
        factoryNodesContact.add(new PointF(34,38));
        factoryNodesContact.add(new PointF(34,39));

        factoryNodesContact.add(new PointF(35,37));
        factoryNodesContact.add(new PointF(35,42));
        factoryNodesContact.add(new PointF(39,41));

        factoryNodesContact.add(new PointF(40,43));
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

        factoryNodesContact.add(new PointF(67,70));
        factoryNodesContact.add(new PointF(68,71));
        factoryNodesContact.add(new PointF(69,72));

        factoryNodesContact.add(new PointF(70,73));
        factoryNodesContact.add(new PointF(71,78));
        factoryNodesContact.add(new PointF(72,82));

        factoryNodesContact.add(new PointF(73,74));
        factoryNodesContact.add(new PointF(74,75));
        factoryNodesContact.add(new PointF(75,76));
        factoryNodesContact.add(new PointF(76,77));
        factoryNodesContact.add(new PointF(77,78));
        factoryNodesContact.add(new PointF(78,79));
        factoryNodesContact.add(new PointF(79,80));
        factoryNodesContact.add(new PointF(80,81));
        factoryNodesContact.add(new PointF(81,82));

        return factoryNodesContact;
    }

}
