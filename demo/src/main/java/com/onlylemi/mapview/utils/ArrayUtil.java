package com.onlylemi.mapview.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/10/26.
 */

public class ArrayUtil {
    public static final String TAG="ArrayUtil";
    public static void arrayCopy(double[][] srcArray,double[][] dstArray){
        if(srcArray==null || dstArray==null){
            LogUtil.w(TAG,"srcArray or dstArray is null");
            return;
        }
        int row=dstArray.length;
        int col=dstArray[0].length;
        if(srcArray.length<row || srcArray[0].length<col){
            LogUtil.w(TAG,"srcArray is smaller than dstArray");
            return;
        }
        for (int i=0;i<row;i++)
            for(int j=0;j<col;j++){
                dstArray[i][j]=srcArray[i][j];
            }
    }

    //列表深拷贝方法
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }



    public static <T>List<T> copyList(List<T> listSrc,int size){
        List<T> tempList=new ArrayList<T>();
        for(int i=0;i<size;i++){
            T tempDes=listSrc.get(i);
            tempList.add(tempDes);
        }
        return tempList;
    }
}
