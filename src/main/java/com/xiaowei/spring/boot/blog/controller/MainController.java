package com.xiaowei.spring.boot.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.xiaowei.spring.boot.blog.domain.Authority;
import com.xiaowei.spring.boot.blog.domain.User;
import com.xiaowei.spring.boot.blog.service.AuthorityService;
import com.xiaowei.spring.boot.blog.service.UserService;

@Controller
public class MainController {
	private static final Long ROLE_USER_AUTHORITY_ID = 2L;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthorityService authorityService;

	@GetMapping("/")
	public String root() {

		return "redirect:/index";
	}

	@GetMapping("/index")
	public String index() {

		return "redirect:/blogs";
	}

	/**
	 * 获取登录界面
	 * @return
	 */
	@GetMapping("/login")
	public String login() {

		return "login";
	}

	@GetMapping("/login-error")
	public String loginError(Model model) {

		model.addAttribute("loginError", true);
		model.addAttribute("errorMsg", "登录失败，用户名或密码错误！");
		return "login";
	}

	@GetMapping("/register")
	public String register() {

		return "register";
	}

	@PostMapping("/register")
	public String registerUser(User user) {
		List<Authority> authorites = new ArrayList<Authority>();
		authorites.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));

		user.setAuthorities(authorites);
		userService.registerUser(user);
		//return "register:/login";
		return "redirect:/login";
	}
	
	@GetMapping("/search")
	public String search() {
		return "search";
	}
}
