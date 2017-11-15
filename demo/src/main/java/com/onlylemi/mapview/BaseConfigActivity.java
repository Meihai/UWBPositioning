package com.onlylemi.mapview;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.onlylemi.mapview.parameter.Constant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2017/11/4.
 */

public class BaseConfigActivity extends Activity {
    public static final String EXTRAS_SCENE_SELECT="com.keydak.mapview.SCENE";
    private String sceneName;
    private TextView sceneNameText;
    private Button addBsBtn;
    private Button delBsBtn;
    private EditText bsName;
    private EditText bsPosx;
    private EditText bsPosy;
    private EditText bsPosz;
    private Bundle b;
    private ListView bslv;
    private BsListAdapter mBsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsconfig);
        b = getIntent().getExtras();
        //从意图获取显示的蓝牙信息
        sceneName=b.getString(EXTRAS_SCENE_SELECT);
        sceneNameText=(TextView) findViewById(R.id.scene_mode);
        sceneNameText.setText(sceneName);
        bsName=(EditText) findViewById(R.id.edit_bsname);
        bsPosx=(EditText) findViewById(R.id.edit_x);
        bsPosy=(EditText) findViewById(R.id.edit_y);
        bsPosz=(EditText) findViewById(R.id.edit_z);
        addBsBtn=(Button) findViewById(R.id.btn_addbs);
        delBsBtn=(Button) findViewById(R.id.btn_deletebs);
        addBsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String bs_name=bsName.getText().toString();
                String bs_posx=bsPosx.getText().toString();
                String bs_posy=bsPosy.getText().toString();
                String bs_posz=bsPosz.getText().toString();
                double[] bsCoord=new double[]{Double.parseDouble(bs_posx),Double.parseDouble(bs_posy),Double.parseDouble(bs_posz)};
                String showText="";
                if(sceneName.equals("FACTORY")){

                    if(Constant.factoryBaseStationInfoMap.containsKey(bs_name)){
                        Constant.factoryBaseStationInfoMap.put(bs_name,bsCoord);
                        showText="更新工厂基站:"+bs_name+","+bs_posx+","+bs_posy+","+bs_posz+" 成功";
                    }else{
                        Constant.factoryBaseStationInfoMap.put(bs_name,bsCoord);
                        showText="添加工厂基站:"+bs_name+","+bs_posx+","+bs_posy+","+bs_posz+" 成功";
                    }
                    mBsListAdapter.setSceneMode("FACTORY");
                }else if(sceneName.equals("COMPANY")){
                    if(Constant.companyBaseStationInfoMap.containsKey(bs_name)){
                        Constant.companyBaseStationInfoMap.put(bs_name,bsCoord);
                        showText="更新公司基站:"+bs_name+","+bs_posx+","+bs_posy+","+bs_posz+" 成功";
                    }else{
                        Constant.companyBaseStationInfoMap.put(bs_name,bsCoord);
                        showText="添加公司基站:"+bs_name+","+bs_posx+","+bs_posy+","+bs_posz+" 成功";
                    }
                    mBsListAdapter.setSceneMode("COMPANY");
                }else{
                    showText="不支持该模式("+sceneName+")";
                }
                updateBsList();
                saveBsPosData();
                Toast.makeText(BaseConfigActivity.this,showText,Toast.LENGTH_SHORT).show();
            }
        });
        delBsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bs_name=bsName.getText().toString();
                String showText="";
                if(sceneName.equals("FACTORY")){
                    if(Constant.factoryBaseStationInfoMap.containsKey(bs_name)){
                        Constant.factoryBaseStationInfoMap.remove(bs_name);
                        mBsListAdapter.setSceneMode("FACTORY");
                        showText="删除工厂基站:"+bs_name+" 成功";
                    }else{
                        showText="不存在工厂基站:"+bs_name+",删除失败";
                    }
                }if(sceneName.equals("COMPANY")){
                    if(Constant.companyBaseStationInfoMap.containsKey(bs_name)){
                        Constant.companyBaseStationInfoMap.remove(bs_name);
                        showText="删除公司基站:"+bs_name+" 成功";
                        mBsListAdapter.setSceneMode("COMPANY");
                    }else{
                        showText="不存在公司基站:"+bs_name+",删除失败";
                    }
                }
                updateBsList();
                saveBsPosData();
                Toast.makeText(BaseConfigActivity.this,showText,Toast.LENGTH_SHORT).show();
            }
        });
        mBsListAdapter=new BsListAdapter();
        mBsListAdapter.setSceneMode(sceneName);
        bslv=(ListView) findViewById(R.id.bslv);
        bslv.setAdapter(mBsListAdapter);
        updateBsList();

    }

    private void updateBsList(){
        ArrayList<BaseStation> baseStations=new ArrayList<BaseStation>();
        if(mBsListAdapter.getSceneMode().equals("FACTORY")){
            Set bsSet=Constant.factoryBaseStationInfoMap.entrySet();
            Iterator iterator=bsSet.iterator();
            while(iterator.hasNext()){
                Map.Entry<String,double[]> entry=(Map.Entry<String,double[]>)iterator.next();
                String bsName=entry.getKey();
                String bsCoord=entry.getValue()[0]+","+entry.getValue()[1]+","+entry.getValue()[2];
                BaseStation insBaseStation=new BaseStation(bsName,bsCoord);
                baseStations.add(insBaseStation);
            }

        }else if(mBsListAdapter.getSceneMode().equals("COMPANY")){
            Set bsSet=Constant.companyBaseStationInfoMap.entrySet();
            Iterator iterator=bsSet.iterator();
            while(iterator.hasNext()){
                Map.Entry<String,double[]> entry=(Map.Entry<String,double[]>)iterator.next();
                String bsName=entry.getKey();
                String bsCoord=entry.getValue()[0]+","+entry.getValue()[1]+","+entry.getValue()[2];
                BaseStation insBaseStation=new BaseStation(bsName,bsCoord);
                baseStations.add(insBaseStation);
            }
        }
        mBsListAdapter.setmBsStations(baseStations);
        mBsListAdapter.notifyDataSetChanged();
    }

    private void saveBsPosData(){
        SharedPreferences.Editor editor1=getSharedPreferences(Constant.FACTORY_BS_DATA_NAME,MODE_PRIVATE).edit();
        editor1.clear();
        editor1.commit();
        Set facBsSet=Constant.factoryBaseStationInfoMap.entrySet();
        Iterator iter=facBsSet.iterator();
        while(iter.hasNext()){
            Map.Entry<String,double[]> bsEntry=(Map.Entry<String,double[]>)iter.next();
            String bsName=bsEntry.getKey();

            double[] posArr=bsEntry.getValue();
            String bspos=String.format("%5.0f,%5.0f,%5.0f",posArr[0],posArr[1],posArr[2]);
            editor1.putString(bsName,bspos);
        }
        editor1.commit();
        SharedPreferences.Editor editor2=getSharedPreferences(Constant.COMPANY_BS_DATA_NAME,MODE_PRIVATE).edit();
        editor2.clear();
        editor2.commit();
        Set comBsSet=Constant.companyBaseStationInfoMap.entrySet();
        Iterator iter2=comBsSet.iterator();
        while(iter2.hasNext()){
            Map.Entry<String,double[]> bsEntry=(Map.Entry<String,double[]>)iter2.next();
            String bsName=bsEntry.getKey();

            double[] posArr=bsEntry.getValue();
            String bspos=String.format("%5.0f,%5.0f,%5.0f",posArr[0],posArr[1],posArr[2]);
            editor2.putString(bsName,bspos);
        }
        editor2.commit();
    }

    /**
     * @Description: TODO<自定义适配器Adapter,作为listview的适配器> 用来显示配置的基站信息
     */
    private class BsListAdapter extends BaseAdapter {
        private ArrayList<BaseStation> mBsStations;
        private LayoutInflater mInflator;
        private String sceneMode;

        public BsListAdapter()
        {
            super();
            mBsStations=new ArrayList<BaseStation>();
            mInflator = getLayoutInflater();
            sceneMode="FACTORY";
        }

        public void setSceneMode(String sceneMode){
            this.sceneMode=sceneMode;
        }

        public String getSceneMode(){
            return this.sceneMode;
        }

        public void setmBsStations(ArrayList<BaseStation> baseStationList){
            mBsStations=baseStationList;
        }

        public ArrayList<BaseStation> getmBsStations(){
            return this.mBsStations;
        }

        public void addBaseStation( BaseStation baseStation)
        {
            if (!mBsStations.contains(baseStation))
            {
                mBsStations.add(baseStation);

            }
        }

        public BaseStation getBaseStation(int position)
        {
            return mBsStations.get(position);
        }

        public void clear()
        {
            mBsStations.clear();
        }
        @Override
        public int getCount()
        {
            return mBsStations.size();
        }

        @Override
        public Object getItem(int i)
        {
            return mBsStations.get(i);
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
            view = mInflator.inflate(R.layout.listbaseinfo, null);
            // 初始化liangge个textview显示蓝牙信息
            TextView tv_bsName = (TextView) view
                    .findViewById(R.id.tv_bsName);
            TextView tv_bsCoord = (TextView) view
                    .findViewById(R.id.tv_bsCoord);
            BaseStation baseStation=mBsStations.get(i);

            tv_bsName.setText(baseStation.getName());
            tv_bsCoord.setText(baseStation.getCoord());

            return view;
        }
    }

}
