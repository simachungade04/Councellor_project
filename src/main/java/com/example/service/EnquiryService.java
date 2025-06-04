package com.example.service;

import java.util.List;

import com.example.dto.ViewEnqsFilterRequest;
import com.example.entities.Enquiry;

public interface EnquiryService {
       public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception;
       
       public List<Enquiry> getAllEnquiries(Integer counsellorId);
       
       public List<Enquiry> getEnquiriesWithFilter(ViewEnqsFilterRequest filterReq, Integer counsellorId);
       
       public Enquiry getEnquriyById(Integer enqId);
}
