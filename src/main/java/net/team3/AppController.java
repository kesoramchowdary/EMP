package net.team3;

import java.io.IOException;

import java.util.List;

import java.util.Optional;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.team3.admin.User;
import net.team3.admin.UserRepository;
import net.team3.employee.Employee;
import net.team3.employee.EmployeeDetails;
import net.team3.employee.EmployeeRepository;


@Controller
public class AppController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private EmployeeRepository empRepo;
	@Autowired
   
	
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup_form";
	}
	
	@GetMapping("/admin/registernewemp")
	public String showEmpRegistrationForm(Model model) {
		model.addAttribute("employee", new Employee());
		
		return "admin/emp_signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		userRepo.save(user);
		
		return "admin/register_success";
	}
	
	@PostMapping("/admin/new_emp_register")
	public String newempRegister(Employee employee) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(employee.getPassword());
		employee.setPassword(encodedPassword);
		Long num = (long) 5;
		
	
		empRepo.save(employee);
		
		return "admin/register_success";}
	
	@GetMapping("/admin/users")
	public String listUsers(Model model) {
		List<Employee> listUsers = empRepo.findAll();
		model.addAttribute("listUsers", listUsers);
	
		
		return "admin/admin_home";
	}
	
	@GetMapping("/admin/login")
    public String viewAdminLoginPage() {
        return "admin/admin_login";
    }
     
    @GetMapping("/employee/login")
    public String viewUserLoginPage() {
        return "employee/employee_login";
    }
     
    @GetMapping("/employee/home")
    public String viewUserHomePage() {
        return "employee/employee_home";
    }
    
    @GetMapping("/deleteEmployee")
	public String deleteEmployee(@RequestParam Long Id) {
		empRepo.deleteById(Id);
		return "redirect:/admin/users";
	}
    
   
    @GetMapping("/employee/personaldetails")
    public String viewPersonalDetails() {
        return "employee/personal_details";
    }
    
    @GetMapping("/employee/editPersonalDetails")
    public String editPersonalDetails() {
        return "employee/edit_personal_details";
    }
    
    @PostMapping("/employee/update_personal_details")
    @PreAuthorize("hasRole('READ_PRIVILEGE')")
	public String personalDetailsUpdate(@RequestParam("address") String address, @RequestParam("phone") String phone, Model model) {
    	Employee employee = empRepo.findByEmail(
    	        SecurityContextHolder.getContext().getAuthentication().getName());
		employee.setAddress(address);
		employee.setPhone(phone);
		empRepo.save(employee);
		return "employee/personal_details";}
    
    
    @GetMapping("/admin/docUpload")
	public String docUpload() {
		
		return "admin/document_upload";
	}
	
	
	 
    
    
}

