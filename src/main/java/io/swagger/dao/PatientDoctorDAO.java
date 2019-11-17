package io.swagger.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import io.swagger.Utility;
import io.swagger.model.PatientDoctor;

public class PatientDoctorDAO implements DAO{
	
	Database db;
	
	public PatientDoctorDAO() {
		this(new DataBaseImpl());
	}
	
	public PatientDoctorDAO(Database dbObj) {
		this.db = dbObj;
	}
	

	public ArrayList<PatientDoctor> getPatientDoctorListData(Integer patientUserId) {
		ArrayList<PatientDoctor> responsePatientDoctor = new ArrayList<PatientDoctor>();
		try {
			Object[] queryParam = { patientUserId };
			ArrayList<HashMap<String, String>> resSet = db.runGetQuery(GetSqlQueries.SQLGETPatientDoctorData,
					queryParam);
			for (HashMap<String, String> res : resSet) {
				PatientDoctor tempPatientDoctor = new PatientDoctor();
				tempPatientDoctor.setId(Utility.convertObjectToInteger(res.get("id")));
				tempPatientDoctor.setVistDate(res.get("visitDate"));
				UserDAO userObj = new UserDAO();
				tempPatientDoctor.setDoctor(userObj.getUserListData(null,  Utility.convertObjectToLong(res.get("doctorUserId")), null, null).get(0));
				responsePatientDoctor.add(tempPatientDoctor);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responsePatientDoctor;
	}

}
