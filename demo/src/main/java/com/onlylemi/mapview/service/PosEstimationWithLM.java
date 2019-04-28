package com.onlylemi.mapview.service;

import com.onlylemi.mapview.parameter.Constant;
import com.onlylemi.mapview.agorithm.trilateration.NonLinearLeastSquaresSolver;
import com.onlylemi.mapview.agorithm.trilateration.TrilaterationFunction;
import com.onlylemi.mapview.utils.ArrayUtil;
import com.onlylemi.mapview.utils.LogUtil;

import org.apache.commons.math3.fitting.leastsquares.GaussNewtonOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

import Jama.Matrix;

/**
 * Created by admin on 2017/11/9.
 * Levernberg-Marquartdt algorithm
 */

public class PosEstimationWithLM extends AbstractPosEstimation implements LocationI {
    private static final String TAG="PosEstimationWithLM";
    //写文件
    private static String fileName="uwbLMDistance.txt";
    private static String filePath="/sdcard/indoorLocation/";

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

        double[][] bsspos=new double[baseStationInfo.length][3];
        writeUwbDataToFile(filePath,fileName,baseStationInfo);
        //    LogUtil.d(TAG,"After copy bsspos the first line value:"+bsspos[0][0]+","+bsspos[0][1]+","+bsspos[0][2]);
        ArrayUtil.arrayCopy(baseStationInfo,bsspos);
        //  LogUtil.d(TAG,"After copy bsspos the first line value:"+bsspos[0][0]+","+bsspos[0][1]+","+bsspos[0][2]);
        //纠正测距误差
//        for(int i=0;i<bsspos.length;i++){
//            for (int j=0;j<bsspos.length;j++){
//                if(j!=i){
//                    Matrix bssposim=new Matrix(bsspos[i],bsspos[i].length);
//                    Matrix bssposjm=new Matrix(bsspos[j],bsspos[j].length);
//                    Matrix minusijm=bssposim.minus(bssposjm);
//                    Matrix dijm=minusijm.transpose().times(minusijm);
//                    //LogUtil.d(TAG,"dijm row="+dijm.getRowDimension()+",dijm col="+dijm.getColumnDimension());
//                    //获取两个基站的距离
//                    double dij=Math.sqrt(dijm.get(0,0));
//                    //两个球相离的情况
//                    if (dij>(baseStationInfo[i][3]+baseStationInfo[j][3])){
//                        //如果两个球相离,则增加两个球的半径误差
//                        double radius=Math.abs(dij-(baseStationInfo[i][3]+baseStationInfo[j][3]));
//                        baseStationInfo[i][3]=baseStationInfo[i][3]+radius/2.0;
//                        baseStationInfo[j][3]=baseStationInfo[j][3]+radius/2.0;
//                    }else if(dij<Math.abs(baseStationInfo[i][3]-baseStationInfo[j][3])){
//                        double radiuserr=Math.abs(baseStationInfo[i][3]-baseStationInfo[j][3])-dij;
//                        if(baseStationInfo[i][3]>baseStationInfo[j][3]){
//                            baseStationInfo[i][3]=baseStationInfo[i][3]-radiuserr;
//                        }else{
//                            baseStationInfo[j][3]=baseStationInfo[j][3]-radiuserr;
//                        }
//                    }
//                }
//            }
//        }
        double[][] positions=new double[baseStationInfo.length][3];
        double[] distances=new double[baseStationInfo.length];
       //对位置和距离赋值
        for(int i=0;i<baseStationInfo.length;i++){
            positions[i][0]=baseStationInfo[i][0];
            positions[i][1]=baseStationInfo[i][1];
            positions[i][2]=baseStationInfo[i][2];
            distances[i]=baseStationInfo[i][3];
        }

        NonLinearLeastSquaresSolver solver=new NonLinearLeastSquaresSolver(new TrilaterationFunction(positions,distances),
                    new LevenbergMarquardtOptimizer());

        LeastSquaresOptimizer.Optimum optimum=null;
        try{
            optimum=solver.solve();
        }catch(Exception ex){
            LogUtil.w(TAG,"使用LM解算标签位置出错,输入参数错误:"+ex.getMessage());
            return null;
        }

        double[] result=optimum.getPoint().toArray();

        LogUtil.w(TAG,"坐标位置为:"+result[0]+result[1]+result[2]);

        return result;
    }


}
