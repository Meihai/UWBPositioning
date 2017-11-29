package com.onlylemi.mapview.service;

import com.onlylemi.mapview.parameter.Constant;
import com.onlylemi.mapview.utils.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 最优化位置计算
 */

public abstract class AbstractPosEstimation {
    private static final String TAG = "AbstractPosEstimation";
    private static StringBuilder recvStrBuild;
    private static final int baseStationNum=6;  //基站个数设置为10个
    private static final int NO_ERROR_CORRECTED=0;
    private static final int ERROR_CORRECTED=1;
    private long prevRecvTime=System.currentTimeMillis(); //上一次接收的时间和字符串
    private int prevRecvLastIndex=0; //上一次接收的最后一个索引
    /**
     *
     * @param recvStr  通过蓝牙接收到的来自超宽带标签板的测距数据
     * @param dealMethod 对测距数据的处理方式 0 对测距数据不进行非视距误差就纠正 1 对测距数据进行误差纠正
     * @param baseLowBound 进行标签解算所需要的最少基站数量
     * @return 返回一个关于基站的坐标和相关测距的二维数组 每一行数据的数据结构形式为 基站的x,y,z坐标,标签与基站的测量距离,相对应的基站名称
     */
    public double[][] parseDistanceStr(String recvStr,int dealMethod,int baseLowBound,String sceneName){
//        LogUtil.i(TAG,"recvStr="+recvStr);
        if(recvStrBuild==null){
            recvStrBuild=new StringBuilder();
        }
        recvStrBuild.append(recvStr);
  //      LogUtil.w(TAG,"Abstract recvStr:"+recvStrBuild.toString());
        //如果接收时间间隔超过2秒,则删除之前收到的数据
//        if(System.currentTimeMillis()-prevRecvTime>2000){
//            recvStrBuild.delete(0,prevRecvLastIndex);
//            prevRecvTime=System.currentTimeMillis();
//            prevRecvLastIndex=recvStrBuild.length();
//        }

        String[] baseStationStrList=recvStrBuild.toString().split("\r\n");
        //小于4,说明收到的基站测距数据小于4个,无法进行测距
        if(baseStationStrList.length<7){
            return null;
        }
        LogUtil.d(TAG,"--------------------------------------------------------");
        LogUtil.d(TAG,"cost time:"+(System.currentTimeMillis()-prevRecvTime)+"ms");
        LogUtil.d(TAG,"recvStr="+(recvStrBuild.toString()));
        LogUtil.d(TAG,"--------------------------------------------------------");
        prevRecvTime=System.currentTimeMillis();
        recvStrBuild.delete(0,recvStrBuild.length());

        prevRecvLastIndex=0;
        //存放每个基站收到的测距数据
        HashMap<String,Integer> distanceCntMap=new HashMap<String,Integer>();
        //存放收到的每个基站测距数据位于标签测距数组中的索引
        HashMap<String,List<Integer>> distanceIndexMap=new HashMap<String,List<Integer>>();
        //收到的各个测距基站数据,按收到的时间先后排序
        List<Double> distanceList=new ArrayList<Double>();
        int index=0;
        for (int i=0;i<baseStationStrList.length;i++){
            if(baseStationStrList[i].startsWith("distance")){
                 try{
                        //去掉字符串前后的空格
                        String bsName=baseStationStrList[i].substring(baseStationStrList[i].indexOf("distance")+9,baseStationStrList[i].indexOf(":")).trim();
                        String distance=baseStationStrList[i].substring(baseStationStrList[i].indexOf(":")+1,baseStationStrList[i].indexOf("mm")).trim();
                        if(distanceCntMap.containsKey(bsName)){
                            int countnum=distanceCntMap.get(bsName);
                            countnum=countnum+1;
                            distanceCntMap.put(bsName,countnum);
                            List<Integer> indexList=distanceIndexMap.get(bsName);
                            indexList.add(index);
                            distanceIndexMap.put(bsName,indexList);
                            index++;
                            distanceList.add(Double.parseDouble(distance));
                        }else{
                            distanceCntMap.put(bsName,1);
                            List<Integer> indexList=new ArrayList<Integer>();
                            indexList.add(index);
                            distanceIndexMap.put(bsName,indexList);
                            index++;
                            distanceList.add(Double.parseDouble(distance));
                        }
                 }catch(Exception e){
                         StringBuilder sbmes=new StringBuilder();
                         sbmes.append("String:"+baseStationStrList[i]+" parse Error");
                         sbmes.append(",");
                         sbmes.append(e.getMessage());
                         LogUtil.w(TAG,sbmes.toString());
                 }
            }
        }
        double[][] bsDistanceArr=distanceErrorCorrected(distanceCntMap,
                    distanceIndexMap,
                    distanceList,
                    dealMethod,
                    baseLowBound,
                     sceneName);
        return bsDistanceArr;

    }

    /**
     *
     * @param distanceCntMap  基站名称-基站测距数据键值对
     * @param distanceIndexMap 基站名称-基站测距数据对应索引列表键值对
     * @param distanceList 基站测距数据列表
     * @param dealMethod 基站误差处理方法
     * @param baseLowBound 能够解算出标签位置的基站数量下界
     * @return double[][]
     */
    private double[][] distanceErrorCorrected(HashMap<String,Integer> distanceCntMap,
                                                HashMap<String,List<Integer>> distanceIndexMap,
                                                List<Double> distanceList,
                                                int dealMethod,
                                                int baseLowBound,
                                                String sceneName){
        int baseStationCnt=distanceCntMap.size();
        if (baseStationCnt<baseLowBound){
             StringBuilder sb=new StringBuilder();
             sb.append("The distance data num "+baseStationCnt+" is less than "+ baseLowBound);
             sb.append(" calculate tag position failed!");
             LogUtil.w(TAG,sb.toString());
             return null;
        }
        int dataCnt=distanceList.size();
        Map<String,Double> distanceMap=new HashMap<String,Double>();
        Iterator iterator=distanceIndexMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,List<Integer>> entry=(Map.Entry<String,List<Integer>>)iterator.next();
            String bsName=entry.getKey();
            List<Integer> indexList=entry.getValue();
            //取最后一个元素,忽略前面的元素,以保证数据实时更新
            int lastDataIndexOfbs=indexList.get(indexList.size()-1);
            distanceMap.put(bsName,distanceList.get(lastDataIndexOfbs));
        }
        //将距离map转为数组的形式
        double[][] baseStationArr=new double[distanceMap.size()][5];
        Iterator iter=distanceMap.entrySet().iterator();
        int countbs=0;
        while (iter.hasNext()){
            Map.Entry entry=(Map.Entry) iter.next();
            String key=(String) entry.getKey();
            Double val=(Double) entry.getValue();
            try{
                if(sceneName.equals("Factory")){
                    if(!Constant.factoryBaseStationInfoMap.containsKey(key)){
                        LogUtil.w(TAG,"factoryBaseStationInfoMap does not contain key:"+key);
                        return null;
                    }
                    baseStationArr[countbs][0]= Constant.factoryBaseStationInfoMap.get(key)[0];
                    baseStationArr[countbs][1]= Constant.factoryBaseStationInfoMap.get(key)[1];
                    baseStationArr[countbs][2]= Constant.factoryBaseStationInfoMap.get(key)[2];
                }else if(sceneName.equals("Company")){
                    if(!Constant.companyBaseStationInfoMap.containsKey(key)){
                        LogUtil.w(TAG,"companyBaseStationInfoMap does not contain key:"+key);
                        return null;
                    }
                    baseStationArr[countbs][0]= Constant.companyBaseStationInfoMap.get(key)[0];
                    baseStationArr[countbs][1]= Constant.companyBaseStationInfoMap.get(key)[1];
                    baseStationArr[countbs][2]= Constant.companyBaseStationInfoMap.get(key)[2];
                }
                baseStationArr[countbs][3]=val;
                baseStationArr[countbs][4]=Double.parseDouble(key);
                countbs++;
            }catch(Exception ex){
              //  LogUtil.w(TAG,"Convert distanceMap to array failed! "+ex.getMessage());
                ex.printStackTrace();
                return null;
            }
        }
        switch(dealMethod){
            case NO_ERROR_CORRECTED: //不对原始数据进行测距误差纠正
                return baseStationArr;
            case ERROR_CORRECTED://对原始数据进行距离误差纠正
                // TODO: 2017/10/26
                /**
                 *  添加误差纠错的代码
                 */
                return baseStationArr;
            default:
                LogUtil.w(TAG,"The dealMethod:"+dealMethod+" cannot be dealed!");
                return null;
        }

    }

//    private static class RecvDataParser(){
//        private
//        public RecvDataParser(String recvData){
//
//        }
//    }



}
