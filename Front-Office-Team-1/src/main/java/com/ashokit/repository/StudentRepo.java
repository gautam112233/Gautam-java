package com.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.StudentEnqEntity;

public interface StudentRepo extends JpaRepository<StudentEnqEntity, Integer> {

	
	//@Query(value = "SELECT enq_id FROM AIT_STUDENT_ENQURIES ");
}
