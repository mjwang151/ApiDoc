package com.amarsoft.exportdoc;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * 主接口定义：包含接口名、输入、输出及示例
 * @author kwqin
 * created on 2020年12月31日 上午9:33:52
 */
@Getter
@Setter
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
	

	
}
