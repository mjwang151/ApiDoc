package com.amarsoft.exportexcel.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amarsoft.exportdoc.util.JSONTools;
import com.amarsoft.exportexcel.TransResultToExcel;
import com.amarsoft.exportexcel.template.TemplateFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QueryAndExport {

    @Autowired
    QueryHubservice queryHubservice;

    @Value("${config.path}")
    String path;

    /**
     *
     * @param params
     * @param tmp
     * @return 文件路径
     */
    public String doExecute(String env,String params,String tmp){
        JSONObject reqJo = JSONObject.parseObject(params);
        log.info("req param:{}",reqJo);
        String transcode = JSONTools.getString(reqJo,"transcode");
        String uri = "https://app.amarsoft.com/hubservicetest";
        if(env.equals("product")){
            uri = "http://www.amardata.com/data";
        }
        JSONArray jsonArray = queryHubservice.queryHub(reqJo,uri);
        log.info("response data:{}",jsonArray);
        if(jsonArray.size() > 0){
            new TransResultToExcel(TemplateFactory.getTemplate(tmp)).parseJSONToExcel(path+"/data/" +transcode+".xlsx",jsonArray, transcode);
            return path+"/data/" +transcode+".xlsx";
        }else{
            new TransResultToExcel(TemplateFactory.getTemplate(tmp)).parseJSONToExcel(path+"/data/" +transcode+".xlsx",new JSONArray(), transcode);
            return path+"/data/" +transcode+".xlsx";
        }
    }
}
