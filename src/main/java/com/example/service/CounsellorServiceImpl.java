package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.dto.DashboardResponse;
import com.example.entities.Counsellor;
import com.example.entities.Enquiry;
import com.example.repos.CounsellorRepo;
import com.example.repos.EnquiryRepo;

@Service
public class CounsellorServiceImpl implements CounsellorService{
	
	private CounsellorRepo counsellorRepo;
	private EnquiryRepo enqRepo;
	
	public CounsellorServiceImpl(CounsellorRepo counsellorRepo, EnquiryRepo enqRepo) {
		
		this.counsellorRepo = counsellorRepo;
		this.enqRepo = enqRepo;
		
	}

	@Override
	public Counsellor findByEmail(String email) {
	
		return counsellorRepo.findByEmail(email);
	}

	@Override
	public boolean register(Counsellor counsellor) {
		Counsellor savedCounsellor = counsellorRepo.save(counsellor);
		if(null != savedCounsellor.getCounsellorId()) {
			return true;
		}
		return false;
	}

	@Override
	public Counsellor login(String email, String pwd) {
		
		return counsellorRepo.findByEmailAndPwd(email, pwd);
	}

	@Override
	public DashboardResponse getDashboardInfo(Integer counsellorId) {
		DashboardResponse response = new DashboardResponse();
		
		List<Enquiry> enqList = enqRepo.getEnquiriesByCounsellorId(counsellorId);
		int totalEnq = enqList.size();
		
		int enrolledEnqs = enqList.stream()
				.filter(e -> e.getEnqStatus().equals("Enrolled"))
				.collect(Collectors.toList())
				.size();
		
		int lostEnqs = enqList.stream()
				.filter(e -> e.getEnqStatus().equals("Lost"))
				.collect(Collectors.toList())
				.size();
		
		
		int openEnqs = enqList.stream()
				.filter(e -> e.getEnqStatus().equals("Open"))
				.collect(Collectors.toList())
				.size();
		
		response.setTotalEnqs(totalEnq);
		response.setEnrolledEnqs(enrolledEnqs);
		response.setLostEnqs(lostEnqs);
		response.setOpenEnqs(openEnqs);
		
		return response;
	}
	
	

}
