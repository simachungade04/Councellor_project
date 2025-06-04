package com.example.service;

import com.example.dto.DashboardResponse;
import com.example.entities.Counsellor;

public interface CounsellorService {

	
	public Counsellor findByEmail(String email);
	
	public boolean register(Counsellor counsellor);
	
	public Counsellor login (String email, String pwd);
	
	public DashboardResponse getDashboardInfo(Integer counsellorId);
	
}
