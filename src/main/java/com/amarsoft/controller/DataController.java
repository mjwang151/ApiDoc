package com.amarsoft.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amarsoft.config.DataBaseConfig;
import com.amarsoft.service.impl.DataBaseConfigImpl;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 59851
 */
@RestController
@Api(tags = "数据下载") //controller的描述
@Slf4j
public class DataController {
    @Autowired
    DataBaseConfigImpl dataBaseConfigImpl;

    @RequestMapping("query")
    public JSONObject query(Model model, @RequestParam String reqparam) {
        log.info("查询结构" + reqparam);
        JSONObject ret = new JSONObject();
        ret.put("code", 0);
        ret.put("msg", "查询成功");
        ret.put("data", dataBaseConfigImpl.queryField(JSONObject.parseObject(reqparam)));
        return ret;
    }

    @RequestMapping("queryData")
    public JSONObject queryData(Model model, @RequestParam String reqparam, HttpServletRequest request) {
        JSONObject reqJo = JSONObject.parseObject(reqparam);
        if (StringUtils.isNoneBlank(request.getParameter("page"))) {
            reqJo.put("page", Integer.parseInt(request.getParameter("page")));
        }
        if (StringUtils.isNoneBlank(request.getParameter("limit"))) {
            reqJo.put("limit", Integer.parseInt(request.getParameter("limit")));
        }
        if (StringUtils.isNoneBlank(request.getParameter("key[id]"))) {
            reqJo.put("key", request.getParameter("key[id]"));
        }

        log.info("查询数据" + reqJo);

        JSONObject jo = dataBaseConfigImpl.queryData(reqJo);
        return jo;
    }


    @RequestMapping("getDataSource")
    public JSONObject getDataSource(Model model) {
        JSONObject ret = new JSONObject();
        ret.put("status", true);
        ret.put("msg", "查询成功");
        JSONArray ja = new JSONArray();
        int i = 100;
        for (Map.Entry<String, DruidDataSource> entry : DataBaseConfig.dataSourceMap.entrySet()) {
            JSONObject dataJo = new JSONObject();
            dataJo.put("id", i);
            dataJo.put("title", entry.getKey());
            ja.add(dataJo);
            i++;
        }
        ret.put("data", ja);
        return ret;
    }
}






