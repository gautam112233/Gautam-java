package com.ashokit.Services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.Binding.LoginForm;
import com.ashokit.Binding.SignUpForm;
import com.ashokit.Binding.UnlockForm;
import com.ashokit.Utility.EmailUtils;
import com.ashokit.Utility.PwdUtils;
import com.ashokit.entity.UserDtlsEntity;
import com.ashokit.repository.UserRepo;

@Service
public class UserServiceImpl  implements UserService{
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private HttpSession session;

	@Override
	public String login(LoginForm form) {
	UserDtlsEntity entity = userRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());
		
  if(entity==null) {
	  return "invalid credentials";
  }
	if(entity.getAccStatus().equals("Locked")) {
		return "your account is locked"; 
	}
	
	//create session and store user data in session
	session.setAttribute("userId", entity.getUserId());
	
	return "success";
		
    }
	
	
	
	@Override
	public boolean signup(SignUpForm from) {
		
		UserDtlsEntity user = userRepo.findByEmail(from.getEmail());
		
				if(user!=null) {
			return false;
		}
		
		//copy data from danding obj to entity obj
		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(from, entity);
		
		
		//todo:generate randam pwd and set to object
		String tempPwd = PwdUtils.generateRandomPwd();
		entity.setPwd(tempPwd);
		
		
		//Todo account status as locked 
		entity.setAccStatus("Locked");
		
		
		//Todo insert the record
		
		userRepo.save(entity);
		
		//todo send email to unlock the account
		
		String to =from.getEmail();
		String subject="UNLOCK YOUR ACCOUNT";
		StringBuffer  body= new StringBuffer("");
		body.append("<h1>use Below temperary password to unlock your account </h>");
		body.append("Temperary pwd: "+tempPwd);
		body.append("<br>");
		body.append("<a href=\"http://localhost:8080/unlock?email="+to+"\">Click Here to unlock</a>");
		
		
		emailUtils.sendEmail(to, subject,body.toString());
		
		return true;
	}
	
	
	@Override
	public boolean forgotPwd(String email) {
		// TODO check record present in db with given mail
		UserDtlsEntity entity = userRepo.findByEmail(email);
	
		// TODO if record not present send error msg
		
			if(entity==null) {
			return false;
		}
		
		
		// TODO if record present send pwd in mail and send success msg 
			
			 String Subject="Recover Password";
			 String body="Your Password :"+entity.getPwd()	;
			
			emailUtils.sendEmail(email, Subject, body);
			
		return true;
	}
	
	
	
	
	@Override
	public boolean unlockAccount(UnlockForm form) {
		
		UserDtlsEntity entity = userRepo.findByEmail(form.getEmail());
		if(entity.getPwd().equals(form.getTempPwd())) {
			entity.setPwd(form.getNewPwd());
			entity.setAccStatus("UNLOCKED");
			userRepo.save(entity);
			return true;
		}else{
		return false;
		}
		
		
	}

}
