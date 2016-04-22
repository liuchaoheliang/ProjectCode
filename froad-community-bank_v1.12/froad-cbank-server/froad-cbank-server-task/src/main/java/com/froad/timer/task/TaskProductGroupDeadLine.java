package com.froad.timer.task;

import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.ThriftConfig;

public class TaskProductGroupDeadLine implements Runnable{

	@Override
	public void run() {

		//调商品接口
		LogCvt.debug("定时任务: 商品团购预售到期修改上下架状态------开始------");
		
		ProductService.Iface productClient =
				(ProductService.Iface)ThriftClientProxyFactory.createIface(ProductService.class.getName(), ProductService.class.getSimpleName(), ThriftConfig.GOODS_SERVICE_HOST, ThriftConfig.GOODS_SERVICE_PORT);
			
		try {
			
			ResultVo resultVo=	productClient.autoOffShelfProductSaleEnd();
			
			if(resultVo.getResultCode().equals(ResultCode.success.getCode())){
				LogCvt.debug("定时任务: 商品团购预售到期修改上下架状态成功");
			}else{
				LogCvt.debug("定时任务: 商品团购预售到期修改上下架状态失败原因:"+resultVo.getResultDesc());
			}
					
		} catch (Exception e){
			LogCvt.debug("定时任务: 商品团购预售到期修改上下架状态[异常],原因:"+e.getMessage());
		}

	}

}
