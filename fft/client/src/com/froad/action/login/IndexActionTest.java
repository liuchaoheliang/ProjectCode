package com.froad.action.login;

import com.froad.action.support.*;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.Store.Store;
import com.froad.client.advert.Advert;
import com.froad.client.announcement.Announcement;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchant.TagMAP;
import com.froad.client.merchantPreferential.MerchantPreferential;
import com.froad.client.searches.Searches;
import com.froad.util.Command;
import com.froad.util.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 14-3-13.
 */
public class IndexActionTest extends BaseActionSupport {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 6002160263444909107L;
	private AnnouncementActionSupport announcementActionSupport;
	private AdvertActionSupport advertActionSupport;//广告
	private List<Advert> advertList;//广告
	private Announcement announcement;
	private SearchesActionSupport searchesActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private MerchantPreferentialActionSupport merchantPreferentialActionSupport;
	private StoreActionSupport storeActionSupport;


	/**
	 * 首页
	 * @return
	 */
	public String index(){
		//公告
		announcement=announcementActionSupport.getAnnouncementOrderByImportant();

		return "index";
	}




	public Announcement getAnnouncement() {
		return announcement;
	}


	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}

	public AnnouncementActionSupport getAnnouncementActionSupport() {
		return announcementActionSupport;
	}

	public void setAnnouncementActionSupport(
			AnnouncementActionSupport announcementActionSupport) {
		this.announcementActionSupport = announcementActionSupport;
	}

	public AdvertActionSupport getAdvertActionSupport() {
		return advertActionSupport;
	}

	public void setAdvertActionSupport(AdvertActionSupport advertActionSupport) {
		this.advertActionSupport = advertActionSupport;
	}

	public List<Advert> getAdvertList() {
		return advertList;
	}

	public void setAdvertList(List<Advert> advertList) {
		this.advertList = advertList;
	}

	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}

	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}

	public MerchantPreferentialActionSupport getMerchantPreferentialActionSupport() {
		return merchantPreferentialActionSupport;
	}

	public void setMerchantPreferentialActionSupport(
			MerchantPreferentialActionSupport merchantPreferentialActionSupport) {
		this.merchantPreferentialActionSupport = merchantPreferentialActionSupport;
	}

	public SearchesActionSupport getSearchesActionSupport() {
		return searchesActionSupport;
	}

	public void setSearchesActionSupport(SearchesActionSupport searchesActionSupport) {
		this.searchesActionSupport = searchesActionSupport;
	}

	public StoreActionSupport getStoreActionSupport() {
		return storeActionSupport;
	}

	public void setStoreActionSupport(StoreActionSupport storeActionSupport) {
		this.storeActionSupport = storeActionSupport;
	}


}
