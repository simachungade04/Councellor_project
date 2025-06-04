package com.example.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.ViewEnqsFilterRequest;
import com.example.entities.Enquiry;
import com.example.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	
	private EnquiryService enqService;
	public EnquiryController(EnquiryService enqService) {
		
		this.enqService = enqService;
	}
	@PostMapping("/filter-enqs")
	public String filterEnquries(ViewEnqsFilterRequest viewEnqsFilterRequest, HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		
		List<Enquiry> enqsList = enqService.getEnquiriesWithFilter(viewEnqsFilterRequest, counsellorId);
		model.addAttribute("enquiries", enqsList);
		return "viewEnqPage";
	}

	@GetMapping("/view-enquiries")
	public String getEnquries(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		List<Enquiry> enqList = enqService.getAllEnquiries(counsellorId);
		
		model.addAttribute("enquiries", enqList);
		ViewEnqsFilterRequest filterReq = new ViewEnqsFilterRequest();
		model.addAttribute("viewEnqsFilterRequest", filterReq);
		
		return "viewEnqPage";
		
	}
	
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		Enquiry enquiry = new Enquiry();
		model.addAttribute("enquiry", enquiry);
		return "enquiryForm";
		
	}
	
	@GetMapping("/editEnq")
	public String editEnquriy(@RequestParam("enqId") Integer enqId, Model model) {
		
		Enquiry enquiry = enqService.getEnquriyById(enqId);
		model.addAttribute("enquiry", enquiry);
		return "enquiryForm";
	}
	
	@PostMapping("/addEnq")
	public String handleAddEnquiry(Enquiry enquiry, HttpServletRequest req, Model model) throws Exception{
		
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		boolean isSaved = enqService.addEnquiry(enquiry, counsellorId);
		
		if(isSaved) {
			model.addAttribute("smsg", "Enquiry Added");
		}else {
			model.addAttribute("emsg", "Failed To Add Enquiry");
		}
		enquiry = new Enquiry();
		model.addAttribute("enquiry", enquiry);
		return "enquiryForm";
		
		
	}
}
