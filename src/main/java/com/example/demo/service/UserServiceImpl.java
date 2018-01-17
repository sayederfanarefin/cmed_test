package com.example.demo.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Prescription;
import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.PrescriptionRepository;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PrescriptionRepository prescriptionRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	public void savePrescription(Prescription prescription) {
		
		prescriptionRepository.save(prescription);
	}

	@Override
	public List<Prescription> findPrescriptionByUserId(int user_id) {
		
		return prescriptionRepository.findByUserid(user_id);
	}
	
	@Override
	public void deletePrescription(Prescription prescription) {
		
		//prescriptionRepository.save(prescription);
		prescriptionRepository.delete(prescription);
	}
	
	@Override
	public Prescription findByPrescriptionId(int id) {
		
		return prescriptionRepository.findById(id);
	}

}
