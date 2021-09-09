package com.amarsoft.AmarPackTest;

import com.alibaba.fastjson.JSONArray;
import com.amarsoft.exportexcel.TransResultToExcel;
import com.amarsoft.exportexcel.template.TemplateFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author mjwang
 * @version 1.0
 * @date 2021/4/2 21:49
 */
@SpringBootTest
@Slf4j
public class TestExportExcel {

	public static JSONArray read(String path,String fileName){
		JSONArray ja = new JSONArray();
		try {
			File file = null;
			InputStream fi = null;
			BufferedReader r = null;
			file = new File(path,fileName);
			fi = new FileInputStream(file);
			try {
				r = new BufferedReader(new InputStreamReader(fi,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				r = new BufferedReader(new InputStreamReader(fi));
			}
			String nodeData = null;
			while((nodeData = r.readLine()) !=null){
				JSONArray jaa = JSONArray.parseArray(nodeData);
				ja.addAll(jaa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ja;
	}
	public static LinkedHashMap<String,JSONArray> readR11D68(String path,String fileName){
		LinkedHashMap<String,JSONArray> map = new LinkedHashMap<>();
		try {
			File file = null;
			InputStream fi = null;
			BufferedReader r = null;
			file = new File(path,fileName);
			fi = new FileInputStream(file);
			try {
				r = new BufferedReader(new InputStreamReader(fi,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				r = new BufferedReader(new InputStreamReader(fi));
			}
			String nodeData = null;

			while((nodeData = r.readLine()) !=null){
				JSONArray jaa = JSONArray.parseArray(nodeData);
				map.put(jaa.getJSONObject(0).getString("name"),jaa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public static LinkedHashMap<String,JSONArray> readR11C53(String path,String fileName){
		LinkedHashMap<String,JSONArray> map = new LinkedHashMap<>();
		try {
			File file = null;
			InputStream fi = null;
			BufferedReader r = null;
			file = new File(path,fileName);
			fi = new FileInputStream(file);
			try {
				r = new BufferedReader(new InputStreamReader(fi,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				r = new BufferedReader(new InputStreamReader(fi));
			}
			String nodeData = null;

			while((nodeData = r.readLine()) !=null){
				JSONArray jaa = JSONArray.parseArray(nodeData);
				map.put(jaa.getJSONObject(0).getJSONArray("basicList").getJSONObject(0).getString("enterpriseName"),jaa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
    @Test
    public void test(){
    	String transcode = "R11D68";
		JSONArray read = read("H:/", "R11D68.txt");
		new TransResultToExcel(TemplateFactory.getTemplate("tmp")).parseJSONToExcel("H:/data/" +transcode+".xlsx",read, transcode);

	}


	@Test
	public void testR11D68(){
		String transcode = "R11D68";
		LinkedHashMap<String, JSONArray> stringJSONArrayMap = readR11D68("H:/", "R11D68.txt");
		new TransResultToExcel(TemplateFactory.getTemplate("tmp")).batchParseJSONToExcel("H:/data/" +transcode+".xlsx",stringJSONArrayMap, transcode);
	}
	@Test
	public void testR11C53(){
		String transcode = "R11C53";
		LinkedHashMap<String, JSONArray> stringJSONArrayMap = readR11C53("H:/", "R11C53.txt");
		new TransResultToExcel(TemplateFactory.getTemplate("tmp")).batchParseJSONToExcel("H:/data/" +transcode+".xlsx",stringJSONArrayMap, transcode);
	}




}
