package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.dto.DashboardResponse;
import com.example.entities.Counsellor;
import com.example.service.CounsellorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

	
	private CounsellorService counsellorService;
	
	public CounsellorController(CounsellorService counsellorService) {
		this.counsellorService = counsellorService;
	}
	
	@GetMapping("/")
	public String index(Model model) {
		Counsellor cobj = new Counsellor();
		model.addAttribute("counsellor", cobj);
		
		return "index";
	}
	
	@PostMapping("/login")
	public String handleLoginBtn(Counsellor counsellor, HttpServletRequest request, Model model) {
		Counsellor c = counsellorService.login(counsellor.getEmail(), counsellor.getPwd());
		
		if(c == null) {
			model.addAttribute("emsg", "Invalid Credentials");
			return "index";
		}else {
			HttpSession session = request.getSession(true);
			session.setAttribute("counsellorId", c.getCounsellorId());
			
			DashboardResponse dbresobj = counsellorService.getDashboardInfo(c.getCounsellorId());
			
			model.addAttribute("dashboardInfo", dbresobj);
			
			return "dashboard";
		}
	}
	@GetMapping("/dashboard")
	public String displayDashboard(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		DashboardResponse dbresobj = counsellorService.getDashboardInfo(counsellorId);
		model.addAttribute("dashboardInfo", dbresobj);
		
		return "dashboard";
		
	}
	@GetMapping("/register")
	public String registerPage(Model model) {
		Counsellor cobj = new Counsellor();
		
		model.addAttribute("counsellor", cobj);
		return "register";
	}
	
	@PostMapping("/register")
	public String handleRegistration(Counsellor counsellor, Model model) {
		Counsellor byEmail = counsellorService.findByEmail(counsellor.getEmail());
		if(byEmail !=null) {
			model.addAttribute("emsg", "Duplicate Email");
			return "register";
		}
		boolean isRegistered = counsellorService.register(counsellor);
		if(isRegistered) {
			model.addAttribute("smsg", "Registration Success..!!");
		} else {
			model.addAttribute("emsg", "Registration Failed");
		}
		return "register";
		
	}
	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		session.invalidate();
		return "redirect:/";
		
	} 
	
}
