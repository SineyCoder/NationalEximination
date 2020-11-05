package com.example.exam.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author siney
 * @createTime 2020-10-31
 **/
public class ObjectFactoryUtils {

    public static Map<Object, Object> createResultMap(int resultCode, String... obj){
        Map<Object, Object> map = new HashMap<>();
        if(obj.length > 0 && obj.length % 2 == 0){
            map.put("code", resultCode);
            for(int i = 0;i < obj.length / 2;i++){
                map.put(obj[2 * i], obj[2 * i + 1]);
            }
        }
        return map;
    }

}
