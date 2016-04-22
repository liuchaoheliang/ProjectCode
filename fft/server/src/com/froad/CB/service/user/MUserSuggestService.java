package com.froad.CB.service.user;

import javax.jws.WebService;

import com.froad.CB.AppException.AppException;
import com.froad.CB.po.user.Suggest;

@WebService
public interface MUserSuggestService {

	public Suggest add(Suggest suggest) throws AppException;
}
