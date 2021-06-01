package com.amarsoft.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amarsoft.bean.DownloadDocBean;
import com.amarsoft.exportdoc.ApiDocBuilder;
import com.amarsoft.exportdoc.ApiDocExportService;
import com.amarsoft.exportdoc.template.DocTemplate;
import freemarker.template.Template;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 59851
 */
@RestController
@Api(tags = "数据下载") //controller的描述
@Slf4j
public class DataController {


	@RequestMapping("query")
	public JSONObject query(Model model){
		log.info("查询了query1");

		JSONObject ret = new JSONObject();
		ret.put("code",0);
		ret.put("msg","查询成功");

		JSONObject jo = new JSONObject();
		jo.put("id","12");
		jo.put("username","121231");
		JSONArray ja = new JSONArray();
		ja.add(jo);
		ja.add(jo);
		ret.put("count",ja.size());
		ret.put("data",ja);
		return ret;
	}


	@RequestMapping("query2")
	public JSONObject query2(Model model){
		log.info("查询了query2");
		JSONObject ret = new JSONObject();
		ret.put("code",0);
		ret.put("msg","查询成功");

		JSONObject jo = new JSONObject();
		jo.put("id","12aa");
		jo.put("username","121231asffaas阿斯顿");
		JSONArray ja = new JSONArray();
		ja.add(jo);
		ja.add(jo);
		ret.put("count",ja.size());
		ret.put("data",ja);
		return ret;
	}
}






