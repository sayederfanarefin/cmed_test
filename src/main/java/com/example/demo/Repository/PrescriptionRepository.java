package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Prescription;

@Repository("prescriptionRepository")
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
	Prescription findById(int id);
	Prescription findByMedicines(String Medicines);
	List<Prescription> findByUserid(int user_id);
	
}