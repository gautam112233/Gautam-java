package com.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.EnqStatusEntity;

public interface EnquiryRepo extends JpaRepository<EnqStatusEntity, Integer> {

}
