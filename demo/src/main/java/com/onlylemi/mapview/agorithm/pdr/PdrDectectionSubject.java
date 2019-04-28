package com.onlylemi.mapview.agorithm.pdr;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.onlylemi.mapview.utils.ArrayUtil;
import com.onlylemi.mapview.utils.FileUtil;
import com.onlylemi.mapview.utils.LogUtil;
import com.onlylemi.mapview.utils.SignalProcessUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 2017/12/17.
 */

public class PdrDectectionSubject implements PdrSubject,SensorEventListener {
    private static final String TAG="PdrDectectionSubject";
    private ArrayList<PdrObserver> pdrObservers=new ArrayList<PdrObserver>();
    private double curPosx=0;
    private double curPosy=0;
    private Timer timer; //设置定时,定时时间间隔1s
    private static final int TIMER_PERIOD=1000; //单位毫秒
    private static final int TIMER_DELAY=1000;//初始启动延时1秒
    private static final double PEAK_THRESHOLD=0.1; //加速度峰值门限 0.3m/s^2
    private static final int PEAK_TIME_LOWER_BOUND=200;//加速度峰值时间最小间隔 ms
    private static final int PEAK_TIME_UPPER_BOUND=1000;//加速度峰值时间最大间隔 ms
    private ArrayList<Long> timeIndexList=new ArrayList<Long>();
    private ArrayList<float[]> orientList=new ArrayList<float[]>(); //存放方向传感器数据
    private ArrayList<float[]> linearAcceleList=new ArrayList<float[]>(); //存放线性加速度传感器数据
    private Sensor gyroscopeSensor=null;//陀螺仪传感器 测量角速度
    private Sensor acceleSensor=null;//加速度传感器
    private Sensor gravitySensor=null;//重力加速度传感器
    private Sensor megneticSensor=null; //地磁场传感器
    //private ArrayList<float[]> gyroscopeList=new ArrayList<float[]>();// 存放角速度传感器数据
    private static long lastPeakFindTime=System.currentTimeMillis();

    private SensorManager mSensorManager;
    private Sensor orientSensor;
    private Sensor linearAcceleSensor;
   // private Sensor gyroscopeSensor;
   // private float[] mRotationMatrixFromVector = new float[16] ;
   // private float[] mRotationMatrix = new float[16];
    private float[] orientationVals = new float[3];
    private float[] linearAcceleVals=new float[3];
    //private float[] gyroscopeVals=new float[3];

    private ArrayList<double[]> stepRecord=new ArrayList<double[]>(); //记录每走一步的开始时间和结束时间以及相对应的步数
    private int curStepCnt=0;

    //扩展卡尔曼滤波器
    private PdrExKalman pdrExKalman;

    //写文件
    private static String fileName="sensorSample.txt";
    private static String filePath="/sdcard/indoorLocation/";

    //卡尔曼滤波结果
    //写文件
    private static String fileName1="sensorKalman.txt";

    private StringBuilder recordSb;

    public PdrDectectionSubject(SensorManager sensorM){
        mSensorManager=sensorM;
       // orientSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        orientSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        linearAcceleSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        megneticSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        acceleSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gravitySensor=mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        gyroscopeSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    }

    public void start(){
        mSensorManager.registerListener(this,orientSensor,SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this,linearAcceleSensor,SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this,gyroscopeSensor,SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this,megneticSensor,SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this,acceleSensor,SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this,gravitySensor,SensorManager.SENSOR_DELAY_UI);
        pdrExKalman=new PdrExKalman(curPosx,curPosy,0,0);
        timer=new Timer();
        timer.scheduleAtFixedRate(getTask(),TIMER_DELAY,TIMER_PERIOD);
    }

    public void stop(){
        mSensorManager.unregisterListener(this);
        if(timer!=null){
            timer.cancel();
        }
    }

    private TimerTask getTask(){
        TimerTask task=new TimerTask(){
            @Override
            public void run(){
                //todo 实现步数的更新
                Long stopTime=System.currentTimeMillis();
                Long startTime=stopTime-TIMER_PERIOD;
                //获取这一秒钟的平均方向
                if(orientList.size()>0){
                    List<Long> timeListTemp=null;
                    List<float[]> orientListTemp=null;
                    List<float[]> linearAcceleListTemp=null;

                    try{
                         int size=timeIndexList.size();
                         timeListTemp=ArrayUtil.copyList(timeIndexList,size);
                         orientListTemp=ArrayUtil.copyList(orientList,size);
                         linearAcceleListTemp=ArrayUtil.copyList(linearAcceleList,size);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }

                    float[] averageOrient=getAverageValue(startTime,stopTime,timeListTemp,orientListTemp);
                    stepDetection(linearAcceleListTemp,timeListTemp,PEAK_THRESHOLD,PEAK_TIME_LOWER_BOUND,PEAK_TIME_UPPER_BOUND,2);
                    double speed=calCurrentSpeed(stepRecord, 0.8,startTime,stopTime);
                    double[] estimatePos=pdrExKalman.estimatePos(speed,averageOrient[0]);
                    StringBuilder sb=new StringBuilder();
                    sb.append(System.currentTimeMillis()+","+pdrExKalman.getKfParams().getXd()[0][0]+","+pdrExKalman.getKfParams().getXd()[1][0]+
                            ","+pdrExKalman.getKfParams().getXd()[2][0]+","+pdrExKalman.getKfParams().getXd()[3][0]+"\r\n");
                    FileUtil.writeTxtToFile(sb.toString(),filePath,fileName1);
                    curPosx=estimatePos[0];
                    curPosy=estimatePos[1];
                    notifyObservers();
//                    if(stepRecord.size()>1){
//                        LogUtil.i(TAG,"最后一个记录数据为:"+String.format("%13.0f",stepRecord.get(stepRecord.size()-1)[0])+","+String.format("%13.0f",stepRecord.get(stepRecord.size()-1)[1])+","+stepRecord.get(stepRecord.size()-1)[2]);
//                    }
//                    LogUtil.i(TAG,"定时结束时间为:"+System.currentTimeMillis()+",耗时:"+(System.currentTimeMillis()-stopTime)+",开始时间:"+stopTime+",speed="+speed);
                }
            }
        };
        return task;
    }

    /**
     * @param startTime 每一个周期的开始时间
     * @param stopTime  每一个周期的结束时间
     * @param timeIndexList 采集传感器数据相对应的时间戳
     * @param sensorValueList 采集传感器数据值数组列表,列表中的每一个元素代表一个传感器的x,y,z三个轴向分量采样值
     */
    private float[] getAverageValue(Long startTime,Long stopTime,List<Long> timeIndexList,List<float[]> sensorValueList){
        float[] averageSensorVals=new float[sensorValueList.get(0).length];
        int count=0;
        for(int i=timeIndexList.size()-1;i>0;i--){
            if(timeIndexList.get(i)<startTime){
                break;
            }
            if(timeIndexList.get(i)<stopTime && timeIndexList.get(i)>startTime){
                for(int j=0;j<sensorValueList.get(0).length;j++){
                    averageSensorVals[j]+=sensorValueList.get(i)[j];
                }
                count++;
            }
        }
        //取均值
        if(count>0){
            for(int j=0;j<sensorValueList.get(0).length;j++){
                averageSensorVals[j]/=count;
            }
        }else{
            for(int j=0;j<sensorValueList.get(0).length;j++){
                averageSensorVals[j]=sensorValueList.get(sensorValueList.size()-1)[j];
            }
        }
        return averageSensorVals;
    }

    private int count=1;
    private int initRecordCnt=0;
    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType()==Sensor.TYPE_ORIENTATION){//TYPE_ROTATION_VECTOR){
              orientationVals=event.values;
        }else if(event.sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION){
            linearAcceleVals=event.values;
        }
        if(count++ % 40 ==0) {
            if (initRecordCnt == 0) {
                recordSb = new StringBuilder();
                recordSb.append("time,TYPE_ORIENTATION,TYPE_LINEAR_ACCELERATION\r\n");
                FileUtil.writeTxtToFile(recordSb.toString(), filePath, fileName);
                initRecordCnt++;
            }
            if (initRecordCnt > 0) {
                recordSb = new StringBuilder();
                recordSb.append(System.currentTimeMillis());
                recordSb.append(",");
                recordSb.append(orientationVals[0] + "," + orientationVals[1] + "," + orientationVals[2]);
                recordSb.append(",");
                recordSb.append(linearAcceleVals[0] + "," + linearAcceleVals[1] + "," + linearAcceleVals[2]);
                recordSb.append("\r\n");
                FileUtil.writeTxtToFile(recordSb.toString(), filePath, fileName);
                initRecordCnt++;
               // LogUtil.w(TAG, "initRecordCnt=" + initRecordCnt + "," + linearAcceleVals[0] + "," + linearAcceleVals[1] + "," + linearAcceleVals[2]);
            }
            Long sampleTime = System.currentTimeMillis();
            timeIndexList.add(sampleTime);
            orientList.add(new float[]{orientationVals[0], orientationVals[1], orientationVals[2]});
            linearAcceleList.add(new float[]{linearAcceleVals[0], linearAcceleVals[1], linearAcceleVals[2]});
        }
        //gyroscopeList.add(gyroscopeVals);
    }
    //getter yaw
    public float getOrientationYaw(){
        return orientationVals[0];
    }
    // getter pitch
    public float getOrientationPitch(){
        return orientationVals[1];
    }

    //getter roll
    public float getOrientationRoll(){
        return orientationVals[2];
    }
    // get Yaw,Pitch,Roll
    public float[] getOrientations(){
        return orientationVals;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy){}

    @Override
    public void registerObserver(PdrObserver o){
        pdrObservers.add(o);
    }
    @Override
    public void removeObserver(PdrObserver o){
        pdrObservers.remove(o);
    }
    @Override
    public void notifyObservers(){
        for(PdrObserver observer:pdrObservers){
            observer.update(curPosx,curPosy,curStepCnt);
        }
    }


    /**
     *
     * @param acceleration 线性加速度传感器采样值
     * @param timeIndexList 采样时刻列表
     * @param peakThreshold 峰值门限 若大于这一个值说明有可能到达了峰值
     * @param peakTimeLowerBound 两个峰值采样时刻间隔的最小时间
     * @param peakTimeUpperBound 两个峰值采样间隔的最大时间
     * @param windowSize 滑动滤波器窗口大小
     * @return 最后一个峰值对应的时间戳
     */
    private void stepDetection(List<float[]> acceleration,List<Long> timeIndexList,double peakThreshold,double peakTimeLowerBound,double peakTimeUpperBound,int windowSize){

        //记录峰值检测状态
         boolean lastPeakFind=false;

         // 是静止后的第一步标志
         boolean stationFirstStep=true;
         boolean stepStartStatus=false; //静止开始状态是否找到
         long stepStartTime=0;
         boolean stepOverStatus=false; //跨步结束状态
         long stepOverTime=0; //跨步结束时间索引

        //找索引
        int startIndex=0;
        for(int i=timeIndexList.size()-1;i>0;i--){
            if(timeIndexList.get(i)<=lastPeakFindTime){
                if(i+1<timeIndexList.size()){
                    startIndex=i+1;
                }else{
                    startIndex=i;
                }
                break;
            }
        }
//        LogUtil.i(TAG,"这次检测的开始索引为:"+startIndex+","+"上次检测到峰值时间为:"+lastPeakFindTime+",时间最后一个索引为:"+timeIndexList.size());
        //滑动滤波
         float[] filteredData=averageWindowFilter(acceleration,1,windowSize,startIndex);
         //检测峰值
         for (int i=windowSize+1+startIndex;i<filteredData.length-windowSize;i++){
              //  如果已探测到峰值,寻找跨步结束点
               if(lastPeakFind && !stepOverStatus){
                   if(timeIndexList.get(i)-lastPeakFindTime>peakTimeLowerBound/4.0){
                       //零点交叉
                       if(filteredData[i]>0 && filteredData[i-1]<0){
                           if(stepStartStatus){
                               stepOverTime=timeIndexList.get(i).longValue();
                               long stepOverTimeTemp=stepOverTime;
                               long stepStartTimeTemp=stepStartTime;
//                               LogUtil.i(TAG,"运行结束前当前步数为:"+curStepCnt+",i="+i+",开始时间:"+stepStartTimeTemp+",结束时间:"+stepOverTimeTemp);
                               stepOverStatus=true;
                               curStepCnt+=1;
                               stepRecord.add(new double[]{stepStartTimeTemp,stepOverTimeTemp,curStepCnt});
                               stepStartTime=timeIndexList.get(i).longValue();
                               LogUtil.i(TAG,"运行结束后,i="+i+",开始时间:"+stepStartTimeTemp+",结束时间:"+stepOverTimeTemp+String.format(",%13.0f",stepRecord.get(stepRecord.size()-1)[0])
                                      +String.format(",%13.0f",stepRecord.get(stepRecord.size()-1)[1]));

                           }else{
                               stepStartTime=timeIndexList.get(i);
                               stepStartStatus=true;
                           }
                       }
                   }
               }
               //检测到峰值
              if(filteredData[i]>filteredData[i-1] && filteredData[i]>filteredData[i+1] && filteredData[i]>peakThreshold){
                   if(stationFirstStep || timeIndexList.get(i)-lastPeakFindTime>peakTimeUpperBound){
                       //寻找跨步开始点
                       for(int j=i;j>=0;j--){
                           if(filteredData[j]<=0 && filteredData[j+1]>=0 && timeIndexList.get(i)-timeIndexList.get(j)>peakTimeLowerBound/4.0){
                                stationFirstStep=false;
                                stepStartTime=timeIndexList.get(j);
                                stepStartStatus=true;
                               break;
                           }
                       }
                   }
                  lastPeakFind=true;
                  lastPeakFindTime=timeIndexList.get(i);
                  stepOverStatus=false;
              }
         }
    }

    /**
     *
     * @param stepRecord1 步数记录列表
     * @param stepLength 步长,默认设置为0.6m
     * @param startTime  开始时间
     * @param stopTime   结束时间
     * @return 估算在规定时间间隔内的步行速度
     */
    private double calCurrentSpeed(ArrayList<double[]> stepRecord1, double stepLength,long startTime,long stopTime){
        ArrayList<double[]> intervalStepRecord=new ArrayList<>();
        double startCnt=0;
        double stopCnt=0;
        for (int i=stepRecord1.size()-1;i>0;i--){
            if(stepRecord1.get(i)[0]<=startTime-10000){  //每一步结束的时间
                intervalStepRecord.add(new double[]{stepRecord1.get(i)[0],stepRecord1.get(i)[1],stepRecord1.get(i)[2]});
                break;
            }
            intervalStepRecord.add(new double[]{stepRecord1.get(i)[0],stepRecord1.get(i)[1],stepRecord1.get(i)[2]});

        }
        if(intervalStepRecord.size()>=2){
            double[] x=new double[intervalStepRecord.size()];
            double[] y=new double[intervalStepRecord.size()];
            for(int i=0;i<intervalStepRecord.size();i++){
                x[i]=intervalStepRecord.get(i)[1];
                y[i]=intervalStepRecord.get(i)[2];
            }
            double[] coef= SignalProcessUtil.getLineFitting(x,y);
            startCnt=SignalProcessUtil.predictYByCurve(coef,startTime);
            stopCnt=SignalProcessUtil.predictYByCurve(coef,stopTime);
        }
        double speed=(stopCnt-startCnt)*stepLength;
        return speed;
    }

    private float[] averageWindowFilter(List<float[]> unfilteredData,int filteredIndex,int windowSize,int startIndex){
        float[] averageV=new float[unfilteredData.size()];
        if(startIndex<windowSize){
            for(int i=startIndex;i<windowSize+startIndex;i++){
                averageV[i]=unfilteredData.get(i)[filteredIndex];
            }
            for (int i=startIndex+windowSize;i<(unfilteredData.size()-windowSize);i++){
                float accelSum=0;
                for(int j=i+windowSize;j>=i-windowSize;j--){
                    accelSum+=unfilteredData.get(j)[filteredIndex];
                }
                accelSum=accelSum/(2*windowSize+1);
                if(i==averageV.length){
                    LogUtil.i(TAG,"i 等于averageV的长度,i="+i);
                    break;
                }

                averageV[i]=accelSum;
            }
            for(int i=unfilteredData.size()-windowSize;i<unfilteredData.size();i++){
                averageV[i]=unfilteredData.get(i)[filteredIndex];
            }
        }else{
            for (int i=startIndex;i<unfilteredData.size()-windowSize;i++){
                float accelSum=0;
                for(int j=i+windowSize;j>=i-windowSize;j--){
                    accelSum+=unfilteredData.get(j)[filteredIndex];
                }
                accelSum=accelSum/(2*windowSize+1);
                averageV[i]=accelSum;
            }
            for(int i=unfilteredData.size()-windowSize;i<unfilteredData.size();i++){
                averageV[i]=unfilteredData.get(i)[filteredIndex];
            }
        }
        return averageV;
    }
}
