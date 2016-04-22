package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.Advert;
import com.froad.CB.po.Agreement;
@WebService
public interface AdvertService {
	public Integer addAdvert(Advert advert) throws Exception;
	public List<Advert> getAdverts(Advert queryCon);
	public boolean updateAdvert(Advert advert);
	public boolean stopAdvert(Advert advert);
	
	/**
	 * 分页查询
	 * @param advert
	 * @return
	 */
	public Advert getAdvertByPager(Advert pager);
}
