package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 实现页面跳转
 * @author xint1ao
 *
 */
@Controller
public class PageController {
	/**
	 * 访问/打开首页
	 */
	@RequestMapping("/")     
	public String showIndex(){
		return "index";   //返回逻辑视图
	}
	
	/**
	 * 展示其他页面
	 * @param page
	 * @return
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page){
		return page;
	}
}
