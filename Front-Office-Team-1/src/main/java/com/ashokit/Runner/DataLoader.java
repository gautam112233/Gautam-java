package com.ashokit.Runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ashokit.entity.CourseEntity;
import com.ashokit.entity.EnqStatusEntity;
import com.ashokit.repository.CourseRepo;
import com.ashokit.repository.EnquiryRepo;

@Component
public class DataLoader implements ApplicationRunner {
	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnquiryRepo enquiryRepo;
	
	
@Override
public void run(ApplicationArguments args) throws Exception {
	
	courseRepo.deleteAll();
	
	CourseEntity c1= new CourseEntity();
	c1.setCourseName("java");
	
	CourseEntity c2= new CourseEntity();
	c2.setCourseName("python");
	
	CourseEntity c3= new CourseEntity();
	c3.setCourseName("devops");
	
	courseRepo.saveAll(Arrays.asList(c1,c2,c3));
	
	
	enquiryRepo.deleteAll();
	
	EnqStatusEntity s1 = new EnqStatusEntity();
	s1.setStatusName("New");
	
	EnqStatusEntity s2 = new EnqStatusEntity();
	s2.setStatusName("Enrolled");
	
	EnqStatusEntity s3 = new EnqStatusEntity();
	s3.setStatusName("Lost");
	
	enquiryRepo.saveAll(Arrays.asList(s1,s2,s3));
	
	
	
	
	
	
	
	
}
}
