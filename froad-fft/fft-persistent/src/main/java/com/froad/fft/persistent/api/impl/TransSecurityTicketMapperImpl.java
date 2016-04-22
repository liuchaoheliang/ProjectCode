package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import com.froad.fft.persistent.api.TransSecurityTicketMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.TransSecurityTicket;
import com.froad.fft.persistent.util.DesUtils;

public class TransSecurityTicketMapperImpl implements TransSecurityTicketMapper {

	private Logger log=Logger.getLogger(TransSecurityTicketMapperImpl.class);
	
	@Resource
	private TransSecurityTicketMapper transSecurityTicketMapper;
	
	@Override
	public Long saveTransSecurityTicket(TransSecurityTicket transSecurityTicket) {
		String ticket=this.encrypt(transSecurityTicket.getSecuritiesNo().toLowerCase());
		transSecurityTicket.setSecuritiesNo(ticket);
		transSecurityTicketMapper.saveTransSecurityTicket(transSecurityTicket);
		return transSecurityTicket.getId();
	}

	@Override
	public Boolean updateTransSecurityTicketById(
			TransSecurityTicket transSecurityTicket) {
		return transSecurityTicketMapper.updateTransSecurityTicketById(transSecurityTicket);
	}

	@Override
	public TransSecurityTicket selectTransSecurityTicketById(Long id) {
		TransSecurityTicket bean = transSecurityTicketMapper.selectTransSecurityTicketById(id);
		if(bean!=null){
			String ticket=this.decrypt(bean.getSecuritiesNo());
			bean.setSecuritiesNo(ticket);
			return bean;
		}
		return null;
	}

	

	@Override
	public TransSecurityTicket selectBySecurityNo(String SecurityNo) {
		String ticket=this.encrypt(SecurityNo.toLowerCase());
		return transSecurityTicketMapper.selectBySecurityNo(ticket);
	}

	

	@Override
	public List<TransSecurityTicket> selectByPage(Page page) {
		List<TransSecurityTicket> list = transSecurityTicketMapper.selectByPage(page);
		if(list==null||list.size()==0){
			return null;
		}
		String ticket=null;
		for (int i = 0; i < list.size(); i++) {
			ticket=list.get(i).getSecuritiesNo();
			list.get(i).setSecuritiesNo(this.decrypt(ticket));
		}
		return list;
	}

	

	@Override
	public Integer selectByPageCount(Page page) {
		return transSecurityTicketMapper.selectByPageCount(page);
	}

	@Override
	public List<TransSecurityTicket> selectByCondition(TransSecurityTicket transSecurityTicket) {
		if(transSecurityTicket!=null&&transSecurityTicket.getSecuritiesNo()!=null){
			String no=this.encrypt(transSecurityTicket.getSecuritiesNo().toLowerCase());
			transSecurityTicket.setSecuritiesNo(no);
		}
		List<TransSecurityTicket> list=transSecurityTicketMapper.selectByCondition(transSecurityTicket);
		if(list==null||list.size()==0){
			return null;
		}
		String ticket=null;
		for (int i = 0; i < list.size(); i++) {
			ticket=list.get(i).getSecuritiesNo();
			list.get(i).setSecuritiesNo(this.decrypt(ticket));
		}
		return list;
	}

	@Override
	public List<TransSecurityTicket> selectByTransId(Long transId) {
		List<TransSecurityTicket> list=transSecurityTicketMapper.selectByTransId(transId);
		if(list==null||list.size()==0){
			return null;
		}
		String ticket=null;
		for (int i = 0; i < list.size(); i++) {
			ticket=list.get(i).getSecuritiesNo();
			list.get(i).setSecuritiesNo(this.decrypt(ticket));
		}
		return list;
	}

	
	private String encrypt(String str){
		if(str==null||"".equals(str)){
			return null;
		}
		try {
			DesUtils desUtils=new DesUtils();
			return desUtils.encrypt(str);
		} catch (Exception e) {
			log.error("券号加密异常",e);
		}
		return null;
	}
	
	private String decrypt(String str){
		if(str==null||"".equals(str)){
			return null;
		}
		try {
			DesUtils desUtils=new DesUtils();
			return desUtils.decrypt(str);
		} catch (Exception e) {
			log.error("券号解密异常",e);
		}
		return null;
	}
}
