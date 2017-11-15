package com.onlylemi.mapview.parameter;

import android.graphics.PointF;

import com.onlylemi.mapview.service.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/10/31.
 */

public class TestData {

    public static ArrayList<Point> getPointList() {
        ArrayList<Point> pointList=new ArrayList<Point>();
        double x=640; //单位为mm
        double y=400;//
        double step=300; //走路步伐为300mm一步
        int i;
        for (i=0;i<50;i++){
            pointList.add(new Point(x,y+i*step));
        }
        y=y+(i-1)*step;
        for(i=1;i<16;i++){
            pointList.add(new Point(x+i*step,y));
        }
        x=x+(i-1)*step;
        for(i=1;i<50;i++){
            pointList.add(new Point(x,y-i*step));
        }
        y=400;
        x=640+15*step;
        for(i=1;i<16;i++){
            pointList.add(new Point(x-i*step,y));
        }
        x=640;
        for(i=1;i<9;i++){
            pointList.add(new Point(x+i*step,y));
        }
        x=x+(i-1)*step;
        for(i =1;i<50;i++){
            pointList.add(new Point(x,y+i*step));
        }
        return pointList;
    }

    //工厂机柜坐标,一共40个 地图大小440*850
    public static List<PointF> getFactoryMarks() {
        List<PointF> marks = new ArrayList<>();
        //A通道左边标记点
        marks.add(new PointF(50, 122));
        marks.add(new PointF(50,160));
        marks.add(new PointF(50,198));
        //A通道中间过道标记点
        marks.add(new PointF(81,122));
        marks.add(new PointF(81,160));
        marks.add(new PointF(81,198));
        //A通道右边标记点
        marks.add(new PointF(110,122));
        marks.add(new PointF(110,160));
        marks.add(new PointF(110,198));

        //过道标记点
        marks.add(new PointF(50,238));
        marks.add(new PointF(81,238));
        marks.add(new PointF(110,238));

        //B 通道左边标记点
        marks.add(new PointF(50,270));
        marks.add(new PointF(50,308));
        marks.add(new PointF(50,346));
        marks.add(new PointF(50,384));
        //B通道中间过道标记点
        marks.add(new PointF(81,270));
        marks.add(new PointF(81,308));
        marks.add(new PointF(81,346));
        marks.add(new PointF(81,384));
        //B通道右边标记点
        marks.add(new PointF(110,270));
        marks.add(new PointF(110,308));
        marks.add(new PointF(110,346));
        marks.add(new PointF(110,384));
        return marks;
    }

    //工厂机柜所在地点名称
    public static List<String> getFactoryMarksName(){
        List<String> marksName=new ArrayList<>();
        marksName.add("AL1");//A通道左边第一个标记点
        marksName.add("AL2");
        marksName.add("AL3");

        marksName.add("AP1");//A通道走廊第一个点
        marksName.add("AP2");
        marksName.add("AP3");

        marksName.add("AR1");//A通道右边第一个标记点
        marksName.add("AR2");
        marksName.add("AR3");

        //中间过道标记点
        marksName.add("P1");
        marksName.add("P2");
        marksName.add("P3");

        //B通道左边标记点
        marksName.add("BL1");
        marksName.add("BL2");
        marksName.add("BL3");
        marksName.add("BL4");
        //B通道过道标记点
        marksName.add("BP1");
        marksName.add("BP2");
        marksName.add("BP3");
        marksName.add("BP4");
        //B通道右边标记点
        marksName.add("BR1");
        marksName.add("BR2");
        marksName.add("BR3");
        marksName.add("BR4");

        return marksName;
    }
    //添加公司机柜地点坐标
    public static List<PointF> getCompanyMarks(){
        List<PointF> companyMarks=new ArrayList<PointF>();
        companyMarks.add(new PointF(289,288));
       // companyMarks.add(new PointF(317,288));
        companyMarks.add(new PointF(345,288));
      //  companyMarks.add(new PointF(372,288));
        companyMarks.add(new PointF(400,288));
    //    companyMarks.add(new PointF(428,288));
        return companyMarks;

    }

    //添加公司地点名称
    public static List<String> getCompanyMarksName(){
        List<String> companyMarksName=new ArrayList<String>();
        companyMarksName.add("c1");
       // companyMarksName.add("c2");
        companyMarksName.add("c3");
       // companyMarksName.add("c4");
        companyMarksName.add("c5");
     //   companyMarksName.add("c6");
        return companyMarksName;
    }

    public static List<PointF> getCompanyNodesList(){
        List<PointF> companyNodes=new ArrayList<>();
        //公司前台走廊开端
        companyNodes.add(new PointF(145,114)); //1
        companyNodes.add(new PointF(145,225));//2 展厅边沿走廊位置
        companyNodes.add(new PointF(145,316));//3 展厅门口边沿走廊位置
        companyNodes.add(new PointF(145,351));//4 展厅门口中间走廊位置
        companyNodes.add(new PointF(145,398));//5 展厅门口边沿走廊位置
        companyNodes.add(new PointF(145,492));//6 展厅与会议室边沿
        companyNodes.add(new PointF(145,523));//7 会议室门口中间走廊
        companyNodes.add(new PointF(145,666));//8 会议室2门口走廊位置
        companyNodes.add(new PointF(145,692));//9 会议室2
        companyNodes.add(new PointF(181,351));//10 展厅门口中间位置
        companyNodes.add(new PointF(181,316));//11 展厅门口边沿位置
        companyNodes.add(new PointF(181,398));//12 展厅门口边沿位置
        companyNodes.add(new PointF(259,236));//13 展厅机房后面靠墙壁入口
        companyNodes.add(new PointF(259,283));//14 展厅机房靠近门口方向的机房边缘
        companyNodes.add(new PointF(259,316));//15 展厅机柜边角处
        companyNodes.add(new PointF(259,330));//16 展厅机柜边沿通道
        companyNodes.add(new PointF(259,351));//17展厅中间中间通道
        companyNodes.add(new PointF(259,480));//18展厅边沿通道
        companyNodes.add(new PointF(316,240));//19 展厅机柜后面通道
        companyNodes.add(new PointF(316,330));//20 展厅机房前面边沿通道
        companyNodes.add(new PointF(316,351));//21 展厅机房中间通道
        companyNodes.add(new PointF(316,480));//22 展厅边沿通道
        companyNodes.add(new PointF(346,240));//23 展厅机柜后面通道
        companyNodes.add(new PointF(346,330));//24 展厅机房前面边沿通道
        companyNodes.add(new PointF(346,351));//25 展厅机房中间通道
        companyNodes.add(new PointF(346,480));//26 展厅边沿通道
        companyNodes.add(new PointF(376,240));//27 展厅机柜后面通道
        companyNodes.add(new PointF(376,330));//28 展厅机房前面边沿通道
        companyNodes.add(new PointF(376,351));//29 展厅机房中间通道
        companyNodes.add(new PointF(376,480));//30 展厅边沿通道
        companyNodes.add(new PointF(406,240));//31 展厅机柜后面通道
        companyNodes.add(new PointF(406,330));//32 展厅机房前面边沿通道
        companyNodes.add(new PointF(406,351));//33 展厅机房中间通道
        companyNodes.add(new PointF(406,480));//34 展厅边沿通道
        companyNodes.add(new PointF(436,240));//35 展厅机柜后面通道
        companyNodes.add(new PointF(436,330));//36 展厅机房前面边沿通道
        companyNodes.add(new PointF(436,351));//37 展厅机房中间通道
        companyNodes.add(new PointF(436,480));//38 展厅边沿通道

        companyNodes.add(new PointF(476,330));//39 展厅机房前面边沿通道
        companyNodes.add(new PointF(476,351));//40 展厅机房中间通道
        companyNodes.add(new PointF(476,480));//41 展厅边沿通道

        companyNodes.add(new PointF(506,330));//42 展厅机房前面边沿通道
        companyNodes.add(new PointF(506,351));//43 展厅机房中间通道
        companyNodes.add(new PointF(506,480));//44 展厅边沿通道

        companyNodes.add(new PointF(536,330));//45 展厅机房前面边沿通道
        companyNodes.add(new PointF(536,351));//46 展厅机房中间通道
        companyNodes.add(new PointF(536,480));//47 展厅边沿通道

        companyNodes.add(new PointF(566,330));//48 展厅机房前面边沿通道
        companyNodes.add(new PointF(566,351));//49 展厅机房中间通道
        companyNodes.add(new PointF(566,480));//50 展厅边沿通道
        return companyNodes;
    }

    public static List<PointF> getCompanyNodesContactList(){
        List<PointF> companyNodesContact=new ArrayList<PointF>();
        companyNodesContact.add(new PointF(0,1));
        companyNodesContact.add(new PointF(1,2));
        companyNodesContact.add(new PointF(2,3));
        companyNodesContact.add(new PointF(2,10));
        companyNodesContact.add(new PointF(3,4));
        companyNodesContact.add(new PointF(3,9));
        companyNodesContact.add(new PointF(4,5));
        companyNodesContact.add(new PointF(4,11));
        companyNodesContact.add(new PointF(5,6));
        companyNodesContact.add(new PointF(6,7));
        companyNodesContact.add(new PointF(7,8));
        //8只跟7相连
        companyNodesContact.add(new PointF(9,10));
        companyNodesContact.add(new PointF(9,11));
        companyNodesContact.add(new PointF(9,16));
        companyNodesContact.add(new PointF(10,14));
        companyNodesContact.add(new PointF(11,16));
        companyNodesContact.add(new PointF(12,13));
        companyNodesContact.add(new PointF(12,18));
        companyNodesContact.add(new PointF(13,14));
        companyNodesContact.add(new PointF(14,15));
        companyNodesContact.add(new PointF(15,16));
        companyNodesContact.add(new PointF(15,19));
        companyNodesContact.add(new PointF(16,17));
        companyNodesContact.add(new PointF(16,20));
        companyNodesContact.add(new PointF(17,21));
        companyNodesContact.add(new PointF(18,22));
        companyNodesContact.add(new PointF(19,20));
        companyNodesContact.add(new PointF(19,23));
        companyNodesContact.add(new PointF(20,21));
        companyNodesContact.add(new PointF(20,24));
        companyNodesContact.add(new PointF(21,25));
        companyNodesContact.add(new PointF(22,26));
        companyNodesContact.add(new PointF(23,24));
        companyNodesContact.add(new PointF(23,27));
        companyNodesContact.add(new PointF(24,25));
        companyNodesContact.add(new PointF(24,28));
        companyNodesContact.add(new PointF(25,29));
        companyNodesContact.add(new PointF(26,30));
        companyNodesContact.add(new PointF(27,28));
        companyNodesContact.add(new PointF(27,31));
        companyNodesContact.add(new PointF(28,29));
        companyNodesContact.add(new PointF(28,32));
        companyNodesContact.add(new PointF(29,33));
        companyNodesContact.add(new PointF(30,34));
        companyNodesContact.add(new PointF(31,32));
        companyNodesContact.add(new PointF(31,35));
        companyNodesContact.add(new PointF(32,33));
        companyNodesContact.add(new PointF(32,36));
        companyNodesContact.add(new PointF(33,37));
        companyNodesContact.add(new PointF(35,36));
        companyNodesContact.add(new PointF(35,38));
        companyNodesContact.add(new PointF(36,37));
        companyNodesContact.add(new PointF(36,39));
        companyNodesContact.add(new PointF(37,40));
        companyNodesContact.add(new PointF(38,39));
        companyNodesContact.add(new PointF(38,41));
        companyNodesContact.add(new PointF(39,40));
        companyNodesContact.add(new PointF(39,42));
        companyNodesContact.add(new PointF(40,43));
        companyNodesContact.add(new PointF(41,42));
        companyNodesContact.add(new PointF(41,44));
        companyNodesContact.add(new PointF(42,43));
        companyNodesContact.add(new PointF(42,45));
        companyNodesContact.add(new PointF(43,49));
        companyNodesContact.add(new PointF(44,45));
        companyNodesContact.add(new PointF(44,47));
        companyNodesContact.add(new PointF(45,46));
        companyNodesContact.add(new PointF(45,48));
        companyNodesContact.add(new PointF(46,49));
        companyNodesContact.add(new PointF(47,48));
        companyNodesContact.add(new PointF(48,49));
        return companyNodesContact;
    }


    public static List<PointF> getFctoryNodesList(){
        List<PointF> factoryNodes=new ArrayList<>();
        factoryNodes.add(new PointF(22,101)); //0 机房左上角坐标
        factoryNodes.add(new PointF(85,101)); //1 机房上走廊中间坐标
        factoryNodes.add(new PointF(148,101));//2 机房右上角走廊坐标
        factoryNodes.add(new PointF(171,116));//3 机房门口坐标
        factoryNodes.add(new PointF(190,116));//4 机房门外坐标
        factoryNodes.add(new PointF(22,134)); //5 机房左走廊坐标
        factoryNodes.add(new PointF(85,134)); //6 机房中间通道坐标
        factoryNodes.add(new PointF(148,134));//7 机房右走廊通道坐标
        factoryNodes.add(new PointF(22,172)); //8
        factoryNodes.add(new PointF(85,172)); //9
        factoryNodes.add(new PointF(148,172)); //10
        factoryNodes.add(new PointF(22,210)); //11
        factoryNodes.add(new PointF(85,210)); //12
        factoryNodes.add(new PointF(148,210)); //13
        factoryNodes.add(new PointF(22,232)); //14 过道坐标
        factoryNodes.add(new PointF(50,232)); //15 过道坐标
        factoryNodes.add(new PointF(85,232)); //16 过道坐标
        factoryNodes.add(new PointF(110,232)); //17过道坐标
        factoryNodes.add(new PointF(148,232)); //18 过道坐标
        factoryNodes.add(new PointF(22,256)); //19
        factoryNodes.add(new PointF(85,256));//20 微模块门口坐标
        factoryNodes.add(new PointF(148,256));//21
        factoryNodes.add(new PointF(22,294)); //22
        factoryNodes.add(new PointF(85,294)); //23
        factoryNodes.add(new PointF(148,294)); //24
        factoryNodes.add(new PointF(22,322)); //25
        factoryNodes.add(new PointF(85,322)); //26
        factoryNodes.add(new PointF(148,322));//27
        factoryNodes.add(new PointF(22,360));//28
        factoryNodes.add(new PointF(85,360));//29

        factoryNodes.add(new PointF(148,360));//30
        factoryNodes.add(new PointF(22,398)); //31
        factoryNodes.add(new PointF(85,398)); //32
        factoryNodes.add(new PointF(148,398)); //33
        factoryNodes.add(new PointF(22,425)); //34
        factoryNodes.add(new PointF(50,425)); //35
        factoryNodes.add(new PointF(85,425));//36
        factoryNodes.add(new PointF(110,425));//37
        factoryNodes.add(new PointF(148,425)); //38
        return factoryNodes;
    }

    public static List<PointF> getFactoryNodesContactList(){
        List<PointF> factoryNodesContact=new ArrayList<PointF>();
        factoryNodesContact.add(new PointF(0,1));
        factoryNodesContact.add(new PointF(0,5));
        factoryNodesContact.add(new PointF(1,2));
        factoryNodesContact.add(new PointF(1,6));
        factoryNodesContact.add(new PointF(2,3));
        factoryNodesContact.add(new PointF(3,4));
        factoryNodesContact.add(new PointF(3,7));
        factoryNodesContact.add(new PointF(5,8));
        factoryNodesContact.add(new PointF(6,9));
        factoryNodesContact.add(new PointF(7,10));
        factoryNodesContact.add(new PointF(8,11));
        factoryNodesContact.add(new PointF(9,12));
        factoryNodesContact.add(new PointF(10,13));
        factoryNodesContact.add(new PointF(11,14));
        factoryNodesContact.add(new PointF(12,16));
        factoryNodesContact.add(new PointF(13,18));
        factoryNodesContact.add(new PointF(14,15));
        factoryNodesContact.add(new PointF(14,19));
        factoryNodesContact.add(new PointF(15,16));
        factoryNodesContact.add(new PointF(16,17));

        factoryNodesContact.add(new PointF(16,20));
        factoryNodesContact.add(new PointF(17,18));
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
        factoryNodesContact.add(new PointF(31,34));
        factoryNodesContact.add(new PointF(32,36));
        factoryNodesContact.add(new PointF(33,38));
        factoryNodesContact.add(new PointF(34,35));
        factoryNodesContact.add(new PointF(35,36));
        factoryNodesContact.add(new PointF(36,37));
        factoryNodesContact.add(new PointF(37,38));
        return factoryNodesContact;
    }


}
