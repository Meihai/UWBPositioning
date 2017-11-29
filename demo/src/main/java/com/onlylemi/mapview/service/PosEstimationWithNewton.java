package com.onlylemi.mapview.service;

import com.onlylemi.mapview.parameter.Constant;
import com.onlylemi.mapview.utils.LogUtil;

import Jama.Matrix;

/**
 * Created by admin on 2017/10/27.
 * 使用牛顿迭代法估算标签位置
 */

public class PosEstimationWithNewton extends AbstractPosEstimation implements LocationI {
    private static final String TAG="PostEstimationWithNewton";
    @Override
    public double[]  convertDistanceToPos(String baseStationInfo,String sceneName){
        LogUtil.i(TAG,baseStationInfo);
        double[][] bsInfoArr=parseDistanceStr(baseStationInfo, Constant.NO_ERROR_CORRECTED, Constant.BASE_STATION_DOWN_LIMIT,sceneName);
        if(bsInfoArr==null){
            LogUtil.w(TAG,"bsInfoArr is null,can not calculate tag position!");
            return null;
        }
        return estimateTagPos(bsInfoArr);
    }

    private double[] estimateTagPos(double[][] baseStationInfo){
        double[] originalx=calcWithLeastSquare(baseStationInfo);
        MultivariateNewton mNewton=new MultivariateNewton(originalx,1e-5,100,baseStationInfo);
        double[] result=mNewton.getNewtonMin();
        return result;
    }

    private double[] calcWithLeastSquare(double[][] axisArray){
        int baseNum=axisArray.length;
        //先计算初始值
          /*三边定位最小二乘法计算坐标位置X=(A'A)^A'b*/
        double[][] aM=new double[baseNum-1][3];
        double[][] bM=new double[baseNum-1][1];
           /*数组aM和bM初始化*/
        for(int i=0;i<baseNum-1;i++){
            aM[i][0]=(axisArray[i][0]-axisArray[baseNum-1][0])*2.0;
            aM[i][1]=(axisArray[i][1]-axisArray[baseNum-1][1])*2.0;
            aM[i][2]=(axisArray[i][2]-axisArray[baseNum-1][2])*2.0;
            bM[i][0]=(Math.pow(axisArray[i][0],2)-Math.pow(axisArray[baseNum-1][0],2))+
                    (Math.pow(axisArray[i][1],2)-Math.pow(axisArray[baseNum-1][1],2))+
                    (Math.pow(axisArray[i][2],2)-Math.pow(axisArray[baseNum-1][2],2))+
                    (Math.pow(axisArray[baseNum-1][3],2)-Math.pow( axisArray[i][3],2));
        }
          /*将数组封装成矩阵*/
        Matrix b1=new Matrix(bM);
        Matrix a1=new Matrix(aM);
        double[] tag=new double[3];
        System.out.println("a1*********b1*************************");
        b1.print(5,2);
        a1.print(5,2);
        try{
            Matrix tagm=a1.solve(b1);
            tag=tagm.getRowPackedCopy();
        }catch(Exception ex){

            ex.printStackTrace();
        }

        return tag;
    }
}
