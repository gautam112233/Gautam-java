package com.ashokit.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ashokit.Binding.DashboardResponse;
import com.ashokit.Binding.EnquiryForm;
import com.ashokit.Binding.EnquirySearchCriteria;
import com.ashokit.Services.EnquiryService;
import com.ashokit.entity.StudentEnqEntity;

@Controller
public class EnquiryController {
	@Autowired
	private HttpSession session;
	@Autowired
	private EnquiryService enqService;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		Integer userId = (Integer) session.getAttribute("userId");

		DashboardResponse dashboardData = enqService.getDashboardData(userId);
		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";

	}

	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {

		boolean status = enqService.saveEnquriry(formObj);
		if (status) {
			model.addAttribute("succMsg", "Enquiry added");
		} else {
			model.addAttribute("errMsg", "Problam occured");
		}

		return "add-enquiry";
	}

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {

		initForm(model);

		return "add-enquiry";

	}

	private void initForm(Model model) {

		// get courses from dropDown
		List<String> courses = enqService.getCourses();

		// get enq status from dropdown
		List<String> enqStatuses = enqService.getEnqStatuses();

		// create bainding class obj
		EnquiryForm formObj = new EnquiryForm();

		// set data in model obj

		model.addAttribute("coursesNames", courses);
		model.addAttribute("statusNames", enqStatuses);
		model.addAttribute("formObj", formObj);

	}

	@GetMapping("/enquiries")
	public String viewEnquiriesPage(EnquirySearchCriteria criteria, Model model) {

		initForm(model);
		// model.addAttribute("searchForm",new EnquirySearchCriteria());
		List<StudentEnqEntity> enquiries = enqService.getEnquiries();
		model.addAttribute("enquiries", enquiries);

		return "view-enquiries";

	}

	@GetMapping("/filter-enquiries")
	public String getFilteredEnqs(@RequestParam String cname, @RequestParam String status, @RequestParam String mode,
			Model model) {

		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourseName(cname);
		criteria.setClassMode(mode);
		criteria.setEnqStatus(status);

		Integer userId = (Integer) session.getAttribute("userId");
		List<StudentEnqEntity> filteredEnqs = enqService.getFilteredEnqs(criteria, userId);

		model.addAttribute("enquiries", filteredEnqs);
		return "filter-enquiries";
	}

	@GetMapping("/edit")
	public String EditEnq(@RequestParam("enqId") Integer enqId, Model model) {
		// System.out.println(id);
		initForm(model);
		StudentEnqEntity editEnquiry = enqService.editEnquiry(enqId);
		model.addAttribute("formObj", editEnquiry);
		return "add-enquiry";
	}

	@GetMapping("/delete")
	public String DeleteEnq(@RequestParam("enqId") Integer enqId, Model model) {
		// System.out.println(id);

		initForm(model);
		// model.addAttribute("searchForm",new EnquirySearchCriteria());
		List<StudentEnqEntity> enquiries = enqService.deleteEnquiry(enqId);
		model.addAttribute("enquiries", enquiries);

		return "view-enquiries";
	}

}
