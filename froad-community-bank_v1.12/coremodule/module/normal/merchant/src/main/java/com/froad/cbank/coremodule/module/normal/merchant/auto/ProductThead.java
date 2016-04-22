//package com.froad.cbank.coremodule.module.normal.merchant.auto;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.apache.thrift.TException;
//import org.springframework.stereotype.Component;
//
//import com.froad.cbank.coremodule.module.normal.merchant.service.HandleProduct_Service;
//import com.froad.thrift.vo.EditProductAuditStateVo;
//
///**
// * @author Administrator
// *
// */
//
//public class ProductThead implements Runnable {
//    private HandleProduct_Service handleProduct_Service;
//	private List<EditProductAuditStateVo> list;
//    public ProductThead(List<EditProductAuditStateVo> list,HandleProduct_Service handleProduct_Service){
//    	this.list=list;
//    	this.handleProduct_Service=handleProduct_Service;
//    }
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.lang.Runnable#run()
//	 */
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		try {
//			handleProduct_Service.updateProductAudit(list);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//}
