package com.taotao.sso.controller;

import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HttpServletBean;

@Controller
@RequestMapping("/page")
public class PageController {
	
	@RequestMapping("/register")
	public String toRegister(){
		return "register";
	}
	
	@RequestMapping("/login")
	public String showLogin(String redirect, Model model){
		model.addAttribute("redirect", redirect);
		return "login";
	}
	
}
