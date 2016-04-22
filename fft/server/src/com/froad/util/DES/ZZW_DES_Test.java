package com.froad.util.DES;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.modes.PaddedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.junit.Test;   
  

  


	/**
	 * 类描述：测试类 （可供参考）
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: zhuzhiwei zhuzhiwei@f-road.com.cn
	 * @time: Mar 2, 2012 7:01:03 PM 
	 */
public class ZZW_DES_Test {   
   // private String publicKey;   
   // private String privateKey;   
  
    private static String TrnKey="04A80B13649E916B" +
    							 "E062B0F7F292CE04" +
    							 "04A80B13649E916B";
    private static String MacKey="8A8A40469785D513"+
    							 "4391AE833B67701F"+
    							 "8A8A40469785D513";
    
    //private static String login_test="616263647C8686B73B68F50602";//abcd|1234
   
    private static String login_test="616263647C4B4FED7325325A3B";
    private static String mac_test="92941F5F";
    private static String iv="7856341278563412";//0x
    

    
    public static void sevalidate(){
    	//
    	
    	//ECB
    	Froad_SEValidate seValidate=new Froad_SEValidate();
   
    	
    	
    	String setest=seValidate.SePasswordEncrypt("上海", TrnKey);
    	System.out.println("zzw方法  se加密"+setest);
    	String sevtest=seValidate.SePasswordDecrypt(setest, TrnKey);
    	System.out.println("zzw方法   se解密"+sevtest);
    	//mac
    	String mactest=seValidate.SeMacEncrypt("我", MacKey, iv);
    	System.out.println("zzw方法   mac"+mactest);
    	
    	
    	//=====
     	String[] testString=seValidate.SeLoginUnhex(login_test);
     	String username=testString[0];
     	String password=testString[1];
    	password=seValidate.SePasswordDecrypt(ByteHexFormat.hex(password.getBytes()), TrnKey);
    	System.out.println("zzw方法 测试 3DECECB 密码"+password);
    	mactest=seValidate.SeMacEncrypt(username+"|"+password, MacKey, iv);
    	System.out.println("zzw方法   测试 mac"+mactest);
    	
    	//================================================================
    	//解密
    	String a=new String(ByteHexFormat.unhex(login_test));
    	String[] aStringArray=a.split("\\|");
    	System.out.println("a"+a+"[0]"+aStringArray[0]+"[1]"+aStringArray[1]);
   
    	
    	DesEncrypt desEncrypt=new DesEncrypt();
    	desEncrypt.setKey(ByteHexFormat.unhex(TrnKey));
    	byte[] desdesTest=desEncrypt.get3DesDesCode(aStringArray[1].getBytes());
    	String desdecString=new String(desdesTest);
    	System.out.println("hex解密后："+desdecString);
    	
    	
    	//login 解析结果
    	String keyword=new String(ByteHexFormat.unhex("8686B73B68F50602"));
    	System.out.println(keyword);
    	
    	//加密
    	String input="3132333400000000";
    	
    	String inputa="1234";
    //	ByteHexFormat.hex(inputa.getBytes());
    	System.out.println("...."+ByteHexFormat.To8byte(inputa));
    	
    	DesEncrypt desencrypt=new DesEncrypt();
    	desencrypt.setKey(ByteHexFormat.unhex(TrnKey));
    	byte[] desEnctest=desencrypt.get3DesEncCode(ByteHexFormat.unhex(input));
    	
    	String desEncString=new String(desEnctest);
    	String desEncHex=ByteHexFormat.hex(desEnctest);
    	System.out.println("加密后:"+desEncString+" hex: "+desEncHex);
    	
    	//解密
    	byte[] desDecHex=ByteHexFormat.unhex(desEncHex);
    	byte[] desDestest=desencrypt.get3DesDesCode(desDecHex);
    	String desDecString=new String(desDestest);
    	System.out.println("hex解密后："+desDecString);
    	
    	
    	
    	//mac 3des cbc
    	String Block="616263647c3132333400000000000000"; //32
    	//String Block="abcd|1234"; //16
    	desencrypt.setKey(ByteHexFormat.unhex(MacKey));
    	//byte[] macByte=desencrypt.getDesCBCEncCode(Util.unhexba(MacKey), Util.unhexba(Block), Util.unhexba(iv));
    	byte[] macByte=desencrypt.get3DesCBCEncCode(ByteHexFormat.unhex(Block), ByteHexFormat.unhex(iv));
    	String macString=new String(macByte);
    	String machexString = ByteHexFormat.hex(macByte);
    	System.out.println(""+macString.getBytes()+"  "+machexString);
    	
    	byte[] cbcdesTest=desencrypt.get3DesCBCDesCode(ByteHexFormat.unhex(machexString), ByteHexFormat.unhex(iv));
    	String cbcdecString=new String(cbcdesTest);
    	System.out.println("hex解密后："+cbcdecString);
    	
    	//解密
//    	byte[] desDestest=desencrypt.get3DesDesCode(desEncString.getBytes());
//    	String desDecString=new String(desDestest);
//    	desDecString=new String (Util.unhexba(desDecString));
//    	System.out.println("解密后："+desDecString);
    	//hex解密
//    	byte[] desDestest=desencrypt.get3DesDesCode(Util.unhexba(desEncHex));
//    	String desDecString=new String(desDestest);
//    	System.out.println("hex解密后："+desDecString);
//    	desDecString=new String (Util.unhexba(desDecString));
//    	System.out.println("hex解密后："+desDecString);
    }
	
	public static void main(String[] args){
		try {
			//rsamain.certTest();
			//rsamain.setUp();
			//test();
			//test2();
			sevalidate();
			String a="1234a";
			String b=ByteHexFormat.hex(a.getBytes());
			System.out.println("hex 1234a:"+b);
			
			System.exit(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    



  
}  