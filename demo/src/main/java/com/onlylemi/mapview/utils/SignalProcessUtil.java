package com.onlylemi.mapview.utils;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import Jama.Matrix;

/**
 * Created by admin on 2017/12/17.
 */

public class SignalProcessUtil {
    /**
     * 滑动均值滤波器
     * @param unfilteredData 需要滤波的序列数据
     * @param windowSize 滑动窗口大小
     * @return
     */
    public static double[] slideAverageFilter(double[] unfilteredData,int windowSize){
       double[] filteredData=new double[unfilteredData.length];
        for(int i=0;i<windowSize;i++){
            filteredData[i]=unfilteredData[i];
        }
        for(int i=windowSize;i<unfilteredData.length;i++){
            double sum=0;
            for(int j=i;j>i-windowSize;j++){
                sum=sum+unfilteredData[j];
            }
            sum=sum/windowSize;
            filteredData[i]=sum;
        }
        return filteredData;
    }

    /**
     * 直线拟合 不包含垂直情况
     * @param x  自观测变量
     * @param y  因变量
     * @return b+kx,[b,k]
     */
    public static double[] getLineFitting(double[] x, double[] y){
        if(x==null || y==null || (x.length!=y.length)){
            return null;
        }

        WeightedObservedPoints points=new WeightedObservedPoints();
        for(int i=0;i<x.length;i++){
            points.add(x[i],y[i]);
        }
        PolynomialCurveFitter fitter= PolynomialCurveFitter.create(1);
        double[] result=fitter.fit(points.toList());
        return result;
    }

    public static double predictYByCurve(double[] curveCoeff,double x){
        double result=0;
        double tv=1;
        for(int i=0;i<curveCoeff.length;i++){
            result+=curveCoeff[i]*tv;
            tv=tv*x;
        }
        return result;
    }

    /**
     * 公司坐标x轴正南方向,y轴正西方向
     * @param enuPos 东北天坐标
     * @param theta  正南沿顺时针转到x轴所形成的夹角,单位角度
     * @return
     */
    public static double[] enuToComCoord(double[] enuPos,double theta){
        double[][] transMatrix=new double[][]{{Math.sin(Math.toRadians(theta)),-1.0*Math.cos(Math.toRadians(theta)),0},
                                                 {-1.0*Math.cos(Math.toRadians(theta)),-1.0*Math.sin(Math.toRadians(theta)),0},
                                                 {0,0,1}};
        Matrix transMatix1=new Matrix(transMatrix,3,3);
        Matrix enuMatrix=new Matrix(enuPos,3);
        Matrix result=transMatix1.times(enuMatrix);
        double[][] resultArr=result.getArray();
        return new double[]{resultArr[0][0],resultArr[1][0],resultArr[2][0]};
    }

    /**
     *
     * @param comCoord 公司地图坐标
     * @param theta 正南沿顺时针转到公司x轴所形成的夹角,单位角度
     * @return enu坐标
     */
    public static double[] comCoordToEnu(double[] comCoord,double theta){
        double[][] transMatrix=new double[][]{{Math.sin(Math.toRadians(theta)),-1.0*Math.cos(Math.toRadians(theta)),0},
                                                 {-1.0*Math.cos(Math.toRadians(theta)),-1.0*Math.sin(Math.toRadians(theta)),0},
                                                 {0,0,1}};
        Matrix transMatix1=new Matrix(transMatrix,3,3);
        Matrix comCoordMatrix=new Matrix(comCoord,3);
        Matrix result=transMatix1.times(comCoordMatrix);
        double[][] resultArr=result.getArray();
        return new double[]{resultArr[0][0],resultArr[1][0],resultArr[2][0]};
    }

    /*拉格朗日插值法*/
    public static double[] lag_method_interpolation(double X[],double Y[],double X0[]){
        int m=X.length;
        int n=X0.length;
        double Y0[]=new double[n];
        for(int i1=0;i1<n;i1++){//遍历X0
            double t=0;
            for(int i2=0;i2<m;i2++){//遍历Y
                double u=1;
                for(int i3=0;i3<m;i3++){//遍历X
                    if(i2!=i3){
                        u=u*(X0[i1]-X[i3])/(X[i2]-X[i3]);
                    }
                }
                u=u*Y[i2];
                t=t+u;
            }
            Y0[i1]=t;
        }
        return Y0;
    }

}
