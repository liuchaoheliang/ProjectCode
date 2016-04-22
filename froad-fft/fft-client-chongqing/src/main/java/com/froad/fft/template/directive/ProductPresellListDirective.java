package com.froad.fft.template.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.froad.fft.dto.ProductDto;
import com.froad.fft.dto.ProductPresellDto;
import com.froad.fft.support.base.ProductPresellSupport;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("productPresellListDirective")
public class ProductPresellListDirective extends BaseDirective {
	
	// 变量名称 
	private static final String VARIABLE_NAME = "productPresells";
	
	@Resource
	ProductPresellSupport productPresellSupport;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// TODO Auto-generated method stub
		List<ProductDto> products=new ArrayList<ProductDto>();
		//查询结果
		ProductDto productDto = new ProductDto();
		productDto.setIsMarketable(true);//上架
		productDto.setIsEnablePresell(true);//启用预售
		products= productPresellSupport.getProductByPresell(productDto);
		setLocalVariable(VARIABLE_NAME, products, env, body);
	}

}
