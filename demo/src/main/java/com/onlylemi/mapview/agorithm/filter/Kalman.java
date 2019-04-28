package com.onlylemi.mapview.agorithm.filter;

import Jama.Matrix;

/**
 * Created by admin on 2017/11/30.
 */

public class Kalman {
    private KfParams kfParams;

    /**
     *
     * @param px 坐标x
     * @param py 坐标y
     * @param vx 速度vx
     * @param vy 速度vy
     */
    public Kalman(double px,double py,double vx,double vy){
        kf_init(px,py,vx,vy);
    }
    /**
     *  设置状态x为(坐标x,坐标y,速度x,速度y),观测值z为(坐标x,坐标y)
     */
    public void kf_init(double px,double py,double vx,double vy){
        kfParams=new KfParams();
        double[][] Bd=new double[4][4];//外部输入为0
        double[][] Ud=new double[4][1];//外部输入为0
        double[][] Kd=new double[4][2];//卡尔曼增益为0
        double[][] Zd=new double[2][1];
        double[][] Pd=new double[4][4];
        double[][] Xd=new double[][]{{px},{py},{vx},{vy}};
        //和线性系统有关,这里的线性系统是上一时刻的位置加上速度等于当前时刻的位置,而速度本身保持不变
        double[][] Ad=new double[][]{{1,0,1,0},{0,1,0,1},{0,0,1,0},{0,0,0,1}};
        // 预测噪声协方差矩阵Q：假设预测过程上叠加一个高斯噪声，协方差矩阵为Q
        //大小取决于对预测过程的信任程度。比如，假设认为运动目标在y轴上的速度可能不匀速，那么可以把这个对角矩阵的最后一个值调大。有时希望出来的轨迹更平滑，可以把这个调更小
        double[][] Qd=new double[][]{{0.01,0,0,0},{0,0.01,0,0},{0,0,0.01,0},{0,0,0,0.01}};
        //观测矩阵H:z=H*x
        //这里的状态是（坐标x， 坐标y， 速度x， 速度y），观察值是（坐标x， 坐标y），所以H = eye(2, 4)
        double[][] Hd=new double[][]{{1,0,0,0},{0,1,0,0}};
        //观测噪声协方差矩阵R：假设观测过程上存在一个高斯噪声，协方差矩阵为R
        //大小取决于对观察过程的信任程度。比如，假设观测结果中的坐标x值常常很准确，那么矩阵R的第一个值应该比较小
        double[][] Rd=new double[][]{{2,0},{0,2}};
        kfParams.setBd(Bd);
        kfParams.setUd(Ud);
        kfParams.setKd(Kd);
        kfParams.setZd(Zd);
        kfParams.setPd(Pd);
        kfParams.setXd(Xd);
        kfParams.setAd(Ad);
        kfParams.setQd(Qd);
        kfParams.setHd(Hd);
        kfParams.setRd(Rd);
    }

    public KfParams kf_update(KfParams kfParams){
        Matrix Adm=new Matrix(kfParams.getAd());
        Matrix Bdm=new Matrix(kfParams.getBd());
        Matrix Udm=new Matrix(kfParams.getUd());
        Matrix Kdm=new Matrix(kfParams.getKd());
        Matrix Zdm=new Matrix(kfParams.getZd());
        Matrix Pdm=new Matrix(kfParams.getPd());
        Matrix Xdm=new Matrix(kfParams.getXd());
        Matrix Qdm=new Matrix(kfParams.getQd());
        Matrix Hdm=new Matrix(kfParams.getHd());
        Matrix Rdm=new Matrix(kfParams.getRd());

        // 卡尔曼滤波更新方程
        Matrix nextXdm=Adm.times(Xdm).plus(Bdm.times(Udm));
        Matrix nextPdm=Adm.times(Pdm).times(Adm.transpose()).plus(Qdm);
        Matrix updateKdm=nextPdm.times(Hdm.transpose()).times(Hdm.times(nextPdm).times(Hdm.transpose()).plus(Rdm).inverse());
        Matrix updateXdm=nextXdm.plus(Kdm.times(Zdm.minus(Hdm.times(nextXdm))));
        Matrix updatePdm=nextPdm.minus(Kdm.times(Hdm).times(nextPdm));

        kfParams.setKd(updateKdm.getArray());
        kfParams.setXd(updateXdm.getArray());
        kfParams.setPd(updatePdm.getArray());
        return  kfParams;
    }

    public double[] estimatePos(double[] posArray){
        if(kfParams==null){
            System.out.println("Init KfParams First!");
            return null;
        }

        double[][] Zd=new double[][]{{posArray[0]},{posArray[1]}};
        kfParams.setZd(Zd);
        kfParams=kf_update(kfParams);
        double[][] estimateXd=kfParams.getXd();
        double[] estimateX=new double[]{estimateXd[0][0],estimateXd[1][0]};
        return estimateX;
    }

    public KfParams getKfParams(){
        return this.kfParams;
    }
}
