package com.froad.process.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.ProductCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.entity.Product;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;

public class ProductUtil extends AbstractProcess{
     
	private com.froad.db.chonggou.mappers.ProductMapper productCGMapper;
	private TransferMapper transferMapper;
    private com.froad.db.ahui.mappers.ProductMapper productMapperAnhui;
    private Map<String,String> newIDmap;
	
	public ProductUtil(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		productCGMapper=sqlSession.getMapper(com.froad.db.chonggou.mappers.ProductMapper.class);
		transferMapper=sqlSession.getMapper(TransferMapper.class);
		productMapperAnhui=ahSqlSession.getMapper(com.froad.db.ahui.mappers.ProductMapper.class);
		newIDmap=new HashMap<String,String>();
		//查询中间表放入内存
		List<Transfer> transfers=transferMapper.queryGroupList(TransferTypeEnum.product_id);
		for(Transfer transfer:transfers){
			newIDmap.put(transfer.getOldId(), transfer.getNewId());
		}
		//查询所有数据
		List<Product> productsAnhui=productMapperAnhui.selectAllProductInAnhui();
		LogCvt.info("fft_product大小为:"+productsAnhui.size());
		for(Product product:productsAnhui){
			//根据ID查询重构
			String newId=newIDmap.get(String.valueOf(product.getId()));
			LogCvt.info("ProductId为"+product.getId()+",对应的newId为:"+newId);
			ProductCG productcg=new ProductCG();
			productcg.setClientId("anhui");
			productcg.setProductId(newId);
			ProductCG productCG=productCGMapper.getProductById(productcg);
			if(product.getProductVerifyTime()!=null){
				productCG.setAuditTime(product.getProductVerifyTime());
				LogCvt.info("ProductVerifyTime:为"+product.getProductVerifyTime());
			}else{
				productCG.setAuditTime(product.getCreateTime());
				LogCvt.info("ProductVerifyTime为空,CreateTime为"+product.getCreateTime());
			}
			productCGMapper.updateProduct(productCG);
			
		}
		
	}

	
	
}

