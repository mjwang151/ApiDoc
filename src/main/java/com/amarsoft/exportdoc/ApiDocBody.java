package com.amarsoft.exportdoc;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

/**
 * 接口文档：接口列表
 * @author kwqin
 * created on 2020年12月31日 上午9:32:21
 */
public class ApiDocBody {
	private List<ApiDef> apilist = new ArrayList<ApiDef>();
	
	public JSONArray tojson() {
		JSONArray ja = new JSONArray(apilist.size());
		for(ApiDef api : apilist) {
			ja.add(api.tojson());
		}
		return ja;
	}

	public List<ApiDef> getApilist() {
		return apilist;
	}

	public void setApilist(List<ApiDef> apilist) {
		this.apilist = apilist;
	}
	
}
