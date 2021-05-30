package com.amarsoft.exportdoc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amarsoft.exportdoc.inner.InnerMessageUtil;
import com.amarsoft.exportdoc.util.JSONTools;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
@Component
@Setter
@Getter
public class ApiDocBuilder {
	@Autowired
	InnerMessageUtil innerMessageUtil;

	String com_type;


	public ApiDocBuilder(String com_type) {
		this.com_type = com_type;
	}
	public ApiDocBuilder() {
	}
	public  ApiDoc build(String intfs) throws Exception {
		ApiDoc doc = new ApiDoc();
		doc.setCompany_type(com_type);
		String serviceUrl = doc.getServiceurl();
		doc.setServiceurl(serviceUrl);
		AtomicInteger index = new AtomicInteger(1);
		List<String> list = new ArrayList<String>();
		for(String intf : intfs.split(",")) {
			if(intf != null && !intf.equals("undefined")) {
				list.add(intf);
			}
		}
//		list = list.stream().sorted((b1,b2)->b1.compareTo(b2)).collect(Collectors.toList());
		for (int i = 0; i < list.size(); i++) {
			doc.getBody().getApilist().add(buildApiDef(list.get(i), index));
		}
		return doc;
	}

	/**
	 * 构建一个接口
	 * @param intf
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public  ApiDef buildApiDef(String intf, AtomicInteger index) throws Exception {
		ApiDef api = new ApiDef();
		JSONObject demoMessage = innerMessageUtil.demoMessage(intf).getJSONObject("data");
		api.setId("2."+index.getAndIncrement()+".");
		api.setName(demoMessage.getString("servicename"));
		api.setNo(demoMessage.getString("serviceno"));
		ApiSegDef input = new ApiSegDef();
		input.setTitle("输入参数");
		input.setDemo(demoMessage.getString("demoinput") == null?"":demoMessage.getString("demoinput"));
		JSONArray inputArr = demoMessage.getJSONArray("input");
		buildApiInput(input, inputArr);
		api.setInput(input);

		ApiSegDef output = new ApiSegDef();
		output.setTitle("输出参数");
		output.setDemo(demoMessage.getString("demooutput") == null?"":demoMessage.getString("demooutput"));
		JSONArray outputArr = demoMessage.getJSONArray("output");

		Map<String,List<JSONObject>> outputArr2 = (Map<String,List<JSONObject>>) demoMessage.get("outputmap");
		buildApiOutput(output, outputArr,outputArr2);
		api.setOutput(output);
		return api;
	}

	/**
	 * 输入
	 * @param input
	 * @param inputArr
	 */
	public static void buildApiInput(ApiSegDef input, JSONArray inputArr) {
		ApiTableDef inputdef = new ApiTableDef();
		for(int i=0;i<inputArr.size();i++) {
			JSONObject inputjo = inputArr.getJSONObject(i);
			ApiColumnDef column = new ApiColumnDef();
			column.setId(String.valueOf(i+1));
			column.setName(inputjo.getString("intf"));
			column.setDesc(inputjo.getString("intfname"));
			column.setType(inputjo.getString("intftype"));
			column.setNeed(inputjo.getString("needtype"));
			column.setComment(inputjo.getString("remark"));
			inputdef.getCollist().add(column);
		}
		input.setTable(inputdef);
	}

	/**TODO 暂时使用测试案例
	 * 输入

	 */
	public static void buildApiOutput(ApiSegDef output, JSONArray outputArr, Map<String,List<JSONObject>> outputArr2) {
		String[][] out1 = new String[outputArr.size()][4];
		Map<String, EnhanceApiTableDef> map = new LinkedHashMap<String, EnhanceApiTableDef>();
		for (int i = 0; i < outputArr.size(); i++) {
			JSONObject jo1 = outputArr.getJSONObject(i);
			out1[i][0] = JSONTools.getString(jo1, "intf","");
			out1[i][1] = JSONTools.getString(jo1, "intfname","");
			out1[i][2] = JSONTools.getString(jo1, "needtype","");
			out1[i][3] = JSONTools.getString(jo1, "intftype","");
		}
		int index = 0;
		ApiTableDef outputdef = new ApiTableDef();
		for(String[] dataArr : out1) {
			index++;
			ApiColumnDef column = new ApiColumnDef();
			column.setId(String.valueOf(index));
			column.setName(dataArr[0]);
			column.setDesc(dataArr[1]);
			column.setType(dataArr[3]);
			column.setNeed(dataArr[2]);
			column.setComment(dataArr[1]);
			outputdef.getCollist().add(column);
		}
		output.setTable(outputdef);
		for (Entry<String,List<JSONObject>> entry : outputArr2.entrySet()) {
			String key = entry.getKey();
			if(StringUtils.isEmpty(key)) continue;
			List<JSONObject> valuelist = entry.getValue();
			EnhanceApiTableDef endef = new EnhanceApiTableDef();
			endef.setDefName(key);
			for (int k = 0; k < valuelist.size(); k++) {
				EnhanceApiColumnDef column = new EnhanceApiColumnDef();
				JSONObject jo1 = valuelist.get(k);
				column.setId(JSONTools.getString(jo1, "orderno",""));
				column.setName(JSONTools.getString(jo1, "intf",""));
				column.setDesc(JSONTools.getString(jo1, "intfname",""));
				column.setNeed(JSONTools.getString(jo1, "needtype",""));
				column.setType(JSONTools.getString(jo1, "intftype",""));
				column.setComment(JSONTools.getString(jo1, "remark",""));

				endef.getCollist().add(column);
			}
			map.put(key, endef);
		}
		for(String key : map.keySet()) {
			output.getEnhanceTable().add(map.get(key));
		}
	}



}
