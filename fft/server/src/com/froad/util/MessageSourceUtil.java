package com.froad.util;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

public class MessageSourceUtil implements MessageSourceAware{

	private static MessageSource messageSource;
	
	public static MessageSource getSource(){
		return messageSource;
	}
	
	@Override
	public void setMessageSource(MessageSource ms) {
		messageSource=ms;
	}

}
