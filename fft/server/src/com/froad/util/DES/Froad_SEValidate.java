package com.froad.util.DES;

public class Froad_SEValidate {
	
	
	/**
	  * 方法描述：hex解码  616263647C4B4FED7325325A3B  to abcd  KO韘%2Z
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @update: 
	  * @time: Mar 2, 2012 6:30:31 PM
	  */
	public String[] SeLoginUnhex(String input){
		String login=new String(ByteHexFormat.unhex(input));
    	String[] aStringArray=login.split("\\|");
    	return aStringArray;
	}
	/**
	  * 方法描述：3DES_ECB加密  
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @update: 
	  * @time: Mar 2, 2012 5:09:45 PM
	  */
	public String SePasswordEncrypt(String input,String TrnKey){
		input=ByteHexFormat.To8byte(input);
    	DesEncrypt desEncrypt=new DesEncrypt();
    	desEncrypt.setKey(ByteHexFormat.unhex(TrnKey));
    	byte[] desEncByte=desEncrypt.get3DesEncCode(ByteHexFormat.unhex(input));
    	String desEncHex=ByteHexFormat.hex(desEncByte);
    	return desEncHex;
	}
	
	/**
	  * 方法描述：3DES_ECB解密 
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @update: 
	  * @time: Mar 2, 2012 5:14:45 PM
	  */
	public String SePasswordDecrypt(String encData,String TrnKey){
    	DesEncrypt desEncrypt=new DesEncrypt();
    	desEncrypt.setKey(ByteHexFormat.unhex(TrnKey));
    	byte[] desdesByte=desEncrypt.get3DesDesCode(ByteHexFormat.unhex(encData));
    	int desLength=desdesByte.length;
    	for(int i=0;i<desdesByte.length;i++){
    			//System.out.println(desdesByte[i]);
    			if(desdesByte[i]==0){
    				desLength=i;
    				break;
    			}
    	}
    	byte[] desNewByte= new byte[desLength];
    	System.arraycopy(desdesByte, 0, desNewByte, 0, desLength);
    	String desdecString=new String(desNewByte);
    	return desdecString;
	}
	
	/**
	  * 方法描述：3dec CBC mac校验
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	  * @update: 
	  * @time: Mar 2, 2012 5:19:27 PM
	  */
	public String SeMacEncrypt(String input, String macKey,String iv){
    	input=ByteHexFormat.To8byte(input);
    	DesEncrypt desEncrypt=new DesEncrypt();
    	desEncrypt.setKey(ByteHexFormat.unhex(macKey));
    	byte[] macByte=desEncrypt.get3DesCBCEncCode(ByteHexFormat.unhex(input), ByteHexFormat.unhex(iv));
    	//byte[] macByte=desEncrypt.get3DesCBCEncCode(ByteHexFormat.To8byte(input).getBytes(), ByteHexFormat.unhex(iv));
    	String machexString = ByteHexFormat.hex(macByte);
    	//System.out.println(machexString);
    	int length=machexString.length();
    	int num=length/16;
    	int start=(num-1)*16;
    	int end=start+8;
    	return machexString.substring(start, end);
	}
}
