package com.ashokit.Services;

import java.util.List;

import com.ashokit.Binding.DashboardResponse;
import com.ashokit.Binding.EnquiryForm;
import com.ashokit.Binding.EnquirySearchCriteria;
import com.ashokit.entity.StudentEnqEntity;

public interface EnquiryService {
	
	public DashboardResponse getDashboardData (Integer userId);

	
	public List<String>getCourses();
	
	public List<String>getEnqStatuses();
	
	public boolean saveEnquriry(EnquiryForm form);
	
	public List<StudentEnqEntity> getEnquiries();
	
	public List<StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria criteria ,Integer userId);
	
	public StudentEnqEntity editEnquiry(Integer stdId);
	
	public List<StudentEnqEntity> deleteEnquiry(Integer stdId);
}	