package com.onlylemi.mapview.service;

import com.onlylemi.mapview.agorithm.trilateration.LinearLeastSquaresSolver;
import com.onlylemi.mapview.agorithm.trilateration.TrilaterationFunction;
import com.onlylemi.mapview.parameter.Constant;
import com.onlylemi.mapview.utils.LogUtil;
/**
 * Created by admin on 2017/11/16.
 */

public class PosEstimationWithLSQ extends AbstractPosEstimation implements LocationI{
    private static final String TAG="PosEstimationWithLSQ";
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

        LinearLeastSquaresSolver solver=new LinearLeastSquaresSolver(new TrilaterationFunction(positions,distances));
        double[] result= solver.solve().toArray();
        LogUtil.w(TAG,"坐标位置为:"+result[0]+result[1]+result[2]);
        return result;
    }
}
