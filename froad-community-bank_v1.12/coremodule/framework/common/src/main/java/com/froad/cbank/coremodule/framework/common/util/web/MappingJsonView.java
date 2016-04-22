package com.froad.cbank.coremodule.framework.common.util.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.alibaba.fastjson.JSON;

public class MappingJsonView extends MappingJacksonJsonView {

	Logger log = LoggerFactory.getLogger(MappingJsonView.class);

	protected Object filterModel(Map<String, Object> model) {
		Map<?, ?> result = (Map<?, ?>) super.filterModel(model);
		result.remove("void");
		if (result.size() == 1) {
			Object ob = result.values().iterator().next();
			
			if(log.isDebugEnabled()) {
				log.debug("RESPONE: " + JSON.toJSONString(ob));
			}
			return ob;
		} else {
			if(log.isDebugEnabled()) {
				log.debug("RESPONE: " + JSON.toJSONString(result));
			}
			
			return result;
		}

	}
}
