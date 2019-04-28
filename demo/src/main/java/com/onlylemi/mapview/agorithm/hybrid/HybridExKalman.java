package com.onlylemi.mapview.agorithm.hybrid;

import com.onlylemi.mapview.agorithm.filter.KfParams;
import com.onlylemi.mapview.agorithm.pdr.PdrKfParams;

import Jama.Matrix;

/**
 * Created by admin on 2017/12/16.
 */

public class HybridExKalman {
    private HybridKfParams kfParams;

    /**
     *
     * @param east , 行人在ENU坐标系中的东向坐标 由UWB测量所得
     * @param north  行人在ENU坐标系下的北向坐标 由UWB测量所得
     * @param speed  行走速度
     * @param phi  航向
     */
    public HybridExKalman(double east, double north, double speed, double phi){
        kf_init(east,north,speed,phi);
    }
    /**
     *  设置状态x为(east,north,speed,phi),观测值z为(s,phi)
     * 这几个参数为卡尔曼滤波的五个方程所涉及到的参数
     *   以下为卡尔曼滤波的五个方程（步骤）
     *   x_ = kf_params.A * kf_params.x + kf_params.B * kf_params.u;
     *   P_ = kf_params.A * kf_params.P * kf_params.A' + kf_params.Q;
     *   kf_params.K = P_ * kf_params.H' * (kf_params.H * P_ * kf_params.H' + kf_params.R)^-1;
     *   kf_params.x = x_ + kf_params.K * (kf_params.z - kf_params.H * x_);
     *   kf_params.P = P_ - kf_params.K * kf_params.H * P_;
     */
    public void kf_init(double east,double north,double speed,double phi){
        kfParams=new HybridKfParams();
        double[][] Bd=new double[4][4];//外部输入为0
        double[][] Ud=new double[4][1];//外部输入为0
        double[][] Kd=new double[4][4];//卡尔曼增益为0
        double[][] Zd=new double[4][1];
        double[][] Pd=new double[4][4];
        double[][] Xd=new double[][]{{east},{north},{speed},{phi}};
        //和线性系统有关,这里的线性系统是上一时刻的位置加上速度等于当前时刻的位置,而速度本身保持不变
        double[][] Ad=new double[][]{{1,0,Math.sin(Math.toRadians(phi)),speed*Math.cos(Math.toRadians(phi))},
                {0,1,Math.cos(Math.toRadians(phi)),-1.0*speed*Math.sin(Math.toRadians(phi))},
                {0,0,1,0},{0,0,0,1}};
        // 预测噪声协方差矩阵Q：假设预测过程上叠加一个高斯噪声，协方差矩阵为Q
        //大小取决于对预测过程的信任程度。比如east和north的定位噪声去1m，速度噪声取0.1m/s,方位噪声取5度
        double[][] Qd=new double[][]{{1,0,0,0},{0,1,0,0},{0,0,0.1,0},{0,0,0,0.0076}};
        //观测矩阵H:z=H*x
        //这里的状态是（坐标x， 坐标y， 速度x， 速度y），观察值是（坐标x， 坐标y），所以H = eye(2, 4)
        double[][] Hd=new double[][]{{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}};
        //观测噪声协方差矩阵R：假设观测过程上存在一个高斯噪声，协方差矩阵为R
        // 观测噪声
        double[][] Rd=new double[][]{{4.0,0,0,0},{0,4.0,0,0},{0,0,0.0016,0},{0,0,0,0.0114}};
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

    public HybridKfParams kf_update(HybridKfParams kfParams){
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

    public double[] estimatePos(double eastUwb,double northUwb,double speed,double phi){
        if(kfParams==null){
            System.out.println("Init KfParams First!");
            return null;
        }

        double[][] Zd=new double[][]{{eastUwb},{northUwb},{speed},{Math.toRadians(phi)}};
        kfParams.setZd(Zd);
        double[][] Ad=new double[][]{{1,0,Math.sin(Math.toRadians(phi)),speed*Math.cos(Math.toRadians(phi))},
                {0,1,Math.cos(Math.toRadians(phi)),-1.0*speed*Math.sin(Math.toRadians(phi))},
                {0,0,1,0},{0,0,0,1}};
        kfParams.setAd(Ad);
        kfParams=kf_update(kfParams);
        double[][] estimateXd=kfParams.getXd();
        double[] estimateX=new double[]{estimateXd[0][0],estimateXd[1][0]};
        return estimateX;
    }

    public HybridKfParams getKfParams(){
        return this.kfParams;
    }
}
