package com.onlylemi.mapview.service;
import com.onlylemi.mapview.utils.LogUtil;

import Jama.Matrix;
/**
 * Created by admin on 2017/10/27.
 */

public class MultivariateNewton {
    private static final String TAG="MultivariteNewton";
    private double[] originalX;
    private double e;
    private int maxCycle;
    private double[][] baseStationInfo;
    private double objy;
    /**
     * 三元二次牛顿迭代法构造函数
     * @param originalX
     * @param e
     * @param maxCycle
     */
    public MultivariateNewton(double[] originalX, double e, int maxCycle, double[][] baseStationInfo){
        this.originalX=originalX;
        this.e=e;
        this.maxCycle=maxCycle;
        this.baseStationInfo=baseStationInfo;
    }

    public double[] getOriginalX(){
        return this.originalX;
    }

    public void setOriginalX(double[] originalX){
        this.originalX=originalX;
    }

    public double getE(){
        return this.e;
    }

    public void setE(double e){
        this.e=e;
    }

    public void setMaxCycle(int maxCycle){
        this.maxCycle=maxCycle;
    }

    public int getMaxCycle(){
        return this.maxCycle;
    }

    public double[][] getBaseStationInfo(){
        return this.baseStationInfo;
    }

    public void setBaseStationInfo(double[][] baseStationInfo){
        this.baseStationInfo=baseStationInfo;
    }
    public double getObjY(){
        return this.objy;
    }

    /**
     * 目标函数
     * @param x 三维坐标
     * @return 目标函数点x处的值
     */
    public double getOriginal(double[] x){
        if(baseStationInfo==null){
            LogUtil.w(TAG,"No basestation position axis data to use!");
            return -1;
        }
        //计算目标函数
        double objv=0.0;
        for(int i=0;i<4;i++){
            double sqSum=Math.pow(x[0]-baseStationInfo[i][0],2)+Math.pow(x[1]-baseStationInfo[i][1],2)+Math.pow(x[2]-baseStationInfo[i][2],2);
            double fi=Math.sqrt(sqSum)-baseStationInfo[i][3];
            objv=objv+Math.pow(fi,2);
        }
        objv=objv/2.0;
        return objv;
    }

    /**
     * 计算多元函数的海森矩阵
     * @param x
     * @return
     */
    public double[][] getHessian(double[] x){
        double [][] jacobian=new double[3][3]; //默认初始化值为0,若不为0,则需要赋予初始值为0
        for(int i=0;i<4;i++){
            double xxi=x[0]-baseStationInfo[i][0];
            double xxiSq=Math.pow(xxi,2);
            double yyi=x[1]-baseStationInfo[i][1];
            double yyiSq=Math.pow(yyi,2);
            double zzi=x[2]-baseStationInfo[i][2];
            double zziSq=Math.pow(zzi,2);
            double sumxyzSq=xxiSq+yyiSq+zziSq;
            double sumxyzSqCube=Math.pow(sumxyzSq,3);
            //第一行
            jacobian[0][0]+=1.0-(baseStationInfo[i][3]/Math.sqrt(sumxyzSq))+(baseStationInfo[i][3]*xxiSq/(Math.sqrt(sumxyzSqCube)));
            jacobian[0][1]+=baseStationInfo[i][3]*xxi*yyi/Math.sqrt(sumxyzSqCube);
            jacobian[0][2]+=baseStationInfo[i][3]*xxi*zzi/Math.sqrt(sumxyzSqCube);

            //第二行
            jacobian[1][0]+=baseStationInfo[i][3]*xxi*yyi/Math.sqrt(sumxyzSqCube);
            jacobian[1][1]+=1.0-(baseStationInfo[i][3]/Math.sqrt(sumxyzSq))+(baseStationInfo[i][3]*yyiSq/(Math.sqrt(sumxyzSqCube)));
            jacobian[1][2]+=baseStationInfo[i][3]*yyi*zzi/Math.sqrt(sumxyzSqCube);

            //第三行
            jacobian[2][0]+=baseStationInfo[i][3]*xxi*zzi/Math.sqrt(sumxyzSqCube);
            jacobian[2][1]+=baseStationInfo[i][3]*yyi*zzi/Math.sqrt(sumxyzSqCube);
            jacobian[2][2]+=1.0-(baseStationInfo[i][3]/Math.sqrt(sumxyzSq))+(baseStationInfo[i][3]*zziSq/(Math.sqrt(sumxyzSqCube)));

        }
        return jacobian;
    }

    /**
     * 计算多元函数的一阶偏导数
     * @param x
     * @return
     */
    public double[][] getOneDerivative(double[] x){
        double[][] oneDerivative=new double[3][1];
        for(int i=0;i<4;i++){
            double xxi=x[0]-baseStationInfo[i][0];
            double xxiSq=Math.pow(xxi,2);
            double yyi=x[1]-baseStationInfo[i][1];
            double yyiSq=Math.pow(yyi,2);
            double zzi=x[2]-baseStationInfo[i][2];
            double zziSq=Math.pow(zzi,2);
            double sumxyzSq=xxiSq+yyiSq+zziSq;
            double fic=(Math.sqrt(sumxyzSq)-baseStationInfo[i][3])/Math.sqrt(sumxyzSq);
            oneDerivative[0][0]+=fic*xxi;
            oneDerivative[1][0]+=fic*yyi;
            oneDerivative[2][0]+=fic*zzi;
        }
        return oneDerivative;
    }

    /**
     * 返回最小值和相对应的x,y,z坐标
     * @return
     */
    public double[] getNewtonMin(){
        double[] x=this.originalX;
        double y=0.0d;
        double k=1;
        //更新公式
        while(k<this.maxCycle){
            y=getOriginal(x);
            double[][] one=getOneDerivative(x);
            while(Math.abs(one[0][0])<e && Math.abs(one[1][0])<e && Math.abs(one[2][0])<e){
                break;
            }
            double[][] two=getHessian(x);
            Matrix hessianM=new Matrix(two);
            Matrix jacobianM=new Matrix(one);
            Matrix hessianInv=hessianM.inverse();
            Matrix delta=hessianInv.times(jacobianM);
            double[][] deltaResult=delta.getArray();
            x[0]=x[0]-deltaResult[0][0];
            x[1]=x[1]-deltaResult[1][0];
            x[2]=x[2]-deltaResult[2][0];
            k++;
        }
        double [] result=new double[3];
        result[0]=x[0];
        result[1]=x[1];
        result[2]=x[2];
        this.objy=y;
        return result;
    }
}
