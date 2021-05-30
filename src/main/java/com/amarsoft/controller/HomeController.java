package com.amarsoft.controller;

import com.amarsoft.bean.DocBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 59851
 */
@RestController
@Api(tags = "用户数据接口") //controller的描述
public class HomeController {

	@ApiOperation(value = "查询用户",notes = "根据id查询用户")
	//描述一个参数，可以配置参数的中文含义，也可以给参数设置默认值，required = true表示如果swagger测试为必填,defaultValue默认值
	@ApiImplicitParam(name= "id",value = "用户id",required = true,defaultValue = "66")
	@GetMapping("/user")
	public DocBean getUserById(Integer id){
		DocBean user = new DocBean();
		user.setId(id);
		return user;
	}

}






