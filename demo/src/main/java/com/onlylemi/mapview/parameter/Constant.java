package com.onlylemi.mapview.parameter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/10/26.
 */
//将基站坐标的定义为常量
public class Constant {
    public static final int NO_ERROR_CORRECTED=0;
    public static final int ERROR_CORRECTED=1;
    public static final int BASE_STATION_DOWN_LIMIT=4;
    public static final String FACTORY_BS_DATA_NAME="factory_bs_data";
    public static final String COMPANY_BS_DATA_NAME="company_bs_data";
    public static final String OTHER_DATA_CONFIG_NAME="config_data";


    public static Map<String,double[]> factoryBaseStationInfoMap=new HashMap<String,double[]>();
    public static Map<String,double[]> companyBaseStationInfoMap=new HashMap<String,double[]>();
    static{
//        factoryBaseStationInfoMap.put("1",new double[]{7100.00,1040.0,3180.0});
//        factoryBaseStationInfoMap.put("2",new double[]{1040.00,6530.00,3100.00});
//        factoryBaseStationInfoMap.put("3",new double[]{5400.00,6500.00,3250.00});
//        factoryBaseStationInfoMap.put("4",new double[]{5400.00,2870.00,3100.00});
//        factoryBaseStationInfoMap.put("5",new double[]{5400.00,600.00,3550.00});
//        factoryBaseStationInfoMap.put("6",new double[]{910.00,-220.00,3100.00});
//        factoryBaseStationInfoMap.put("7",new double[]{2090.00,2220.00,2800.00});
        factoryBaseStationInfoMap.put("1",new double[]{8500,1480.0,3200});
        factoryBaseStationInfoMap.put("2",new double[]{5820,7700,3190});
        factoryBaseStationInfoMap.put("3",new double[]{1800,7350,3240});
        factoryBaseStationInfoMap.put("4",new double[]{5900,790,3600});
        factoryBaseStationInfoMap.put("5",new double[]{6800,4110,3490});
        factoryBaseStationInfoMap.put("6",new double[]{1140,540,3324});
        factoryBaseStationInfoMap.put("7",new double[]{430,2300,2160});
        //公司基站坐标
//        companyBaseStationInfoMap.put("1",new double[]{0,0,2100});
//        companyBaseStationInfoMap.put("2",new double[]{0,5600,2000});
//        companyBaseStationInfoMap.put("3",new double[]{4400,0,3300});
//        companyBaseStationInfoMap.put("4",new double[]{4400,5600,2500});
//        companyBaseStationInfoMap.put("5",new double[]{7000,2500,680});
//        companyBaseStationInfoMap.put("6",new double[]{6600,5000,1800});

//        companyBaseStationInfoMap.put("1",new double[]{6810,3970,2210});
//        companyBaseStationInfoMap.put("2",new double[]{3260,3950,2330});
//        companyBaseStationInfoMap.put("3",new double[]{-750,4580,2250});
//        companyBaseStationInfoMap.put("4",new double[]{6680,1610,2300});
//        companyBaseStationInfoMap.put("5",new double[]{600,1180,2090});
//        //companyBaseStationInfoMap.put("6",new double[]{2800,-380,2100});
//        companyBaseStationInfoMap.put("6",new double[]{400,3900,1910});

//        companyBaseStationInfoMap.put("1",new double[]{6080,4070,1910});
//        companyBaseStationInfoMap.put("2",new double[]{3250,3900,2050});
//        companyBaseStationInfoMap.put("3",new double[]{400,3800,2200});
//        companyBaseStationInfoMap.put("4",new double[]{-800,4450,2170});
//        companyBaseStationInfoMap.put("5",new double[]{-600,1650,2220});
//        companyBaseStationInfoMap.put("6",new double[]{450,1550,2000});
//        companyBaseStationInfoMap.put("7",new double[]{7460,2000,1670});

//        companyBaseStationInfoMap.put("1",new double[]{6000,4100,2350});
//        companyBaseStationInfoMap.put("2",new double[]{3000,3970,2248});
//        companyBaseStationInfoMap.put("3",new double[]{-700,4500,2209});
//        companyBaseStationInfoMap.put("4",new double[]{600,1500,2588});
//        companyBaseStationInfoMap.put("5",new double[]{6100,1630,2257});
//        companyBaseStationInfoMap.put("6",new double[]{-500,1900,2496});
//        companyBaseStationInfoMap.put("7",new double[]{400,3800,2205});

        companyBaseStationInfoMap.put("1",new double[]{7100,3000,2180});
        companyBaseStationInfoMap.put("2",new double[]{5400,5600,2160});
        companyBaseStationInfoMap.put("3",new double[]{-600,7800,2170});
        companyBaseStationInfoMap.put("4",new double[]{600,1650,2140});
        companyBaseStationInfoMap.put("5",new double[]{200,5600,2150});
        companyBaseStationInfoMap.put("6",new double[]{-690,2000,2160});
        companyBaseStationInfoMap.put("7",new double[]{3300,3960,2330});
    }
}
