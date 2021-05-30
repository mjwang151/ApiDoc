package com.amarsoft.exportdoc;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class ApiDoc {
	private String createdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	public String serviceurl = "https://app.amarsoft.com/hubservicetest/api/gateway";

	public String company_type;
	private String demoinput = "{\"transcode\":\"P1301\",\"source\":\"***\",\"userid\":\"***\",\"orgid\":\"***\",\"account\":\"***\",\"params\":{\"name\":\"中国银行\"}}";
	private String demooutput = "{\"msg\":\"数据响应正确!\",\"code\":\"0000\",\"data\":[{……],\"pageIndex\":1,\"totalPage\":2,\"pageSize\":20,\"totalCount\":33}";
	private String[][] datas = {
			{"userid","查询人用户ID","True","String","全行统一的用户编号，每个接口必要参数。接口定义时，不再列举。"},
			{"orgid","查询人所属机构","True","String","全行统一的机构代码，每个接口必要参数。接口定义时，不再列举。"},
			{"account","查询账号","True","String","从安硕大数据申请的查询账号，每个接口必要参数。接口定义时，不再列举。"},
			{"source","查询来源系统","True","String","查询发起系统编码，自行定义，每个接口必要参数。接口定义时，不再列举。"},
			{"transcode","接口编号","True","String","接口编号"},
			{"params","封装业务参数","True","JSONOBject","调用transcode的业务参数，每个接口的业务参数在具体的接口文档中定义"}
	};
	
	private String[][] datas2 = {
			{"code","响应代码","True","VARCHAR(4)","响应代码：0000-正常响应1000-请求参数不合法2000-拟返回数据太多，请使用分页查询(实际无数据返回)3000-账户无当前接口查询权限4000-请求IP没有在白名单中9999-内部错误"},
			{"msg","响应详情","True","VARCHAR(200)","code码对应的描述信息"},
			{"data","业务数据","True","JSONArray","JSONArray格式数据，每个元素的格式参考每个接口定义的输出参数。"},
			{"totalCount","总记录数","False","int","接口调用查询到的总记录数，当接口为分页接口是返回该字段"},
			{"totalPage","总页数","False","int","接口调用查询到的总页数，当接口为分页接口是返回该字段"},
			{"pageSize","每页返回记录数","False","int","接口调用每页返回记录数，当接口为分页接口是返回该字段"},
			{"pageIndex","当前请求页","False","int","接口调用当前页数，当接口为分页接口是返回该字段"}
	};	
	


	private ApiDocBody body = new ApiDocBody();
	
	public JSONObject tojson() {
		String proServiceurl_data = "://www.amardata.com/data/api/gateway";
		String proServiceurl_credit = "://www.amardata.com/credit/api/gateway";
		JSONObject jo = new JSONObject(true);
		jo.put("createdate", createdate);
		jo.put("serviceurl", serviceurl);
		JSONArray prourls = new JSONArray();
		if(StringUtils.isNotBlank(company_type)){
			switch (company_type){
				case "credit":
					prourls.add("http"+proServiceurl_credit);
					prourls.add("https"+proServiceurl_credit);
					break;
				case "data":
					prourls.add("http"+proServiceurl_data);
					prourls.add("https"+proServiceurl_data);
					break;
			}
		}else{
			prourls.add("http"+proServiceurl_credit);
			prourls.add("http"+proServiceurl_data);
			prourls.add("https"+proServiceurl_credit);
			prourls.add("https"+proServiceurl_data);
		}
		jo.put("proserviceurl", prourls);
		jo.put("demoinput", demoinput);
		jo.put("demooutput", demooutput);
		JSONArray commoninput = new JSONArray();
		JSONArray commonoutput = new JSONArray();

		int index = 0;
		for(String[] dataArr : datas) {
			index++;
			JSONObject jo1 = new JSONObject(true);
			jo1.put("id", index);
			jo1.put("name", dataArr[0]);
			jo1.put("desc", dataArr[1]);
			jo1.put("type", dataArr[3]);
			jo1.put("need", dataArr[2]);
			jo1.put("comment", dataArr[4]);
			commoninput.add(jo1);
		}
		
		index = 0;
		for(String[] dataArr : datas2) {
			index++;
			JSONObject jo1 = new JSONObject(true);
			jo1.put("id", index);
			jo1.put("name", dataArr[0]);
			jo1.put("desc", dataArr[1]);
			jo1.put("type", dataArr[3]);
			jo1.put("need", dataArr[2]);
			jo1.put("comment", dataArr[4]);
			commonoutput.add(jo1);
		}
		jo.put("commoninput", commoninput);
		jo.put("commonoutput", commonoutput);

		JSONArray ja = new JSONArray(body.getApilist().size());
		for(ApiDef apidef : body.getApilist()) {
			ja.add(apidef.tojson());
		}
		jo.put("apis", ja);
		return jo;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getServiceurl() {
		return serviceurl;
	}

	public void setServiceurl(String serviceurl) {
		this.serviceurl = serviceurl;
	}

	public String getDemoinput() {
		return demoinput;
	}

	public void setDemoinput(String demoinput) {
		this.demoinput = demoinput;
	}

	public String getDemooutput() {
		return demooutput;
	}

	public void setDemooutput(String demooutput) {
		this.demooutput = demooutput;
	}

	public String[][] getDatas() {
		return datas;
	}

	public void setDatas(String[][] datas) {
		this.datas = datas;
	}

	public ApiDocBody getBody() {
		return body;
	}

	public void setBody(ApiDocBody body) {
		this.body = body;
	}
	
}
