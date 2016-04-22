package com.froad.po;

import com.froad.db.mongo.page.MongoPage;

public class QueryMongoPageRes {
	
	private MongoPage page;
	private Result result;
	
	
	public MongoPage getPage() {
		return page;
	}
	public void setPage(MongoPage mongoPage) {
		this.page = mongoPage;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	
	

}
