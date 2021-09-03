package com.amarsoft.controller;

import com.alibaba.fastjson.JSONObject;
import com.amarsoft.exportdoc.util.JSONTools;
import com.amarsoft.exportexcel.request.QueryAndExport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@RestController
@Api(tags = "接口数据下载")
@Slf4j
public class ApiController {


    @Autowired
    QueryAndExport queryAndExport;

    /**
     * http://localhost:8080/amarpack/swagger-ui.html#/
     * http://localhost:8080/amarpack/reqApi
      {
      "env": "test",
      "tmp": "default",
      "params": {
      "transcode": "R11C53",
      "userid": "11",
      "orgid": "EDS",
      "account": "EDS",
      "source": "EDS",
      "params": {
      "Name": "莱芜泰丰食品有限公司2",
      "nametype": "1"
      }
      }
      }
     */
    @ApiOperation(value = "接口数据下载", notes = "调接口自动生成解析的excel")
    @ApiImplicitParams({
                    @ApiImplicitParam(name = "输入样例", value = "输入样例" ,paramType ="query", required = false, example = "{\n" +
                    "      \"env\": \"test\",\n" +
                    "      \"tmp\": \"default\",\n" +
                    "      \"params\": {\n" +
                    "      \"transcode\": \"R11C53\",\n" +
                    "      \"userid\": \"11\",\n" +
                    "      \"orgid\": \"EDS\",\n" +
                    "      \"account\": \"EDS\",\n" +
                    "      \"source\": \"EDS\",\n" +
                    "      \"params\": {\n" +
                    "      \"Name\": \"莱芜泰丰食品有限公司2\",\n" +
                    "      \"nametype\": \"1\"\n" +
                    "      }\n" +
                    "      }\n" +
                    "      }" ,defaultValue = "{\n" +
                    "      \"env\": \"test\",\n" +
                    "      \"tmp\": \"default\",\n" +
                    "      \"params\": {\n" +
                    "      \"transcode\": \"R11C53\",\n" +
                    "      \"userid\": \"11\",\n" +
                    "      \"orgid\": \"EDS\",\n" +
                    "      \"account\": \"EDS\",\n" +
                    "      \"source\": \"EDS\",\n" +
                    "      \"params\": {\n" +
                    "      \"Name\": \"莱芜泰丰食品有限公司2\",\n" +
                    "      \"nametype\": \"1\"\n" +
                    "      }\n" +
                    "      }\n" +
                    "      }"),
            @ApiImplicitParam(name = "params", value = "输入的请求参数" ,paramType ="body", required = true)

    })
    @RequestMapping(value = "/reqApi", method = RequestMethod.POST)
    public void reqApi(@RequestBody String params, HttpServletResponse response, HttpServletRequest request) {
        String fileName = "";
        try {
            JSONObject paramsJo = JSONObject.parseObject(params);

            String env = JSONTools.getString(paramsJo, "env");
            String params2 = JSONTools.getString(paramsJo, "params");
            String tmp = JSONTools.getString(paramsJo, "tmp");

            fileName = queryAndExport.doExecute(env, params2, tmp);
            System.out.println("download --> " + fileName);
            //要下载的图片地址
            String path = request.getServletContext().getRealPath("/upload");

            //1、设置response 响应头
            response.reset(); //设置页面不缓存,清空buffer
            response.setCharacterEncoding("UTF-8"); //字符编码
            response.setContentType("multipart/form-data"); //二进制传输数据
            //设置响应头
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));

            File file = new File(fileName);
            //2、 读取文件--输入流
            InputStream input = new FileInputStream(file);
            //3、 写出文件--输出流
            OutputStream out = response.getOutputStream();

            //4、执行 写出操作
            byte[] buff = new byte[1024];
            int index = 0;
            while ((index = input.read(buff)) != -1) {
                out.write(buff, 0, index);
                out.flush();
            }
            // 关闭流
            out.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}






