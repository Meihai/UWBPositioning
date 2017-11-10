package com.onlylemi.mapview.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.onlylemi.mapview.utils.LogUtil;

/**
 * Created by admin on 2017/10/25.
 */

public class LocationService extends Service{
    private static final String TAG="LocationService";
    private final IBinder mBinder=new LocalBinder();
    private static  LocationI mlocationCal=new PosEstimationWithLM();//new PosEstimationWithNewton();//new PosEstimationWithCG();
    public static final String ACTION_POSITION_AVAILABLE="com.keydak.uwb.ACTION_POSITION_AVAILABLE";
    public static final String ACTION_POSITION_CALC_FAIL="com.keydak.uwb.ACTION_POSITION_CALC_FAIL";
    public static final String EXTRA_DATA="com.keydak.uwb.EXTRA_DATA";
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
        super.onDestroy();
    }

    public void calcPosition(String baseStationInfo,String sceneName){
        LogUtil.w(TAG,"baseStationInfo="+baseStationInfo);
        double[] positionArr=mlocationCal.convertDistanceToPos(baseStationInfo,sceneName);
        if(positionArr==null){
            broadcastUpdate(ACTION_POSITION_CALC_FAIL,"Calculte with "+TAG);
            LogUtil.w(TAG,"calculate position failed");
            return;
        }
        //计算完毕发送一个广播
        broadcastUpdate(ACTION_POSITION_AVAILABLE,positionArr);
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
        public LocationService getService()
        {
            return LocationService.this;
        }
    }
}
