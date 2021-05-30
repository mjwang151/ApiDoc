package com.amarsoft.exportdoc.inner;


import com.alibaba.fastjson.JSONObject;
import com.amarsoft.bean.AssetApi;
import com.amarsoft.bean.AssetApiParams;
import com.amarsoft.exportdoc.util.JSONTools;
import com.amarsoft.service.impl.DocServiceImpl;
import com.amarsoft.service.impl.EdsDocServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;


/**
 * 内部服务接口输入输出报文预览：输出只针对SQL型接口
 *
 * @author kwqin created on 2020年12月7日 下午7:10:46
 */
@Slf4j
@Component
public class InnerMessageUtil {


    /**
     * 返回data：服务接口号
     */
    public static final String DATA_SERVICE_NO = "serviceno";
    /**
     * 返回data：服务接口名称
     */
    public static final String DATA_SERVICE_NAME = "servicename";
    /**
     * 返回data：输入对象
     */
    public static final String DATA_INPUT = "input";

    /**
     * 返回data：输入对象
     */
    public static final String DATA_OUTPUT = "output";

    /**
     * 返回data：输出子对象
     */
    public static final String DATA_OUTPUT_MAP = "outputmap";

    /**
     * 返回data：输入报文
     */
    public static final String DATA_MESSAGE_INPUT = "demoinput";
    /**
     * 返回data：输出报文
     */
    public static final String DATA_MESSAGE_OUTPUT = "demooutput";
    /**
     * input:参数名
     */
    public static final String INPUT_NAME = "name";
    /**
     * input:参数中文名
     */
    public static final String INPUT_LABEL = "label";
    /**
     * input:参数类型
     */
    public static final String INPUT_TYPE = "type";
    /**
     * input:是否必输
     */
    public static final String INPUT_NEEDINPUT = "needinput";

    public static final String SERVICEURL_TEST = "https://app.amarsoft.com/hubservicetest/api/gateway";
    public static final String SERVICEURL_PRODUCT = "https://app.amarsoft.com/****/api/gateway";

    @Autowired
    EdsDocServiceImpl docService;


    public static JSONObject createResponseJson(int code, String msg) {
        JSONObject jo = new JSONObject(2);
        jo.put("code", code);
        jo.put("msg", msg);
        return jo;
    }

    private static JSONObject createResponseJson(int code, String msg, JSONObject data) {
        JSONObject jo = new JSONObject(3);
        jo.put("code", code);
        jo.put("msg", msg);
        jo.put("data", data);
        return jo;
    }


    /**
     * 传入厂商编号和接口编号，返回样例报文信息
     *
     * @param service 服务编号
     * @return
     * @throws Exception
     */
    public JSONObject demoMessage(String service) throws Exception {
        int code = 0;
        String msg = null;
        AssetApi serviceBiz = docService.findApi(service);
        if (serviceBiz == null) {
            code = 1;
            msg = "服务编号[" + service + "]下找不到！";
            return createResponseJson(code, msg);
        }
        JSONObject demoDataJO = new JSONObject(true);
        demoDataJO.put(DATA_SERVICE_NO, serviceBiz.getApi());
        demoDataJO.put(DATA_SERVICE_NAME, serviceBiz.getName());
        List<AssetApiParams> list = getTrans(service);
        List<JSONObject> inputArr = new ArrayList<JSONObject>();
        List<JSONObject> outArr = new ArrayList<JSONObject>();
        Map<String, List<JSONObject>> map_outArr = new LinkedHashMap<String, List<JSONObject>>();

        Map<String, AssetApiParams> order_map = new LinkedHashMap<String, AssetApiParams>(); //用来排序

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                AssetApiParams biz = list.get(i);
                String needinput = biz.getParamType();
                String paranentId = biz.getParentParamId();
                int level = biz.getLevel();

                if (needinput.equals("REQUEST")) {
                    if (StringUtils.isNotBlank(paranentId)) {
                        if (map_outArr.containsKey(paranentId)) {
                            List<JSONObject> ll = map_outArr.get(paranentId);
                            ll.add(combineTran(biz, serviceBiz));
                        } else {
                            List<JSONObject> ll = new ArrayList<JSONObject>();
                            ll.add(combineTran(biz, serviceBiz));
                            map_outArr.put(paranentId, ll);
                        }
                    } else {
                        inputArr.add(combineTran(biz, serviceBiz));
                    }
                }
                if (needinput.equals("RESPONSE")) {
                    if (StringUtils.isNotBlank(paranentId)) {
                        if (map_outArr.containsKey(paranentId)) {
                            List<JSONObject> ll = map_outArr.get(paranentId);
                            ll.add(combineTran(biz, serviceBiz));
                        } else {
                            List<JSONObject> ll = new ArrayList<JSONObject>();
                            ll.add(combineTran(biz, serviceBiz));
                            map_outArr.put(paranentId, ll);
                        }
                    } else {
                        outArr.add(combineTran(biz, serviceBiz));
                        order_map.put(biz.getParamId(), biz);
                    }
                }


            }
        }

        inputArr = inputArr.stream().sorted((h1, h2) -> {
            int h1order = JSONTools.getInt((JSONObject) h1, "orderno");
            int h2order = JSONTools.getInt((JSONObject) h2, "orderno");
            if (h1order > h2order) {
                return 1;
            } else if (h1order == h2order) {
                return 0;
            } else {
                return -1;
            }
        }).collect(Collectors.toList());

        outArr = outArr.stream().sorted((h1, h2) -> {
            int h1order = JSONTools.getInt((JSONObject) h1, "orderno");
            int h2order = JSONTools.getInt((JSONObject) h2, "orderno");
            if (h1order > h2order) {
                return 1;
            } else if (h1order == h2order) {
                return 0;
            } else {
                return -1;
            }
        }).collect(Collectors.toList());


        Map<String, List<JSONObject>> map_outArr2 = new LinkedHashMap<String, List<JSONObject>>();

        order_map = order_map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        int count = 0;
        for (Map.Entry<String, AssetApiParams> entry : order_map.entrySet()) {
            count++;
            combineOutArr("2." + count, "", combineTran(entry.getValue(), serviceBiz), map_outArr, map_outArr2);
        }
        demoDataJO.put(DATA_INPUT, inputArr);
        demoDataJO.put(DATA_OUTPUT, outArr);
        demoDataJO.put(DATA_OUTPUT_MAP, map_outArr2);

        demoDataJO.put(DATA_MESSAGE_INPUT, serviceBiz.getReqSample());
        demoDataJO.put(DATA_MESSAGE_OUTPUT, serviceBiz.getRespSample());

        return createResponseJson(code, msg, demoDataJO);
    }

    /**
     * @param countPre       数字前缀 - 当前第几个节点
     * @param apiPre         数字当前第几个节点
     * @param assetApiParams 当前Api父节点
     * @param in_map_outArr  所有的输入 map
     * @param out_map_outArr 所有的输出 map
     */
    public void combineOutArr(String countPre, String apiPre, JSONObject assetApiParams, Map<String, List<JSONObject>> in_map_outArr, Map<String, List<JSONObject>> out_map_outArr) {
        if (!in_map_outArr.containsKey(assetApiParams.getString("paramid"))) {
            return;
        }
        List<JSONObject> ll1 = in_map_outArr.get(assetApiParams.getString("paramid"));
        ll1 = ll1.stream().sorted((h1, h2) -> {
            int h1order = JSONTools.getInt(h1, "orderno");
            int h2order = JSONTools.getInt(h2, "orderno");
            if (h1order > h2order) {
                return 1;
            } else if (h1order == h2order) {
                return 0;
            } else {
                return -1;
            }
        }).collect(Collectors.toList());
        apiPre = (StringUtils.isBlank(apiPre) ? "" : (apiPre + "→")) + assetApiParams.getString("intf");
        out_map_outArr.put(countPre + assetApiParams.getString("intfname") + "(" + apiPre + ")", ll1);
        int j = 1;
        for (int i = 0; i < ll1.size(); i++) {
            JSONObject jo = ll1.get(i);
            String paramid = JSONTools.getString(jo, "paramid");
            if (in_map_outArr.containsKey(paramid)) {
                combineOutArr(countPre + "." + j, apiPre , jo, in_map_outArr, out_map_outArr);
                j++;
            }
        }
    }

    /**
     * 组装json
     *
     * @param biz
     * @param serviceBiz
     * @return
     */
    public static JSONObject combineTran(AssetApiParams biz, AssetApi serviceBiz) {
        JSONObject joj = new JSONObject(true);
        joj.put("paramid", biz.getParamId());
        joj.put("level", biz.getLevel());
        joj.put("orderno", biz.getSortNo());
        joj.put("transcode", serviceBiz.getApi());
        joj.put("intf", biz.getName());
        joj.put("intfname", biz.getChName());
        joj.put("needtype", biz.getIsRequired().equals("Y") ? "TRUE" : "FALSE");
        joj.put("intftype", biz.getDataType());
        joj.put("remark", biz.getDescr());
        return joj;
    }

    public static String getMD5Str(String str) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(str.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //16是表示转换为16进制数
        String md5Str = new BigInteger(1, digest).toString(16);
        return md5Str;
    }

//


    private List<AssetApiParams> getTrans(String service) {
        try {
            List<AssetApiParams> params = docService.findParams(service);
            return params;
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }


}
