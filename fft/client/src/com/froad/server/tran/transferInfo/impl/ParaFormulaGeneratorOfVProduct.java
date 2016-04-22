package com.froad.server.tran.transferInfo.impl;

import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.server.tran.transferInfo.ParaFormulaGenerator;

public class ParaFormulaGeneratorOfVProduct implements ParaFormulaGenerator {

	public String getParaFormula(Trans tran) throws Exception {
		// TODO Auto-generated method stub
		TransDetails transDetails=(TransDetails)tran.getTransDetailsList().get(0);
		return transDetails.getGoodsNumber();
	}

}
