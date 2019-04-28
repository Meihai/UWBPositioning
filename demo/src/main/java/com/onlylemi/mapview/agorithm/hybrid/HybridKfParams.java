package com.onlylemi.mapview.agorithm.hybrid;

/**
 * Created by admin on 2017/12/16.
 * PDR 算法和UWB超宽带混合定位所使用的扩展卡尔曼算法
 */

public class HybridKfParams {
    private double[][] Ad;
    private double[][] Bd;
    private double[][] Ud;
    private double[][] Kd;
    private double[][] Zd;
    private double[][] Pd;
    private double[][] Xd;
    private double[][] Qd;
    private double[][] Hd;
    private double[][] Rd;

    public HybridKfParams(){

    }

    /**
     *
     * @param Ad
     * @param Bd
     * @param Ud
     * @param Kd
     * @param Zd
     * @param Pd
     * @param Xd
     * @param Qd
     * @param Hd
     * @param Rd
     * 这几个参数为卡尔曼滤波的五个方程所涉及到的参数
     *   以下为卡尔曼滤波的五个方程（步骤）
     *   x_ = kf_params.A * kf_params.x + kf_params.B * kf_params.u;
     *   P_ = kf_params.A * kf_params.P * kf_params.A' + kf_params.Q;
     *   kf_params.K = P_ * kf_params.H' * (kf_params.H * P_ * kf_params.H' + kf_params.R)^-1;
     *   kf_params.x = x_ + kf_params.K * (kf_params.z - kf_params.H * x_);
     *   kf_params.P = P_ - kf_params.K * kf_params.H * P_;
     */

    public HybridKfParams(double[][] Ad,double[][] Bd,double[][]Ud,double[][] Kd,double[][] Zd,double[][] Pd,double[][] Xd,
                       double[][] Qd,double[][] Hd,double[][]Rd){
        this.Ad=Ad;
        this.Bd=Bd;
        this.Ud=Ud;
        this.Zd=Zd;
        this.Pd=Pd;
        this.Xd=Xd;
        this.Qd=Qd;
        this.Hd=Hd;
        this.Rd=Rd;
    }

    public void setAd(double[][] Ad){
        this.Ad=Ad;
    }

    public double[][] getAd(){
        return this.Ad;
    }

    public void setBd(double[][] Bd){
        this.Bd=Bd;
    }

    public double[][] getBd(){
        return this.Bd;
    }

    public void setUd(double[][] Ud){
        this.Ud=Ud;
    }

    public double[][] getUd(){
        return this.Ud;
    }

    public double[][] getKd(){
        return this.Kd;
    }

    public void setKd(double[][] Kd){
        this.Kd=Kd;
    }
    public void setZd(double[][] Zd){
        this.Zd=Zd;
    }

    public double[][] getZd(){
        return this.Zd;
    }

    public void setPd(double[][] Pd){
        this.Pd=Pd;
    }

    public double[][] getPd(){
        return this.Pd;
    }

    public void setQd(double[][] Qd){
        this.Qd=Qd;
    }

    public double[][] getQd(){
        return this.Qd;
    }

    public void setHd(double[][] Hd){
        this.Hd=Hd;
    }

    public double[][] getHd(){
        return this.Hd;
    }

    public void setRd(double[][] Rd){
        this.Rd=Rd;
    }

    public double[][] getRd(){
        return this.Rd;
    }

    public void setXd(double[][] Xd){
        this.Xd=Xd;
    }

    public double[][] getXd(){
        return this.Xd;
    }
}
