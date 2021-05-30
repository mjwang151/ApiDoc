package com.amarsoft.exportdoc;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 接口描述详情里的字段结构附表
 * @author kwqin
 * created on 2020年12月31日 上午9:14:24
 */
public class EnhanceApiTableDef {
	private String defName;//字段结构附表描述，形如：basicList企业照面信息
	private String name = "字段";
	private String desc = "中文名称";
	private String type = "类型";
	private List<EnhanceApiColumnDef> collist = new ArrayList<EnhanceApiColumnDef>();//字段列表
	
	public JSONObject tojson() {
		JSONObject jo = new JSONObject(true);
		jo.put("defname", defName);
		jo.put("name", name);
		jo.put("desc", desc);
		jo.put("type", type);
		JSONArray data = new JSONArray(collist.size());
		for(EnhanceApiColumnDef col : collist) {
			JSONObject jo1 = new JSONObject(true);
			jo1.put("id", col.getId());
			jo1.put("name", col.getName());
			jo1.put("desc", col.getDesc());
			jo1.put("type", col.getType());
			jo1.put("need", col.getNeed());
			jo1.put("comment", col.getComment());
			
			data.add(jo1);
		}
		jo.put("data", data);
		return jo;
	}
	
	public String getDefName() {
		return defName;
	}
	public void setDefName(String defName) {
		this.defName = defName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<EnhanceApiColumnDef> getCollist() {
		return collist;
	}
	public void setCollist(List<EnhanceApiColumnDef> collist) {
		this.collist = collist;
	}
	
}
