package com.froad.fft.test;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.io.FilenameUtils;

import com.froad.fft.dto.MerchantCommentDto;
import com.froad.fft.dto.MerchantDto;
import com.froad.fft.extend.EnumConverter;
import com.froad.fft.persistent.common.enums.MerchantType;
import com.froad.fft.persistent.entity.Merchant;
import com.froad.fft.persistent.entity.MerchantComment;

public class test {
	
	/** ConvertUtilsBean */
	private static final ConvertUtilsBean convertUtils;
	
	static {
		convertUtils = new ConvertUtilsBean() {
			
			@Override
			public String convert(Object value) {
				if (value != null) {
					Class<?> type = value.getClass();
					if (type.isEnum() && super.lookup(type) == null) {
						super.register(new EnumConverter(type), type);
					} else if (type.isArray() && type.getComponentType().isEnum()) {
						if (super.lookup(type) == null) {
							ArrayConverter arrayConverter = new ArrayConverter(type, new EnumConverter(type.getComponentType()), 0);
							arrayConverter.setOnlyFirstToString(false);
							super.register(arrayConverter, type);
						}
						Converter converter = super.lookup(type);
						return ((String) converter.convert(String.class, value));
					}
				}
				return super.convert(value);
			}

			@Override
			public Object convert(String value, Class clazz) {
				if (clazz.isEnum() && super.lookup(clazz) == null) {
					super.register(new EnumConverter(clazz), clazz);
				}
				return super.convert(value, clazz);
			}
			
			@Override
			public Object convert(Object value, Class targetType) {
				if (super.lookup(targetType) == null) {
					if (targetType.isEnum()) {
						super.register(new EnumConverter(targetType), targetType);
					} else if (targetType.isArray() && targetType.getComponentType().isEnum()) {
						ArrayConverter arrayConverter = new ArrayConverter(targetType, new EnumConverter(targetType.getComponentType()), 0);
						arrayConverter.setOnlyFirstToString(false);
						super.register(arrayConverter, targetType);
					}
				}
				return super.convert(value, targetType);
			}
		};
	}

	public static void main(String[] args) throws SocketException, IOException {
		// TODO Auto-generated method stub
//		Pattern p=Pattern.compile("/druid.*");
//		
//		System.out.println("http://localhost:8080/fft-server".length());
//		boolean result=p.matcher("http://localhost:8080/fft-server/druid/index.html".substring(32)).matches();
//		System.out.println(result);
//		
//		String path="/fft/image/201402/6335694d-52b0-4405-83aa-704ebf65abf8.jpg";
//		String directory = StringUtils.substringBeforeLast(path, "/");
//		String filename = StringUtils.substringAfterLast(path, "/");
//		System.out.println(directory);
//		System.out.println(filename);
//		
//		String directory_1 = path.substring(0, path.lastIndexOf("/") + 1);
//		System.out.println(path.lastIndexOf("/"));
//		System.out.println(directory_1);
//		
//		System.out.println(path.indexOf("/"));
//		System.out.println(path.substring(path.lastIndexOf("/")+1));   
		
		String filename="/froad-fft/image/201402/bed32ab1-30cf-462c-b106-86e01b540a94.jpg";
		
		String name = FilenameUtils.getName(filename);
		String path = FilenameUtils.getFullPath(filename);
		System.out.println(name);
		System.out.println(path);
		
//		File f = new File("D://4.jpg");
//        InputStream is = new FileInputStream(f);
//		
//		FTPClient ftpClient = new FTPClient();
//		ftpClient.connect("172.18.5.133", 21);
//		ftpClient.login("ftpuser", "qaz123");
//		ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
//		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//		ftpClient.enterLocalPassiveMode();
//		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
//			String directory = StringUtils.substringBeforeLast(path, "/");
//			String filename = StringUtils.substringAfterLast(path, "/");
//			if (!ftpClient.changeWorkingDirectory(directory)) {
//				String[] paths = StringUtils.split(directory, "/");
//				String p = "/";
//				ftpClient.changeWorkingDirectory(p);
//				for (String s : paths) {
//					p += s + "/";
//					if (!ftpClient.changeWorkingDirectory(p)) {
//						ftpClient.makeDirectory(s);
//						ftpClient.changeWorkingDirectory(p);
//					}
//				}
//			}
//			ftpClient.storeFile(filename, is);
//			ftpClient.logout();
//		}
		
		Merchant merchant=new Merchant();
		merchant.setType(MerchantType.froad);
		
		com.froad.fft.enums.MerchantType mt=(com.froad.fft.enums.MerchantType) convertUtils.convert(merchant.getType().name(), com.froad.fft.enums.MerchantType.class);
		
		MerchantDto merchantDto=new MerchantDto();
		merchantDto.setType(mt);
		
		MerchantComment merchantComment=new MerchantComment();
		merchantComment.setStarLevel(MerchantComment.StarLevel.one_star);
		
		System.out.println(convertUtils.convert(merchantComment, com.froad.fft.dto.MerchantCommentDto.StarLevel.class));
		
		
		MerchantCommentDto merchantCommentDto=new MerchantCommentDto();
		merchantCommentDto.setStarLevel(com.froad.fft.dto.MerchantCommentDto.StarLevel.valueOf(merchantComment.getStarLevel().toString()));
		
		
		System.out.println("end");
	}

}
