package io.swagger.dao;

public class GetSqlQueries {
	public static final String SQLLoginUser = "SELECT id as userId\r\n" + ", userName\r\n" + ", userRoleName\r\n"
			+ "FROM tblUser \r\n" + "JOIN cfgUserRole USING(userRole) \r\n" 
			+ "WHERE  userName = ? \r\n" + 
					"     AND BINARY password = ?\r\n";

	public static final String SQLLoginUserById = "SELECT id as userId\r\n" + ", userName\r\n" + ", userRoleName\r\n"
			+ "FROM tblUser \r\n" + "JOIN cfgUserRole USING(userRole) \r\n" + "WHERE id = ?\r\n";

	public static final String SQLGETUsers = "SELECT tu.id as userId\r\n" + ", tu.userName\r\n" + ", tu.email\r\n"
			+ ", tu.phoneNumber\r\n" + ", tud.firstName\r\n" + ", tud.lastName\r\n" + ", tud.bloodGroup\r\n"
			+ ", tud.dateOfBirth\r\n" + ", tud.height\r\n" + ", tud.gender\r\n" + ", cu.userRoleName\r\n" + "  FROM\r\n"
			+ "tblUser tu\r\n" + "JOIN\r\n" + "cfgUserRole cu USING(userRole)\r\n" + "JOIN\r\n"
			+ "tblUserDetails tud ON(tu.id=tud.userId) \r\n"
			+ "WHERE concat(tud.firstName, ' ', tud.lastName)  like ? AND isActive=1\r\n"
			+ "AND tu.id != ?\r\n";

	public static final String SQLGETUser = "SELECT tu.id as userId\r\n" + ", tu.userName\r\n" + ", tu.email\r\n"
			+ ", tu.phoneNumber\r\n" + ", tud.firstName\r\n" + ", tud.lastName\r\n" + ", tud.bloodGroup\r\n"
			+ ", tud.dateOfBirth\r\n" + ", tud.height\r\n" + ", tud.gender\r\n" + ", cu.userRoleName\r\n" + "  FROM\r\n"
			+ "tblUser tu\r\n" + "JOIN\r\n" + "cfgUserRole cu USING(userRole)\r\n" + "JOIN\r\n"
			+ "tblUserDetails tud ON(tu.id=tud.userId) \r\n" + "WHERE tu.id = ?  AND isActive=1\r\n";

	public static final String SQLGETPatientDoctorData = "SELECT tpv.id,\r\n" + "tpv.visitDate,\r\n"
			+ "tdpr.doctorUserId\r\n"
			+ "FROM tblDoctorPatientRelationship tdpr\r\n"
			+ "JOIN tblPatientVisit tpv ON (tpv.doctorPatientRelationshipId=tdpr.id)\r\n"
			+ "WHERE tdpr.patientUserId = ?\r\n" + "order by tpv.id";
	
	public static final String SQLGETEDDData = ""
			+ "SELECT id,\r\n" + 
			"       creationTime,\r\n" +
			"       lastChangedBy,\r\n" +
			"       gestationalType,\r\n" + 
			"       gestationalInputData,\r\n" + 
			"       edd,\r\n" + 
			"       refId,\r\n"+
			"		isActive,\r\n" +
			"		if(edd<SUBDATE(now(), INTERVAL 365 DAY), 1,0) as isEnable\r\n"+
			"FROM tblUserEdd\r\n" + 
			"WHERE userId = ?\r\n"+ 
//			"AND isActive = ?\r\n"+ 
			"ORDER BY creationTime desc";
}
