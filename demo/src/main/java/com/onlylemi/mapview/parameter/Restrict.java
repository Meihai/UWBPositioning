package com.onlylemi.mapview.parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加公司和工厂的约束区域
 */

public class Restrict {
    //公司行人不可达区域
    public static List<double[]> getComRestrictRegionList(){
        List<double[]> comList=new ArrayList<double[]>();
        //存放每个矩形约束的x坐标最小值,x坐标最大值,y坐标最小值,y坐标最大值
        //机柜禁区
        double[] restric1=new double[]{2.4,8.8,0.77,1.97};
        comList.add(restric1);
        //办公区域
        double[] restric2=new double[]{0,8.8,0,-1000};
        comList.add(restric2);
        //楼外区域
        double[] restric3=new double[]{8.8,1000,-1000,1000};
        comList.add(restric3);
        //会议室区域
        double[] restric4=new double[]{0,8.8,5.6,1000};
        comList.add(restric4);
        //室外走廊区域
        double[] restric5=new double[]{-1000,-1.2,-1000,1000};
        comList.add(restric5);
        return comList;
    }

    //公司行人可行域的直线方程
    public static List<double[]> getComFeasibleRegionList(){
        List<double[]> comList=new ArrayList<double[]>();
        //机柜后面通道起点和终点 Ax+By+C=0 起点线段坐标 终点线段坐标
        double[] line1=new double[]{0,1,-0.4,1.8,0.4,6.6,0.4};
        double[] line2=new double[]{1,0,-2.0,2.0,0.4,2.0,2.0};
        double[] line3=new double[]{0,1,-2.3,2.0,2.3,6.6,2.3};
        double[] line4=new double[]{1,0,-7.2,7.2,2.3,7.2,5.5};
        double[] line5=new double[]{0,1,-2.8,0,2.8,8.8,2.8};
        double[] line6=new double[]{0,1,-5.0,0,5.0,8.8,5.0};
        double[] line7=new double[]{1,0,0.6,-0.6,-2.4,-0.6,9.6};
        comList.add(line1);
        comList.add(line2);
        comList.add(line3);
        comList.add(line4);
        comList.add(line5);
        comList.add(line6);
        comList.add(line7);
        return comList;
    }
    //工厂行人不可达区域
    public static List<double[]> getFacRestrictRegionList(){
        List<double[]> facList=new ArrayList<double[]>();
        //机房外面左边不可行区域
        double[] restrict1=new double[]{-1000,0,0,15.9};
        facList.add(restrict1);
        //机房上边不可行区域
        double[] restrict2=new double[]{-1000,1000,-1000,0};
        facList.add(restrict2);
        //机房A通道左边不可行区域
        double[] restrict3=new  double[]{1.41,2.61,1.57,6.11};
        facList.add(restrict3);
        //机房A通道右边不可行区域
        double[] restrict4=new double[]{3.81,5.01,1.57,6.11};
        facList.add(restrict4);
        //机房B通道左边不可行区域
         double[] restrict5=new double[]{1.41,2.61,7.94,14.24};
         facList.add(restrict5);
        //机房B通道右边不可行区域
        double[] restrict6=new double[]{3.81,5.01,7.94,14.24};
        facList.add(restrict6);
        //机房下边不可行区域
        double[] restrict7=new double[]{-1000,1000,15.90,1000};
        facList.add(restrict7);
        //机房右边不可行区域
        double[] restrict8=new double[]{6.8,1000,6.11,1000};
        facList.add(restrict8);
        return facList;
    }

    //工厂机房行人可行域
    public static List<double[]> getFacFeasibleRegionList(){
        List<double[]> facList=new ArrayList<double[]>();
        //y=0.75m
        double[] feasibleRegion1=new double[]{0,1,-0.75,0.9,0.75,6.5,0.75};
        //y=7.0m
        double[] feasibleRegion2=new double[]{0,1,-7.0,0.9,0.75,6.5,0.75};
        //y=15.0m
        double[] feasibleRegion3=new double[]{0,1,-15.0,0.9,0.75,6.5,0.05};
        //x=0.8m
        double[] feasibleRegion4=new double[]{1,0,-0.8,0.8,0.75,0.8,15.0};
        //x=3.2
        double[] feasibleRegion5=new double[]{1,0,-3.2,3.2,1.8,3.2,14};
        //x=5.6
        double[] feasibleRegion6=new double[]{1,0,-5.6,5.6,0.75,5.6,15.0};
        facList.add(feasibleRegion1);
        facList.add(feasibleRegion2);
        facList.add(feasibleRegion3);
        facList.add(feasibleRegion4);
        facList.add(feasibleRegion5);
        facList.add(feasibleRegion6);
        return facList;
    }
}
