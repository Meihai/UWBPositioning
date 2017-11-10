package com.onlylemi.mapview;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.onlylemi.mapview.library.MapView;
import com.onlylemi.mapview.library.MapViewListener;
import com.onlylemi.mapview.library.layer.LocationLayer;
import com.onlylemi.mapview.library.layer.MarkLayer;
import com.onlylemi.mapview.library.layer.RouteLayer;
import com.onlylemi.mapview.library.utils.MapUtils;
import com.onlylemi.mapview.service.BluetoothLeService;
import com.onlylemi.mapview.service.LocationService;
import com.onlylemi.mapview.service.TestData;
import com.onlylemi.mapview.utils.LogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/10/30.
 */
public class BLEMapActivity  extends AppCompatActivity implements SensorEventListener {
    private final static String TAG = "BLEMapActivity";
    //蓝牙4.0的UUID,其中0000ffe1-0000-1000-8000-00805f9b34fb是广州汇承信息科技有限公司08蓝牙模块的UUID
    public static String HEART_RATE_MEASUREMENT = "0000ffe1-0000-1000-8000-00805f9b34fb"; //心率测量
    public static String EXTRAS_DEVICE_NAME = "DEVICE_NAME"; //设备名字
    public static String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS"; //设备地址
    public static String EXTRAS_DEVICE_RSSI = "RSSI"; //蓝牙信号强度
    public static String EXTRA_SCENE_SELECT="SCENE_SELECT";//场景选择
    //添加工厂地图起点对应的像素坐标
    public static final float FACTORY_MAP_START_X=10;
    public static final float FACTORY_MAP_START_Y=80;
    public static final float FACTORY_MAP_END_X=171;
    public static final float FACTORY_MAP_END_Y=440;
    //添加公司
    public static final float COMPANY_MAP_START_X=165;
    public static final float COMPANY_MAP_START_Y=220;
    public static final float COMPANY_MAP_END_X=580;
    public static final float COMPANY_MAP_END_Y=480;

    public static final int UPDATE_CURRENT_POS=1; //更新当前位置标志
    public static final String UPDATE_CURRENT_POS_KEY="update_cur_pos_key";

    //public static final String FactoryMap="";
    //蓝牙连接状态
    private boolean mConnected = false;
    private String status = "disconnected";
    //蓝牙名字
    private String mDeviceName;
    //蓝牙地址
    private String mDeviceAddress;
    //蓝牙信号值
    private String mRssi;
    private Bundle b;
    //室内地图场景
    private String sceneName;
    private Handler mhandler = new Handler();
    private static float prevX=100; //记录上一次的坐标
    private static float prevY=100; //记录上一次的坐标
    private Handler myHandler=new Handler() {
        // 2.重写消息处理函数
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 判断发送的消息
                case UPDATE_CURRENT_POS: {
                    // 更新View
                    float[] tag2dpos = msg.getData().getFloatArray(UPDATE_CURRENT_POS_KEY);
//                    double deltaY=tag2dpos[1]-prevY;
//                    double deltaX=tag2dpos[0]-prevX;
                    prevY=tag2dpos[1];
                    prevX=tag2dpos[0];
                    //获取角度
//                    double deg=Math.atan2(deltaY,deltaX);
//                    deg=90+Math.toDegrees(deg)+mapView.getCurrentRotateDegrees();
                    if(locationLayer!=null){
//                        locationLayer.setOpenCompass(true);
//                        locationLayer.setCompassIndicatorCircleRotateDegree(new Float(deg));
//                        locationLayer.setCompassIndicatorArrowRotateDegree(new Float(deg));
                        if(needNavigated){
                            //todo 添加路径导航功能
                            PointF startPoint=new PointF(prevX,prevY);
                            if(targetP!=null ){
                                if(Math.sqrt(Math.pow(startPoint.x-targetP.x,2)+Math.pow(startPoint.y-targetP.y,2))<50){
                                    List<Integer> routeList=new ArrayList<>();
                                    routeLayer.setRouteList(routeList);
                                    needNavigated=false;
                                }else{
                                    try{
                                        if(sceneName.equals("FACTORY")){
                                            nodes=TestData.getFctoryNodesList();
                                            nodesContract=TestData.getFactoryNodesContactList();
                                        }else if(sceneName.equals("COMPANY")){
                                            nodes=TestData.getCompanyNodesList();
                                            nodesContract=TestData.getCompanyNodesContactList();
                                        }
                                        MapUtils.init(nodes.size(),nodesContract.size());
                                        List<Integer> routeList= MapUtils.getShortestDistanceBetweenTwoPoints(startPoint,
                                                targetP,nodes,nodesContract);
                                        routeLayer.setNodeList(nodes);
                                        routeLayer.setRouteList(routeList);
                                    }catch(Exception ex){
                                        ex.printStackTrace();
                                        LogUtil.w(TAG,ex.getMessage());
                                    }
                                }
                            }
                        }
                       // locationLayer.setCurrentPosition(new PointF(tag2dpos[0],tag2dpos[1]));
                        locationLayer.setCurrentPosition(new PointF(prevX,prevY));
                        mapView.refresh();
                    }
                    break;
                }
            }
            super.handleMessage(msg);
        }
    };
    //蓝牙service,负责后台的蓝牙服务
    private static BluetoothLeService mBluetoothLeService;
    //定位服务
    private static LocationService mLocationService;

    private MapView mapView;
    private LocationLayer locationLayer;
    private MarkLayer markLayer;
    private RouteLayer routeLayer;
    private static List<PointF> nodes;
    private static List<PointF> nodesContract;
    private static List<PointF> marks;
    private static List<String> marksName;
    private boolean needNavigated=false;
    private static PointF targetP;
    private boolean openSensor = false; //是否开启传感器
    private SensorManager sensorManager; //传感器管理层

    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    //蓝牙特征值
    private static BluetoothGattCharacteristic target_chara = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blemap_test);

        b = getIntent().getExtras();
        //从意图获取显示的蓝牙信息
        mDeviceName = b.getString(EXTRAS_DEVICE_NAME);
        mDeviceAddress = b.getString(EXTRAS_DEVICE_ADDRESS);
        mRssi = b.getString(EXTRAS_DEVICE_RSSI);
        sceneName=b.getString(EXTRA_SCENE_SELECT);
        /*启动位置计算*/
        Intent locationServiceIntent=new Intent(this,LocationService.class);
        bindService(locationServiceIntent,mLocationServiceConnection,BIND_AUTO_CREATE);
        /* 启动蓝牙service */
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        /*启动地图*/
        mapView = (MapView) findViewById(R.id.mapview); //寻找地图
        Bitmap bitmap = null;
        try {
            if(sceneName.equals("FACTORY")) {
                bitmap = BitmapFactory.decodeStream(getAssets().open("factory20171031.png"));
                setTitle("工厂机房地图");
            }
            else if(sceneName.equals("COMPANY")){
                bitmap=BitmapFactory.decodeStream(getAssets().open("companay20171103.png"));
                setTitle("公司机房地图");
            }else{
                bitmap=BitmapFactory.decodeStream(getAssets().open("map.png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        mapView.loadMap(bitmap); //加载地图完毕
        mapView.setMapViewListener(new MapViewListener() {
            @Override
            public void onMapLoadSuccess() {
                if(sceneName.equals("FACTORY")){
                    nodes=TestData.getFctoryNodesList();
                    nodesContract=TestData.getFactoryNodesContactList();
                    MapUtils.init(nodes.size(),nodesContract.size());
                    marks=TestData.getFactoryMarks();
                    marksName=TestData.getFactoryMarksName();
                    locationLayer = new LocationLayer(mapView, new PointF(FACTORY_MAP_START_X, FACTORY_MAP_START_Y));
                    routeLayer=new RouteLayer(mapView);
                    markLayer=new MarkLayer(mapView,marks,marksName);
                    markLayer.setMarkIsClickListener(new MarkLayer.MarkIsClickListener(){
                        @Override
                        public void markIsClick(int num){
                            needNavigated=true;
                            targetP=new PointF(marks.get(num).x,marks.get(num).y);
                            PointF startPoint=new PointF(prevX,prevY);
                            List<Integer> routeList= MapUtils.getShortestDistanceBetweenTwoPoints(startPoint,
                                    targetP,nodes,nodesContract);
                            routeLayer.setNodeList(nodes);
                            routeLayer.setRouteList(routeList);
                            mapView.refresh();
                        }
                    });
                    mapView.addLayer(routeLayer);
                }else if(sceneName.equals("COMPANY")){
                    needNavigated=true;
                    nodes=TestData.getCompanyNodesList();
                    nodesContract=TestData.getCompanyNodesContactList();
                    MapUtils.init(nodes.size(),nodesContract.size());
                    marks=TestData.getCompanyMarks();
                    marksName=TestData.getCompanyMarksName();
                    locationLayer = new LocationLayer(mapView, new PointF(COMPANY_MAP_START_X, COMPANY_MAP_START_Y));
                    markLayer=new MarkLayer(mapView,TestData.getCompanyMarks(),TestData.getCompanyMarksName());
                    routeLayer=new RouteLayer(mapView);
                    markLayer=new MarkLayer(mapView,marks,marksName);
                    markLayer.setMarkIsClickListener(new MarkLayer.MarkIsClickListener(){
                        @Override
                        public void markIsClick(int num){
                            needNavigated=true;
                            targetP=new PointF(marks.get(num).x,marks.get(num).y);
                            PointF startPoint=new PointF(prevX,prevY);
                            List<Integer> routeList= MapUtils.getShortestDistanceBetweenTwoPoints(startPoint,
                                    targetP,nodes,nodesContract);
                            routeLayer.setNodeList(nodes);
                            routeLayer.setRouteList(routeList);
                            mapView.refresh();
                        }
                    });
                    mapView.addLayer(routeLayer);
                }else{
                    locationLayer = new LocationLayer(mapView, new PointF(100, 100));
                    List<PointF> pointList= new ArrayList<PointF>();
                    pointList.add(new PointF(200,200));
                    List<String> pointsName=new ArrayList<String>();
                    pointsName.add("test");
                    markLayer=new MarkLayer(mapView,pointList,pointsName);
                }
//                locationLayer.setOpenCompass(true);
//                locationLayer.setCompassIndicatorCircleRotateDegree(0);
//                locationLayer.setCompassIndicatorArrowRotateDegree(0);
                mapView.addLayer(locationLayer);
                mapView.addLayer(markLayer);
                mapView.refresh();
                Log.d(TAG,"Load map success");
            }
            @Override
            public void onMapLoadFail() {
                Log.d(TAG,"Load map failed");
            }
        });
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //解除广播接收器
        unregisterReceiver(mGattUpdateReceiver);
        mBluetoothLeService = null;
        mLocationService=null;
    }

    // Activity出来时候，绑定广播接收器，监听蓝牙连接服务传过来的事件
    @Override
    protected void onResume()
    {
        super.onResume();
        //绑定广播接收器
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        needNavigated=false;
        if (mBluetoothLeService != null)
        {   System.out.println("连接蓝牙服务失败");
            //根据蓝牙地址，建立连接
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            updateConnectionState("连接蓝牙服务失败!");
            Log.d(TAG, "Connect request result=" + result);
        }else{
            updateConnectionState("未发现蓝牙服务!");
        }
    }

    /* BluetoothLeService绑定的回调函数 */
    private final ServiceConnection mServiceConnection = new ServiceConnection()
    {

        @Override
        public void onServiceConnected(ComponentName componentName,
                                       IBinder service)
        {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service)
                    .getService();
            if (!mBluetoothLeService.initialize())
            {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up
            // initialization.
            // 根据蓝牙地址，连接设备
            mBluetoothLeService.connect(mDeviceAddress);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {
            mBluetoothLeService = null;
        }

    };

    /*LocationService绑定的回调函数*/
    private final ServiceConnection mLocationServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mLocationService=((LocationService.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mLocationService=null;
        }
    };

    /**
     * 广播接收器，负责接收BluetoothLeService类发送的数据
     */
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action))//Gatt连接成功
            {
                mConnected = true;
                status = "connected";
                //更新连接状态
                updateConnectionState(status);
               // System.out.println("BroadcastReceiver :" + "device connected");

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED//Gatt连接失败
                    .equals(action))
            {
                mConnected = false;
                status = "disconnected";
                //更新连接状态
                updateConnectionState(status);
             //   System.out.println("BroadcastReceiver :"
              //          + "device disconnected");

            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED//发现GATT服务器
                    .equals(action))
            {
                // Show all the supported services and characteristics on the
                // user interface.
                //获取设备的所有蓝牙服务
                displayGattServices(mBluetoothLeService
                        .getSupportedGattServices());
                System.out.println("BroadcastReceiver :"
                       + "device SERVICES_DISCOVERED");
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action))//有效数据
            {
                //处理发送过来的数据
                String recvData=intent.getExtras().getString(BluetoothLeService.EXTRA_DATA);
                if(recvData==null || recvData.trim().isEmpty()){
                    LogUtil.w(TAG,"The recvData is null:"+recvData);
                    return;
                }
                Log.i(TAG,"recvData="+recvData);
                mLocationService.calcPosition(recvData,sceneName);
                Log.d(TAG,"BroadcastReceiver onData:"
                        + intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }else if(LocationService.ACTION_POSITION_AVAILABLE.equals(action)){
                double[] posTag=intent.getDoubleArrayExtra(LocationService.EXTRA_DATA);
                float[] tag2dpos=convertPoint(posTag);
                Message msg=new Message();
                msg.what = UPDATE_CURRENT_POS;
                Bundle b = new Bundle();
                b.putFloatArray(UPDATE_CURRENT_POS_KEY, tag2dpos);
                msg.setData(b);
                //将连接状态更新的UI的textview上
                myHandler.sendMessage(msg);

            }
        }
    };

    private float[] convertPoint(double[] tagPos){
        double minx=0.0;
        double miny=2.8;
        double maxx=6.665;
        double maxy=18.43;
        double canvasWidth=FACTORY_MAP_END_X-FACTORY_MAP_START_X;
        double canvasHeight=FACTORY_MAP_END_Y-FACTORY_MAP_START_Y;
        double x=tagPos[0];
        double y=tagPos[1];
        double pixl_x=0;
        double pixl_y=0;
        if(sceneName.equals("FACTORY")){
            minx=0.0;
            miny=2.8;
            maxx=6.665;
            maxy=18.43;
            canvasWidth=FACTORY_MAP_END_X-FACTORY_MAP_START_X;
            canvasHeight=FACTORY_MAP_END_Y-FACTORY_MAP_START_Y;
            pixl_x=(x/1000.0)/(maxx-minx)*canvasWidth+FACTORY_MAP_START_X;
            pixl_y=(y/1000.0)/(maxy-miny)*canvasHeight+FACTORY_MAP_START_Y;
        }else if(sceneName.equals("COMPANY")){
            //展厅大小
            minx=3.2;
            miny=4.35;
            maxx=11.0;
            maxy=4.35+5.6;
            canvasWidth=COMPANY_MAP_END_X-COMPANY_MAP_START_X;
            canvasHeight=COMPANY_MAP_END_Y-COMPANY_MAP_START_Y;
            pixl_x=(x/1000.0)/(maxx-minx)*canvasWidth+COMPANY_MAP_START_X;
            pixl_y=(y/1000.0)/(maxy-miny)*canvasHeight+COMPANY_MAP_START_Y;
        }

        float[] tag2dpos=new float[] {  new Float(pixl_x),new Float(pixl_y)};
        return tag2dpos;

    }

    /* 更新连接状态 */
    private void updateConnectionState(String status)
    {
        Toast.makeText(this,status,Toast.LENGTH_SHORT).show();
    }

    /* 意图过滤器 */
    private static IntentFilter makeGattUpdateIntentFilter()
    {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter
                .addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        //添加位置服务过滤器
        intentFilter.addAction(LocationService.ACTION_POSITION_CALC_FAIL);
        intentFilter.addAction(LocationService.ACTION_POSITION_AVAILABLE);
        return intentFilter;
    }


    /**
     * @Title: displayGattServices
     * @Description: TODO(处理蓝牙服务)
     * @param
     * @return void
     * @throws
     */
    private void displayGattServices(List<BluetoothGattService> gattServices)
    {

        if (gattServices == null)
            return;
        String uuid = null;
        String unknownServiceString = "unknown_service";
        String unknownCharaString = "unknown_characteristic";

        // 服务数据,可扩展下拉列表的第一级数据
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();

        // 特征数据（隶属于某一级服务下面的特征值集合）
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList<ArrayList<HashMap<String, String>>>();

        // 部分层次，所有特征值集合
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices)
        {

            // 获取服务列表
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();

            // 查表，根据该uuid获取对应的服务名称。SampleGattAttributes这个表需要自定义。

            gattServiceData.add(currentServiceData);

            System.out.println("Service uuid:" + uuid);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();

            // 从当前循环所指向的服务中读取特征值列表
            List<BluetoothGattCharacteristic> gattCharacteristics = gattService
                    .getCharacteristics();

            ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            // 对于当前循环所指向的服务中的每一个特征值
            for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics)
            {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();

                if (gattCharacteristic.getUuid().toString()
                        .equals(HEART_RATE_MEASUREMENT))
                {
                    // 测试读取当前Characteristic数据，会触发mOnDataAvailable.onCharacteristicRead()
                    mhandler.postDelayed(new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            // TODO Auto-generated method stub
                            mBluetoothLeService
                                    .readCharacteristic(gattCharacteristic);
                        }
                    }, 200);

                    // 接受Characteristic被写的通知,收到蓝牙模块的数据后会触发mOnDataAvailable.onCharacteristicWrite()
                    mBluetoothLeService.setCharacteristicNotification(
                            gattCharacteristic, true);
                    target_chara = gattCharacteristic;
                    // 设置数据内容
                    // 往蓝牙模块写入数据
                    // mBluetoothLeService.writeCharacteristic(gattCharacteristic);
                }
                List<BluetoothGattDescriptor> descriptors = gattCharacteristic
                        .getDescriptors();
                for (BluetoothGattDescriptor descriptor : descriptors)
                {
                    System.out.println("---descriptor UUID:"
                            + descriptor.getUuid());
                    // 获取特征值的描述
                    mBluetoothLeService.getCharacteristicDescriptor(descriptor);
                    // mBluetoothLeService.setCharacteristicNotification(gattCharacteristic,
                    // true);
                }
                gattCharacteristicGroupData.add(currentCharaData);
            }
            // 按先后顺序，分层次放入特征值集合中，只有特征值
            mGattCharacteristics.add(charas);
            // 构件第二级扩展列表（服务下面的特征值）
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.menu_bitmap_layer_test, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mapView.isMapLoadFinish()) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mapView.isMapLoadFinish() && openSensor) {
            float mapDegree = 0; // the rotate between reality map to northern
            float degree = 0;
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                degree = event.values[0];
            }

            locationLayer.setCompassIndicatorCircleRotateDegree(-degree);
            locationLayer.setCompassIndicatorArrowRotateDegree(mapDegree + mapView
                    .getCurrentRotateDegrees() + degree);
            mapView.refresh();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
