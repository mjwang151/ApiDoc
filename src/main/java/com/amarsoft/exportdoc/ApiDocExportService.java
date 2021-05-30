package com.amarsoft.exportdoc;

import com.alibaba.fastjson.JSONObject;
import com.amarsoft.exportdoc.template.DocTemplate;
import com.amarsoft.utils.ApplicationContextUtils;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 接口文档导出通用service
 * @author kwqin
 * created on 2020年12月31日 下午4:42:20
 */
@Component
@Slf4j
public class ApiDocExportService implements ExportFileService{
	@Autowired
	@Qualifier("CommonTemplate")
	DocTemplate commonTemplate;

	@Autowired
	ApiDocBuilder apiDocBuilder;
	
	@Override
	public void handle(Writer out, JSONObject jsonParams) throws Exception {
		JSONObject tojson = apiDocBuilder.build(jsonParams.getString("apis")).tojson();
		log.info("接口json为："+tojson.toJSONString());
		Template t = commonTemplate.getTemplate();
		t.process(tojson, out); 
	}

	@Override
	public String getFileName(JSONObject jsonParams) {
		String api = jsonParams.getString("apis");
		if(api.contains(",")) {
			return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".doc";
		}else {
			return api+".doc";
		}
	}

}
