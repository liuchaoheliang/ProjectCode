package com.froad.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.core.io.ClassPathResource;

import com.froad.QrCodeConstants;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.QrCodeMapper;
import com.froad.enums.MonitorPointEnum;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.QrCode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FileReaderRandomBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.gimpy.DefaultGimpyEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class QrCodeUtil {
	
	public static final Map<String, String> proMap  = PropertiesUtil.loadProperties(QrCodeConstants.QR_PROPERTIES_NAME);
	
	
	public static WordToImage wordImage = null;
	
	public static EquationCaptchaEngine engine = null;
	
	/**
	 * 业务监控
	 */
	private static MonitorService monitorService = new MonitorManager();
	
	static{
		engine = new  EquationCaptchaEngine();
		wordImage = engine.buildInitialFactory();
	}
	/**
	 * 生成二维码,并返回二维码URL
	 * 
	 * @param keyword - QrCodeType + content
	 * @param qrCodeFolder
	 * @param logoFolder
	 * @param logoUrl
	 * @param clientId
	 * @return
	 */
	public String generateQrCode(String keyword, String logoPath, String clientId) throws Exception{
		String fileName = null;
		String qrCodeDir = null;
		String qrCodePath = null;
		String logoQrPath = null;
		String qrCodeUrl = null;
		String scpDir = null;
		String scpFilePath = null;
		String nginxRoot = null;
		File file = null;
		String content = null;
		
		try {
			/**
			 * 普通二维码文件命名为：UUID + .png
			 */
			fileName = new StringBuffer(UUID.randomUUID().toString().replace("-", "")).append(".png").toString();
			qrCodeDir = proMap.get(QrCodeConstants.PROPERTY_QRCODE_LOCAL_DIR).replace(QrCodeConstants.DOUBLE_BACKSLASH, QrCodeConstants.SLASH);
			qrCodePath = new StringBuffer(qrCodeDir).append(fileName).toString();
			
			/**
			 * 生成普通二维码
			 */
			LogCvt.info(new StringBuffer("开始生成普通二维码: ").append(keyword).toString());
			
			/**
			 * 2015/08/18 modify by terry
			 * 修改二维码生成规则，规则如下：
			 * 1.面对面与普通商品生成规则改为:product_id+";"+type
			 * 2.提供码（券）规则不变,依然为：type+product_id
			 */
			char type = keyword.charAt(0);//0=面对面；1=普通商品2=提货码（券）
			content = keyword.substring(1);//去掉QrCodeType
			switch (type) {
			case '0':
			case '1':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
				content = content+";"+type;
				break;
			default:
				break;
			}		
			
			generateNormalQrCode(content, qrCodePath, QrCodeConstants.QR_IMAGE_WIDTH, QrCodeConstants.QR_IMAGE_HEIGHT);
			
			/**
			 * 1.获取银行logo
			 * 2.添加银行logo到普通二维码图片
			 */
			if (null != logoPath){
				LogCvt.info(new StringBuffer("开始生成带logo二维码: ").append(keyword).append(clientId).toString());
				fileName = new StringBuffer(UUID.randomUUID().toString().replace("-", "")).append("_").append(clientId).append(".png").toString();
				logoQrPath = new StringBuffer(qrCodeDir).append(fileName).toString();
				
				// 添加银行logo到二维码
				generateLogoQrCode(qrCodePath, logoPath, logoQrPath);
				
				// 删除不带logo的本地二维码图片
				file = new File(qrCodePath);
				file.delete();
				
				qrCodePath = logoQrPath;
			}
			
			scpDir = proMap.get(QrCodeConstants.PROPERTY_SCP_QRCODE_DIR).replace(QrCodeConstants.DOUBLE_BACKSLASH, QrCodeConstants.SLASH);
			nginxRoot = proMap.get(QrCodeConstants.PROPERTY_ENGINX_ROOT_QRCODE);
			
			// 上传文件
			scpFilePath = uploadQrCode(clientId, qrCodePath, scpDir);
			LogCvt.info(new StringBuffer("二维码已上传到： ").append(scpFilePath).toString());
			if (null != scpFilePath) {
				qrCodeUrl = new StringBuffer().append(scpFilePath.replace(nginxRoot, "")).toString();
				
				/**
				 * 二维码记录入库
				 */
				insertQrcodeToDB(keyword, clientId, qrCodeUrl);
				LogCvt.info(new StringBuffer("二维码记录已入库：").append(keyword).toString());
			} else {
				qrCodeUrl = null;
			}
			
		} catch (Exception e) {
			qrCodeUrl = null;
			LogCvt.error(new StringBuffer("生成二维码失败: ").append(keyword).append(" clientId: ").append(clientId).toString());
			throw e;
		}
		
		// 删除本地二维码图片
		file = new File(qrCodePath);
		file.delete();
		
		LogCvt.info(new StringBuffer(keyword).append(" 二维码URL为： ").append(qrCodeUrl).toString());
		
		return qrCodeUrl;
	}
	
	/**
	 * 获取银行logo到本地
	 * 
	 * @param logoUrl
	 * @param qrLogoPath
	 * @return
	 */
	public boolean getRemoteLogo(String logoUrl, String qrLogoPath){
		URL url = null;
		URLConnection urlConn = null;
		InputStream inputStream = null;
		DataInputStream dataStream = null;
		byte[] dataByte = null;
		OutputStream outputSteam = null;
		boolean isSuccess = false;
		
		try {
			url = new URL(logoUrl);
			urlConn = url.openConnection();
			inputStream = urlConn.getInputStream();
			dataStream = new DataInputStream(inputStream);
			
			// 设置缓冲区大小
			dataByte = new byte[urlConn.getContentLength()];
			
			// 读取数据
			dataStream.readFully(dataByte);
			
			// 写到本地
			outputSteam = new FileOutputStream(qrLogoPath);
			outputSteam.write(dataByte);
			
			outputSteam.close();
			dataStream.close();
			inputStream.close();
			
			isSuccess = true;
		} catch (Exception e) {
			LogCvt.error(new StringBuffer("获取logo失败：").append(logoUrl).toString());
		}
		
		return isSuccess;
	}
	
	/**
	 * 生成普通二维码
	 * 
	 * @param content 二维码内容
	 * @param imagePath 二维码文件路径
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @throws WriterException 
	 * @throws IOException 
	 */
	private void generateNormalQrCode(String content, String imagePath, int width, int height) throws WriterException, IOException{
		Hashtable<EncodeHintType, Object> hints = null;
		BitMatrix byteMatrix = null;
		File image = null;
		
		hints = new Hashtable<EncodeHintType, Object>();
		// 设置编码类型为utf-8
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 设置二维码纠错能力级别为Q（25%容错率）
		if(content.length() > 70){
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		}else{
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
		}
		
		// 生成二维码
		byteMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);
		
		// 写入文件
		image = new File(imagePath);
		MatrixToImageWriter.writeToFile(byteMatrix, "png", image);
	}
	
	/**
	 * 生成带银行logo的二维码
	 * 
	 * @param qrCodePath
	 * @param logoPath
	 * @param logoQrPath
	 * @return
	 * @throws IOException 
	 */
	private void generateLogoQrCode(String qrCodePath, String logoPath, String logoQrPath) throws IOException{
		File orgFile = null;
		File newFile = null;
		BufferedImage orgImage = null;
		BufferedImage qrLogoImage = null;
		BufferedImage bufferedImage = null;
		Graphics2D graphics2D = null;
		int logoWidth = 0;
		int logoHeight = 0;
		int width = 0;
		int height = 0;
		int widthDiff = 0;
		int heightDiff = 0;
		int x = 0;
		int y = 0;

		/**
		 * 普通二维码,不带logo
		 */
		orgFile = new File(qrCodePath);
		orgImage = ImageIO.read(orgFile);
		width = orgImage.getWidth(null);
		height = orgImage.getHeight(null);
		bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		graphics2D = bufferedImage.createGraphics();
		graphics2D.drawImage(orgImage, 0, 0, width, height, null);

		/**
		 * 添加logo到普通二维码
		 */
		qrLogoImage = ImageIO.read(new File(logoPath));
		logoWidth = qrLogoImage.getWidth(null);
		logoHeight = qrLogoImage.getHeight(null);
		graphics2D.setComposite(AlphaComposite.getInstance(
				AlphaComposite.SRC_ATOP, 1));
		widthDiff = width - logoWidth;
		heightDiff = height - logoHeight;
		x = widthDiff / 2;
		y = heightDiff / 2;
		graphics2D.drawImage(qrLogoImage, x, y, logoWidth, logoHeight, null); // 生成logo文件结束
		graphics2D.dispose();

		/**
		 * 写入文件
		 */
		newFile = new File(logoQrPath);
		ImageIO.write(bufferedImage, "png", newFile);
	}
	
	/**
	 * FTP上传二维码图片
	 * 
	 * @param clientId
	 * @param filePath
	 * @param scpDir
	 * @return
	 * @throws IOException 
	 */
	private String uploadQrCode(String clientId, String filePath, String scpDir) throws IOException{
		File file = null;
		String fileName = null;
		StringBuffer strBf = null;
		String curDate = null;
		String ftpFilePath = null;
		
		file = new File(filePath);
		fileName = file.getName();
		
		/**
		 * 上传二维码到服务器
		 */
		strBf = new StringBuffer(scpDir);
		if (null == clientId){
			strBf.append("null");
		} else {
			strBf.append(clientId);
		}
		strBf.append("/");
		
		// 创建client_id目录
		ScpUtil.createDir(strBf.toString());
		
		// 创建日期目录
		curDate = new SimpleDateFormat("yyyy-MM").format(new Date());
		strBf.append(curDate).append("/");
		ScpUtil.createDir(strBf.toString());
		
		ScpUtil.uploadFile(filePath, fileName, strBf.toString(), false);
		
		ftpFilePath = strBf.append(fileName).toString();
		return ftpFilePath;
	}
	
	/**
	 * FTP上传图片验证码
	 * 
	 * @param filePath
	 * @param scpDir
	 * @return
	 * @throws IOException 
	 */
	private String uploadWordImage(String filePath, String scpDir) throws IOException{
		File file = null;
		String fileName = null;
		StringBuffer strBf = null;
		String curDate = null;
		String ftpFilePath = null;
		
		file = new File(filePath);
		fileName = file.getName();
		
		/**
		 * 上传二维码到服务器
		 */
		strBf = new StringBuffer(scpDir);
		// 创建日期目录
		curDate = new SimpleDateFormat("yyyy-MM-dd-HH").format(new Date());
		strBf.append(curDate).append("/");
		ScpUtil.createDir(strBf.toString());
		
		ScpUtil.uploadFile(filePath, fileName, strBf.toString(), false);
		
		ftpFilePath = strBf.append(fileName).toString();
		
		return ftpFilePath;
	}
	
	/**
	 * 生成二维码记录到mysql数据库
	 * 
	 * @param keyword
	 * @param clientId
	 * @param qrcodeUrl
	 */
	private void insertQrcodeToDB(String keyword, String clientId, String qrcodeUrl) throws Exception{
		SqlSession sqlSession = null;
		QrCodeMapper qrcodeMapper = null;
		QrCode qrCodeEntity = null;
		
		/**
		 * 二维码记录入库
		 */
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			qrcodeMapper = sqlSession.getMapper(QrCodeMapper.class);
			
			qrCodeEntity = new QrCode();
			qrCodeEntity.setClientId(clientId);
			qrCodeEntity.setKeyword(keyword);
			qrCodeEntity.setUrl(qrcodeUrl);
			qrCodeEntity.setCreateTime(new Date());
			qrcodeMapper.addQrCode(qrCodeEntity);
			
			sqlSession.commit();
		}catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} finally {
			if(null != sqlSession)
				sqlSession.close();
		}
	}
	
	public static void main(String args[]) {
//		for(int i = 0;i < 100; i++) {
//			QrCodeUtil q = new QrCodeUtil();
//			q.generateCheckCode(i + "" + (i+1) + "" + (i+2) + "" + (i + 3));
//		}
		
		String url = "https://test1mp.ubank365.com/taizhou/m/order/qrcode?type=0&id=1603AE70000";
		String imagePath = "D:\\a.png";
		try {
			new QrCodeUtil().generateNormalQrCode(url, imagePath, 300, 300);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public String generateCheckCode(String content) {
		File file = null;
		String imagePath = null;
		String localImageDir = null;
		String ftpDir = null;
		String ftpImagePath = null;
		String url = null;
		String nginxRoot = null;
		
		try {
			monitorService.send(MonitorPointEnum.Qrcode_Wordimage_Gen_Count);//图片验证码生成每秒触发次数
			
			WordGenerator wordGenerator = new CustomWordGenerator(content);
			engine.addFactory(new GimpyFactory(wordGenerator, wordImage));
			BufferedImage image = wordImage.getImage(content);
			
			localImageDir = proMap.get(QrCodeConstants.PROPERTY_WORDIMAGE_LOCAL_DIR).replace(QrCodeConstants.DOUBLE_BACKSLASH, QrCodeConstants.SLASH);
			ftpDir = proMap.get(QrCodeConstants.PROPERTY_SCP_WRODIMAGE_DIR).replace(QrCodeConstants.DOUBLE_BACKSLASH, QrCodeConstants.SLASH);
			nginxRoot = proMap.get(QrCodeConstants.PROPERTY_ENGINX_ROOT_WORDIMAGE);
					
			// 文字图片格式为: UUID + ".jpeg"
			imagePath = new StringBuffer(localImageDir).append(UUID.randomUUID().toString().replace("-", "")).append(".jpeg").toString();
			//生成文字图片
			file = new File(imagePath);
			ImageIO.write(image, "jpeg", file);
			
			//上传到ftp目录
			ftpImagePath = uploadWordImage(imagePath, ftpDir);
			
			if (null != ftpImagePath){
				url = new StringBuffer().append(ftpImagePath.replace(nginxRoot, "")).toString();
			}
			LogCvt.info(new StringBuffer(content).append("图片验证码URL为： ").append(url).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(new StringBuffer("生成图片验证码失败: ").append(content).toString());
			monitorService.send(MonitorPointEnum.Qrcode_Wordimage_Gen_Failed_Count);//图片验证码生成每秒失败次数
		}
		
		if(file != null) {
			//删除本地文件
			file.delete();
		}
		
		return url;
	}
	
	
	/**
	 * 生成图片验证码, 并返回其url
	 * 
	 * @param content
	 * @return 图片验证码url
	 */
	public String drawWordImage(String content) {
		File file = null;
		int width = 160;
		int height = 60;
		BufferedImage bufferedImage = null;
		Graphics2D graphics2D = null;
		Font font = null;
		String imagePath = null;
		String localImageDir = null;
		String ftpDir = null;
		String ftpImagePath = null;
		String url = null;
		FileOutputStream os = null;
		JPEGImageEncoder encoder = null;
		JPEGEncodeParam param = null;
		String nginxRoot = null;
		
		try {
			LogCvt.info(new StringBuffer("开始生成图片验证码：").append(content).toString());
			
			bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			graphics2D = bufferedImage.createGraphics();
			graphics2D.fillRect(0, 0, width, height);
			font = new Font("宋体" , Font.ROMAN_BASELINE , 28);
			graphics2D.setFont(font);
			graphics2D.setColor(Color.black);
			// 字体居中设置
			graphics2D.drawString(content, (width - graphics2D.getFontMetrics()
					.stringWidth(content)) / 2,
					((height - font.getSize()) / 2 + font.getSize()));
			graphics2D.dispose();
			
			localImageDir = proMap.get(QrCodeConstants.PROPERTY_WORDIMAGE_LOCAL_DIR).replace(QrCodeConstants.DOUBLE_BACKSLASH, QrCodeConstants.SLASH);
			ftpDir = proMap.get(QrCodeConstants.PROPERTY_SCP_WRODIMAGE_DIR).replace(QrCodeConstants.DOUBLE_BACKSLASH, QrCodeConstants.SLASH);
			nginxRoot = proMap.get(QrCodeConstants.PROPERTY_ENGINX_ROOT_WORDIMAGE);
					
			/**
			 * 文字图片格式为: UUID + ".jpeg"
			 */
			imagePath = new StringBuffer(localImageDir)
					.append(UUID.randomUUID().toString().replace("-", ""))
					.append(".jpeg").toString();
			
			/**
			 * 生成文字图片
			 */
			file = new File(imagePath);
			os = new FileOutputStream(file);
			encoder = JPEGCodec.createJPEGEncoder(os);
			param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
			param.setQuality(1.0f, false);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(bufferedImage);
			os.close();
			
			/**
			 * 上传到ftp目录
			 */
			ftpImagePath = uploadWordImage(imagePath, ftpDir);
			if (null != ftpImagePath){
				url = new StringBuffer().append(ftpImagePath.replace(nginxRoot, "")).toString();
			}
			
			LogCvt.info(new StringBuffer(content).append(" 图片验证码URL为： ").append(url).toString());
		} catch (IOException e) {
			LogCvt.error(new StringBuffer("生成图片验证码失败: ").append(content).toString());
			LogCvt.error(e.getMessage());
		}
		
		/**
		 * 删除本地文件
		 */
		file.delete();
		
		return url;
	}
	
}

class EquationCaptchaEngine extends DefaultGimpyEngine{ 
	
	public WordToImage buildInitialFactory(){
		//WordGenerator wordGenerator = new CustomWordGenerator(content);
		BackgroundGenerator backgroundGenerator = new FileReaderRandomBackgroundGenerator(QrCodeConstants.IMAGE_WIDTH,QrCodeConstants.IMAGE_HEIGHT,
				new ClassPathResource(QrCodeConstants.BACKGROUND_IMAGE_PATH).getPath());
		FontGenerator fontGenerator = new RandomFontGenerator(QrCodeConstants.MIN_FONT_SIZE,QrCodeConstants.MAX_FONT_SIZE,QrCodeConstants.FONTS);
		TextPaster textPaster = new DecoratedRandomTextPaster(QrCodeConstants.MIN_WORD_LENGTH,QrCodeConstants.MAX_WORD_LENGTH,
				new RandomListColorGenerator(QrCodeConstants.COLORS),true, new TextDecorator[] {}); 
		return  new ComposedWordToImage(fontGenerator, backgroundGenerator, textPaster);
   	   // addFactory(new GimpyFactory(wordGenerator, word2image));
   	 //   return word2image.getImage(content);
	}
}

class CustomWordGenerator implements WordGenerator{
	
	private char[] possiblesChars;
	
	public CustomWordGenerator(String content) {
		this.possiblesChars = content.toCharArray();
	}
	

	@Override
	public String getWord(Integer paramInteger) {
		StringBuffer word = new StringBuffer(this.possiblesChars.length);
		for (int i = 0; i < possiblesChars.length; ++i) {
			word.append(possiblesChars[i]);
		}
		return word.toString();
	}

	@Override
	public String getWord(Integer paramInteger, Locale paramLocale) {
		return getWord(paramInteger);
	}
		
	
}
