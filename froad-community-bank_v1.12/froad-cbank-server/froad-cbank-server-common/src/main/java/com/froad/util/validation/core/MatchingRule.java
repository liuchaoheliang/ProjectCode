package com.froad.util.validation.core;

import java.lang.reflect.Field;
import java.util.Map;

import com.froad.util.validation.entity.FieldVerfyType;
import com.froad.util.validation.entity.Result;
import com.froad.util.validation.entity.SortResult;
import com.froad.util.validation.entity.VerifyException;
import com.froad.util.validation.entity.VerifyRegular;
import com.froad.util.validation.regular.StringVerify;


public class MatchingRule {

	public static boolean verify(Map<Field, VerifyRegular> regular,Object entity) {
		Field field = null;
		for (Map.Entry<Field, VerifyRegular> entry : regular.entrySet()) {
			try {
				field = entry.getKey();
				field.setAccessible(true);
				Result result = toVerify(SortRegular.sort(entry.getValue()), field.get(entity));
				if(!result.isResult()){
					throw new VerifyException(field.getName() + ": " + result.getMessage());
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return true;
	}
	
	private static Result toVerify(Map<FieldVerfyType, SortResult> sortRegular,Object originalValue){
		
		for (Map.Entry<FieldVerfyType, SortResult> entry : sortRegular.entrySet()) {
			
			SortResult sortResult = entry.getValue();
			
			String errorMsg = sortResult.getErrorMsg();
			
			Object value = sortResult.getRegular();
			
			if(value == null){
				continue;
			}

			FieldVerfyType verfyType = entry.getKey();
			
			if(StringVerify.isEmpty(errorMsg)){
				errorMsg = verfyType.errorMsg;
			}
			
			boolean notVerfyType = false;

			switch (verfyType.code) {
			
			case 0 :
				if((Boolean)value){
					if(originalValue == null){
						return new Result(false, errorMsg);
					}
				}else{
					if(originalValue == null){
						notVerfyType = true;
					}
				}
				break;
				
			case 1 :
				if(!StringVerify.isEmpty((String)value)){
					if(!StringVerify.regular((String)originalValue, (String)value)){
						return new Result(false, errorMsg);
					}
				}
				break;
				
			case 2 :
				int maxLenght = (Integer)value;
				if(maxLenght > 0){
					if(((String)originalValue).length() > maxLenght){
						return new Result(false, errorMsg);
					}
				}
				break;
				
			case 3 :
				int minLenght = (Integer)value;
				if(minLenght > 0){
					if(((String)originalValue).length() < minLenght){
						return new Result(false, errorMsg);
					}
				}
				break;
				
			default:
				break;
			}
			
			if(notVerfyType){
				break;
			}
		}
		
		return new Result(true);
	}
	
	
}
