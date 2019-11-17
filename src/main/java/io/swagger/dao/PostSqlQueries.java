package io.swagger.dao;

public class PostSqlQueries {
	public static String SQLInsertUser = "" + "INSERT INTO tblUser"
			+ "(lastChangedBy, userName, email, password, userRole, phoneNumber ) \r\n" + "VALUES(?, ?, ?, ?, ?, ?)";

	public static String SQLInsertUserDetails = "" + "INSERT INTO tblUserDetails"
			+ "(lastChangedBy, userId, firstName, lastName, gender, height, weight, bloodGroup, dateOfBirth ) \r\n"
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static String SQLUpdateUser = "" + 
	"UPDATE tblUser \r\n" + "SET %s\r\n" 
			+ "WHERE id = ?";
	
	public static String SQLUpdateUserDetails = "" + 
	"UPDATE tblUserDetails \r\n" + "SET %s\r\n" 
			+ "WHERE userId = ?";
	
	public static String SQLInsertEDD = ""
			+ "INSERT INTO tbluseredd (lastChangedBy, userId, gestationalType, gestationalInputData, edd, refId)\r\n" + 
			"VALUES (?, ?, ?, ?, ?, ?)";
	

	public static String SQLUpdateUserEDD = "" + 
	"UPDATE tblUserEdd \r\n" + "SET %s\r\n" 
			+ "WHERE id = ?";
	
	public static String SQLUpdateUserRefEDD = "" + 
	"UPDATE tblUserEdd \r\n" + "SET %s\r\n" 
			+ "WHERE refId = ?";
}
