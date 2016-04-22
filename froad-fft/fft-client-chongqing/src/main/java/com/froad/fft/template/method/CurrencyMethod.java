package com.froad.fft.template.method;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 货币格式化
 * @author FQ
 *
 */
@Component("currencyMethod")
public class CurrencyMethod implements TemplateMethodModel {
	
	private static final String DEFAULT_CURRENCY_SIGN = "￥";//货币符号
	private static final String DEFAULT_CURRENCY_UNIT = "元";//货币单位
	
	private static final Integer PRICE_SCALE = 2;//获取价格精确位数

	@Override
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null && StringUtils.isNotEmpty(arguments.get(0).toString())) {
			boolean showSign = false;
			boolean showUnit = false;
			if (arguments.size() == 2) {
				if (arguments.get(1) != null) {
					showSign = Boolean.valueOf(arguments.get(1).toString());
				}
			} else if (arguments.size() > 2) {
				if (arguments.get(1) != null) {
					showSign = Boolean.valueOf(arguments.get(1).toString());
				}
				if (arguments.get(2) != null) {
					showUnit = Boolean.valueOf(arguments.get(2).toString());
				}
			}
			
			BigDecimal amount = new BigDecimal(arguments.get(0).toString());
			String price = setScale(amount).toString();
			if (showSign) {
				price = DEFAULT_CURRENCY_SIGN + price;
			}
			if (showUnit) {
				price += DEFAULT_CURRENCY_UNIT;
			}
			return new SimpleScalar(price);
		}
		return null;
	}
	
	private BigDecimal setScale(BigDecimal amount) {
		if (amount == null) {
			return null;
		}
		return amount.setScale(PRICE_SCALE);
	}


}
