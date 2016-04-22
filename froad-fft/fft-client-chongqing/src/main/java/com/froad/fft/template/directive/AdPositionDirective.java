package com.froad.fft.template.directive;

import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.froad.fft.dto.AdDto;
import com.froad.fft.dto.AdPositionDto;
import com.froad.fft.support.base.AdPositionSupport;
import com.froad.fft.support.base.AdSupport;

import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 指令模型(自定义宏)  广告位
 * @author FQ
 *
 */
@Component("adPositionDirective")
public class AdPositionDirective extends BaseDirective {
	
	// 变量名称 
	private static final String VARIABLE_NAME = "adPosition";
	private static final String AD_VARIABLE_NAME = "adLists";
	
	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Resource
	private AdPositionSupport adPositionSupport;
	
	@Resource
	private AdSupport adSupport;

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		AdPositionDto adPosition;
		List<AdDto> ads=new ArrayList<AdDto>();
		
		Long id = getId(params);
		adPosition=adPositionSupport.findAdPositionById(id);
		
		ads=adSupport.findEnableAdByAdPositionId(id);
		
		if (body != null) {
			setLocalVariable(VARIABLE_NAME, adPosition, env, body);
		} else {
			if (adPosition != null && adPosition.getPositionStyle() != null) {
				try {
					Map<String, Object> model = new HashMap<String, Object>();
					model.put(VARIABLE_NAME, adPosition);
					model.put(AD_VARIABLE_NAME, ads);
					Writer out = env.getOut();
					new Template("adTemplate", new StringReader(adPosition.getPositionStyle()), freeMarkerConfigurer.getConfiguration()).process(model, out);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
