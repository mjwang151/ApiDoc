package com.amarsoft.exportdoc;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 接口描述详情里的字段结构表
 * @author kwqin
 * created on 2020年12月31日 上午9:14:24
 */
public class ApiTableDef {
	private String id = "序号";
	private String name = "字段名";
	private String desc = "注释";
	private String type = "类型及范围";
	private String need = "必选";
	private String comment = "说明";
	private List<ApiColumnDef> collist = new ArrayList<ApiColumnDef>();//字段列表
	
	public JSONObject tojson() {
		JSONObject jo = new JSONObject(true);
		jo.put("id", id);
		jo.put("name", name);
		jo.put("desc", desc);
		jo.put("type", type);
		jo.put("need", need);
		jo.put("comment", comment);
		JSONArray data = new JSONArray(collist.size());
		for(ApiColumnDef col : collist) {
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
	public List<ApiColumnDef> getCollist() {
		return collist;
	}
	public void setCollist(List<ApiColumnDef> collist) {
		this.collist = collist;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNeed() {
		return need;
	}
	public void setNeed(String need) {
		this.need = need;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
