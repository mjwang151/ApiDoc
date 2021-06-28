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

	@Autowired
	@Qualifier("NoDemoCommonTemplate")
	DocTemplate nodemocommonTemplate;

    @org.junit.jupiter.api.Test
    public void test(){
     OutputStreamWriter pw = null;//定义一个流
		try {
			String transcode = "R1105,R11D05,R11C53,R1501,R227,R228,R230,R301V2,R701V3,R702V3,R703V3,R704V3,R705V3,R706V3,R707V3,R709,R801V2,R802V2,R803V2,R804V2,R805V2,R806V2,R807V2,R808V2,R1201,R1206V2,R1207,R217,R215,R203V2,R329V2,R326,R11D68,B301V2,B203,B215,B217,B801,B802,B803,B804,B805,B806,B807,B808,B701,B702,B703,B704,B705,B706,B707,B1201,B118,B1166,B11B66,B407,B408,J1301,J1302,J1303";
//			String transcode ="R1206V2";
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
