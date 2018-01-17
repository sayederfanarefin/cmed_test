package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Model.User;
import com.example.demo.service.UserService;
import com.example.demo.Model.Prescription;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");
			
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		modelAndView.setViewName("admin/home");
		

		List<Prescription> p = userService.findPrescriptionByUserId(user.getId());

		modelAndView.addObject("results", p);
		return modelAndView;
	}
	
	
	@RequestMapping(value="/admin/createPres", method = RequestMethod.GET)
	public ModelAndView createPres(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		User user = userService.findUserByEmail(auth.getName());
//		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		//modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/createPres");
		
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/createPres", method = RequestMethod.POST)
	public ModelAndView createPres(@Valid Prescription prescription, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("/admin/createPres");
		} else {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findUserByEmail(auth.getName());
			
			prescription.setUserid(user.getId());
			userService.savePrescription(prescription);
			modelAndView.addObject("successMessage", "Prescription has been added successfully");
			modelAndView.addObject("prescription", new Prescription());
			modelAndView.setViewName("/admin/createPres");
			
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/deletePres", method = RequestMethod.GET)
	public String deletePrescription(@RequestParam(name="prescription_id")int prescription_id) {
		
		Prescription pp = userService.findByPrescriptionId(prescription_id);
		userService.deletePrescription(pp);
	    return "redirect:/admin/home";
	}
	
	
	@RequestMapping(value = "/admin/editPres", method = RequestMethod.GET)
	public ModelAndView editPres(@RequestParam(name="prescription_id")int prescription_id) {
		Prescription pp = userService.findByPrescriptionId(prescription_id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/editPres");
		modelAndView.addObject("editing_pres", pp);
		
	    return modelAndView;
	}
	

	@RequestMapping(value = "/admin/editPres", method = RequestMethod.POST)
	public String editPres(@Valid Prescription prescription, BindingResult bindingResult) {
		//ModelAndView modelAndView = new ModelAndView();
		
		if (bindingResult.hasErrors()) {
			//modelAndView.setViewName("Edit Prescription");
		} else {
			
			System.out.println(prescription.getPatientName());
			System.out.println(prescription.getPatientAge());
			

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findUserByEmail(auth.getName());
			
			prescription.setUserid(user.getId());
			
			userService.savePrescription(prescription);
			//modelAndView.addObject("successMessage", "Updated successfully");
			
			
		}
		 return "redirect:/admin/home";
	}
	
}
