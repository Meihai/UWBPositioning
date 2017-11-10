package com.onlylemi.mapview.service;

import com.onlylemi.mapview.Constant;
import com.onlylemi.mapview.agorithm.trilateration.NonLinearLeastSquaresSolver;
import com.onlylemi.mapview.agorithm.trilateration.TrilaterationFunction;
import com.onlylemi.mapview.utils.LogUtil;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

/**
 * Created by admin on 2017/11/9.
 * Levernberg-Marquartdt algorithm
 */

public class PosEstimationWithLM extends AbstractPosEstimation implements LocationI {
    private static final String TAG="PosEstimationWithLM";
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
        LeastSquaresOptimizer.Optimum optimum=solver.solve();

        double[] result=optimum.getPoint().toArray();
        LogUtil.w(TAG,"坐标位置为:"+result[0]+result[1]+result[2]);

        return result;
    }


}
