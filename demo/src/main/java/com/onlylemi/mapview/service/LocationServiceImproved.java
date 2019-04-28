package com.onlylemi.mapview.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.onlylemi.mapview.BaseConfigActivity;
import com.onlylemi.mapview.agorithm.filter.InterpolationFilter;
import com.onlylemi.mapview.utils.LogUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 2017/12/7.
 * 位置服务增强版,主要是在用户运动时对运动轨迹进行平滑,预测用户所在位置
 */

public class LocationServiceImproved extends Service {
    private static final String TAG="LocationService";
    private final IBinder mBinder=new LocationServiceImproved.LocalBinder();
    private static  LocationI mlocationCal=new PosEstimationWithLM();
    public static final String ACTION_POSITION_AVAILABLE="com.keydak.uwb.ACTION_POSITION_AVAILABLE";
    public static final String ACTION_POSITION_CALC_FAIL="com.keydak.uwb.ACTION_POSITION_CALC_FAIL";
    public static final String EXTRA_DATA="com.keydak.uwb.EXTRA_DATA";
    private Timer timer;
    private static long delayTime=1000;//单位为毫秒
    private static long periodTime=80;//单位时间为毫秒
    private static ArrayList<double[]> positionList;
    private static ArrayList<Long> timeStampList;
    private static final long TIME_OUT=5000; //设置最大超时时间为10000毫秒
    private static final double SPEED=1.2; //人步行的最大速度设置为1.2m/s 1.2mm/ms
    @Override
    public IBinder onBind(Intent intent){
        LogUtil.d(TAG,"onBind() executed!");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent){
        LogUtil.d(TAG,"onUnbind() executed!");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate(){
        LogUtil.d(TAG,"LocationService onCreate() executed!");
        timer=new Timer();
        timer.scheduleAtFixedRate(getTask(),delayTime,periodTime);
        positionList=new ArrayList<double[]>();
        timeStampList=new ArrayList<Long>();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        LogUtil.d(TAG,"LocationService onStartCommand() executed!");
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy(){
        LogUtil.d(TAG,"LocationService onDestroy() executed!");
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
        super.onDestroy();
    }

    public void  calcPosition(String baseStationInfo,String sceneName){
        LogUtil.w(TAG,"baseStationInfo="+baseStationInfo);
        if(BaseConfigActivity.algorithmChanged){
            if(BaseConfigActivity.selectedAlgorithm.equals("Newton")){
                mlocationCal=new PosEstimationWithNewton();
            }else if(BaseConfigActivity.selectedAlgorithm.equals("LM")){
                mlocationCal=new PosEstimationWithLM();
            }else if(BaseConfigActivity.selectedAlgorithm.equals("LSQ")){
                mlocationCal=new PosEstimationWithLSQ();
            }else{
                mlocationCal=new PosEstimationWithCG();
            }
            BaseConfigActivity.algorithmChanged=false;
        }
        double[] positionArr=mlocationCal.convertDistanceToPos(baseStationInfo,sceneName);
        if(positionArr==null){
            LogUtil.w(TAG,"在时间"+System.currentTimeMillis()+",calculate position failed,positionArr is null");
            return;
        }
        LogUtil.i(TAG,"在时间"+System.currentTimeMillis()+",获得坐标:"+positionArr[0]+","+positionArr[1]+","+positionArr[2]);
        positionList.add(positionArr);
        timeStampList.add(System.currentTimeMillis());
    }

    public void broadcastUpdate(final String action,double[] tagPosition){
        final Intent intent=new Intent(action);
        intent.putExtra(EXTRA_DATA,tagPosition);
        sendBroadcast(intent);
    }

    public void broadcastUpdate(final String action,String failReason){
        final Intent intent=new Intent(action);
        intent.putExtra(EXTRA_DATA,failReason);
        sendBroadcast(intent);
    }

    public class LocalBinder extends Binder {
        public LocationServiceImproved getService()
        {
            return LocationServiceImproved.this;
        }
    }

    public TimerTask getTask(){
        TimerTask task=new TimerTask(){
            @Override
            public void run(){
                if(positionList==null){
                    return;
                }
                //没有获得足够数据的时候不进行约束，采用原始定位信息
                if(positionList!=null && positionList.size()>0 && positionList.size()<3){
                    broadcastUpdate(ACTION_POSITION_AVAILABLE,positionList.get(positionList.size()-1));
                }else{
                    long currentTime=System.currentTimeMillis();
                    int startIndex=-1;
                    for(int i=0;i<timeStampList.size()-1;i++){
                        if(currentTime-timeStampList.get(i)<TIME_OUT){
                            startIndex=i;
                            break;
                        }
                    }
                    if(startIndex==-1){
                        broadcastUpdate(ACTION_POSITION_CALC_FAIL,"超过10秒没有获得新坐标信息");
                        for(int i=0;i<positionList.size()-1;i++){
                            positionList.remove(0);
                            timeStampList.remove(0);
                        }
                        return;
                    }
                    //数据足够多的时候,根据以前获得的数据对轨迹进行预测和平滑
                    int tectCnt2=positionList.size();
                    int testCnt=positionList.size()-startIndex;
                    double[] posx=new double[positionList.size()-startIndex];
                    double[] posy=new double[positionList.size()-startIndex];
                    double[] timeIndex=new double[positionList.size()-startIndex];
//                    double[] testArray=new double[testCnt];
//                    for(int i=startIndex;i<positionList.size();i++){
//                        posx[i-startIndex]=positionList.get(i)[0];
//                        posy[i-startIndex]=positionList.get(i)[1];
//                        timeIndex[i-startIndex]=timeStampList.get(i)-timeStampList.get(startIndex);
//                    }
//                    double[][] modifyxyt=filterAbnormalPoint(timeIndex,posx,posy);
                  //  InterpolationFilter insInterpolationFilter=new InterpolationFilter(modifyxyt[0],modifyxyt[1],modifyxyt[2]);
                    InterpolationFilter insInterpolationFilter=new InterpolationFilter(timeIndex,posx,posy);
                    double predictX=insInterpolationFilter.predictXByCurTime(currentTime-timeStampList.get(startIndex));
                    double predictY=insInterpolationFilter.predictYByCurTime(currentTime-timeStampList.get(startIndex));
                    LogUtil.i(TAG,"在时刻:"+currentTime+",估测坐标为:"+predictX+","+predictY);
                    broadcastUpdate(ACTION_POSITION_AVAILABLE,new double[]{predictX,predictY,0});
                    for(int i=0;i<startIndex;i++){
                        positionList.remove(0);
                        timeStampList.remove(0);
                    }
                }

            }
        };
        return task;
    }

    private double[][] filterAbnormalPoint(double[] timeSlice,double[] posx,double[] posy){
         double[] filteredPosx=new double[posx.length];
         double[] filteredPosy=new double[posy.length];
         double[] filteredTimeSlice=new double[timeSlice.length];
         for(int i=0;i<posx.length;i++){
             if(i>0 && i<posx.length-1){
                 if((posx[i]>posx[i-1] && posx[i+1]<posx[i])  || ((posx[i]<posx[i-1] && posx[i+1]>posx[i]))){
                     if((timeSlice[i+1]-timeSlice[i-1])<2000 ){
                         filteredPosx[i]=(posx[i-1]+posx[i+1])/2.0;
                     }
                 }
                 if((posy[i]>posy[i-1] && posy[i+1]<posy[i])  || ((posy[i]<posy[i-1] && posy[i+1]>posy[i]))){
                     if((timeSlice[i+1]-timeSlice[i-1])<2000 ){
                         filteredPosy[i]=(posy[i-1]+posy[i+1])/2.0;
                     }
                 }
                 filteredTimeSlice[i]=timeSlice[i];
             }
             else{
                 filteredPosx[i]=posx[i];
                 filteredPosy[i]=posy[i];
                 filteredTimeSlice[i]=timeSlice[i];
             }
         }
         return new double[][]{timeSlice,filteredPosx,filteredPosy};

    }
}
