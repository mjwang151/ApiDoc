package com.amarsoft.AmarPackTest;

import com.alibaba.fastjson.JSONObject;
import com.amarsoft.bean.AssetApi;
import com.amarsoft.exportdoc.ApiDocBuilder;
import com.amarsoft.exportdoc.ApiDocExportService;
import com.amarsoft.exportdoc.template.DocTemplate;
import com.amarsoft.exportpdf.PdfCore;
import com.amarsoft.service.impl.DocServiceImpl;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mjwang
 * @version 1.0
 * @date 2021/4/2 21:49
 */
@SpringBootTest
@Slf4j
public class TestExportDoc {
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

    @org.junit.jupiter.api.Test
    public void test(){
     OutputStreamWriter pw = null;//定义一个流
		try {
			String transcode = "R1104";

			//1:使用File类创建一个要操作的文件路径
			File file = null;
			if(transcode.contains(",")){
				file = new File("F://ApiDoc"+ new SimpleDateFormat("yyyyMMdd").format(new Date()) +".doc");
			}else{
				file = new File("F://"+transcode+".doc");
			}
			Writer writer = new OutputStreamWriter(new FileOutputStream(file),"utf-8");
			apiDocBuilder.setCom_type("data");
			JSONObject tojson = apiDocBuilder.build(transcode).tojson();
			Template t = IconTemplate.getTemplate();
			t.process(tojson, writer);
			if(transcode.contains(",")){
				PdfCore.parse(file.getAbsolutePath(),"F://ApiDoc"+ new SimpleDateFormat("yyyyMMdd").format(new Date()) +".pdf");
			}else{
				PdfCore.parse(file.getAbsolutePath(),"F://"+transcode+".pdf");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
