package com.example.demo.service;



import com.example.demo.Model.User;

import java.util.List;

import com.example.demo.Model.Prescription;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
	public void savePrescription(Prescription prescription);
	public List<Prescription> findPrescriptionByUserId(int user_id);
	public void deletePrescription(Prescription prescription);
	
	public Prescription findByPrescriptionId(int id);
}