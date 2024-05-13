package com.fdmgroup.Genesis.controller;

import com.fdmgroup.Genesis.model.User;
import com.fdmgroup.Genesis.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/")
	public String goToIndexPage() {
		return "index";
	}

	@GetMapping("/register")
	public String toToRegisterPage() {
		return "register";
	}

	@PostMapping("/register")
	public String registerNewUser(User user, Model model) {

		if (userService.registerUser(user)) {
			return "redirect:/login";
		} else {
			model.addAttribute("registerError", "Sign Up Failed!  User already exists!");
			return "register";
		}
	}
	@GetMapping("/login")
	public String goToLoginPage(){
		return "login";
	}
	
	@PostMapping("/login")
	public String verifyUser(@RequestParam String username, @RequestParam String password, HttpSession session, Model model){
		
		if(username.equals("/admin") && password.equals("abc")) {
			return"/admin";
		}

		if (userService.verifyUserCredentials(username, password)) {
			session.setAttribute("activeUser", username);
			return "redirect:/home";
			
			
		} else {
			model.addAttribute("loginError", "Invalid Log In!  Please Try Again!");
			return "/login";
		}
	}

	@GetMapping("/home")
	public String goToHomePage(Model model, HttpSession session){
		
		String username = (String) session.getAttribute("activeUser");
		User user = userService.getUserByUsername(username);
		model.addAttribute("user", user);
		
		return "home";
	}
	
	@GetMapping("/logout")
	public String Logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	

	
}
