package com.onlylemi.mapview;
/**
 * Created by admin on 2017/10/25.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @Description: TODO<MainActivity类实现打开蓝牙、扫描蓝牙>
 * @author
 * @data: 2014-10-12 上午10:28:18
 * @version: V1.0
 */
public class MainActivity extends Activity implements OnClickListener {
    // 扫描蓝牙按钮
    private Button scan_btn;
    private Button config_btn;
    private Spinner sceneSpin;//室内场景选择
    // 蓝牙适配器
    BluetoothAdapter mBluetoothAdapter;
    // 蓝牙信号强度
    private ArrayList<Integer> rssis;
    // 自定义Adapter
    LeDeviceListAdapter mleDeviceListAdapter;
    // listview显示扫描到的蓝牙信息
    ListView lv;
    // 描述扫描蓝牙的状态
    private boolean mScanning;
    private boolean scan_flag;
    private Handler mHandler;
    int REQUEST_ENABLE_BT = 1;
    // 蓝牙扫描时间
    private static final long SCAN_PERIOD = 10000;
    //场景集合
    public static final String[] sceneArray=new String[]{"FACTORY","COMPANY"};
    private String selectedScene="FACTORY";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化控件
        init();
        // 初始化蓝牙
        init_ble();
        initBs();
        scan_flag = true;
        // 自定义适配器
        mleDeviceListAdapter = new LeDeviceListAdapter();
        // 为listview指定适配器
        lv.setAdapter(mleDeviceListAdapter);
		/* listview点击函数 */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position,
                                    long id)
            {
                // TODO Auto-generated method stub
                final BluetoothDevice device = mleDeviceListAdapter
                        .getDevice(position);
                if (device == null)
                    return;
//                final Intent intent = new Intent(MainActivity.this,
//                        BleActivity.class);
//                intent.putExtra(BleActivity.EXTRAS_DEVICE_NAME,
//                        device.getName());
//                intent.putExtra(BleActivity.EXTRAS_DEVICE_ADDRESS,
//                        device.getAddress());
//                intent.putExtra(BleActivity.EXTRAS_DEVICE_RSSI,
//                        rssis.get(position).toString());
                final Intent intent = new Intent(MainActivity.this,
                        BLEMapActivity.class);
                intent.putExtra(BLEMapActivity.EXTRAS_DEVICE_NAME,
                        device.getName());
                intent.putExtra(BLEMapActivity.EXTRAS_DEVICE_ADDRESS,
                        device.getAddress());
                intent.putExtra(BLEMapActivity.EXTRAS_DEVICE_RSSI,
                        rssis.get(position).toString());
                intent.putExtra(BLEMapActivity.EXTRA_SCENE_SELECT,selectedScene);
                if (mScanning)
                {
					/* 停止扫描设备 */
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    mScanning = false;
                }
                try
                {
                    // 启动Ble_Activity
                    startActivity(intent);
                } catch (Exception e)
                {
                    e.printStackTrace();
                    // TODO: handle exception
                }
            }
        });

    }

    /**
     * @Title: init
     * @Description: TODO(初始化UI控件)
     * @param
     * @return void
     * @throws
     */
    private void init()
    {
        scan_btn = (Button) this.findViewById(R.id.scan_dev_btn);
        scan_btn.setOnClickListener(this);
        lv = (ListView) this.findViewById(R.id.lv);
        mHandler = new Handler();
        sceneSpin=(Spinner) this.findViewById(R.id.scene_spinner);
        sceneSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id){
                  selectedScene=sceneArray[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
        config_btn=(Button) this.findViewById(R.id.btn_config);
        config_btn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,BaseConfigActivity.class);
                intent.putExtra(BaseConfigActivity.EXTRAS_SCENE_SELECT,selectedScene);
                try{
                    startActivity(intent);
                }catch(Exception ex){
                    ex.printStackTrace();
               }
            }
        });
    }
    /**
     * @Title: init_ble
     * @Description: TODO(初始化蓝牙)
     * @param
     * @return void
     * @throws
     */
    private void init_ble()
    {
        // 手机硬件支持蓝牙
        if (!getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_BLUETOOTH_LE))
        {
            Toast.makeText(this, "不支持BLE", Toast.LENGTH_SHORT).show();
            finish();
        }
        // Initializes Bluetooth adapter.
        // 获取手机本地的蓝牙适配器
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        // 打开蓝牙权限
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    /**
     * 初始化基站数据
     */
    private void initBs(){
        SharedPreferences pref1=getSharedPreferences(Constant.FACTORY_BS_DATA_NAME,MODE_PRIVATE);
        Map<String,String> factoryBsMap=(Map<String,String>)pref1.getAll();
        if(factoryBsMap!=null && factoryBsMap.size()>0){
            Constant.factoryBaseStationInfoMap.clear();
            Set bsSet=factoryBsMap.entrySet();
            Iterator iter=bsSet.iterator();
            while(iter.hasNext()){
                Map.Entry<String,String> bsEntry=(Map.Entry<String,String>)iter.next();
                String bsName=bsEntry.getKey();
                String bsPos=bsEntry.getValue();
                String[] bsPosArr=bsPos.split(",");
                double[] posArr=new double[]{Double.parseDouble(bsPosArr[0]),Double.parseDouble(bsPosArr[1]),Double.parseDouble(bsPosArr[2])};
                Constant.factoryBaseStationInfoMap.put(bsName,posArr);
            }
        }

        SharedPreferences pref2=getSharedPreferences(Constant.COMPANY_BS_DATA_NAME,MODE_PRIVATE);
        Map<String,String> companyBsMap=(Map<String,String>)pref2.getAll();
        if(companyBsMap!=null && companyBsMap.size()>0){
            Constant.companyBaseStationInfoMap.clear();
            Set bsSet=companyBsMap.entrySet();
            Iterator iter=bsSet.iterator();
            while(iter.hasNext()){
                Map.Entry<String,String> bsEntry=(Map.Entry<String,String>)iter.next();
                String bsName=bsEntry.getKey();
                String bsPos=bsEntry.getValue();
                String[] bsPosArr=bsPos.split(",");
                double[] posArr=new double[]{Double.parseDouble(bsPosArr[0]),Double.parseDouble(bsPosArr[1]),Double.parseDouble(bsPosArr[2])};
                Constant.companyBaseStationInfoMap.put(bsName,posArr);
            }
        }
    }

    /*
     * 按钮响应事件
     */
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        if (scan_flag)
        {
            mleDeviceListAdapter = new LeDeviceListAdapter();
            lv.setAdapter(mleDeviceListAdapter);
            scanLeDevice(true);
        } else
        {
            scanLeDevice(false);
            scan_btn.setText("扫描设备");
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

    }

    /**
     * @Title: scanLeDevice
     * @Description: TODO(扫描蓝牙设备 )
     * @param enable
     *            (扫描使能，true:扫描开始,false:扫描停止)
     * @return void
     * @throws
     */
    private void scanLeDevice(final boolean enable)
    {
        if (enable)
        {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    mScanning = false;
                    scan_flag = true;
                    scan_btn.setText("扫描设备");
                    Log.i("SCAN", "stop.....................");
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);
			/* 开始扫描蓝牙设备，带mLeScanCallback 回调函数 */
            Log.i("SCAN", "begin.....................");
            mScanning = true;
            scan_flag = false;
            scan_btn.setText("停止扫描");
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else
        {
            Log.i("Stop", "stoping................");
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            scan_flag = true;
        }

    }

    /**
     * 蓝牙扫描回调函数 实现扫描蓝牙设备，回调蓝牙BluetoothDevice，可以获取name MAC等信息
     *
     * **/
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback()
    {

        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi,
                             byte[] scanRecord)
        {
            // TODO Auto-generated method stub

            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
					/* 讲扫描到设备的信息输出到listview的适配器 */
                    mleDeviceListAdapter.addDevice(device, rssi);
                    mleDeviceListAdapter.notifyDataSetChanged();
                }
            });

            System.out.println("Address:" + device.getAddress());
            System.out.println("Name:" + device.getName());
            System.out.println("rssi:" + rssi);

        }
    };

    /**
     * @Description: TODO<自定义适配器Adapter,作为listview的适配器>
     * @author 广州汇承信息科技有限公司
     * @data: 2014-10-12 上午10:46:30
     * @version: V1.0
     */
    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> mLeDevices;

        private LayoutInflater mInflator;

        public LeDeviceListAdapter()
        {
            super();
            rssis = new ArrayList<Integer>();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device, int rssi)
        {
            if (!mLeDevices.contains(device))
            {
                mLeDevices.add(device);
                rssis.add(rssi);
            }
        }

        public BluetoothDevice getDevice(int position)
        {
            return mLeDevices.get(position);
        }

        public void clear()
        {
            mLeDevices.clear();
            rssis.clear();
        }

        @Override
        public int getCount()
        {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i)
        {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        /**
         * 重写getview
         *
         * **/
        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {

            // General ListView optimization code.
            // 加载listview每一项的视图
            view = mInflator.inflate(R.layout.listitem, null);
            // 初始化三个textview显示蓝牙信息
            TextView deviceAddress = (TextView) view
                    .findViewById(R.id.tv_deviceAddr);
            TextView deviceName = (TextView) view
                    .findViewById(R.id.tv_deviceName);
            TextView rssi = (TextView) view.findViewById(R.id.tv_rssi);

            BluetoothDevice device = mLeDevices.get(i);
            deviceAddress.setText(device.getAddress());
            deviceName.setText(device.getName());
            rssi.setText("" + rssis.get(i));

            return view;
        }
    }

}
