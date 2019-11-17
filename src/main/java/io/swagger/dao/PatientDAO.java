package io.swagger.dao;

import io.swagger.model.Patient;

public class PatientDAO implements DAO{
	
	Database db;
	
	public PatientDAO() {
		this(new DataBaseImpl());
	}
	
	public PatientDAO(Database dbObj) {
		this.db = dbObj;
	}
	
	public Patient getPatientData(Integer userId) {
		Patient patientObj = new Patient();
		PatientDoctorDAO pdObj = new PatientDoctorDAO();
		EDDDAO eddObj = new EDDDAO();
		patientObj.setPatientVisit(pdObj.getPatientDoctorListData(userId));
		patientObj.setEddList(eddObj.getEddListData(userId));
		return patientObj;

	}
}
