package com.yao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页Controller
 */
@Controller
public class IndexController {

	@RequestMapping("/")
	public String root(){
		System.out.println("进入了这个方法......");
		return "redirect:/login.html";
	}
}
