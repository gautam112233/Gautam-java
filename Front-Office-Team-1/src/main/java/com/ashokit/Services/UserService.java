package com.ashokit.Services;

import com.ashokit.Binding.LoginForm;
import com.ashokit.Binding.SignUpForm;
import com.ashokit.Binding.UnlockForm;

public interface UserService 	{

	
	public String  login(LoginForm form);
	
	public boolean signup(SignUpForm from);
	
	public boolean unlockAccount(UnlockForm form);
	
	public boolean forgotPwd(String email);
}
