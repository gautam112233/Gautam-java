package com.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.UserDtlsEntity;

public interface UserRepo extends JpaRepository<UserDtlsEntity, Integer> {

	public UserDtlsEntity findByEmail(String email);
	
	public UserDtlsEntity findByEmailAndPwd(String email,String pwd );
}
