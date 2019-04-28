package com.onlylemi.mapview.agorithm.hybrid;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.onlylemi.mapview.HybridMapActivity;
import com.onlylemi.mapview.agorithm.filter.InterpolationFilter;
import com.onlylemi.mapview.agorithm.filter.Kalman;
import com.onlylemi.mapview.agorithm.pdr.PdrObserver;
import com.onlylemi.mapview.agorithm.pdr.PdrSubject;
import com.onlylemi.mapview.parameter.Restrict;
import com.onlylemi.mapview.utils.ArrayUtil;
import com.onlylemi.mapview.utils.FileUtil;
import com.onlylemi.mapview.utils.LogUtil;
import com.onlylemi.mapview.utils.SignalProcessUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * 超宽带和PDR联合定位
 */
public class HybridSubject implements PdrSubject,SensorEventListener {
    private static final String TAG="HybridSubject";
    private ArrayList<PdrObserver> pdrObservers=new ArrayList<PdrObserver>();
    private double curPosx=0;
    private double curPosy=0;
    private Timer timer; //设置定时,定时时间间隔1s
    private static final int TIMER_PERIOD=1000; //单位毫秒
    private static final int TIMER_DELAY=1000;//初始启动延时1秒
    private static final double PEAK_THRESHOLD=0.3; //加速度峰值门限 0.3m/s^2
    private static final int PEAK_TIME_LOWER_BOUND=200;//加速度峰值时间最小间隔 ms
    private static final int PEAK_TIME_UPPER_BOUND=1000;//加速度峰值时间最大间隔 ms
    private ArrayList<Long> timeIndexList=new ArrayList<Long>();
    private ArrayList<float[]> orientList=new ArrayList<float[]>(); //存放方向传感器数据
    private ArrayList<float[]> linearAcceleList=new ArrayList<float[]>(); //存放线性加速度传感器数据
    private Vector<Double> eastPosxList=new Vector<Double>();
    private Vector<Double> northPosyList=new Vector<Double>();
    private Vector<double[]> uwbPosList=new Vector<double[]>();
    private Vector<Long> uwbPosTime=new Vector<Long>();
    private int no_update_cnt=0; //从上一次更新到最后一次检测时间,有多久没有更新数据

    private Sensor acceleSensor=null;//加速度传感器
    private Sensor gravitySensor=null;//重力加速度传感器
    private Sensor megneticSensor=null; //地磁场传感器
    //private ArrayList<float[]> gyroscopeList=new ArrayList<float[]>();// 存放角速度传感器数据
    private static long lastPeakFindTime=System.currentTimeMillis();
    private PosShareVar posShareVar=new PosShareVar();
    private SensorManager mSensorManager;
    private Sensor orientSensor;  //方向传感器
    private Sensor linearAcceleSensor; //线性加速度传感器
    private Sensor gyroscopeSensor; //陀螺仪传感器
    private Sensor yawSensor;
    private float[] mRotationMatrixFromVector = new float[16] ; //Yaw角计算传感器
    private float[] mRotationMatrix = new float[16];
    private float[] orientationVals = new float[3];
    private float[] linearAcceleVals=new float[3];
    private float[] gyroscopeVals=new float[3];  //角速度瞬时值
    private float[] accelerometerValues=new float[3]; //加速度瞬时值
    private float[] magneticFieldValues=new float[3]; //磁力计瞬时值
    private float[] gravityValues=new float[3]; //重力加速度瞬时值
    private float[] yawValues=new float[3];//yaw 传感器

    private ArrayList<double[]> stepRecord=new ArrayList<double[]>(); //记录每走一步的开始时间和结束时间以及相对应的步数
    private int curStepCnt=0;
    private int lastStepCnt=0;
    private double stepLength=0.7;
    //扩展卡尔曼滤波器
    private Kalman pdrExKalman;


    //写文件
    private static String fileName="sensorHybrid.txt";
    private static String filePath="/sdcard/indoorLocation/";

    //数据分开采集看看
    private static String accelFileName="sensorAccel.txt";
    private static String linearAccelFileName="sensorLinearAccel.txt";
    private static String orientFileName="sensorOrient.txt";
    private static String gyroscopeFileName="sensorGyroscope.txt";

    //卡尔曼滤波结果
    //写文件
    private static String fileName1="sensorHybridKalman.txt";
    private static String fileName2="uwbLocationData.txt";

    private StringBuilder recordSb;

    public HybridSubject(SensorManager sensorM){
        mSensorManager=sensorM;
        yawSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        orientSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        linearAcceleSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        megneticSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        acceleSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gravitySensor=mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        gyroscopeSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    }

    public void start(){
        mSensorManager.registerListener(this,orientSensor,SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this,linearAcceleSensor,SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this,gyroscopeSensor,SensorManager.SENSOR_DELAY_GAME);
       // mSensorManager.registerListener(this,megneticSensor,SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this,acceleSensor,SensorManager.SENSOR_DELAY_GAME);
        //mSensorManager.registerListener(this,gravitySensor,SensorManager.SENSOR_DELAY_GAME);
       // mSensorManager.registerListener(this,yawSensor,SensorManager.SENSOR_DELAY_GAME);
       // pdrExKalman=new HybridExKalman(curPosx,curPosy,0,0);
        pdrExKalman=new Kalman(curPosx,curPosy,0,0);
        no_update_cnt=11;
        timer=new Timer();
        timer.scheduleAtFixedRate(getTask1(),TIMER_DELAY,TIMER_PERIOD);
    }

    public void stop(){
        mSensorManager.unregisterListener(this);
        if(timer!=null){
            timer.cancel();
        }
    }
    /**
     *   注意uwbPos 位置的单位要转换为m
     */

    public void setCoord(double[] uwbPos,double theta){
        double[] newPosition=constraitCheck(uwbPos);
        double[] enuPos=SignalProcessUtil.comCoordToEnu(newPosition,theta);
        long updateTime=System.currentTimeMillis();
        posShareVar.setPosx(enuPos[0]);
        posShareVar.setPosy(enuPos[1]);
        posShareVar.setUpdateTime(updateTime);
        posShareVar.setUpdateFlag(true);
        //保存测距数据历史信息
        eastPosxList.add(enuPos[0]);
        northPosyList.add(enuPos[1]);
        uwbPosTime.add(updateTime);
        String line=System.currentTimeMillis()+","+enuPos[0]+","+enuPos[1]+"\r\n";
        FileUtil.writeTxtToFile(line,filePath,fileName2);
    }

    private TimerTask getTask1(){
        TimerTask task=new TimerTask(){
            @Override
            public void run(){
                //todo 实现步数的更新
                Long stopTime=System.currentTimeMillis();
                deleteOutdatedData(lastPeakFindTime);
                deleteUwbOutdatedData(stopTime-10000);
                List<Long> timeListTemp = null;
                List<float[]> orientListTemp = null;
                List<float[]> linearAcceleListTemp = null;
                float[] averageOrient=null;
                if(orientList.size()>0) {
                    try {
                        int size = timeIndexList.size();
                        timeListTemp = ArrayUtil.copyList(timeIndexList, size);
                        orientListTemp = ArrayUtil.copyList(orientList, size);
                        linearAcceleListTemp = ArrayUtil.copyList(linearAcceleList, size);
                        averageOrient=orientListTemp.get(orientListTemp.size()-1);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    stepDetection(linearAcceleListTemp,timeListTemp,PEAK_THRESHOLD,PEAK_TIME_LOWER_BOUND,PEAK_TIME_UPPER_BOUND,1);
                }
                double[] estimatePos=null;
                if(posShareVar.getUpdateFlag()){
                     double[] tempPos=predictPos(stopTime); //估算当前时刻的x,y坐标

                     if(no_update_cnt>10){
                        pdrExKalman.kf_init(tempPos[0],tempPos[1],0,0);
                     }
                     estimatePos=pdrExKalman.estimatePos(tempPos);
                     no_update_cnt=0;
                     posShareVar.setUpdateFlag(false);
                    lastStepCnt=curStepCnt;
                    lastPeakFindTime=stopTime;
                }else{
                    no_update_cnt++;
                    double predictPosx=curPosx;
                    double predictPosy=curPosy;
                    //检测到步数有更新
                    if(lastStepCnt<curStepCnt){
                         while(lastStepCnt<curStepCnt){
                                predictPosx=predictPosx+stepLength*Math.sin(Math.toRadians(averageOrient[0]));
                                predictPosy=predictPosy+stepLength*Math.cos(Math.toRadians(averageOrient[0]));
                                lastStepCnt=lastStepCnt+1;
                          }
                        estimatePos=pdrExKalman.estimatePos(new double[]{predictPosx,predictPosy});
                    }
                }
                if(estimatePos!=null){
                    curPosx=estimatePos[0];
                    curPosy=estimatePos[1];
                    notifyObservers();
                    String line=System.currentTimeMillis()+","+curPosx+","+curPosy+"\r\n";
                    FileUtil.writeTxtToFile(line,filePath,fileName1);
                }
            }
        };
        return task;
    }

    private double[] predictPos(double stopTime){
         int size=uwbPosTime.size();
         double[] xtime=new double[size];
         double[] yuwbPosx=new double[size];
         double[] yuwbPosy=new double[size];
         for(int i=0;i<size;i++){
             xtime[i]=uwbPosTime.get(i);
             yuwbPosx[i]=eastPosxList.get(i);
             yuwbPosy[i]=northPosyList.get(i);
         }
        InterpolationFilter interp1=new InterpolationFilter(xtime, yuwbPosx, yuwbPosy);
        double x0=interp1.predictXByCurTime(stopTime);
        double y0=interp1.predictYByCurTime(stopTime);
        return new double[]{x0,y0};
    }

    /**
     *
     * @param deadLineTime
     */
    private void deleteOutdatedData(long deadLineTime){
          //删除传感器数据
          int cnt=0;
          for(int i=0;i<timeIndexList.size();i++){
              if(timeIndexList.get(i)<deadLineTime){
                  cnt++;
              }else{
                  break;
              }
          }
          if(stepRecord.size()>200){
              for(int j=0;j<100;j++){
                  stepRecord.remove(0);
              }
          }
          for(int i=0;i<cnt;i++){
              timeIndexList.remove(0);
              orientList.remove(0);
              linearAcceleList.remove(0);
          }

    }

    private void deleteUwbOutdatedData(long deadLineTime){
        //删除超宽带历史测距数据
        int uwbCnt=0;
        for(int i=0;i<uwbPosTime.size();i++){
            if(uwbPosTime.get(i)<deadLineTime){
                uwbCnt++;
            }else{
                break;
            }
        }

        for(int i=0;i<uwbCnt;i++){
            uwbPosTime.remove(0);
            eastPosxList.remove(0);
            northPosyList.remove(0);
        }
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
            String line=System.currentTimeMillis()+","+event.values[0]+","+event.values[1]+","+event.values[2];
            FileUtil.writeTxtToFile(line, filePath, orientFileName);
        }else if(event.sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION){
            linearAcceleVals=event.values;
            String line=System.currentTimeMillis()+","+event.values[0]+","+event.values[1]+","+event.values[2];
            FileUtil.writeTxtToFile(line, filePath, linearAccelFileName);
        }else if(event.sensor.getType()==Sensor.TYPE_GYROSCOPE){
            gyroscopeVals=event.values;
            String line=System.currentTimeMillis()+","+event.values[0]+","+event.values[1]+","+event.values[2];
            FileUtil.writeTxtToFile(line, filePath, gyroscopeFileName);
        }else if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            accelerometerValues=event.values;
            String line=System.currentTimeMillis()+","+event.values[0]+","+event.values[1]+","+event.values[2];
            FileUtil.writeTxtToFile(line, filePath, accelFileName);
        }
//        else if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
//            magneticFieldValues=event.values;
//        }else if(event.sensor.getType()==Sensor.TYPE_GRAVITY){
//            gravityValues=event.values;
//        }else if(event.sensor.getType()==Sensor.TYPE_ROTATION_VECTOR){
//            mSensorManager.getRotationMatrixFromVector(mRotationMatrixFromVector, event.values);
//
//            mSensorManager.remapCoordinateSystem(mRotationMatrixFromVector,
//                    SensorManager.AXIS_X, SensorManager.AXIS_Z,
//                    mRotationMatrix);
//            mSensorManager.getOrientation(mRotationMatrix, yawValues);
//
//            yawValues[0] = (float) Math.toDegrees(yawValues[0]);
//            yawValues[1] = (float) Math.toDegrees(yawValues[1]);
//            yawValues[2] = (float) Math.toDegrees(yawValues[2]);
//        }
//        if(count++ % 40 ==0) {
//            if (initRecordCnt == 0) {
//                recordSb = new StringBuilder();
//                recordSb.append("time,TYPE_ORIENTATION,TYPE_LINEAR_ACCELERATION,TYPE_GYROSCOPE,TYPE_ACCELEROMETER,TYPE_MAGNETIC_FIELD,TYPE_GRAVITY,TYPE_ROTATION_VECTOR\r\n");
//                FileUtil.writeTxtToFile(recordSb.toString(), filePath, fileName);
//                initRecordCnt++;
//            }
//            if (initRecordCnt > 0) {
//                recordSb = new StringBuilder();
//                recordSb.append(System.currentTimeMillis());
//                recordSb.append(",");
//                recordSb.append(orientationVals[0] + "," + orientationVals[1] + "," + orientationVals[2]);
//                recordSb.append(",");
//                recordSb.append(linearAcceleVals[0] + "," + linearAcceleVals[1] + "," + linearAcceleVals[2]);
//                recordSb.append(",");
//                recordSb.append(gyroscopeVals[0]+","+gyroscopeVals[1]+","+gyroscopeVals[2]);
//                recordSb.append(",");
//                recordSb.append(accelerometerValues[0]+","+accelerometerValues[1]+","+accelerometerValues[2]);
//                recordSb.append(",");
//                recordSb.append(magneticFieldValues[0]+","+magneticFieldValues[1]+","+magneticFieldValues[2]);
//                recordSb.append(",");
//                recordSb.append(gravityValues[0]+","+gravityValues[1]+","+gravityValues[2]);
//                recordSb.append(",");
//                recordSb.append(yawValues[0]+","+yawValues[1]+","+yawValues[2]);
//                recordSb.append("\r\n");
//                FileUtil.writeTxtToFile(recordSb.toString(), filePath, fileName);
//                initRecordCnt++;
//                // LogUtil.w(TAG, "initRecordCnt=" + initRecordCnt + "," + linearAcceleVals[0] + "," + linearAcceleVals[1] + "," + linearAcceleVals[2]);
//            }
            Long sampleTime = System.currentTimeMillis();
            timeIndexList.add(sampleTime);
            orientList.add(new float[]{orientationVals[0], orientationVals[1], orientationVals[2]});
            linearAcceleList.add(new float[]{linearAcceleVals[0], linearAcceleVals[1], linearAcceleVals[2]});
        //}
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

    //位置的输入单位为m
    private double[] constraitCheck(double[] position){
        double posx=position[0];
        double posy=position[1];
        double nposx=posx;
        double nposy=posy;
        boolean isFeasible=true;
        List<double[]> restrictList=null;
        List<double[]> feasibleRegionList=null;
        if(HybridMapActivity.sceneName.equals("Company")){
             restrictList= Restrict.getComRestrictRegionList();
             feasibleRegionList=Restrict.getComFeasibleRegionList();
        }else if(HybridMapActivity.sceneName.equals("Factory")){
            restrictList= Restrict.getFacRestrictRegionList();
            feasibleRegionList=Restrict.getFacFeasibleRegionList();
        }
        if(restrictList!=null && feasibleRegionList!=null){
            for(int i=0;i<restrictList.size();i++){
                if(posx>restrictList.get(i)[0] && posx< restrictList.get(i)[1] && posy>restrictList.get(i)[2] && posy<restrictList.get(i)[3]){
                    isFeasible=false;
                    break;
                }
            }
            if(!isFeasible){
                List<double[]> distanceRecord=new ArrayList<double[]>();
                for (int lineNum=0;lineNum<feasibleRegionList.size();lineNum++){
                    //不可行域的点到可行域距离的计算
                    double dist=Math.abs((feasibleRegionList.get(lineNum)[0]*posx+
                            feasibleRegionList.get(lineNum)[1]*posy+
                            feasibleRegionList.get(lineNum)[2])/Math.sqrt(Math.pow(feasibleRegionList.get(lineNum)[0],2)
                            +Math.pow(feasibleRegionList.get(lineNum)[1],2)));
                    distanceRecord.add(new double[]{lineNum,dist});
                }
                //对距离按照从小到大排序
                Collections.sort(distanceRecord,new Comparator<double[]>(){
                    public int compare(double[] arg0,double[] arg1){
                        if(arg0[1]>arg1[1]){
                            return 1;
                        }else if(arg0[1]<arg1[1]){
                            return -1;
                        }else{
                            return 0;
                        }
                    }
                });
                //将不是可行域的点映射到可行域点上 取距离最近的两个路径
                for (int i=0;i<2;i++){
                    int lineIndex=(int)distanceRecord.get(i)[0];
                    //如果直线与y轴平行
                    if(Math.abs(feasibleRegionList.get(lineIndex)[1])<1e-6 && Math.abs(feasibleRegionList.get(lineIndex)[0])>1e-5){
                        nposx=-feasibleRegionList.get(lineIndex)[2]/feasibleRegionList.get(lineIndex)[0];
                        nposy=posy;
                    }//如果直线与x轴平行
                    else if(Math.abs(feasibleRegionList.get(lineIndex)[0])<1e-6 && Math.abs(feasibleRegionList.get(lineIndex)[1])>1e-5){
                        nposy=-feasibleRegionList.get(lineIndex)[2]/feasibleRegionList.get(lineIndex)[1];
                        nposx=posx;
                    }//任意斜率
                    else{
                        //与原始直线相垂直的斜率
                        double slope2=feasibleRegionList.get(lineIndex)[1]/feasibleRegionList.get(lineIndex)[0];
                        //截距
                        double b2=posy-slope2*posx;
                        //方程2的一般式系数为:
                        double A2=slope2;
                        double B2=-1.0;
                        double C2=b2;
                        //求两条直线的交点
                        nposy=(feasibleRegionList.get(lineIndex)[2]-C2)*A2/(B2*A2-feasibleRegionList.get(lineIndex)[1]*feasibleRegionList.get(lineIndex)[0]);
                        nposx=-(feasibleRegionList.get(lineIndex)[1]*nposy+feasibleRegionList.get(lineIndex)[2])/feasibleRegionList.get(lineIndex)[0];
                    }
                    boolean newFeasibleFlag=true;
                    for(int index=0;index<restrictList.size();index++){
                        if(nposx>restrictList.get(index)[0] && nposx< restrictList.get(index)[1] && posy>restrictList.get(index)[2] && posy<restrictList.get(index)[3]){
                            newFeasibleFlag=false;
                            break;
                        }
                    }
                    if(newFeasibleFlag){
                        break;
                    }
                    if(i==1 && !newFeasibleFlag){
                        int lineIndex1=(int)distanceRecord.get(0)[0];
                        //计算的点到最近距离的路径的起始点坐标
                        double distance1=Math.sqrt(Math.pow(posx-feasibleRegionList.get(lineIndex1)[3],2)+Math.pow(posy-feasibleRegionList.get(lineIndex1)[4],2));
                        //计算的点到最近距离的路径的终点坐标
                        double distance2=Math.sqrt(Math.pow(posx-feasibleRegionList.get(lineIndex1)[5],2)+Math.pow(posy-feasibleRegionList.get(lineIndex1)[6],2));
                        if(distance1<distance2){
                            nposx=feasibleRegionList.get(lineIndex1)[3];
                            nposy=feasibleRegionList.get(lineIndex1)[4];
                        }else{
                            nposx=feasibleRegionList.get(lineIndex1)[5];
                            nposy=feasibleRegionList.get(lineIndex1)[6];
                        }
                    }
                }
            }
        }
        return new double[]{nposx,nposy,position[2]};
    }
}
