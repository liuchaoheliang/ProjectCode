package com.froad.action.support.trans;

import com.froad.client.authTicket.AuthTicket;
import com.froad.client.authTicket.AuthTicketService;
import com.froad.client.authTicket.Result;


public class RepeatMsgActionSupport {

	private AuthTicketService authTicketServicek;

	public Result repeatMsg(Integer transId,Integer authId){
		return authTicketServicek.getAuthTickByAuthId(transId,authId);
	}
	public  AuthTicket getAuthTicketById(Integer authId){
		return authTicketServicek.getAuthTicketById(authId);
	}
	public AuthTicketService getAuthTicketServicek() {
		return authTicketServicek;
	}
	
	public void setAuthTicketServicek(AuthTicketService authTicketServicek) {
		this.authTicketServicek = authTicketServicek;
	}
	
}
