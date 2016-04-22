package com.froad.action.api.command;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class FroadDes {
	
	public static String defaultKey="1111111111111111";
	public static byte[] makeRand(int n) {
		byte[] rand = new byte[n];
		Random r = new Random();
		r.nextBytes(rand);
		return rand;
	}
	
	public static byte[] encrypt(byte[] data ,byte[] keysession){
		return do3des(data, keysession);
	}

	public static byte[] decrypt(byte[] data ,byte[] keysession){
		return do3desun(data, keysession);
	}

	public static byte[] domac(int p1, byte[] data ,byte[] keysession){
		byte[] x = do3des1(data, keysession);
		return Util.cutBytes(x, x.length-8, 8);
	}

	public static byte[] bajoin(byte[] left, byte[] right) {
		return Util.unhexba(Util.hex(left) + Util.hex(right));
	}

	public static byte[] not(byte[] a) {
		byte[] x = new byte[a.length];
		for(int i=0;i<a.length;i++){
			x[i] = (byte) ~a[i];
		}
		return x;
	}

	public static byte[] do3des1(byte[] data, byte[] key) {
		// 16byte ==> 24byte
		String a = Util.hex(key);
		String b = a + a.substring(0,16);
//		System.out.println("b:"+b);
		byte[] key1 = Util.unhexba(b);
//		System.out.println("key1:"+Util.hex(key1));

		try {
//			 final String Algorithm = "DESede";

            //������?
            SecretKey deskey = new SecretKeySpec(key1, "DESede");
            //��ʼ����
            IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);

            //����
            Cipher c1 = Cipher.getInstance("DESede/CBC/NOPADDING");
            c1.init(Cipher.ENCRYPT_MODE, deskey, zeroIv);
            return c1.doFinal(data);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
	}
	
	public static byte[] do3des2(byte[] data, byte[] key) {

        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=//
        int dataLen,i,j;
		String mac;
		byte[] plantext = new byte[8],plantext2 = new byte[8],mKey = new byte[8];
		byte[] iv={0,0,0,0,0,0,0,0};

		String mdata = Util.hex(data);
		dataLen = mdata.length();

		if(dataLen % 2 !=0){

	        return null;
	    }
		dataLen=dataLen/2;
		if(plantext2==null){
	        return null;
	    }
		byte[] bdata = Util.unhexba(mdata);
		plantext=new byte[dataLen];
		System.arraycopy(bdata,0,plantext,0,dataLen);
		
		System.arraycopy(key,0,mKey,0,8);

		DesEncrypt des = new DesEncrypt();
//		des.setKey(mKey);
		i=0;
		while(i<dataLen){
		       for(j=0;j<8;) {
		           if(i<dataLen) {
		        	   plantext2[j] = plantext[i];
					   j++;
				   }
		           else {
		        	   plantext2[j] = 0x20;
					   j++;
				   }
		           i++;
		       }
		       for(j=0;j<8;j++){
		           iv[j] = (byte) (iv[j] ^ plantext2[j]);
		       }
		       iv=des.getDesECBEncCode(mKey, iv); 
		       System.out.println("iv:"+Util.hex(iv));
		}
		mac=Util.hex(iv);
        return Util.unhexba(mac.substring(0,8));

	}
	
	

	public static byte[] do3des(byte[] data, byte[] key) {
		// 16byte ==> 24byte
		String a = Util.hex(key);
		String b = a + a.substring(0,16);
//		System.out.println("b:"+b);
		byte[] key1 = Util.unhexba(b);
		System.out.println("key2:"+Util.hex(key1));

		try {
//			 final String Algorithm = "DESede";

            //������?
            SecretKey deskey = new SecretKeySpec(key1, "DESede");

            //����
            Cipher c1 = Cipher.getInstance("DESede/ECB/NOPADDING");
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(data);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
	}

	public static byte[] do3desun(byte[] data, byte[] key) {
		// 16byte ==> 24byte
		String a = Util.hex(key);
		String b = a + a.substring(0,16);
//		System.out.println("b:"+b);
		byte[] key1 = Util.unhexba(b);
//		System.out.println("key1:"+Util.hex(key1));

		try {
//			 final String Algorithm = "DESede";

            //������?
            SecretKey deskey = new SecretKeySpec(key1, "DESede");

            //����
            Cipher c1 = Cipher.getInstance("DESede/ECB/NOPADDING");
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(data);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
	}
	private static String XOR(String data1,String data2){
		byte[] d1=data1.getBytes();
		byte[] d2=data2.getBytes();
		byte[] re=new byte[16];
		for(int i=0;i<16;i++){
			re[i]=(byte)(d1[i]^d2[i]);
		}
		return re.toString();
		
	}
	public static String Des3ECBEncrypt(String hexString, String hexKey, String hexICV){
		try
        {
            if (hexKey.length() != 32 && hexKey.length() != 48)
            {
                return null;
            }

            String hextext = hexString;
            String cipher = null;
            String block = null;
            for (int i = 0; i < (hextext.length() / 16); i++)
            {
                if (i == 0)
                {
                    block = hextext.substring(i * 16, 16);
                    block = XOR(block, hexICV);
                }

//                cipher = Des3Encrypt(block, hexKey);
                if (null == cipher)
                    return null;

                if (i < (hextext.length() / 16) - 1)
                {
                    block = XOR(cipher, hextext.substring((i + 1) * 16, 16));
                }
            }

            return cipher;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
	}
	/**
	 * �µķ��������ܻ�
	 * */
	public static byte [] pinFill_1(String pin){
		String lenth = String.valueOf(pin.length());
		String hexLenth = Integer.toHexString(Integer.valueOf(lenth));
		pin = LeftFill(hexLenth, 2, "0") + pin; //new lenth
		while(pin.length()%16!=0){
			pin += "F";
		}
		return Util.unhexba(pin);
	}
	
	/**
	 * �ɵļ��ܷ���
	 * */
	public static byte [] pinFill(String pin){
		pin = LeftFill(String.valueOf(pin.length()/2), 2, "0") + pin; //old lenth/2
		byte[] bsrc = Util.unhexba(pin); // old ��Ҫ��ԭ����string ת hex byte[]
		int i = 0,j,srcLen,tpLen;
		tpLen=srcLen=bsrc.length;
		while(tpLen%8!=0)tpLen++;
		byte[] tempsrc = new byte[tpLen];
	    while(i<srcLen){
	       /* �ֶ� + ��λ */
	       for(j=0;j<8;) {
	           if(i<srcLen) {
	               tempsrc[i] = bsrc[i];
				   j++;
			   }
	           else {
	               tempsrc[i] = 0x20; // old ��0x20
				   j++;
			   }
	           i++;
	       }
	    }
	    return tempsrc;
	}


	public  static String LeftFill(String src,int count,String in)
    {
    	if(count>0)
    	{
    		if(src.length()<count)
    		{
	    		int i;
	    		String fills="";
	    		if(in==null || in.equals(""))
	    			in="0";
	    		for(i=0;i<count;i++)
	    		{
	    			fills+=in;
	    		}
	    		return fills.substring(0,fills.length()-src.length())+src;
    		}else
    		{
    			return src;
    		}
    	}else
    	{
    		return src;
    	}
    }

}
