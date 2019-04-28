package com.onlylemi.mapview.agorithm.filter;

import com.onlylemi.mapview.utils.LogUtil;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

/**
 * Created by admin on 2017/12/7.
 */

public class InterpolationFilter {
    private static final String TAG="InterpolationFilter";
    private double[] timeSlice;
    private double[] posx;
    private double[] posy;
    public InterpolationFilter(double[] timeSlice, double[] posx, double[] posy){
        this.timeSlice=timeSlice;
        this.posx=posx;
        this.posy=posy;
    }

    public double predictXByCurTime(double time){
        double[] coeff=getCurveFitting(timeSlice,posx,2);
        LogUtil.i(TAG,"X预测曲线系数:");
        for(int i=0;i<coeff.length;i++){
            LogUtil.i(TAG,coeff[i]+",");
        }
        LogUtil.i(TAG,"X预测曲线系数结束");
        double predX=predictYByCurve(coeff,time);
        return predX;
    }


    public double predictYByCurTime(double time){
        double[] coeffy=getCurveFitting(timeSlice,posy,2);
        LogUtil.i(TAG,"Y预测曲线系数:");
        for(int i=0;i<coeffy.length;i++){
            LogUtil.i(TAG,coeffy[i]+",");
        }
        LogUtil.i(TAG,"Y预测曲线结束");
        double predY=predictYByCurve(coeffy,time);
        return predY;
    }

    public void setTimeSlice(double[] timeSlice){
        this.timeSlice=timeSlice;
    }

    public double[] getTimeSlice(){
        return this.timeSlice;
    }

    public void setPosx(double[] posx){
        this.posx=posx;
    }

    public double[] getPosx(){
        return this.posx;
    }

    public void setPosy(double[] posy){
        this.posy=posy;
    }

    public double[] getPosy(){
        return this.posy;
    }
    /**
     *
     * @param x  自观测变量
     * @param y  因变量
     * @param degree 多项式次数
     * @return
     */
    public double[] getCurveFitting(double[] x, double[] y, int degree){
        if(x==null || y==null || (x.length!=y.length) || degree<0){
            return null;
        }
        WeightedObservedPoints points=new WeightedObservedPoints();
        for(int i=0;i<x.length;i++){
            points.add(x[i],y[i]);
        }
        PolynomialCurveFitter fitter= PolynomialCurveFitter.create(degree);
        double[] result=fitter.fit(points.toList());
        return result;
    }

    public double predictYByCurve(double[] curveCoeff,double x){
        double result=0;
        double tv=1;
        for(int i=0;i<curveCoeff.length;i++){
            result+=curveCoeff[i]*tv;
            tv=tv*x;
        }
        return result;
    }
}
