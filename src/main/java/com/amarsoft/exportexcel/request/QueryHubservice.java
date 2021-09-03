package com.amarsoft.exportexcel.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.amarsoft.exportdoc.util.JSONTools;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class QueryHubservice {




    /**
     * 访问hubservice
     * @param uri 接口地址
     * @param requestJson 请求参数
     * @return
     */
    public static JSONObject queryHubService(String uri, JSONObject requestJson) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Object value = null;
        try {
            HttpPost httpPost = new HttpPost (uri+"/api/gateway");
            httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(new StringEntity(requestJson.toString(), Charset.forName("UTF-8")));
            httpPost.setConfig(RequestConfig.custom()
                    .setConnectionRequestTimeout(20000)
                    .setConnectTimeout(20000)
                    .setSocketTimeout(20000)
                    .build());
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String resp = null;
            if (entity != null) {
                resp = EntityUtils.toString(entity,"UTF-8");
            }
            response.close();
            value = JSON.parse(resp, Feature.OrderedField);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (JSONObject) value;
    }

    /**
     *
     * @param requestJson
     * @return
     */
    public JSONArray queryHub(JSONObject requestJson,String uri){
        JSONObject jsonObject = queryHubService(uri, requestJson);
        if(isSuccess(jsonObject)){
            return jsonObject.getJSONArray("data");
        }
        return new JSONArray();
    }

    public boolean isSuccess(JSONObject result) {
        String code = JSONTools.getString(result,"code","");
        String data = JSONTools.getString(result,"data","");
        if(code.equals("0000") && StringUtils.isNoneBlank(data)){
            return true;
        }
        return false;
    }

}
