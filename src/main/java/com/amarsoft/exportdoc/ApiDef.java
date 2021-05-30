package com.amarsoft.exportdoc;

import com.alibaba.fastjson.JSONObject;

/**
 * 主接口定义：包含接口名、输入、输出及示例
 * @author kwqin
 * created on 2020年12月31日 上午9:33:52
 */
public class ApiDef {
	private String id;
	private String name;
	private String no;
	private ApiSegDef input;
	private ApiSegDef output;
	
	public JSONObject tojson() {
		JSONObject jo = new JSONObject(true);
		jo.put("id", id);
		jo.put("name", name+"（"+no+"）");
		jo.put("input", input.tojson());
		jo.put("output", output.tojson());
		return jo;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public ApiSegDef getInput() {
		return input;
	}
	public void setInput(ApiSegDef input) {
		this.input = input;
	}
	public ApiSegDef getOutput() {
		return output;
	}
	public void setOutput(ApiSegDef output) {
		this.output = output;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
