package com.onlylemi.mapview.service;

/**
 * Created by admin on 2017/10/25.
 */

public interface LocationI {
    /*根据测距数据计算标签位置*/
    double[] convertDistanceToPos(String baseStationInfo,String sceneName);

}
