package com.froad.action.api.command;


public class Encrypt {
	//������pan�ĳ���
	private final int encryptPanLenth = 12;
	//���֤18λ���⴦��
	private final int idNoLenth = 18;
	
	/**
	 * ��ȡ��������ת��Ϊascii�����ǰ������
	 * @param number
	 * @return
	 */
	public String asciiGetBody(String number){
		if(null!=number){
			return fillNormalBody(Util.hex(number.length())+number);
		}
		return null;
	}
	
	/**
	 * ��ͨ�Ĳ�λ����
	 * @param body
	 * @return
	 */
	public String fillNormalBody(String sbody){
		String ascIIBody = Util.ascii(sbody);
		if(null!=ascIIBody && ascIIBody.length()%2==0){
			int len = ascIIBody.length();
			if(len%16!=0){
				ascIIBody += "80";
			}else{
				return ascIIBody+"8000000000000000";
			}
			while(ascIIBody.length()%16!=0){
				ascIIBody += "00";
			}
			return ascIIBody;
		}
		return null;
	}
	
	/**
	 * ��ȡpin��pan����Ľ��
	 * @param pin
	 * @param pan
	 * @return
	 */
	public String getPinByPan(String pin,String pan){
		if(null!=pin && null!=pan && pan.length()>encryptPanLenth){
			String panStr = "0000";
			if(pan.length() == idNoLenth){
				panStr += pan.substring(idNoLenth-encryptPanLenth-1, pan.length()-1);
			}else{
				panStr += pan.substring(pan.length()-encryptPanLenth, pan.length());
			}
			pin = Util.hex(pin.length())+pin;
			while(pin.length()%16 != 0){
				pin += "FF";
			}
			if(pin.length() == 16){
				byte[] pinByte = Util.unhexba(pin);
				byte[] panByte = Util.unhexba(panStr);
				byte[] pinXorPan = new byte[8];
				for(int i=0;i<pinByte.length;i++){
					pinXorPan[i] = (byte) (pinByte[i]^panByte[i]);
				}
				return Util.hex(pinXorPan);
			}else{
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param dkey
	 * @param random
	 * @return
	 */
	public String diverse(String dkey,String random){
		byte[] rand = Util.unhexba(random);
		byte[] key = Util.unhexba(dkey);
		byte[] tleft = FroadDes.do3des(rand, key);
		byte[] tright = FroadDes.do3des(FroadDes.not(rand), key);
		byte[] tkey = FroadDes.bajoin(tleft, tright);
		return Util.hex(tkey);
	}
	
	/**
	 * 
	 * @param key
	 * @param date
	 * @return
	 */
	public String encrypt(String key,String date){
		return Util.hex(FroadDes.do3des(Util.unhexba(date), Util.unhexba(key)));
	}
	
	/**
	 * 
	 * @param key
	 * @param date
	 * @return
	 */
	public String deEncrypt(String key,String date){
		return Util.hex(FroadDes.do3desun(Util.unhexba(date), Util.unhexba(key)));
	}
	
	/**
	 * 
	 * @param key
	 * @param date
	 * @return
	 */
	public String signMac(String key,String date) {
		return Util.hex(do3desMac(Util.unhexba(date), Util.unhexba(key)));
	}
	
	/**
	 * 
	 * @param data
	 * @param key
	 * @return ����8λmac
	 */
	public static byte[] do3desMac(byte[] data, byte[] key) {
		int dataLen, i, j;
		String mac;
		byte[] plantext = new byte[8], plantext2 = new byte[8], mKey = new byte[8];
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0 };
		String mdata = Util.hex(data);
		dataLen = mdata.length();
		if (dataLen % 2 != 0) {
			return null;
		}
		dataLen = dataLen / 2;
		if (plantext2 == null) {
			return null;
		}
		byte[] bdata = Util.unhexba(mdata);
		plantext = new byte[dataLen];
		System.arraycopy(bdata,0,plantext,0,dataLen);
		System.arraycopy(key,0,mKey,0,8);
		DesEncrypt des = new DesEncrypt();
		i = 0;
		while (i < dataLen) {
			boolean isfirst = false;
			for (j = 0; j < 8;) {
				if (i < dataLen) {
					plantext2[j] = plantext[i];
					j++;
				} else {
					if (!isfirst) {
						plantext2[j] = (byte) 0x80;
						isfirst = true;
					} else {
						plantext2[j] = (byte) 0x00;
					}
					j++;
				}
				i++;
			}
			for (j = 0; j < 8; j++) {
				iv[j] = (byte) (iv[j] ^ plantext2[j]);
			}
			iv = des.getDesECBEncCode(mKey, iv);
		}
		if (dataLen % 8 == 0) {
			byte[] add = Util.unhexba("8000000000000000");
			for (j = 0; j < 8; j++) {
				iv[j] = (byte) (iv[j] ^ add[j]);
			}
			iv = des.getDesECBEncCode(mKey, iv);
		}
		mac = Util.hex(iv);
		return Util.unhexba(mac.substring(0, 8));
	}
	
	public String clientEncrypt(String key,String returnStr){
		key += "0000000000000000";
		key = key.substring(0, 16);
		String linshiKey = diverse(FroadAPICommand.KEY+FroadAPICommand.KEY, key);
		
		String str = new String(Util.hex(returnStr.getBytes()));
		str += "80";
		while (str.length()%16!=0) {
			str += "0";
		}
		return encrypt(linshiKey, str);
	}
	
	
	public String clientDeEncrypt(String key,String returnStr){
		key += "0000000000000000";
		key = key.substring(0, 16);
		String linshiKey = diverse(FroadAPICommand.KEY+FroadAPICommand.KEY, key);
		String resul = deEncrypt(linshiKey, returnStr);
		//System.out.println("web resul = "+resul);
		if(resul.length()%16==0){
			//截取正常数据内容
			int dex = resul.lastIndexOf("80");
			String cutStr = resul.substring(0, dex);
			//切掉补位的尾巴
			String resultStr = new String(Util.unhexba(cutStr));
			System.out.println("解析出加密的数据为： "+resultStr);
			//拿到结构为true"blank"account"blank"idtype"blank"idno"blank"mobileno
			 return resultStr;
		}else{
			//密文异常，长度必须为16的倍数
			return "密文异常，长度必须为16的倍数";
		}
	}
	
	public static void main(String[] args) {
		String key = "121128000012000000001";
		String a = new Encrypt().clientEncrypt(key, "");
		System.out.println("加密后："+a );
		new Encrypt().clientDeEncrypt(key, a);
	}
}
