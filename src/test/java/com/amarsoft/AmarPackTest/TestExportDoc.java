package com.amarsoft.AmarPackTest;

import com.alibaba.fastjson.JSONObject;
import com.amarsoft.bean.AssetApi;
import com.amarsoft.exportdoc.ApiDocBuilder;
import com.amarsoft.exportdoc.ApiDocExportService;
import com.amarsoft.service.impl.DocServiceImpl;
import freemarker.template.Template;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @author mjwang
 * @version 1.0
 * @date 2021/4/2 21:49
 */
@SpringBootTest
public class TestExportDoc {
    @Autowired
    ApiDocBuilder apiDocBuilder;
    @Autowired
    ApiDocExportService apiDocExportService;
    @org.junit.jupiter.api.Test
    public void test(){
     OutputStreamWriter pw = null;//定义一个流
		try {
			String transcode = "R314,R1103";
			  //1:使用File类创建一个要操作的文件路径
	        File file = new File("E://"+transcode+".doc");
			Writer writer = new OutputStreamWriter(new FileOutputStream(file),"utf-8");
			JSONObject tojson = apiDocBuilder.build(transcode).tojson();
			System.out.println(tojson);
			Template t = apiDocExportService.getTemplate();
			t.process(tojson, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
