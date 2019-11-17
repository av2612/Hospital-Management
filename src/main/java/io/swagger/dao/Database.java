package io.swagger.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface Database {
	
	public ArrayList<HashMap<String, String>> runGetQuery(String mQuery, Object[] listOfParamsValue)throws SQLException;
	public int runPostQuery(String mQuery, Object[] listOfParamsValue) throws SQLException;
	public int runPutQuery(String mQuery, String[] keyList, Object[] listOfParamsValue) throws SQLException;
}
