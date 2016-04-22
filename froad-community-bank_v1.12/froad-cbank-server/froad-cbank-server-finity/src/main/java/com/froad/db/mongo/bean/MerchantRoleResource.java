package com.froad.db.mongo.bean;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * CbMerchantRoleResource po. 
 */


public class MerchantRoleResource implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * client_id+role_id
	 * */
	public String _id; // optional

	public List<MerchantResource> resources; // optional

	// Constructors

	/** default constructor */
	public MerchantRoleResource() {
	}

	/** full constructor */
	public MerchantRoleResource(String _id, List<MerchantResource> resources) {
		this._id = _id;
		this.resources = resources;
	}


	// Property accessors
	@JSONField(name="_id")
	public String get_id() {
		return _id;
	}

	@JSONField(name="_id")
	public void set_id(String _id) {
		this._id = _id;
	}

	@JSONField(name="resources")
	public List<MerchantResource> getResources() {
		return resources;
	}

	@JSONField(name="resources")
	public void setResources(List<MerchantResource> resources) {
		this.resources = resources;
	}


}