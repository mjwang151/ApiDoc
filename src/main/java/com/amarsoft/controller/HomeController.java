package com.amarsoft.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amarsoft.bean.DocBean;
import com.amarsoft.bean.DownloadDocBean;
import com.amarsoft.exportdoc.ApiDocBuilder;
import com.amarsoft.exportdoc.ApiDocExportService;
import com.amarsoft.exportdoc.template.DocTemplate;
import com.amarsoft.exportpdf.PdfCore;
import freemarker.template.Template;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 59851
 */
@RestController
@Api(tags = "接口文档下载") //controller的描述
@Slf4j
public class HomeController {

	@Autowired
	ApiDocBuilder apiDocBuilder;
	@Autowired
	ApiDocExportService apiDocExportService;
	@Autowired
	@Qualifier("CommonTemplate")
	DocTemplate commonTemplate;
	@Autowired
	@Qualifier("IconTemplate")
	DocTemplate IconTemplate;



    @ApiOperation(value = "下载接口文档", notes = "aaa")
    @ApiImplicitParams({
			@ApiImplicitParam(name = "transcodes", value = "接口编号(用逗号隔开)", required = true, defaultValue = ""),
			@ApiImplicitParam(name = "company", value = "data/credit", required = false, defaultValue = ""),
			@ApiImplicitParam(name = "templateType", value = "模板类型", required = false, defaultValue = "edsservice-icon"),
			@ApiImplicitParam(name = "isNeedDemo", value = "是否需要demo", required = false)
	})
    @GetMapping("/download")
    public void downloadDoc(DownloadDocBean downloadDocBean,HttpServletResponse resp) {
		PrintWriter writer = null;
		String fileName = "";
		try {
			String transcode = downloadDocBean.getTranscodes();
			if(transcode.contains(",")){
				fileName = new SimpleDateFormat("yyyyMMdd").format(new Date()) +".doc";
			}else{
				fileName = transcode+".doc";
			}
			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("Content-disposition",
					"attachment;filename=" +   URLEncoder.encode(fileName, "UTF-8"));;
			resp.setContentType("application/x-download");
			writer = resp.getWriter();
			log.info("开始生成接口文档："+transcode);
			apiDocBuilder.setCom_type(downloadDocBean.getCompany());
			JSONObject tojson = apiDocBuilder.build(transcode).tojson();
			Template t = IconTemplate.getTemplate();
			t.process(tojson, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(writer != null) {
				writer.close();
			}
		}
    }

}






