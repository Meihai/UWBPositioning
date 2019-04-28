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
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mypopsy.drawable.SearchArrowDrawable;
import com.mypopsy.widget.FloatingSearchView;
import com.onlylemi.mapview.adapter.ArrayRecyclerAdapter;
import com.onlylemi.mapview.agorithm.pdr.PdrDectectionSubject;
import com.onlylemi.mapview.agorithm.pdr.PdrObserver;
import com.onlylemi.mapview.dagger.DaggerBLEMapComponent;
import com.onlylemi.mapview.dagger.DaggerPdrMapComponent;
import com.onlylemi.mapview.library.MapView;
import com.onlylemi.mapview.library.MapViewListener;
import com.onlylemi.mapview.library.layer.LocationLayer;
import com.onlylemi.mapview.library.layer.MarkLayer;
import com.onlylemi.mapview.library.layer.RouteLayer;
import com.onlylemi.mapview.library.utils.MapUtils;
import com.onlylemi.mapview.parameter.LocationSet;
import com.onlylemi.mapview.parameter.MapConfigData3;
import com.onlylemi.mapview.search.SearchController;
import com.onlylemi.mapview.search.SearchResult;
import com.onlylemi.mapview.service.BluetoothLeService;
import com.onlylemi.mapview.service.LocationServiceImproved;
import com.onlylemi.mapview.utils.FileUtil;
import com.onlylemi.mapview.utils.LogUtil;
import com.onlylemi.mapview.utils.PackageUtils;
import com.onlylemi.mapview.utils.SignalProcessUtil;
import com.onlylemi.mapview.utils.ViewUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by admin on 2017/12/16.
 */

public class PdrMapActivity extends AppCompatActivity implements ActionMenuView.OnMenuItemClickListener,
        SearchController.Listener,PdrObserver{
    private final static String TAG = "PdrMapActivity";
    public static String EXTRA_SCENE_SELECT="SCENE_SELECT";//场景选择
    public static final int icon_search=1;
    //添加工厂地图起点对应的像素坐标
    public static final float FACTORY_MAP_START_X=12;
    public static final float FACTORY_MAP_START_Y=240;
    public static final float FACTORY_MAP_END_X=500;
    public static final float FACTORY_MAP_END_Y=1387;
    //添加公司
    public static final float COMPANY_MAP_START_X=320;
    public static final float COMPANY_MAP_START_Y=400;
    public static final float COMPANY_MAP_END_X=1333;
    public static final float COMPANY_MAP_END_Y=1172;
    private Bundle b;
    //添加搜索框
    private FloatingSearchView mSearchView;
    private static final int REQ_CODE_SPEECH_INPUT = 42;
    private PdrMapActivity.SearchAdapter mAdapter;
    //室内地图场景
    private String sceneName;
    private static float prevX=546; //记录上一次的坐标
    private static float prevY=343; //记录上一次的坐标
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
    private SensorManager sensorManager; //传感器管理层
    private PdrDectectionSubject pdrDectectionSubject;

    public static final int UPDATE_CURRENT_POS=1; //更新当前位置标志
    public static final String UPDATE_CURRENT_POS_KEY="update_cur_pos_key";
    @Inject
    SearchController mSearch; //搜索控制器通过依赖注入方式注入

    private Handler myHandler=new Handler() {
        // 2.重写消息处理函数
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 判断发送的消息
                case UPDATE_CURRENT_POS: {
                    // 更新View
                    float[] tag2dpos = msg.getData().getFloatArray(UPDATE_CURRENT_POS_KEY);
                    prevY=tag2dpos[1];
                    prevX=tag2dpos[0];
                    LogUtil.i(TAG,"prex="+prevX+",prevy="+prevY+",步数为:"+tag2dpos[2]);
                    if(locationLayer!=null){
                        if(needNavigated){
                            //todo 添加路径导航功能
                            PointF startPoint=new PointF(prevX,prevY);
                            if(targetP!=null ){
                                if(Math.sqrt(Math.pow(startPoint.x-targetP.x,2)+Math.pow(startPoint.y-targetP.y,2))<100){
                                    List<Integer> routeList=new ArrayList<>();
                                    routeLayer.setRouteList(routeList);
                                    markLayer.setIsClickMark(false);
                                    needNavigated=false;
                                    Toast.makeText( PdrMapActivity.this,"你已经到达终点附近了，谢谢使用!",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    try{
                                        if(sceneName.equals("Factory")){
                                            nodes= MapConfigData3.getFctoryNodesList();
                                            nodesContract=MapConfigData3.getFactoryNodesContactList();
                                        }else if(sceneName.equals("Company")){
                                            nodes=MapConfigData3.getCompanyNodesList();
                                            nodesContract=MapConfigData3.getCompanyNodesContactList();
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
                        locationLayer.setCurrentPosition(new PointF(prevX,prevY));
                        mapView.refresh();
                    }
                    break;
                }
            }
            super.handleMessage(msg);
        }
    };


    @Override
    public void update(double posx,double posy,double curStep){
        float[] tag2dpos=convertPoint(new double[]{posx,posy,curStep});
        Message msg=new Message();
        msg.what = UPDATE_CURRENT_POS;
        Bundle b = new Bundle();
        b.putFloatArray(UPDATE_CURRENT_POS_KEY, tag2dpos);
        msg.setData(b);
        //将连接状态更新的UI的textview上
        myHandler.sendMessage(msg);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        DaggerPdrMapComponent.builder().build().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blemap_test);
        b = getIntent().getExtras();       //从意图获取显示的蓝牙信息
        sceneName=b.getString(EXTRA_SCENE_SELECT);
        /*启动地图*/
        mapView = (MapView) findViewById(R.id.mapview); //寻找地图
        Bitmap bitmap = null;
        try {
            if(sceneName.equals("Factory")) {
                bitmap = BitmapFactory.decodeStream(getAssets().open("fac20171130.png"));
                //bitmap = BitmapFactory.decodeStream(getAssets().open("fac20171128.png"));
                // bitmap = BitmapFactory.decodeStream(getAssets().open("fac20171125.png"));
                setTitle("工厂机房地图");
            }
            else if(sceneName.equals("Company")){
                bitmap=BitmapFactory.decodeStream(getAssets().open("com20171125.png"));
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
                if(sceneName.equals("Factory")){
                    nodes=MapConfigData3.getFctoryNodesList();
                    nodesContract=MapConfigData3.getFactoryNodesContactList();
                    MapUtils.init(nodes.size(),nodesContract.size());
                    marks=MapConfigData3.getFactoryMarks();
                    marksName=MapConfigData3.getFactoryMarksName();
                    locationLayer = new LocationLayer(mapView, new PointF(FACTORY_MAP_START_X, FACTORY_MAP_START_Y));
                    routeLayer=new RouteLayer(mapView);
                    markLayer=new MarkLayer(mapView,marks,marksName);
                    markLayer.setMarkIsClickListener(new MarkLayer.MarkIsClickListener(){
                        @Override
                        public void markIsClick(int num){
                            needNavigated=true;
                            targetP=new PointF(marks.get(num).x,marks.get(num).y);
                            PointF startPoint=new PointF(prevX,prevY);
                            LogUtil.w(TAG,"startPoint.x="+startPoint.x+",startPoint.y="+startPoint.y);
                            List<Integer> routeList= MapUtils.getShortestDistanceBetweenTwoPoints(startPoint,
                                    targetP,nodes,nodesContract);
                            routeLayer.setNodeList(nodes);
                            routeLayer.setRouteList(routeList);
                            mapView.refresh();
                        }
                    });
                    mapView.addLayer(routeLayer);
                }else if(sceneName.equals("Company")){
                    needNavigated=true;
                    nodes=MapConfigData3.getCompanyNodesList();
                    nodesContract=MapConfigData3.getCompanyNodesContactList();
                    MapUtils.init(nodes.size(),nodesContract.size());
                    marks=MapConfigData3.getCompanyMarks();
                    marksName=MapConfigData3.getCompanyMarksName();
                    locationLayer = new LocationLayer(mapView, new PointF(COMPANY_MAP_START_X, COMPANY_MAP_START_Y));
                    markLayer=new MarkLayer(mapView,MapConfigData3.getCompanyMarks(),MapConfigData3.getCompanyMarksName());
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
                locationLayer.setOpenCompass(true);
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
        pdrDectectionSubject=new PdrDectectionSubject(sensorManager);
        pdrDectectionSubject.registerObserver(this);
        initSearchView();
    }

    private void initSearchView(){
        mSearch.setListener(this);
        mSearchView = (FloatingSearchView) findViewById(R.id.search_location);
        mSearchView.setAdapter(mAdapter = new PdrMapActivity.SearchAdapter());
        mSearchView.showLogo(false);
        mSearchView.setItemAnimator(new PdrMapActivity.CustomSuggestionItemAnimator(mSearchView));

        updateNavigationIcon(icon_search);
        mSearchView.showIcon(true);

        mSearchView.setOnIconClickListener(new FloatingSearchView.OnIconClickListener() {
            @Override
            public void onNavigationClick() {
                // toggle
                mSearchView.setActivated(!mSearchView.isActivated());
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSearchAction(CharSequence text) {
                mSearchView.setActivated(false);
            }
        });
//        //菜单点击监听器
        mSearchView.setOnMenuItemClickListener(this);
        //搜索文本监听器
        mSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {
                showClearButton(query.length() > 0 && mSearchView.isActivated());
                //如果文本有改变,则启动搜索
                search(query.toString().trim());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSearchView.setOnSearchFocusChangedListener(new FloatingSearchView.OnSearchFocusChangedListener() {
            @Override
            public void onFocusChanged(final boolean focused) {
                boolean textEmpty = mSearchView.getText().length() == 0;
                showClearButton(focused && !textEmpty);
                if(!focused) showProgressBar(false);
                mSearchView.showLogo(false);
            }
        });
        mSearchView.setText(null);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mSearch.cancel();
    }


    // Activity出来时候，绑定广播接收器，监听蓝牙连接服务传过来的事件
    @Override
    protected void onResume()
    {
        super.onResume();
        pdrDectectionSubject.start();
        needNavigated=false;
    }

    @Override
    protected void onPause(){
        pdrDectectionSubject.stop();
        super.onPause();
    }

    private float[] convertPoint(double[] tagPos){
        double minx=0.0;
        double miny=2.8;
        double maxx=6.665;
        double maxy=18.43;
        double canvasWidth=FACTORY_MAP_END_X-FACTORY_MAP_START_X;
        double canvasHeight=FACTORY_MAP_END_Y-FACTORY_MAP_START_Y;
        double[] enupos=new double[]{tagPos[0],tagPos[1],0};
        double theta=-90;//单位:度
        double[] swuPos= SignalProcessUtil.enuToComCoord(enupos,theta);
        double x=swuPos[0];
        double y=swuPos[1];
        double pixl_x=0;
        double pixl_y=0;
        if(sceneName.equals("Factory")){
            minx=0.0;
            miny=2.8;
            maxx=6.665;
            //maxx=7.0;
            maxy=18.43;
            canvasWidth=FACTORY_MAP_END_X-FACTORY_MAP_START_X;
            canvasHeight=FACTORY_MAP_END_Y-FACTORY_MAP_START_Y;
            pixl_x=x/(maxx-minx)*canvasWidth+FACTORY_MAP_START_X;
            pixl_y=y/(maxy-miny)*canvasHeight+FACTORY_MAP_START_Y;
        }else if(sceneName.equals("Company")){
            //展厅大小
            minx=3.2;
            miny=4.35;
            maxx=11.0;
            maxy=4.35+5.6;
            canvasWidth=COMPANY_MAP_END_X-COMPANY_MAP_START_X;
            canvasHeight=COMPANY_MAP_END_Y-COMPANY_MAP_START_Y;
            pixl_x=x/(maxx-minx)*canvasWidth+COMPANY_MAP_START_X;
            pixl_y=y/(maxy-miny)*canvasHeight+COMPANY_MAP_START_Y;
        }
        float[] tag2dpos=new float[] {  new Float(pixl_x),new Float(pixl_y),new Float(tagPos[2])};
        return tag2dpos;
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

    private void search(String query) {
        showProgressBar(mSearchView.isActivated());
        mSearch.search(query);
    }

    private void updateNavigationIcon(int itemId) {
        Context context = mSearchView.getContext();
        Drawable drawable = null;
        switch(itemId) {
            case icon_search:
                drawable = new SearchArrowDrawable(context);
                break;
        }
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ViewUtils.getThemeAttrColor(context, R.attr.colorControlNormal));
        mSearchView.setIcon(drawable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mSearchView.setActivated(true);
                    mSearchView.setText(result.get(0));
                }
                break;
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                mSearchView.setText(null);
                mSearchView.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                break;
            case R.id.menu_tts:
                PackageUtils.startTextToSpeech(this, getString(R.string.speech_prompt), REQ_CODE_SPEECH_INPUT);
                break;
            case R.id.menu_nav:
                needNavigated=false;
                List<Integer> routeList=new ArrayList<>();
                markLayer.setIsClickMark(false);
                routeLayer.setRouteList(routeList);
                mapView.refresh();
                break;
        }
        return true;
    }

    @Override
    public void onSearchStarted(String query) {
        //nothing to do
    }

    @Override
    public void onSearchResults(SearchResult...searchResults) {
        mAdapter.setNotifyOnChange(false);
        mAdapter.clear();
        if (searchResults != null) mAdapter.addAll(searchResults);
        mAdapter.setNotifyOnChange(true);
        mAdapter.notifyDataSetChanged();
        showProgressBar(false);
    }

    @Override
    public void onSearchError(Throwable throwable) {
        onSearchResults(getErrorResult(throwable));
    }

    private void onItemClick(SearchResult result) {
        mSearchView.setActivated(false);
        if(locationLayer!=null){
            PointF startPoint=new PointF(prevX,prevY);
            PointF targetPoint= LocationSet.locationMap.get(result.title);
            int index=findMarkIndex(result.title);
            if(index==-1){
                Toast.makeText(PdrMapActivity.this,"地图上未找到该标识点!",Toast.LENGTH_SHORT).show();
                return;
            }
            if(targetPoint!=null ){
                if(Math.sqrt(Math.pow(startPoint.x-targetPoint.x,2)+Math.pow(startPoint.y-targetPoint.y,2))<50){
                    List<Integer> routeList=new ArrayList<>();
                    routeLayer.setRouteList(routeList);
                }else if((sceneName.equals("Factory") && !MapConfigData3.getFactoryMarksName().contains(result.title))
                        || (sceneName.equals("Company") && !MapConfigData3.getCompanyMarksName().contains(result.title))){
                    mapView.refresh();
                    Toast.makeText(this,"目前地图不存在该地点,请切换地图再进行查找",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    markLayer.setIsClickMark(true);
                    markLayer.setNum(index);
                    markLayer.getMarkIsClickListener().markIsClick(index);
                    mapView.mapCenterWithPoint(targetPoint.x,targetPoint.y);
                }
            }
        }
        if(new Float(prevX).isNaN() || new Float(prevY).isNaN()){
            LogUtil.w(TAG,"计算出来的坐标为NAN");
            return;
        }
        locationLayer.setCurrentPosition(new PointF(prevX,prevY));

        mapView.refresh();

    }

    private int findMarkIndex(String name){
        int index=-1;
        for (int i=0;i<markLayer.getMarksName().size();i++){
            if(markLayer.getMarksName().get(i).equals(name)){
                index=i;
                break;
            }
        }
        return index;

    }

    private void showProgressBar(boolean show) {
        mSearchView.getMenu().findItem(R.id.menu_progress).setVisible(show);
    }

    private void showClearButton(boolean show) {
        mSearchView.getMenu().findItem(R.id.menu_clear).setVisible(show);
    }

    private static SearchResult getErrorResult(Throwable throwable) {
        LogUtil.w(TAG,throwable.getMessage());
        return new SearchResult("没找到该地名,请换个关键词试试");
    }

    private class SearchAdapter extends ArrayRecyclerAdapter<SearchResult, PdrMapActivity.SuggestionViewHolder> {

        private LayoutInflater inflater;

        SearchAdapter() {
            setHasStableIds(true);
        }

        @Override
        public PdrMapActivity.SuggestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(inflater == null) inflater = LayoutInflater.from(parent.getContext());
            return new PdrMapActivity.SuggestionViewHolder(inflater.inflate(R.layout.item_suggestion, parent, false));
        }

        @Override
        public void onBindViewHolder(PdrMapActivity.SuggestionViewHolder holder, int position) {
            holder.bind(getItem(position));
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    private class SuggestionViewHolder extends RecyclerView.ViewHolder {
        ImageView left,right;
        TextView text, url;

        public SuggestionViewHolder(final View itemView) {
            super(itemView);
            left = (ImageView) itemView.findViewById(R.id.icon_start);
            right= (ImageView) itemView.findViewById(R.id.icon_end);
            text = (TextView) itemView.findViewById(R.id.text);
            url = (TextView) itemView.findViewById(R.id.url);
            left.setImageResource(R.drawable.iclocation);
            itemView.findViewById(R.id.text_container)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClick(mAdapter.getItem(getAdapterPosition()));
                        }
                    });
            right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSearchView.setText(text.getText());
                }
            });
        }
        void bind(SearchResult result) {
            text.setText(Html.fromHtml(result.title));
            url.setText(result.visibleUrl);
            url.setVisibility(result.visibleUrl == null ? View.GONE : View.VISIBLE);
        }
    }

    private static class CustomSuggestionItemAnimator extends BaseItemAnimator {

        private final static Interpolator INTERPOLATOR_ADD = new DecelerateInterpolator(3f);
        private final static Interpolator INTERPOLATOR_REMOVE = new AccelerateInterpolator(3f);
        private final FloatingSearchView mSearchView;

        public CustomSuggestionItemAnimator(FloatingSearchView searchView) {
            mSearchView = searchView;
            setAddDuration(150);
            setRemoveDuration(150);
        }

        @Override
        protected void preAnimateAdd(RecyclerView.ViewHolder holder) {
            if(!mSearchView.isActivated()) return;
            ViewCompat.setTranslationX(holder.itemView, 0);
            ViewCompat.setTranslationY(holder.itemView, -holder.itemView.getHeight());
            ViewCompat.setAlpha(holder.itemView, 0);
        }

        @Override
        protected ViewPropertyAnimatorCompat onAnimateAdd(RecyclerView.ViewHolder holder) {
            if(!mSearchView.isActivated()) return null;
            return ViewCompat.animate(holder.itemView)
                    .translationY(0)
                    .alpha(1)
                    .setStartDelay((getAddDuration() / 2) * holder.getLayoutPosition())
                    .setInterpolator(INTERPOLATOR_ADD);
        }

        @Override
        public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            dispatchMoveFinished(holder);
            return false;
        }

        @Override
        protected ViewPropertyAnimatorCompat onAnimateRemove(RecyclerView.ViewHolder holder) {
            return ViewCompat.animate(holder.itemView)
                    .alpha(0)
                    .setStartDelay(0)
                    .setInterpolator(INTERPOLATOR_REMOVE);
        }
    }
}
