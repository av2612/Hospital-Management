package io.swagger.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import io.swagger.Utility;
import io.swagger.api.impl.AuthChecker;
import io.swagger.model.Edd;
import io.swagger.model.EddList;

public class EDDDAO implements DAO {
	Database db;

	public EDDDAO() {
		this(new DataBaseImpl());
	}

	public EDDDAO(Database dbObj) {
		this.db = dbObj;
	}

	public EddList getEddListData(Integer userId) {
		EddList eddListObj = new EddList();
		Edd edd = new Edd();
		UserDAO udaoObj = new UserDAO();
		try {
			Object[] queryParam = { userId };
			ArrayList<HashMap<String, String>> resSet = db.runGetQuery(GetSqlQueries.SQLGETEDDData, queryParam);
			for(HashMap<String, String> res: resSet) {
				if(res.get("isActive").equals("1")) {
				edd.setId(Utility.convertObjectToInteger(res.get("id")));
				edd.setEdd(res.get("edd"));
				edd.setRefId(Utility.convertObjectToInteger(res.get("refId")));
				edd.setGestationalType(res.get("gestationalType"));
				JSONObject gestationalInputData = new JSONObject(res.get("gestationalInputData"));
				Map<String, Object> eddData = gestationalInputData.toMap();
				edd.setGestationInputData(eddData);
				edd.setEnteredBy(udaoObj
						.getUserListData(null, Utility.convertObjectToLong(res.get("lastChangedBy")), null, null).get(0));
				edd.setEntryTime(res.get("creationTime"));
				eddListObj.setIsEnabled(res.get("isEnable").equals("1"));
				eddListObj.setLatestEDD(edd);
				break;
				}
			}
			eddListObj.setUserId((long) userId);
			for (HashMap<String, String> res : resSet) {
				HashMap<String, Object> eddListMap = new HashMap<String, Object>();
				JSONObject gestationalInputData = new JSONObject(res.get("gestationalInputData"));
				Map<String, Object> eddData = gestationalInputData.toMap();
				eddListMap.put("enteredBy", udaoObj
						.getUserListData(null, Utility.convertObjectToLong(res.get("lastChangedBy")), null, null).get(0));
				eddListMap.put("gestationalInputData", eddData);
				eddListMap.put("id", res.get("id"));
				eddListMap.put("gestationalType", res.get("gestationalType"));
				eddListMap.put("refId", res.get("refId"));
				eddListMap.put("entryTime", res.get("creationTime"));
				eddListMap.put("edd", res.get("edd"));
				eddListMap.put("isActive", res.get("isActive"));
				eddListObj.addEddItem(Utility.convertObjectToInteger(res.get("refId")), eddListMap);
//				if (res.get("gestationalType").equals("LMP")) {
//					eddListObj.addLMPItem
//				}
//				if (res.get("gestationalType").equals("IVF")) {
//					eddListObj.addIVFItem(Utility.convertObjectToInteger(res.get("refId")), eddListMap);
//				}
//				if (res.get("gestationalType").equals("Ultrasound")) {
//					eddListObj.addEddItem(Utility.convertObjectToInteger(res.get("refId")), eddListMap);
//				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return eddListObj;
	}

	public Edd postEddListData(Edd body, String edd, AuthChecker acObj) {
		try {
			JSONObject gestationInputData = new JSONObject(body.getGestationInputData());
			Object[] queryParam = { acObj.getLastChangedBy(), body.getUserId(), body.getGestationalType(),
					gestationInputData.toString(), edd, null };
			int eddId = db.runPostQuery(PostSqlQueries.SQLInsertEDD, queryParam);
			if (eddId > 0) {
				String[] keyList = { "refId" };
				Object[] listOfParamsValue = { body.getRefId()!=null? body.getRefId():eddId, eddId };
				db.runPutQuery(PostSqlQueries.SQLUpdateUserEDD, keyList, listOfParamsValue);
				body.setId(eddId);
				body.setRefId(body.getRefId()!=null? body.getRefId(): eddId);
				body.setUserId(body.getUserId());
				body.setEdd(edd);
			} else
				throw new IllegalArgumentException("edd not inserted");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return body;
	}

	public boolean deleteEddListDataOnRefId(Edd body,AuthChecker acObj) {
		try {
			String[] keyList = {"isActive"};
			Object[] queryParam = { 0 , body.getRefId()};
			db.runPutQuery(PostSqlQueries.SQLUpdateUserRefEDD, keyList, queryParam);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
