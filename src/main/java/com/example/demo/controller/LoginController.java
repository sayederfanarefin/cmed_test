package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		//modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		//modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		
		//System.out.println("HOME Paise");
		List<Prescription> p = userService.findPrescriptionByUserId(user.getId());
//		if(p==null) {
//			System.out.println("HOME Paise but p null");
//		}else {
//			System.out.println("HOME Paise  p o paise ");
//			System.out.println(p.get(0).getPatientName());
//		}
		modelAndView.addObject("results", p);
		return modelAndView;
	}
	
	
	@RequestMapping(value="/admin/createPres", method = RequestMethod.GET)
	public ModelAndView createPres(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		//modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/createPres");
		
		System.out.println("createPres Paise");
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
			//System.out.println("createPres Paise -- PoST" + String.valueOf(user.getId()));
		}
		return modelAndView;
	}
	

}
