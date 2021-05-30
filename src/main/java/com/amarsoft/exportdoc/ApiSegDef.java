package com.amarsoft.exportdoc;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 接口输入输出段定义
 * @author kwqin
 * created on 2020年12月31日 上午10:18:38
 */
public class ApiSegDef {
	private String title ;//输入参数/输出参数
	private ApiTableDef table;
	private String demo;
	private List<EnhanceApiTableDef> enhanceTable = new ArrayList<EnhanceApiTableDef>();
	
	public JSONObject tojson() {
		JSONObject jo = new JSONObject(true);
		jo.put("title", title);
		jo.put("table", table.tojson());
		jo.put("hastable", !table.getCollist().isEmpty());
		JSONArray data2 = new JSONArray();
		if(enhanceTable!=null && !enhanceTable.isEmpty()) {
			for(EnhanceApiTableDef table2 : enhanceTable) {
				data2.add(table2.tojson());
			}
		}
		jo.put("table2", data2);
		jo.put("hastable2", !enhanceTable.isEmpty());
		jo.put("demo", demo);
		return jo;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ApiTableDef getTable() {
		return table;
	}
	public void setTable(ApiTableDef table) {
		this.table = table;
	}
	public String getDemo() {
		return demo;
	}
	public void setDemo(String demo) {
		this.demo = demo;
	}
	public List<EnhanceApiTableDef> getEnhanceTable() {
		return enhanceTable;
	}
	public void setEnhanceTable(List<EnhanceApiTableDef> enhanceTable) {
		this.enhanceTable = enhanceTable;
	}
	
}
