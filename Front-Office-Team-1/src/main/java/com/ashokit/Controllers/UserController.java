package com.ashokit.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ashokit.Binding.LoginForm;
import com.ashokit.Binding.SignUpForm;
import com.ashokit.Binding.UnlockForm;
import com.ashokit.Services.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/signup")
	public String signUpPage(Model model) {
		model.addAttribute("user",new SignUpForm());	
		return"signup";
	}
	
	
	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form ,Model model) {
		
		boolean status=userService.signup(form);
				
				if(status){
					model.addAttribute("succMsg","Check your gmail");
				}else {
					
					model.addAttribute("errMsg"," Chooses unique Email :Problam Occured");
					
				}
		return "signup";
	}
	
	@GetMapping("/unlock")
	public String unlockPage(@RequestParam  String email, Model model) {
		
		UnlockForm	unlockFormObj = new UnlockForm();
		unlockFormObj.setEmail(email);
		
		model.addAttribute("unlock",unlockFormObj);
		
		return"unlock";
	}
	
	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute ("unlock") UnlockForm unlock ,Model model) {
		
		System.out.println(unlock);
		
		if(unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			
			boolean status= userService.unlockAccount(unlock);
			
			if(status) {
				model.addAttribute("succMsg","your account unlocked");
			}else {
				model.addAttribute("errMsg","Given temp password  is incorrect  Check mail");
			}
			
			
			
			
		} else {
		model.addAttribute("errMsg","New pwd and cnfrm pwd not same");
		}
		return "unlock";
	}
	
	
	@GetMapping("/login")
	public String loginPage( Model model) {
		model.addAttribute("loginForm",new LoginForm());
		 
		return"login";
	}
	
	
	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm loginform, Model model) {
		 
		String status = userService.login(loginform);
		
		if(status.contains("success")) {
			
			return "redirect:/dashboard";
		}
		model.addAttribute("errMsg",status);
		
		return"login";
	}
	
	
	@GetMapping("/forgot")
	public String forgetPwdPage() {
		return"forgotPwd";
	}

	
	
	@PostMapping("/forgotPwd")
	public String forgetPwd(@RequestParam("email") String email,Model model) {
		boolean status = userService.forgotPwd(email);
		
		if(status) {
			//send succ msg
			model.addAttribute("succMsg","pwd sent to your gmail");
			
		}else {
			//send error msg
			model.addAttribute("errMsg", "invalid email");
			
		}
		
		return"forgotPwd";
	}

	
	
}
