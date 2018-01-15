package com.example.demo.Model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "prescription")
public class Prescription {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "prescription_id")
	private int id;
	
	@Column(name = "user_id")
	private int userid;
	
	@Column(name = "patientName")
	@NotEmpty(message = "*Please provide your name")
	private String patientName;
	
	
	@Column(name = "patientAge")
	private int patientAge;
	
//	private int xyz;
//	
//	public Prescription() {
//		xyz = user_id;
//		
//	}
	
	public int getUserid() {
		return userid;
	}

	public void setUserid(int user_id) {
		this.userid = user_id;
	}

	public String getNextVisit() {
		return nextVisit;
	}

	public void setNextVisit(String nextVisit) {
		this.nextVisit = nextVisit;
	}

	@Column(name = "prescriptionDate")
	@NotEmpty(message = "*Please provide prescription date")
	private String prescriptionDate;
	
	@Column(name = "patientGender")
	@NotEmpty(message = "*Please provide your gender")
	private String patientGender;
	
	
	@Column(name = "diagnosis")
	private String diagnosis;
	
	@Column(name = "medicines")
	private String medicines;
	
	@Column(name = "nextVisit")
	//@NotEmpty(message = "*Please provide prescription date")
	private String nextVisit;
	
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "user_prescription", joinColumns = @JoinColumn(name = "prescription_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
//	private Set<Role> roles;
//
//	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public int getPatientAge() {
		return patientAge;
	}

	public void setPatientAge(int patientAge) {
		this.patientAge = patientAge;
	}

	public String getPrescriptionDate() {
		return prescriptionDate;
	}

	public void setPrescriptionDate(String prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
	}

	public String getPatientGender() {
		return patientGender;
	}

	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getMedicines() {
		return medicines;
	}

	public void setMedicines(String medicines) {
		this.medicines = medicines;
	}

	

}
