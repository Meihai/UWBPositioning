package com.onlylemi.mapview.service;

import com.onlylemi.mapview.utils.ArrayUtil;
import com.onlylemi.mapview.parameter.Constant;

import Jama.Matrix;
/**
 * Created by admin on 2017/10/26.
 */

public class PosEstimationWithCG extends AbstractPosEstimation implements LocationI {
    private static final String TAG="PostEstimationWithCG";
    @Override
    public double[] convertDistanceToPos(String baseStationInfo,String sceneName){
        double[][] bsInfoArr=parseDistanceStr(baseStationInfo, Constant.NO_ERROR_CORRECTED, Constant.BASE_STATION_DOWN_LIMIT,sceneName);
        if(bsInfoArr==null){
//            LogUtil.w(TAG,"bsInfoArr is null,can not calculate tag position!");
            return null;
        }
        new Matrix(bsInfoArr).print(5,2);
        return estimateTagPos(bsInfoArr);
    }
    private double[] estimateTagPos(double[][] baseStationInfo){
        if(baseStationInfo.length<3 || baseStationInfo[0].length<3){
            //     LogUtil.d(TAG,"baseStationInfo is invalid");
            return null;
        }
        double[][] bsspos=new double[baseStationInfo.length][3];
        //    LogUtil.d(TAG,"After copy bsspos the first line value:"+bsspos[0][0]+","+bsspos[0][1]+","+bsspos[0][2]);
        ArrayUtil.arrayCopy(baseStationInfo,bsspos);
        //  LogUtil.d(TAG,"After copy bsspos the first line value:"+bsspos[0][0]+","+bsspos[0][1]+","+bsspos[0][2]);
        //纠正测距误差
        for(int i=0;i<bsspos.length;i++){
            for (int j=0;j<bsspos.length;j++){
                if(j!=i){
                    Matrix bssposim=new Matrix(bsspos[i],bsspos[i].length);
                    Matrix bssposjm=new Matrix(bsspos[j],bsspos[j].length);
                    Matrix minusijm=bssposim.minus(bssposjm);
                    Matrix dijm=minusijm.transpose().times(minusijm);
                    //LogUtil.d(TAG,"dijm row="+dijm.getRowDimension()+",dijm col="+dijm.getColumnDimension());
                    //获取两个基站的距离
                    double dij=Math.sqrt(dijm.get(0,0));
                    //两个球相离的情况
                    if (dij>(baseStationInfo[i][3]+baseStationInfo[j][3])){
                        //如果两个球相离,则增加两个球的半径误差
                        double radius=Math.abs(dij-(baseStationInfo[i][3]+baseStationInfo[j][3]));
                        baseStationInfo[i][3]=baseStationInfo[i][3]+radius/2.0;
                        baseStationInfo[j][3]=baseStationInfo[j][3]+radius/2.0;
                    }else if(dij<Math.abs(baseStationInfo[i][3]-baseStationInfo[j][3])){
                        double radiuserr=Math.abs(baseStationInfo[i][3]-baseStationInfo[j][3])-dij;
                        if(baseStationInfo[i][3]>baseStationInfo[j][3]){
                            baseStationInfo[i][3]=baseStationInfo[i][3]-radiuserr;
                        }else{
                            baseStationInfo[j][3]=baseStationInfo[j][3]-radiuserr;
                        }
                    }
                }
            }
        }
        //计算第一个约束条件
        Matrix A=new Matrix(bsspos.length-1,3);
        for(int i=0;i<bsspos.length-1;i++){
            double elem1=(bsspos[i+1][0]-bsspos[0][0])*(-2.0);
            double elem2=(bsspos[i+1][1]-bsspos[0][1])*(-2.0);
            double elem3=(bsspos[i+1][2]-bsspos[0][2])*(-2.0);

            A.set(i,0,elem1);
            A.set(i,1,elem2);
            A.set(i,2,elem3);
        }
        //基站向量模长
        Matrix K=new Matrix(bsspos.length,1);
        for (int i=0;i<bsspos.length;i++){
            Matrix bsselem=new Matrix(bsspos[i],bsspos[i].length);
            Matrix elemenMLen=bsselem.transpose().times(bsselem);
            K.set(i,0,elemenMLen.get(0,0));
        }

        //M-1维向量 KV
        Matrix KV=new Matrix(bsspos.length-1,1);
        for(int i=0;i<bsspos.length-1;i++){
            KV.set(i,0,K.get(0,0)-K.get(i+1,0));
        }

        //Y1 (M-1)*(2M-1)维矩阵
        Matrix Y1=new Matrix(bsspos.length-1,2*bsspos.length-1);
        for(int i=0;i<bsspos.length-1;i++){
            double sqd1=Math.pow(baseStationInfo[0][3],2)*(-1.0);
            double sqd2=Math.pow(baseStationInfo[i+1][3],2);
            Y1.set(i,0,sqd1);
            Y1.set(i,i+1,sqd2);
            Y1.set(i,bsspos.length+i,1.0);
        }

        // 求Q 3*(2M-1)矩阵
//        Matrix Q,Q1,Q2;
//        try{
//             Q=(A.transpose().times(A)).inverse().times(A.transpose()).times(Y1);
//            //求 Q1 和 Q2
//            Q1=Q.getMatrix(0,Q.getRowDimension()-1,0,bsspos.length-1);
//            Q2=Q.getMatrix(0,Q.getRowDimension()-1,bsspos.length,2*bsspos.length-2);
//        }catch(Exception ex){
//            ex.printStackTrace();
//            return null;
//        }

        //求alpha_min
        Matrix alphaMin=new Matrix(bsspos.length,1);
        for(int i=0;i<bsspos.length;i++){
            double alpha_max=0.0;
            for(int j=0;j<bsspos.length;j++){
                if(j!=i){
                    //计算第i个基站和第j个基站之间的距离
                    Matrix bssi=new Matrix(bsspos[i],bsspos[i].length);
                    Matrix bssj=new Matrix(bsspos[j],bsspos[j].length);
                    Matrix bssi_j=bssi.minus(bssj);
                    Matrix Lijm=bssi_j.transpose().times(bssi_j);
                    double Lij=Math.sqrt(Lijm.get(0,0));
                    double alpha_st=(Lij-baseStationInfo[j][3])/baseStationInfo[i][3];
                    if(alpha_st>alpha_max && alpha_st<=1){
                        alpha_max=alpha_st;
                    }
                }
            }
            alphaMin.set(i,0,alpha_max);
        }
        Matrix vMin=alphaMin.arrayTimesEquals(alphaMin);
        Matrix vMax=new Matrix(bsspos.length,1,1.0);
       // Matrix V0=vMax;
        Matrix V0=vMin.plus(vMax).times(0.5);
        //此处调用CG算法 todo

        Matrix Y2=new Matrix(V0.getRowDimension()+KV.getRowDimension(),V0.getColumnDimension());
        Y2.setMatrix(0,V0.getRowDimension()-1,0,V0.getColumnDimension()-1,V0);
        Y2.setMatrix(V0.getRowDimension(),V0.getRowDimension()+KV.getRowDimension()-1,0,KV.getColumnDimension()-1,KV);
        Matrix estimateTagM=A.solve(Y1.times(Y2));
        //Matrix estimateTagM=(A.transpose().times(A)).inverse().times(A.transpose()).times(Y1).times(Y2);
        double[] estimateTag=estimateTagM.getRowPackedCopy();
        return estimateTag;
    }


}
