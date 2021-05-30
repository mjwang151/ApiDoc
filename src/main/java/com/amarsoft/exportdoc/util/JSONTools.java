package com.amarsoft.exportdoc.util;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * JBO转JSONObect，JBOList转JSONArray
 * @author xgchen
 * @author dwyang 2018-09-03
 */
public class JSONTools {




    public static Object getValue(JSONObject jsonObj, String key) {
        Set<String> keys = jsonObj.keySet();
        for (String curKey : keys) {
            if (curKey.equalsIgnoreCase(key)) {
                return jsonObj.get(curKey);
            }
        }

        return null;
    }

    public static boolean containsKey(JSONObject jsonObj,String key) {
        Set<String> keys = jsonObj.keySet();
        for (String curKey : keys) {
            if (curKey.equalsIgnoreCase(key)) return true;
        }

        return false;
    }

    public static String getString(JSONObject jsonObj,String key) {
        Object value = getValue(jsonObj, key);
        if (value == null) {
            return null;
        }

        return value.toString();
    }

    public static String getString(JSONObject jsonObj,String key,String defaultValue) {
        Object value = getValue(jsonObj, key);
        if (value == null) {
            value = defaultValue;
        }

        return value.toString();
    }

    public static Integer getInt(JSONObject jsonObj,String key) {
        Object value = getValue(jsonObj, key);
        if (value == null) {
            return null;
        }

        return parseInteger(key,value);
    }

    public static Double getDouble(JSONObject jsonObj,String key) {
        Object value = getValue(jsonObj, key);
        if (value == null) {
            return null;
        }

        return parseDouble(value);
    }

    public static Double getDouble(JSONObject jsonObj,String key,Double defaultValue) {
        Object value = getValue(jsonObj, key);
        if (value == null) {
            return defaultValue;
        }

        return parseDouble(value);
    }

    public static Integer getInt(JSONObject jsonObj,String key,Integer defaultValue) {
        Object value = getValue(jsonObj, key);
        if (value == null) {
            return defaultValue;
        }

        return parseInteger(key,value);
    }

    public static JSONObject getJSONObject(JSONObject jsonObject,String key) {
        Object value = getValue(jsonObject, key);
        if (value == null) {
            return null;
        }

        if (!(value instanceof JSONObject)) {
            throw new RuntimeException("Key:"+key+"属性值不是JSON对象,原始值为:"+value);
        }
        return (JSONObject) value;
    }

    private static Integer parseInteger(String key,Object value) {
        if (value instanceof String) {
            String regex = "\\d+";
            String s = (String) value;
            if (s.matches(regex)) {
                return Integer.parseInt(s);
            } else if ("null".equalsIgnoreCase(s)) {
                return null;
            }
            throw new RuntimeException("无法将value:"+value+"转换成int型");
        }
        Number number = (Number) value;

        return number.intValue();
    }


    private static Double parseDouble(Object value) {
        if (value instanceof String) {
            String s = (String) value;
            String regex = "\\d+(\\.\\d+)?";
            if (s.matches(regex)) {
                return Double.parseDouble(s);
            } else if ("null".equalsIgnoreCase(s)) {
                return null;
            }
            throw new RuntimeException("无法将value:"+value+"转换成Double型");
        } else {
            Number number = (Number) value;
            return number.doubleValue();
        }
    }
    /**
     * 将搜索条件字符串转为Map
     * @param searchCriteria
     * @return
     */
    public static Map<String, String> strToMap(String searchCriteria){
        Map<String, String> map = new HashMap<>();
        if(searchCriteria != null && searchCriteria != ""){
            String[] strings = searchCriteria.split("@");
            for(String string : strings){
                String[] items = string.split("~");
                if(items.length == 1){
                    map.put(items[0], "");
                }else{
                    map.put(items[0], items[1]);
                }
            }
        }
        return map;
    }



}
