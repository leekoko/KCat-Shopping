package com.taotao.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param,@PathVariable Integer type, String callBack){
		
		TaotaoResult result = null;
		
		//参数有效性验证
		if(StringUtils.isBlank(param)){
			result = TaotaoResult.build(400, "校验内容不能为空");
		}
		if(type == null){
			result = TaotaoResult.build(400, "校验内容类型不能为空");
		}
		if(type != 1 && type != 2 && type != 3){
			result = TaotaoResult.build(400, "校验内容类型错误");
		}
		//校验出错
		if(null != result){
			if(null != callBack){
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callBack);
				return mappingJacksonValue;
			}else{
				return result;
			}
		}
		//调用服务
		try {
			result = userService.checkData(param, type);
		} catch (Exception e) {
			result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		//校验出错
		if(null != callBack){
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callBack);
			return mappingJacksonValue;
		}else{
			return result;
		}
	}
	
	//创建用户
	@RequestMapping(value="/register",method=RequestMethod.POST)    //仅支持post，不添加都支持
	@ResponseBody
	public TaotaoResult createUser(TbUser user){
		try {
			TaotaoResult result = userService.createUser(user);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	//用户登陆
	@RequestMapping(value="/login",method=RequestMethod.POST)    //仅支持post，不添加都支持
	@ResponseBody
	public TaotaoResult userLogin(String username, String password){
		try {
			TaotaoResult result = userService.userLogin(username, password);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	@RequestMapping(value="/token/{token}")    //支持get请求
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback){  //获取url的值
		TaotaoResult result = null;
		try {
			result = userService.getUserByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		if(StringUtils.isEmpty(callback)){  //非json调用
			return result;    //直接返回对象
		}else{
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;   //返回callback对象
		}

	}
	
	@RequestMapping("showLogin")
	public String showLogin(){
		return "/login";
	}
	
}
