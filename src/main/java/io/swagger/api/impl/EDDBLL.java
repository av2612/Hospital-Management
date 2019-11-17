package io.swagger.api.impl;

import java.util.Map;

import io.swagger.Utility;
import io.swagger.dao.EDDDAO;
import io.swagger.model.Edd;

public class EDDBLL {
	EDDDAO eddDaoObj = new EDDDAO();
	public Edd eddEntryPoint(Edd body, AuthChecker acObj) {
		if(body.getRefId()!=null)
			eddDaoObj.deleteEddListDataOnRefId(body, acObj);
		if (body.getGestationalType() == null)
			throw new IllegalArgumentException("gestationalType can not be null");
		else if (body.getGestationalType().equals("LMP"))
			return this.calculateEddOnLMP(body, acObj);
		else if (body.getGestationalType().equals("Ultrasound"))
			return this.calculateEddOnUltrasound(body, acObj);
		else if (body.getGestationalType().equals("IVF"))
			return this.calculateEddOnIVF(body, acObj);
		else
			throw new IllegalArgumentException("Gestational Type not in the list"+body.getGestationalType());
	}

	private Edd calculateEddOnLMP(Edd body, AuthChecker acObj) {
		
		Map<String, Object> gestaionalInputData = body.getGestationInputData();
		String lmpDate = (String) gestaionalInputData.get("LMPDate");
		String edd = Utility.addDaysToDate(lmpDate, 280, "yyyy-MM-dd");
		return eddDaoObj.postEddListData(body, edd, acObj);
		
	}

	private Edd calculateEddOnUltrasound(Edd body, AuthChecker acObj) {
		EDDDAO eddDaoObj = new EDDDAO();
		Map<String, Object> gestaionalInputData = body.getGestationInputData();
		String ultrasoundDate = (String) gestaionalInputData.get("ultrasoundDate");
		Integer gestationalAge = Utility.convertObjectToInteger(gestaionalInputData.get("gestationalAge"));
		String startDate = Utility.addDaysToDate(ultrasoundDate, -gestationalAge , "yyyy-MM-dd");
		String edd = Utility.addDaysToDate(startDate, 280, "yyyy-MM-dd");
		return eddDaoObj.postEddListData(body, edd, acObj);
	}

	private Edd calculateEddOnIVF(Edd body, AuthChecker acObj) {
		EDDDAO eddDaoObj = new EDDDAO();
		Map<String, Object> gestaionalInputData = body.getGestationInputData();
		String dateOfTransfer = (String) gestaionalInputData.get("dateOfTransfer");
		String edd = Utility.addDaysToDate(dateOfTransfer, -14+280 , "yyyy-MM-dd");
		return eddDaoObj.postEddListData(body, edd, acObj);
	}
}
