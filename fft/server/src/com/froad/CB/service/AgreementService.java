package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.Agreement;

/** 
 * @author FQ 
 * @date 2013-1-31 下午02:24:24
 * @version 1.0
 * 协议
 */
@WebService
public interface AgreementService {
	
	/**
	 * 增加协议
	 * @param agreement
	 * @return
	 */
	public Integer addAgreement(Agreement agreement);
	
	/**
	 * 根据ID 查询协议
	 * @param id
	 * @return
	 */
	public Agreement getAgreementById(Integer id);
	
	/**
	 * 根据Agreement 更新协议
	 * @param agreement
	 * @return
	 */
	public boolean updateAgreement(Agreement agreement);
	
	/**
	 * 条件查询
	 * @param agreement
	 * @return
	 */
	public List<Agreement> getAgreement(Agreement agreement);
}
