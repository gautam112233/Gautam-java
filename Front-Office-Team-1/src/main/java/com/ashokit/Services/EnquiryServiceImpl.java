package com.ashokit.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.Binding.DashboardResponse;
import com.ashokit.Binding.EnquiryForm;
import com.ashokit.Binding.EnquirySearchCriteria;
import com.ashokit.entity.CourseEntity;
import com.ashokit.entity.EnqStatusEntity;
import com.ashokit.entity.StudentEnqEntity;
import com.ashokit.entity.UserDtlsEntity;
import com.ashokit.repository.CourseRepo;
import com.ashokit.repository.EnquiryRepo;
import com.ashokit.repository.StudentRepo;
import com.ashokit.repository.UserRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnquiryRepo enquiryRepo;
	@Autowired
	private StudentRepo studentRepo;
	@Autowired
	private HttpSession session;
	
	@Override
	public DashboardResponse getDashboardData(Integer userId) {
		
		DashboardResponse response = new DashboardResponse();
		
		Optional<UserDtlsEntity> findById = userRepo.findById(userId);
		
		if(findById.isPresent()) {
			UserDtlsEntity userEntity = findById.get();
			List<StudentEnqEntity> enquiries = userEntity.getEnquiries();
			int totalCnt = enquiries.size();
			
				Integer enrolledCnt = enquiries.stream()
					.filter(e ->e.getEnqStatus().equals("Enrolled"))
			.collect(Collectors.toList()).size(); 
			
				Integer lostCnt = enquiries.stream()
						.filter(e ->e.getEnqStatus().equals("Lost"))
				.collect(Collectors.toList()).size(); 
				
				
				response.setTotalEnquriesCnt(totalCnt);
				response.setEnrolledCtn(enrolledCnt);
				response.setLostCtn(lostCnt);
				
				
				
				
		}
		
		return response;
	}
	
	@Override
	public List<String> getCourses() {

		List<CourseEntity> findAll = courseRepo.findAll();
		List<String>names =new ArrayList<>();
		
		for(CourseEntity entity :findAll) {
			names.add(entity.getCourseName());
			
		}
		
		return names;
	}
	
	@Override
	public List<String> getEnqStatuses() {
		
		List<EnqStatusEntity> findAll = enquiryRepo.findAll();
		List <String>statusList=new ArrayList<>();
		
		for(EnqStatusEntity entity :findAll) {
		
			statusList.add(entity.getStatusName());
		}
		
		return statusList;
	}

	@Override
	public boolean saveEnquriry(EnquiryForm form) {
		
		StudentEnqEntity enqEntity =new StudentEnqEntity();
		BeanUtils.copyProperties(form, enqEntity);
		
		Integer userId =(Integer)session.getAttribute("userId");
		   
		UserDtlsEntity userEntity = userRepo.findById(userId).get();
		enqEntity.setUser(userEntity);
		
		studentRepo.save(enqEntity);
		
		
		return true;
	}
	
	
	@Override
	public List<StudentEnqEntity> getEnquiries() {

		Integer userId =(Integer) session.getAttribute("userId");
		Optional<UserDtlsEntity> findById = userRepo.findById(userId);
		
		if(findById.isPresent()) {
			
			UserDtlsEntity userDtlsEntity = findById.get();
			
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			
			return enquiries;
		}
		
		
		return null;
	}
	
	
	
	@Override
	public List<StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria criteria, Integer userId) {

		Optional<UserDtlsEntity> findById = userRepo.findById(userId);
		
		if(findById.isPresent()) {
			
			UserDtlsEntity userDtlsEntity = findById.get();
			
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			
			//filter logic
			
			if(null!=criteria.getCourseName() & !"".equals(criteria.getCourseName())) {
				
				enquiries= enquiries.stream()
				.filter(e -> e.getCourseName().equals(criteria.getCourseName()))
				.collect(Collectors.toList());
			}
			if(null!=criteria.getEnqStatus() & !"".equals(criteria.getEnqStatus())) {
				
				enquiries=	enquiries.stream()
				.filter(e ->e.getEnqStatus().equals(criteria.getEnqStatus()))
				.collect(Collectors.toList());
			}
			
			if(null!=criteria.getClassMode() & !"".equals(criteria.getClassMode())) {
				
				enquiries=	enquiries.stream()
				.filter(e ->e.getClassMode().equals(criteria.getClassMode()))
				.collect(Collectors.toList());
			}
				
			
			return enquiries;
		}
		return null;
	}
	
	
	@Override
	public StudentEnqEntity editEnquiry(Integer stdId) {
		Optional<StudentEnqEntity> findById = studentRepo.findById(stdId);
		
		if(findById.isPresent()) {
			StudentEnqEntity studentEntity=findById.get();
			return studentEntity;
		}
		
		return null;
	}
	
	
	@Override
	public List<StudentEnqEntity> deleteEnquiry(Integer enqId) {
		
		
		
		/*
		 * boolean existsById = studentRepo.existsById(enqId); if(existsById) {
		 * Optional<StudentEnqEntity> findById = studentRepo.findById(enqId);
		 * 
		 * studentRepo.delete(findById.get()); }
		 */
		
		studentRepo.deleteById(enqId);
			
			Integer userId =(Integer) session.getAttribute("userId");
			Optional<UserDtlsEntity> findById = userRepo.findById(userId);
			
			if(findById.isPresent()) {
				
				UserDtlsEntity userDtlsEntity = findById.get();
				
				List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
				
				return enquiries;
			}
			return null;
		
	
	}
	
	
	
	
	
	
	
	
	
	
}
