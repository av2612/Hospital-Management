package io.swagger.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import io.swagger.Utility;
import io.swagger.api.impl.AuthChecker;
import io.swagger.model.LoginResponse;
import io.swagger.model.User;

public class UserDAO {

	Database db;
	PatientDAO pdObj;

	public UserDAO() {
		this(new DataBaseImpl(), new PatientDAO());
	}

	public UserDAO(Database db, PatientDAO pd) {
		this.db = db;
		this.pdObj = pd;
	}

	public ArrayList<User> getUserListData(String name, Long userId, String userRoleType, Integer lastChangedBy) {
		ArrayList<User> responseSet = new ArrayList<User>();
		if (userId != null) {
			String userQuery = GetSqlQueries.SQLGETUser;
			Object[] queryParam = { userId };
			return getUserList(db, responseSet, userQuery, queryParam, userRoleType);
		} else {
			if (name == null)
				name = "";
			Object[] queryParam = { "%" + name + "%", lastChangedBy };
			String userQuery = GetSqlQueries.SQLGETUsers;
			return getUserList(db, responseSet, userQuery, queryParam, userRoleType);
		}
	}

	private ArrayList<User> getUserList(Database db, ArrayList<User> responseSet, String userQuery, Object[] queryParam,
			String userRoleType) {
		try {
			ArrayList<HashMap<String, String>> resSet = db.runGetQuery(userQuery, queryParam);
			for (HashMap<String, String> res : resSet) {
				User tempUser = new User();
				tempUser = this.setUser(tempUser, res, userRoleType);
				responseSet.add(tempUser);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseSet;
	}

	private User setUser(User temp, HashMap<String, String> res, String userRoleType) {
		temp.setUserId(Utility.convertObjectToInteger(res.get("id")));
		temp.setUserName(res.get("userName"));
		temp.setBloodGroup(res.get("bloodGroup"));
		temp.setDateOfBirth(res.get("dateOfBirth"));
		temp.setFirstName(res.get("firstName"));
		temp.setLastName(res.get("lastName"));
		temp.setGender(res.get("gender"));
		temp.setUserEmail(res.get("email"));
		temp.setPhoneNumber(res.get("phoneNumber"));
		temp.setWeight(Utility.convertObjectToBigDecimal(res.get("weight")));
		temp.setHeight(Utility.convertObjectToBigDecimal(res.get("height")));
		temp.setUserRole(res.get("userRoleName"));
		if (userRoleType != null && userRoleType.equals("PATIENT")) {
			temp.setPatientData(pdObj.getPatientData(Utility.convertObjectToInteger(res.get("id"))));
		}
		return temp;
	}

	public LoginResponse postUserData(User userEntity) throws SQLException {
		int userId = 0;
		if (userEntity.getUserId() == null && userEntity.getPassword() != null) {

			Object[] listOfParamUserValue = { -1, userEntity.getUserName(), userEntity.getUserEmail(),
					userEntity.getPassword(), Utility.getUserRole(userEntity.getUserRole(), true),
					userEntity.getPhoneNumber() };

			userId = db.runPostQuery(PostSqlQueries.SQLInsertUser, listOfParamUserValue);
			Object[] listOfParamUserDetailsValue = { userId, userId, userEntity.getFirstName(),
					userEntity.getLastName(), userEntity.getGender(), userEntity.getHeight(), userEntity.getWeight(),
					userEntity.getBloodGroup(), userEntity.getDateOfBirth() };
			db.runPostQuery(PostSqlQueries.SQLInsertUserDetails, listOfParamUserDetailsValue);

		}

		LoginResponseDAO lrObj = new LoginResponseDAO();
		LoginResponse responseObj = lrObj.getById(userId, userEntity.getPassword());
//	lrObj.getLoginResponseData(body)
		return responseObj;
	}

	public User putUserData(User userEntity, Boolean isActive, AuthChecker acObj) throws SQLException {
		if (userEntity.getUserId() != null) {
			String[] updateUserColumnName = { "lastChangedBy", "email", "password", "phoneNumber", "userRole", "isActive" };
			Object[] updateUserColumnValue = { acObj.getLastChangedBy(), userEntity.getUserEmail(), userEntity.getPassword(),
					userEntity.getPhoneNumber(), Utility.getUserRole(userEntity.getUserRole(), false), isActive ? 1 : 0,
					userEntity.getUserId() };
			Boolean isUpdated = db.runPutQuery(PostSqlQueries.SQLUpdateUser, updateUserColumnName,
					updateUserColumnValue) == 1 ? true : false;
			if (isUpdated) {
				this.updateUserDetails(userEntity, acObj);
				return this.getUserListData(null, (long) userEntity.getUserId(), "PATIENT", acObj.getLastChangedBy()).get(0);
			} else
				throw new IllegalArgumentException("No data to update");

		} else
			throw new IllegalArgumentException("User Id not proved to update");
	}

	public void updateUserDetails(User userEntity, AuthChecker acObj) throws SQLException {
		if (userEntity.getUserId() != null) {
			String[] updateUserColumnName = { "lastChangedBy", "firstName", "lastName", "gender", "height", "weight", "bloodGroup",
					"dateOfBirth" };
			Object[] updateUserColumnValue = { acObj.getLastChangedBy(), userEntity.getFirstName(), userEntity.getLastName(),
					userEntity.getGender(), userEntity.getHeight(), userEntity.getWeight(), userEntity.getBloodGroup(),
					userEntity.getDateOfBirth(), userEntity.getUserId() };
			Boolean isUpdated = db.runPutQuery(PostSqlQueries.SQLUpdateUserDetails, updateUserColumnName,
					updateUserColumnValue) == 1 ? true : false;
			if (!isUpdated) 
				throw new IllegalArgumentException("User details not updated");
		}
		else
			throw new IllegalArgumentException("User Id not found");

	}

}
