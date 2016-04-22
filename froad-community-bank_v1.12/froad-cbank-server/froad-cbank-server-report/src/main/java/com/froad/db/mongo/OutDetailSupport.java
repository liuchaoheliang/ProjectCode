package com.froad.db.mongo;

import java.util.ArrayList;
import java.util.List;

import com.froad.Constants;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.po.mongo.OutletDetail;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class OutDetailSupport {

    private MongoManager mongo = new MongoManager();
    
    public List<String> getOutletIdListByMerchantId(String merchantId) {
        List<OutletDetail> list = getOutletListByMerchantId(merchantId);
        List<String> outletList = new ArrayList<String>();
        for(OutletDetail outlet : list) {
            outletList.add(outlet.getId());
        }
        return outletList;
    }
    
    public List<OutletDetail> getOutletListByMerchantId(String merchantId) {
        DBObject where = new BasicDBObject();
        
        where.put("merchant_id", merchantId);
        return (List<OutletDetail>)mongo.findAll(where, MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
    }
    
    public static void main(String[] args) {
        System.setProperty(Constants.CONFIG_PATH, "./config/");
        OutDetailSupport suppert = new OutDetailSupport();
        List<OutletDetail> list = suppert.getOutletListByMerchantId("040C69F08025");
        System.out.println(JSonUtil.toJSonString(list));
    }
    
    
}
