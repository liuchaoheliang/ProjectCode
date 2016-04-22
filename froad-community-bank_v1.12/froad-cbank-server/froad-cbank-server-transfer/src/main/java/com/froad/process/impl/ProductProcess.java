package com.froad.process.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.alibaba.druid.support.logging.Log;
import com.froad.common.qrcode.QrCodeGenerateService;
import com.froad.common.qrcode.QrCodeRequestVo;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.ProductGroup;
import com.froad.db.ahui.entity.ProductPresell;
import com.froad.db.ahui.mappers.AreaMapper;
import com.froad.db.ahui.mappers.MerchantExMapper;
import com.froad.db.ahui.mappers.MerchantMapper;
import com.froad.db.ahui.mappers.MerchantOutletMapper;
import com.froad.db.ahui.mappers.OutletBankMapper;
import com.froad.db.ahui.mappers.ProductCommentMapper;
import com.froad.db.chonggou.entity.AreaCG;
import com.froad.db.chonggou.entity.AreaProducts;
import com.froad.db.chonggou.entity.BankOrgCode;
import com.froad.db.chonggou.entity.ClientProductAudit;
import com.froad.db.chonggou.entity.ProductArea;
import com.froad.db.chonggou.entity.ProductBuyLimit;
import com.froad.db.chonggou.entity.ProductCG;
import com.froad.db.chonggou.entity.ProductCategoryCG;
import com.froad.db.chonggou.entity.ProductCategoryInfoCG;
import com.froad.db.chonggou.entity.ProductCityArea;
import com.froad.db.chonggou.entity.ProductCityOutlet;
import com.froad.db.chonggou.entity.ProductDetail;
import com.froad.db.chonggou.entity.ProductOutlet;
import com.froad.db.chonggou.entity.ProductOutletInfo;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.AreaMapperCG;
import com.froad.db.chonggou.mappers.MerchantMapperCG;
import com.froad.db.chonggou.mappers.OrgMapper;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.ModuleID;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ProductStatus;
import com.froad.enums.ProductType;
import com.froad.enums.QrCodeType;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.common.enums.ProductVerifyType;
import com.froad.fft.persistent.entity.Area;
import com.froad.fft.persistent.entity.Merchant;
import com.froad.fft.persistent.entity.MerchantOutlet;
import com.froad.fft.persistent.entity.OutleBank;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.persistent.entity.ProductComment;
import com.froad.fft.persistent.entity.ProductExpand;
import com.froad.fft.persistent.entity.ProductFamous;
import com.froad.logback.LogCvt;
import com.froad.po.Org;
import com.froad.process.AbstractProcess;
import com.froad.util.ClientProductAuditUtil;
import com.froad.util.Constans;
import com.froad.util.JsoupUtil;
import com.froad.util.MongoTableName;
import com.froad.util.RedisKeyUtil;
import com.froad.util.SimpleID;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ProductProcess extends AbstractProcess{
	
	private static SimpleID simpleID = new SimpleID(ModuleID.product);
	private String CLIENTID="anhui";
	private Map<String,Transfer> transferTypeEnumMap=new HashMap<String,Transfer>();
	private Map<Object,Object> areaMap=new HashMap<Object,Object>();
	private AreaMapperCG areaMapper;
	public ProductProcess(String name,ProcessServiceConfig config) {
		super(name,config);
	}


	@Override
	public void process() {
		LogCvt.info("ProductProcess:Process.........");
		InsertProductToCG();//插入cb_product表中
		
		
		
	}
	
	public void InsertProductToCG(){
		LogCvt.info("开始cb_product表转换操作");
		
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		boolean isOrg=false;//判断是否普通商户
	//	boolean isPrice=false;//价格不为空标记
		//先将原安徽数据查出
		
			/*********************************************数据库操作**************************************************/
			com.froad.db.chonggou.mappers.ProductMapper productCGMapper=sqlSession.getMapper(com.froad.db.chonggou.mappers.ProductMapper.class);
			TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
			com.froad.db.ahui.mappers.ProductMapper productMapperAnhui = ahSqlSession.getMapper(com.froad.db.ahui.mappers.ProductMapper.class);
			MerchantMapperCG merchantMapper=sqlSession.getMapper(MerchantMapperCG.class);
			com.froad.db.chonggou.mappers.ProductCategoryMapper productCategoryMapper=sqlSession.getMapper(com.froad.db.chonggou.mappers.ProductCategoryMapper.class);
			ProductCommentMapper productCommentMapper=ahSqlSession.getMapper(ProductCommentMapper.class);
			areaMapper=sqlSession.getMapper(AreaMapperCG.class);
			/*********************************************数据库操作**************************************************/
			/**
			 * 将中间表存入内存中,涉及到门店表,商户表
			 */
			List<Transfer> transferMerchants=transferMapper.queryGroupList(TransferTypeEnum.merchant_id);
			List<Transfer> transferOutlets=transferMapper.queryGroupList(TransferTypeEnum.outlet_id);
			List<Transfer> transferCategorys=transferMapper.queryGroupList(TransferTypeEnum.productcategory_id);
			List<Transfer> transferAreas=transferMapper.queryGroupList(TransferTypeEnum.area_id);
			List<AreaCG> areas=areaMapper.findallArea();
			for(Transfer transfer:transferMerchants){
				transferTypeEnumMap.put(transfer.getOldId()+transfer.getType(), transfer);
			}
			for(Transfer transfer:transferOutlets){
				transferTypeEnumMap.put(transfer.getOldId()+transfer.getType(), transfer);
			}
			for(Transfer transfer:transferCategorys){
				transferTypeEnumMap.put(transfer.getOldId()+transfer.getType(), transfer);
			}
			for(Transfer transfer:transferAreas){
				transferTypeEnumMap.put(transfer.getOldId()+transfer.getType(), transfer);
			}
			for(AreaCG area:areas){
				areaMap.put(area.getId(), area);
			}
			
			List<Product> productsAnhui=productMapperAnhui.selectAllProductInAnhui();
			for(Product productAnhui:productsAnhui){
				LogCvt.info("查询的productId为="+productAnhui.getId());
				ProductCG product=new ProductCG();
				//寻找转换后的merchant_id
				//Transfer transfer=transferMapper.queryNewId(productAnhui.getMerchantId()+"", TransferTypeEnum.merchant_id);
				Transfer transfer=transferTypeEnumMap.get(productAnhui.getMerchantId()+TransferTypeEnum.merchant_id.getCode());
				product.setMerchantId(transfer.getNewId());
				product.setClientId(CLIENTID);
				//机构码,先根据Merchant_id寻找商户orgcode再处理
				/**
				 * 依赖商户表,商户表跑好以后去查找org_code
				 */
				com.froad.po.Merchant merchant=merchantMapper.findMerchantByMerchantId(product.getMerchantId());
				//商户orgcode就是商品的org_code
				if(merchant!=null){
					//新增商户名称字段
					if(merchant.getMerchantName()!=null&&!"".equals(merchant.getMerchantName())){
						product.setMerchantName(merchant.getMerchantName());
					}else{
						product.setMerchantName("");
					}
//					if(!merchant.getMerchantStatus()){//是否为机构商户
//						product.setOrgCode(merchant.getOrgCode());
//				//		isOrg=true;
//					}
//					else{
					    /**
					     * ********************预售机构特殊处理***********************
					     * 如果是预售商品的四级机构按照org_path_tree,否则则按商户的四级机构
					     */
					    boolean flag=false;
                    	if(productAnhui.getIsEnablePresell()){
    						if(productAnhui.getOrgTreePath()!=null){
    							String treepath=Constans.splitOrgTreePath(productAnhui.getOrgTreePath());
    							String[] treepaths=treepath.split(",");
    							if(treepaths.length==3){
    								product.setProOrgCode(treepaths[0]);
    								product.setCityOrgCode(treepaths[1]);
    								product.setCountryOrgCode(treepaths[2]);
    								product.setOrgCode(treepaths[2]);
    							}else if(treepaths.length==2){
    								product.setProOrgCode(treepaths[0]);
    								product.setCityOrgCode(treepaths[1]);
    								product.setCountryOrgCode("");
    								product.setOrgCode(treepaths[1]);
    							}else{
    								product.setProOrgCode(treepaths[0]);
    								product.setCityOrgCode("");
    								product.setCountryOrgCode("");
    								product.setOrgCode(treepaths[0]);
    							}
    						}else{
    							product.setProOrgCode("");
								product.setCityOrgCode("");
								product.setCountryOrgCode("");
								product.setOrgCode("");
    						}
    					}else{
    						if(merchant.getCityOrgCode()!=null&&merchant.getCountyOrgCode()!=null&&merchant.getProOrgCode()!=null&&merchant.getOrgCode()!=null){
    							product.setProOrgCode(merchant.getProOrgCode());
    							product.setCountryOrgCode(merchant.getCountyOrgCode());
    							product.setCityOrgCode(merchant.getCityOrgCode());
    							product.setOrgCode(merchant.getOrgCode());	
    							flag=true;
    						}
    						if(merchant.getCityOrgCode()!=null&&merchant.getCountyOrgCode()!=null&&merchant.getProOrgCode()!=null){
    							if(!flag){
    								product.setProOrgCode(merchant.getProOrgCode());
    								product.setCityOrgCode(merchant.getCityOrgCode());
    								product.setCountryOrgCode(merchant.getCountyOrgCode());
    								product.setOrgCode(merchant.getCountyOrgCode());
    								flag=true;
    							}
    						}
    						if(merchant.getCityOrgCode()!=null&&merchant.getProOrgCode()!=null) {
    						   if(!flag){
    							   product.setProOrgCode(merchant.getProOrgCode());
    								product.setCityOrgCode(merchant.getCityOrgCode());
    								product.setOrgCode(merchant.getCityOrgCode());
    								flag=true;
    						   }
    						}
                            if(merchant.getProOrgCode()!=null){
                            	 if(!flag){
                            			product.setProOrgCode(merchant.getProOrgCode());
            							product.setOrgCode(merchant.getProOrgCode());
      						   }
                            }
    					}
						
                    
				//	}
//					if(isOrg){
//						setOrgCode(product,productCGMapper);//设置Org_code
//					}
//				}else{
//					product.setOrgCode("0000");//万一没有商户的暂时处理
//				}
				//生成product_id,生成后插入转换表中
				//Transfer transferId=transferMapper.queryNewId(String.valueOf(productAnhui.getId()), TransferTypeEnum.product_id);
//				if(transferId==null){
//				
//				}
//			else{
//					product.setProductId(transferId.getNewId());
//				}
				product.setProductId(simpleID.nextId());
				Transfer transfer1=new Transfer();
				transfer1.setOldId(String.valueOf(productAnhui.getId()));
				transfer1.setNewId(String.valueOf(product.getProductId()));
				transfer1.setType(TransferTypeEnum.product_id);
				transferMapper.insert(transfer1);
				//模拟
				if(productAnhui.getCreateTime()!=null){
					product.setCreateTime((productAnhui.getCreateTime()));
				}
				//上下架状态 判断前需要判断商品是否有效
				if(productAnhui.getIsEnable()){
					if(productAnhui.getIsMarketable()){
						product.setIsMarketable("1");//已上架
						//由于名优特惠产品没有上架时间，判断一下
						if(productAnhui.getRackTime()==null){
							product.setRackTime(productAnhui.getUpdateTime());
						}else{
							product.setRackTime(productAnhui.getRackTime());
						}
					}else{
						product.setIsMarketable("2");//下架
						product.setRackTime(productAnhui.getRackTime());
						String date1=format.format(date);
						try {
							product.setDownTime(format.parse(date1));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}else{
					product.setIsMarketable("3");//逻辑删除
				}
				
				if(productAnhui.getDescription()!=null&&!"".equals(productAnhui.getDescription())){
					product.setIntroduction(Constans.converText(productAnhui.getDescription()));
				}else{
					product.setIntroduction("");
				}
				if(productAnhui.getBuyKnow()!=null&&!"".equals(productAnhui.getBuyKnow())){
					product.setBuyKnow(JsoupUtil.getHtmlSpanTextByTag(productAnhui.getBuyKnow()));
				}
				//如果pernumber和FileCardMxaNumber都为0则为is_limit为false
				if(productAnhui.getPerNumber()!=null&&productAnhui.getFilmCardMaxNumber()!=null){
					if(productAnhui.getPerNumber()==0&&productAnhui.getFilmCardMaxNumber()==0){
						product.setIsLimit(false);
					}else{
						product.setIsLimit(true);
					}
				}else{
					product.setIsLimit(false);
				}
				//价格处理，需要乘以1000
				boolean isBankPoint=false;
				if(productAnhui.getSpecialProductType()!=null){
					if(productAnhui.getSpecialProductType().getCode().equals("03")||productAnhui.getSpecialProductType().getCode().equals("04")){
						product.setPrice(DealWithPrice(productAnhui.getBankPoints(),true));
					}
					else{
						product.setPrice(DealWithPrice(productAnhui.getPrice(),true));
					}
					isBankPoint=true;
				}else if(productAnhui.getPrice()!=null&&!isBankPoint){
					product.setPrice(DealWithPrice(productAnhui.getPrice(),true));
					}else{
						product.setPrice(0);
					}
				//销量处理
				if(productAnhui.getSellCount()!=null){
					product.setSellCount(productAnhui.getSellCount());
				}else{
					product.setSellCount(0);
				}
				
				/**配送规则,以及对buyknow,start_time的处理
				 * 指定商品类型可以选择的配送方式(0送货上门,1网点自提,2配送或自提)
				 * 比如代表团购只能自提;预售可以自提或配送;
				 * 名优特惠只能配送;在线积分只能配送;网点礼品只能自提
				 * 名优特惠,预售,团购的BriefIntroduction为summary,buyknow,starttime,endtime以各自表为准
				 */
				if(productAnhui.getProductGroupId()!=null){
					if(productAnhui.getProductGroupId()==0L&&productAnhui.getIsEnableGroup()){//数据库中特殊处理
						product.setType(ProductType.group.toString());
						product.setDeliveryOption("1");
						product.setStartTime(new Date());
						product.setEndTime(new Date());
						productAnhui.setPerNumber(0);
						product.setIsLimit(false);
					}
				}
				
				if(productAnhui.getIsEnableGroup()&&productAnhui.getProductGroupId()!=0L){//团购只能自提
					product.setDeliveryOption("1");
					ProductGroup progroup=productMapperAnhui.selectProductGroupById(productAnhui.getProductGroupId());
					if(progroup.getSummary()!=null&&!"".equals(progroup.getSummary())){
						product.setBriefIntroduction(JsoupUtil.getHtmlText(progroup.getSummary()));
					}else{
						product.setBriefIntroduction("");
					}
					if(progroup.getBuyKnow()!=null&&!"".equals(progroup.getBuyKnow())){
						product.setBuyKnow(JsoupUtil.getHtmlText(progroup.getBuyKnow()));
					}
					if(progroup.getStartTime()!=null){
						product.setStartTime(progroup.getStartTime());
					}
					if(progroup.getEndTime()!=null){
						product.setEndTime(progroup.getEndTime());
					}
					if(progroup.getPerNumber()!=null&&productAnhui.getFilmCardMaxNumber()!=null){
						if(productAnhui.getPerNumber()==0&&productAnhui.getFilmCardMaxNumber()==0){
							product.setIsLimit(false);
						}else{
							product.setIsLimit(true);
						}
						productAnhui.setPerNumber(progroup.getPerNumber());
					}
					if(progroup.getTrueBuyerNumber()!=null&&progroup.getTrueBuyerNumber()!=0){
						product.setSellCount(progroup.getTrueBuyerNumber());
					}
					if(progroup.getDescription()!=null&&!"".equals(progroup.getDescription())){
						product.setIntroduction(Constans.converText(progroup.getDescription()));
					}
					product.setType(ProductType.group.toString());
					product.setProductGroup(progroup);
					//团购新增区域ID
					
				}else if(productAnhui.getIsEnablePresell()){//预售
					if(productAnhui.getDistributionType()!=null){
						if(productAnhui.getDistributionType().equals("0")){
							product.setDeliveryOption("1");
						}else if(productAnhui.getDistributionType().equals("1")){
							product.setDeliveryOption("0");
						}
					}else{
							product.setDeliveryOption("2");
					}
					ProductPresell propresell=productMapperAnhui.selectProductPresellById(productAnhui.getProductPresellId());
					if(productAnhui.getDescription()!=null&&!"".equals(productAnhui.getDescription())){
						product.setBriefIntroduction(productAnhui.getDescription());
					}else{
						product.setBriefIntroduction("");
					}
					if(propresell.getBuyKnow()!=null&&!"".equals(JsoupUtil.getHtmlSpanTextByTag(propresell.getBuyKnow()))){
						product.setBuyKnow(propresell.getBuyKnow());
					}
					if(propresell.getStartTime()!=null){
						product.setStartTime(propresell.getStartTime());
					}
					if(propresell.getEndTime()!=null){
						product.setEndTime(propresell.getEndTime());
					}
					if(propresell.getPerNumber()!=null&&productAnhui.getFilmCardMaxNumber()!=null){
						if(productAnhui.getPerNumber()==0&&productAnhui.getFilmCardMaxNumber()==0){
							product.setIsLimit(false);
						}else{
							product.setIsLimit(true);
						}
						productAnhui.setPerNumber(propresell.getPerNumber());
					}
					if(propresell.getTrueBuyerNumber()!=null&&propresell.getTrueBuyerNumber()!=0){
						product.setSellCount(propresell.getTrueBuyerNumber());
					}
					if(productAnhui.getIntroduction()!=null&&!"".equals(productAnhui.getIntroduction())){
						product.setIntroduction(Constans.converText(productAnhui.getIntroduction()));
					}
					product.setType(ProductType.presell.toString());
					product.setProductPresell(propresell);
				}else if(productAnhui.getIsEnableFamous()){//名优特惠
					product.setDeliveryOption("0");
					ProductFamous profamous=productMapperAnhui.selectProductFamousById(productAnhui.getProductFamousId());
					if(profamous.getSummary()!=null&&!"".equals(profamous.getSummary())){
						product.setBriefIntroduction(profamous.getSummary());
					}else{
						product.setBriefIntroduction("");
					}
					if(profamous.getBuyKnow()!=null&&!"".equals(profamous.getBuyKnow())){
						product.setBuyKnow(JsoupUtil.getHtmlSpanTextByTag(profamous.getBuyKnow()));
					}
					if(profamous.getStartTime()!=null){
						product.setStartTime(profamous.getStartTime());
					}
					if(profamous.getEndTime()!=null){
						product.setEndTime(profamous.getEndTime());
					}
					if(profamous.getPerNumber()!=null&&productAnhui.getFilmCardMaxNumber()!=null){
						if(productAnhui.getPerNumber()==0&&productAnhui.getFilmCardMaxNumber()==0){
							product.setIsLimit(false);
						}else{
							product.setIsLimit(true);
						}
						productAnhui.setPerNumber(profamous.getPerNumber());
					}
					product.setType(ProductType.special.toString());
					if(profamous.getDescription()!=null&&!"".equals(profamous.getDescription())){
						product.setIntroduction(Constans.converText(profamous.getDescription()));
					}
				}
				
				if(productAnhui.getSpecialProductType()!=null){
					if(productAnhui.getSpecialProductType().getCode().equals("03")){
						ProductExpand productExpand= productMapperAnhui.selectExchangeByProductId(productAnhui.getId());
						product.setDeliveryOption("0");
						product.setType(ProductType.onlinePoint.toString());
						if(productExpand!=null){
							if(productExpand.getEffectTime()!=null){
								product.setStartTime(productExpand.getEffectTime());
							}else{
								product.setStartTime(productAnhui.getCreateTime());
							}
							if(productExpand.getExpireTime()!=null){
								product.setEndTime(productExpand.getExpireTime());
							}else{
								product.setEndTime(new Date());
							}
						}else{
							product.setStartTime(productAnhui.getCreateTime());
							String endtime="2015-12-31 23:55:46";
							try {
								product.setEndTime(format.parse(endtime));
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						if(productAnhui.getDescription()!=null&&!"".equals(productAnhui.getDescription())){
							product.setBriefIntroduction(productAnhui.getDescription());
						}
					}else if(productAnhui.getSpecialProductType().getCode().equals("04")){
						ProductExpand productExpand= productMapperAnhui.selectExchangeByProductId(productAnhui.getId());
						product.setDeliveryOption("1");
						product.setType(ProductType.dotGift.toString());
						if(productExpand!=null){
							if(productExpand.getEffectTime()!=null){
								product.setStartTime(productExpand.getEffectTime());
							}else{
								product.setStartTime(productAnhui.getCreateTime());
							}
							if(productExpand.getExpireTime()!=null){
								product.setEndTime(productExpand.getExpireTime());
							}else{
								product.setEndTime(new Date());
							}
						}else{
							product.setStartTime(productAnhui.getCreateTime());
							String endtime="2015-12-31 23:55:46";
							try {
								product.setEndTime(format.parse(endtime));
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						if(productAnhui.getDescription()!=null&&!"".equals(productAnhui.getDescription())){
							product.setBriefIntroduction(productAnhui.getDescription());
						}
						if(productAnhui.getIntroduction()!=null&&!"".equals(productAnhui.getDescription())){
							product.setIntroduction(Constans.converText(productAnhui.getIntroduction()));
						}
					}
					
				}
				if(productAnhui.getName()!=null&&!"".equals(productAnhui.getName())){
					product.setName(productAnhui.getName());
				}else{
					product.setName("");
				}
				if(productAnhui.getFullName()!=null&&!"".equals(productAnhui.getFullName())){
					product.setFullName(productAnhui.getFullName());
				}else{
					product.setFullName("");
				}
			
				if(productAnhui.getMarketPrice()!=null&&!"".equals(productAnhui.getMarketPrice())){
					product.setMarketPrice(DealWithPrice(productAnhui.getMarketPrice(),true));
				}
				if(productAnhui.getCost()!=null&&!"".equals(productAnhui.getCost())){
					product.setCost(DealWithPrice(productAnhui.getCost(),true));
				}
				if(productAnhui.getWeight()!=null&&!"".equals(productAnhui.getWeight())){
					product.setWeight(productAnhui.getWeight());
				}
				if(productAnhui.getWeightUnit()!=null&&!"".equals(productAnhui.getWeightUnit())){
					product.setWeightUnit(productAnhui.getWeightUnit());
				}
				if(productAnhui.getStore()!=null){
					if(productAnhui.getStore()==-1){
						product.setStore(9999);
					}else{
						product.setStore(productAnhui.getStore());
					}
				}
				
				if(productAnhui.getOrderValue()!=null){
					product.setOrderValue(productAnhui.getOrderValue());
				}
				product.setIsSeckill("0");
				product.setIsBest(productAnhui.getIsBest());
	
					product.setPoint(setAvgPoint(productCommentMapper, productAnhui));
				
				
				/**
				 * 商品审核状态 2审核失败,3未提交,1审核通过,0带审核
				 * 如果商户状态为解约或者禁用，则所属商品一律下架,待审核状态改为未提交
				 */
				if(productAnhui.getProductVerifyType()!=null){
					boolean isNoCommit=true;//判断是否为待审核状态,不为待审核则按原来状态
					if(merchant.getDisableStatus().equals("1")||merchant.getDisableStatus().equals("2")){
						product.setIsMarketable("2");
						if(productAnhui.getProductVerifyType().getCode().equals("0")){
							product.setAuditState(ProductAuditState.noCommit.getCode());
							isNoCommit=false;
						}
						
					}
					if(isNoCommit){
						ProductVerifyType verity=productAnhui.getProductVerifyType();
						if(verity.getCode().equals("2")){
							product.setAuditState(ProductAuditState.failAudit.getCode());
						}else if(verity.getCode().equals("3")){
							product.setAuditState(ProductAuditState.noCommit.getCode());
						}else if(verity.getCode().equals("0")){
							product.setAuditState(ProductAuditState.noAudit.getCode());
						}
						else{
							product.setAuditState(ProductAuditState.passAudit.getCode());
							
						}
					}
					
				}
			
				//审核时间初始化
				String auditime="2001-01-01 00:00:00";
				try {
					product.setAuditTime(format.parse(auditime));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				product.setDeliveryMoney(0);
				if(productAnhui.getAuditComment()!=null&&!"".equals(productAnhui.getAuditComment())){
					product.setAuditComment(productAnhui.getAuditComment());
				}
				product.setAuditOrgCode("");
				//初始审核机构和终审机构  如果没有商户机构的处理
				if(!product.getOrgCode().equals("0000")){
					/**
					 * 根据`org_verify_state`判断（1：省联社审核通过，2：法人行社审核通过，3：网店审核通过）
					 * 
					 */
					boolean orgstate=true;//判断是否已经设置
					if(productAnhui.getOrgVerifyState()!=null){
				      if(productAnhui.getOrgVerifyState().equals("3")){
				    	  /****************预售特殊处理***************/
					    	 //预售根据org_tree_path来判断当前审核机构
					    	 if(productAnhui.getIsEnablePresell()&&productAnhui.getProductVerifyType()!=null){
					    		 if(productAnhui.getProductVerifyType().getCode().equals("0")){
					    			 if(product.getCountryOrgCode()!=null&&!"".equals(product.getCountryOrgCode())){
					    				 product.setAuditStartOrgCode(product.getCountryOrgCode());
								    	 product.setAuditEndOrgCode(product.getProOrgCode());
								    	 product.setAuditOrgCode(product.getCityOrgCode());
								    	 orgstate=false;
					    			 }
					    			 if(product.getCityOrgCode()!=null&&orgstate&&!"".equals(product.getCityOrgCode())){
						    			 product.setAuditStartOrgCode(product.getCityOrgCode());
								    	 product.setAuditEndOrgCode(product.getProOrgCode());
								    	 product.setAuditOrgCode(product.getCityOrgCode());
						    		 }
					    		 }else{//审核通过和审核失败处理
					    			 if(product.getCityOrgCode()!=null){
						    			 product.setAuditStartOrgCode(product.getCityOrgCode());
								    	 product.setAuditEndOrgCode(product.getProOrgCode());
								    	 product.setAuditOrgCode(product.getCityOrgCode());
								    	 orgstate=false;
						    		 }
					    			 if(product.getProOrgCode()!=null&&orgstate){
					    				 product.setAuditStartOrgCode(product.getProOrgCode());
								    	 product.setAuditEndOrgCode(product.getProOrgCode());
								    	 product.setAuditOrgCode(product.getProOrgCode());
					    			 }
					    		 }
					    	 }else{
					    		 if(merchant.getCountyOrgCode()!=null){
							    	 product.setAuditStartOrgCode(merchant.getCountyOrgCode());
							    	 product.setAuditEndOrgCode(merchant.getProOrgCode());
							    	 product.setAuditOrgCode(merchant.getCityOrgCode());
							    	 orgstate=false;
							     }
							     if(merchant.getCityOrgCode()!=null&&orgstate){
							    	 product.setAuditStartOrgCode(merchant.getCityOrgCode());
							    	 product.setAuditEndOrgCode(merchant.getProOrgCode());
							    	 product.setAuditOrgCode(merchant.getCityOrgCode());
							     }
					    	 }
				    	  
				    	  
							    
							
				     }else if(productAnhui.getOrgVerifyState().equals("2")){
				    	 /****************预售特殊处理***************/
				    	 //预售根据org_tree_path来判断当前审核机构
				    	 if(productAnhui.getIsEnablePresell()&&productAnhui.getProductVerifyType()!=null){
				    		 if(productAnhui.getProductVerifyType().getCode().equals("0")){
				    			 if(product.getCityOrgCode()!=null&&!"".equals(product.getCityOrgCode())){
					    			 product.setAuditStartOrgCode(product.getCityOrgCode());
							    	 product.setAuditEndOrgCode(product.getProOrgCode());
							    	 product.setAuditOrgCode(product.getCityOrgCode());
							    	 orgstate=false;
					    		 }
				    			 if(product.getProOrgCode()!=null&&orgstate&&!"".equals(product.getProOrgCode())){
				    				 product.setAuditStartOrgCode(product.getProOrgCode());
							    	 product.setAuditEndOrgCode(product.getProOrgCode());
							    	 product.setAuditOrgCode(product.getProOrgCode());
				    			 }
				    		 }else{//审核通过和审核失败处理
				    			 if(product.getCityOrgCode()!=null){
					    			 product.setAuditStartOrgCode(product.getCityOrgCode());
							    	 product.setAuditEndOrgCode(product.getProOrgCode());
							    	 product.setAuditOrgCode(product.getCityOrgCode());
							    	 orgstate=false;
					    		 }
				    			 if(product.getProOrgCode()!=null&&orgstate){
				    				 product.setAuditStartOrgCode(product.getProOrgCode());
							    	 product.setAuditEndOrgCode(product.getProOrgCode());
							    	 product.setAuditOrgCode(product.getProOrgCode());
				    			 }
				    		 }
				    		    
				    	 }else{
				    		 if(merchant.getCountyOrgCode()!=null){
						    	 product.setAuditStartOrgCode(merchant.getCountyOrgCode());
						    	 product.setAuditEndOrgCode(merchant.getProOrgCode());
						    	 product.setAuditOrgCode(merchant.getProOrgCode());
						    	 orgstate=false;
						     }
						     if(merchant.getCityOrgCode()!=null&&orgstate){
						    	 product.setAuditStartOrgCode(merchant.getCityOrgCode());
						    	 product.setAuditEndOrgCode(merchant.getProOrgCode());
						    	 product.setAuditOrgCode(merchant.getProOrgCode());
						     }
						     //团购商品特殊处理,如果是省联社审核并且为待审核状态的和有效团购商品都改为戴审核
						        if(productAnhui.getIsEnableGroup()&&productAnhui.getProductVerifyType().getCode().equals("0")&&productAnhui.getIsEnable()){
						        	product.setAuditState(ProductAuditState.noAudit.getCode());
						        } 
				    	 }
				    	 
				    	   
				    	 
				    	 
				     }else if(productAnhui.getOrgVerifyState().equals("1")){
				    	 /****************预售特殊处理***************/
				    	 //预售根据org_tree_path来判断当前审核机构
				    	 if(productAnhui.getIsEnablePresell()&&productAnhui.getProductVerifyType()!=null){
				    		 if(productAnhui.getProductVerifyType().getCode().equals("0")){
				    			 if(product.getCityOrgCode()!=null&&!"".equals(product.getCityOrgCode())){
					    			 product.setAuditStartOrgCode(product.getCityOrgCode());
							    	 product.setAuditEndOrgCode(product.getProOrgCode());
							    	 product.setAuditOrgCode(product.getCityOrgCode());
					    		 }
				    			 if(product.getProOrgCode()!=null&&!"".equals(product.getProOrgCode())){
				    				 product.setAuditStartOrgCode(product.getProOrgCode());
							    	 product.setAuditEndOrgCode(product.getProOrgCode());
							    	 product.setAuditOrgCode(product.getProOrgCode());
				    			 }
				    		 }else{//审核通过和审核失败处理
				    			 if(product.getCityOrgCode()!=null){
					    			 product.setAuditStartOrgCode(product.getCityOrgCode());
							    	 product.setAuditEndOrgCode(product.getProOrgCode());
							    	 product.setAuditOrgCode(product.getCityOrgCode());
							    	 orgstate=false;
					    		 }
				    			 if(product.getProOrgCode()!=null&&orgstate){
				    				 product.setAuditStartOrgCode(product.getProOrgCode());
							    	 product.setAuditEndOrgCode(product.getProOrgCode());
							    	 product.setAuditOrgCode(product.getProOrgCode());
				    			 }
				    		 }
				    	 }
				    	 else{
				    		 if(merchant.getCountyOrgCode()!=null){
						    	 product.setAuditStartOrgCode(merchant.getCountyOrgCode());
						    	 product.setAuditEndOrgCode(merchant.getProOrgCode());
						    	 product.setAuditOrgCode(merchant.getProOrgCode());
						    	 orgstate=false;
						     }
					    	 else if(merchant.getCityOrgCode()!=null&&orgstate){
							    	 product.setAuditStartOrgCode(merchant.getCityOrgCode());
							    	 product.setAuditEndOrgCode(merchant.getProOrgCode());
							    	 product.setAuditOrgCode(merchant.getProOrgCode());
							     }
					    	 else {
					    		 product.setAuditStartOrgCode(merchant.getProOrgCode());
					    		 product.setAuditEndOrgCode(merchant.getProOrgCode());
					    		 product.setAuditOrgCode(merchant.getProOrgCode());
					    	 } 
				    	 }
				    	
//				    	 //预售商品特殊处理,如果是省联社审核并且为待审核状态的和有效预售商品都改为审核
					        if(productAnhui.getIsEnablePresell()&&productAnhui.getProductVerifyType().getCode().equals("0")&&productAnhui.getIsEnable()){
					        	product.setAuditState(ProductAuditState.passAudit.getCode());
					        }
				     }
						}
					else{//如果为空则审核通过
						   if(merchant.getCountyOrgCode()!=null){
						    	 product.setAuditStartOrgCode(merchant.getCountyOrgCode());
						    	 product.setAuditEndOrgCode(merchant.getProOrgCode());
						    	 product.setAuditOrgCode(merchant.getCityOrgCode());
						    	 orgstate=false;
						     }
						     if(merchant.getCityOrgCode()!=null&&orgstate){
						    	 product.setAuditStartOrgCode(merchant.getCityOrgCode());
						    	 product.setAuditEndOrgCode(merchant.getProOrgCode());
						    	 product.setAuditOrgCode(merchant.getCityOrgCode());
						    	 orgstate=false;
						     }
						     if(merchant.getProOrgCode()!=null&&orgstate){
						    	 product.setAuditStartOrgCode(merchant.getProOrgCode());
						    	 product.setAuditEndOrgCode(merchant.getProOrgCode());
						    	 product.setAuditOrgCode(merchant.getProOrgCode());
						     }
						     //团购商品如果是待审核状态则特殊处理为待审核状态
//						     if(productAnhui.getIsEnableGroup()&&productAnhui.getProductVerifyType().getCode().equals("0")&&productAnhui.getIsEnable()){
//						    	 if(productAnhui.getIsMarketable()){
//						    		 product.setAuditState(ProductAuditState.passAudit.getCode()); 
//						    	 }else{
//						    		 product.setAuditState(ProductAuditState.noAudit.getCode()); 
//						    	 }
//						    		 
//						     }else
						     if(productAnhui.getSpecialProductType()!=null){
						    	 if(productAnhui.getSpecialProductType().getCode().equals("04")&&productAnhui.getProductVerifyType().getCode().equals("0")&&productAnhui.getIsEnable()){
						    		 product.setAuditState(ProductAuditState.noAudit.getCode()); 
						    	 }
						     }
						     else{
						    	 product.setAuditState(ProductAuditState.passAudit.getCode());  
						    	
						     }
					}
//					ClientProductAuditUtil productAudit=new ClientProductAuditUtil();
//					ClientProductAudit cpa= productAudit.findClientProductAuditByOrgCode(product.getClientId(), product.getOrgCode(), product.getType());
//					if(cpa!=null){
//						product.setAuditStartOrgCode(cpa.getStartAuditOrgCode());
//						product.setAuditEndOrgCode(cpa.getEndAuditOrgCode());
//						product.setAuditOrgCode(cpa.getStartAuditOrgCode());
//					}else{
//						product.setAuditStartOrgCode("0");
//						product.setAuditEndOrgCode("0");
//					}
					
				}else{
					product.setAuditStartOrgCode("0");
					product.setAuditEndOrgCode("0");
				}
				
				//新增商品分类
				Transfer transferCategory=transferTypeEnumMap.get(String.valueOf(productAnhui.getProductCategoryId())+TransferTypeEnum.productcategory_id.getCode());
				if(transferCategory!=null){
					product.setCategoryTreePath(transferCategory.getNewId());
				}else{
					product.setCategoryTreePath("");
				}
				
				
				//库存redis
				setStrinRedis(RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(product.getClientId(), product.getMerchantId(), product.getProductId()),ObjectUtils.toString(product.getStore(), ""));
				//添加商品基本信息
				productCGMapper.addProduct(product);
				//生成二维码
				QrCodeGenerateService Qservice=new QrCodeGenerateService();
				QrCodeRequestVo requestVo=new QrCodeRequestVo(QrCodeType.PRODUCT.getCode()+product.getProductId(),product.getClientId());
				Qservice.generateQrCode(requestVo);
//	        if(ProductAuditState.noAudit.toString().equals(product.getAuditState())){
//	        	   if(ProductType.special.toString().equals(product.getType())){
//		                setPreauditCount(RedisKeyUtil.cbbank_preaudit_mingyou_count_client_id_org_code(product.getClientId(),product.getAuditOrgCode()),1L);
//	                } else if(ProductType.dotGift.toString().equals(product.getType())){
//	                    setPreauditCount(RedisKeyUtil.cbbank_preaudit_duihuan_count_client_id_org_code(product.getClientId(),product.getAuditOrgCode()),1L);
//	                }
//	        }
				//插入商品详情
				insertProductDetail(productAnhui,product,merchant,productCategoryMapper,transferMapper,productCGMapper);
				//放入缓存
				productRedis(product);
				
		}
		}
	}
     public void setOrgCode(ProductCG product,com.froad.db.chonggou.mappers.ProductMapper productCG){
		  Map<String,Object> param = new HashMap<String,Object>();
		 // orgCode=changeOrgCode(orgCode);
		  if(!product.getOrgCode().equals("")&&product.getOrgCode()!=null){
			  param.put("clientId", product.getClientId());
			  param.put("orgCode", product.getOrgCode());//普通商品的org_code是商户的org_code
			  
			  List<BankOrgCode> orgs = productCG.findOrgs(param);
			  BankOrgCode bankOrg = null;
			  if(orgs!=null && orgs.size()>0){
				  bankOrg = orgs.get(0);
			  }
			  if(bankOrg!=null){
				  if(OrgLevelEnum.orgLevel_one.getLevel().equals(bankOrg.getOrgLevel())){//一级机构不用审核
					  product.setAuditState(ProductAuditState.passAudit.toString());
					  product.setProOrgCode(bankOrg.getOrgCode());
				  } else if(OrgLevelEnum.orgLevel_two.getLevel().equals(bankOrg.getOrgLevel())){
					  product.setProOrgCode(bankOrg.getProvinceAgency());
					  product.setCityOrgCode(bankOrg.getOrgCode());
				  } else if(OrgLevelEnum.orgLevel_three.getLevel().equals(bankOrg.getOrgLevel())){
					  product.setProOrgCode(bankOrg.getProvinceAgency());
					  product.setCityOrgCode(bankOrg.getCityAgency());
					  product.setCountryOrgCode(bankOrg.getOrgCode());
				  } else if(OrgLevelEnum.orgLevel_four.getLevel().equals(bankOrg.getOrgLevel())){
					  product.setProOrgCode(bankOrg.getProvinceAgency());
					  product.setCityOrgCode(bankOrg.getCityAgency());
					  product.setCountryOrgCode(bankOrg.getCountryAgency());
				  }
			  }
		  }
     }
     private void setStrinRedis(String key,String value) {
         redis.putString(key, value);
     }
     private void setPreauditCount(String key,Long value){
         String count = redis.getString(key);
         if(count!=null){
             redis.incrBy(key, value);
         } else {
             redis.putString(key, "1");
         }
     }
     public void insertProductDetail(Product product,ProductCG productcg,com.froad.po.Merchant merchant,com.froad.db.chonggou.mappers.ProductCategoryMapper productMapperCG,TransferMapper transferMapper,com.froad.db.chonggou.mappers.ProductMapper productCGMapper){
    	 try{
    		 /*********************************************数据库操作**************************************************/
    		 MerchantOutletMapper merchantOutletMapper=ahSqlSession.getMapper(MerchantOutletMapper.class);
    		// OrgMapper orgMapper=sqlSession.getMapper(OrgMapper.class);
    		 OutletBankMapper outletBankMapper=ahSqlSession.getMapper(OutletBankMapper.class);
    		 /*********************************************数据库操作**************************************************/
    		 boolean flag=false;
    		 /*********************************************基本信息**************************************************/
    		 ProductDetail productDetail=new ProductDetail();
    		 productDetail.setCreateTime(product.getCreateTime());
    		 productDetail.setId(productcg.getProductId());
    		 productDetail.setIsSeckill(productcg.getIsSeckill());
    		 productDetail.setIsBest(BooleanUtils.toInteger(productcg.getIsBest(), 1, 0, 0));
    		 productDetail.setIsLimit(BooleanUtils.toInteger(productcg.getIsLimit(), 1, 0, 0));
    		 productDetail.setName(productcg.getName());
    		 productDetail.setFullName(productcg.getFullName());
    		 productDetail.setBriefIntroduction(productcg.getBriefIntroduction());
    		 productDetail.setProductType(productcg.getType());
    		 productDetail.setPrice(productcg.getPrice());
    		 productDetail.setMarketPrice(productcg.getMarketPrice());
    		 productDetail.setClientId(productcg.getClientId());
    		 productDetail.setIsMarketable(productcg.getIsMarketable());
    		 productDetail.setDeliveryOption(productcg.getDeliveryOption());
    		 productDetail.setSellCount(productcg.getSellCount());
    		 productDetail.setStartTime(productcg.getStartTime());
    		 productDetail.setRackTime(productcg.getRackTime());
    		 productDetail.setEndTime(productcg.getEndTime());
    		 productDetail.setMerchantId(productcg.getMerchantId());
    		 productDetail.setName(productcg.getName());
    		 productDetail.setProductType(productcg.getType());
    		 productDetail.setMerchantName(merchant.getMerchantName());
    		 /*********************************************基本信息**************************************************/
    		 /**
    		  * 查询商品分类情况
    		  * 根据转换后的ID查询详情中对应的分类ID
    		  */
    		 if(product.getProductCategoryId()!=null&&product.getProductCategoryId()!=0){
    			// Transfer transfer=transferMapper.queryNewId(String.valueOf(product.getProductCategoryId()), TransferTypeEnum.productcategory_id);
    			 Transfer transfer=transferTypeEnumMap.get(String.valueOf(product.getProductCategoryId())+TransferTypeEnum.productcategory_id.getCode());
    			 if(transfer!=null){
        			 ProductCategoryCG productCategoryCG=productMapperCG.findProductCategoryById(Long.parseLong(transfer.getNewId()));
        			 List<ProductCategoryInfoCG> productCategoryInfo = new ArrayList<ProductCategoryInfoCG>();
        			 String treePath=productCategoryCG.getTreePath();
        			 ProductCategoryInfoCG  productCategory=null;
        			 //添加商品分类到详情表
        			 if(treePath != null){
        				 String[] pcids = treePath.split(" ");
        				 boolean isContain = false;
        				 for(String pcid : pcids){
        					 productCategory = new ProductCategoryInfoCG();
        					 productCategory.setProductCategoryId(Long.valueOf(pcid));
        					 productCategoryInfo.add(productCategory);
        					 if(productCategoryCG.getId().longValue()==Long.valueOf(pcid).longValue()){
        						 isContain=true;
        					 }
        				 }
        				 if(isContain==false){
        					 productCategory = new ProductCategoryInfoCG();
        					 productCategory.setProductCategoryId(productCategoryCG.getId());
        					 productCategoryInfo.add(productCategory);
        				 }
        			 } else {
        				 productCategory = new ProductCategoryInfoCG();
        				 productCategory.setProductCategoryId(productCategoryCG.getId());
        				 productCategoryInfo.add(productCategory);
        			 }
        			 productDetail.setProductCategoryInfo(productCategoryInfo);
    			 }

    		 }
    		 //购买限制,只需要开始时间,结束时间,per_number对应的最大购买数量
    		 ProductBuyLimit buyLimit=new ProductBuyLimit();
    		 if(product.getIsEnableGroup()||product.getIsEnablePresell()||product.getIsEnableFamous()){
    			 if(product.getPerNumber()!=null){
    				 buyLimit.setMax(product.getPerNumber());
    			 }
    			 if(productcg.getStartTime()!=null){
    				 buyLimit.setStartTime(productcg.getStartTime().getTime());
    			 }
    			 if(productcg.getEndTime()!=null){
    				 buyLimit.setEndTime(productcg.getEndTime().getTime());
    			 }
    		 }else{
    			 if(product.getPerNumber()!=null){
    				 buyLimit.setMax(product.getPerNumber());
    			 }
    		 }
    		 productDetail.setBuyLimit(buyLimit);
    		// List<ProductOutlet> productOutlets=null;
    		 List<Long> areaIds=new ArrayList<Long>();
    		 Map<String,Object> m=new HashMap<String,Object>();
    		 /**
    		  * 1.查询原安徽表商品对应门店信息
    		  * 2.根据原门店和新门店ID转换获取新的Org_code
    		  */
    		 if(productcg.getType().equals("2")){
    			 
    			 //旧门店对应机构查询
    		 List<MerchantOutlet> merchantOutlets=merchantOutletMapper.selectPresellProductOutlet(product.getProductPresellId());
    			 List<String> orgCodes=new ArrayList<String>();
    			 List<ProductCityArea> cityAreas=new ArrayList<ProductCityArea>();//新增区域
    			 List<ProductCityOutlet> orgOutlets=new ArrayList<ProductCityOutlet>();;//市级-门店关系
    			 Map<Long,ProductCityArea> cityAreaMap=new HashMap<Long,ProductCityArea>();//记录是否有同市不同区的map
    			 Map<Long,ProductCityOutlet> productCityMap=new HashMap<Long,ProductCityOutlet>();//记录是否有同市不同区的map
    			 
    			 
    			 if(merchantOutlets!=null&&merchantOutlets.size()>0){
    				 Map<String,String> orgMap=new HashMap<String,String>();
    				 for(MerchantOutlet merchantOutlet:merchantOutlets ){
    					 boolean isOutlet=false;
    					 ProductCityArea cityArea=new ProductCityArea();
    					 ProductCityOutlet orgOutlet=new ProductCityOutlet();
    					// List<ProductArea> countys=new ArrayList<ProductArea>();//城市下的区
    					 ProductOutlet productOutlet=new ProductOutlet();
    					 productOutlet.setOutletName(merchantOutlet.getName());
    					 //Transfer tranferOutlet=transferMapper.queryNewId(String.valueOf(merchantOutlet.getId()), TransferTypeEnum.outlet_id);
    					 Transfer tranferOutlet=transferTypeEnumMap.get(String.valueOf(merchantOutlet.getId())+TransferTypeEnum.outlet_id.getCode());
    					 productOutlet.setOutletId(tranferOutlet.getNewId());
    					 productOutlet.setAddress(merchantOutlet.getAddress());
    					 productOutlet.setPhone(merchant.getPhone());
    				//	 productOutlet.setOrgCode();
    					 if(merchantOutlet.getAreaId()!=null){
    						 Transfer tranferarea=transferTypeEnumMap.get(String.valueOf(merchantOutlet.getAreaId())+TransferTypeEnum.area_id.getCode());
    						 if(tranferarea!=null){
    							 areaIds.add(Long.parseLong(tranferarea.getNewId()));
    							 //先判断map是否含有相同市的ID,treepath存在3级才判断,如果是相同市的话就将不同区放入ProductArea的list中
    							 AreaCG newarea=(AreaCG)areaMap.get(Long.parseLong(tranferarea.getNewId()));
    							 if(newarea!=null){
    								 String[] treepaths=splitAreaTreePath(newarea.getTreePath());
    								 if(treepaths.length==3){
        								 if(cityAreaMap.containsKey(Long.parseLong(treepaths[1]))){
        									 boolean hasArea=true;
        									 ProductCityArea productCityArea=cityAreaMap.get(Long.parseLong(treepaths[1]));
        									 List<ProductArea> productAreas=productCityArea.getCountys();
        									 ProductArea productArea=new ProductArea();
        									 /**********************去除重复的地区ID*********************/
        										for(int k=0;k<productAreas.size();k++){
            										if(Long.parseLong(treepaths[2])==productAreas.get(k).getAreaId()){
            											hasArea=false;
            										}
            									}
        									 /**********************去除重复的地区ID*********************/	
        										//如果区已经存在就不用加了
        										if(hasArea){
        											productArea.setAreaId(Long.parseLong(treepaths[2]));
        											productArea.setAreaName(newarea.getName());
        											productAreas.add(productArea);
        											productCityArea.setCountys(productAreas);
        											cityAreaMap.put(Long.parseLong(treepaths[1]), productCityArea);
        											/**********************从之前的list中剔除已有的area在重新set*********************/
        											for(int i=0;i<cityAreas.size();i++){
        												ProductCityArea cityArea1=  cityAreas.get(i);  
        												if(cityArea1.getCityId()==Long.parseLong(treepaths[1])){
        													cityAreas.remove(i);
        													break;
        												}
        											}
        											cityAreas.add(productCityArea);
        										}
        										productOutlet.setAreaId(Long.parseLong(treepaths[2]));
        									 orgOutlet.setCityId(Long.parseLong(treepaths[1]));
        									 isOutlet=true;
        									 /**********************从之前的list中剔除已有的area在重新set*********************/
        								 }else{
        									 //第一次初始化入口
        									 cityArea.setCityId(Long.parseLong(treepaths[1]));
        									 AreaCG areaCG=(AreaCG)areaMap.get(Long.parseLong(treepaths[1]));
        									 cityArea.setCityName(areaCG.getName());
        									 List<ProductArea> productAreas=new ArrayList<ProductArea>();
        									 ProductArea productArea=new ProductArea();
        									 productArea.setAreaId(newarea.getId());
        									 productArea.setAreaName(newarea.getName());
        									 productAreas.add(productArea);
        									 cityArea.setCountys(productAreas);
        									 cityAreas.add(cityArea);
        									 cityAreaMap.put(Long.parseLong(treepaths[1]), cityArea);
        									 orgOutlet.setCityId(cityArea.getCityId());
        									 orgOutlet.setCityName(cityArea.getCityName());
        									 productOutlet.setAreaId(productArea.getAreaId());
        								//	 productCityMap.put(orgOutlet.getCityId(), orgOutlet);
        									 isOutlet=true;
        								 }
        							 }
//    								 else if(treepaths.length==2){
//        								 cityArea.setCityId(newarea.getId());
//        								 cityArea.setCityName(newarea.getName());
//        							 }
    							 }
    					 } 
    						 
    					 }
    					 
    					
    					
    					 /**************************普通商品门店转换查询*****************************/
//    					 Org orgold=new Org();
//    					 orgold.setClientId(productcg.getClientId());
//    					 orgold.setOutletId(tranferOutlet.getNewId());
//    					 Org org=orgMapper.findOrgById(orgold);
    					 OutleBank outleBank=outletBankMapper.queryByOutletId(merchantOutlet.getId());
    				//	 LogCvt.info("outleBank_id="+outleBank.getOutleId());
    					 if(outleBank!=null){
    						 String bankOrgCode=Constans.filterOrg(outleBank.getParentBankOrg());
    							 if(!orgMap.containsKey(bankOrgCode)){
    								 orgCodes.add(bankOrgCode);
    								 orgMap.put(bankOrgCode,bankOrgCode);
    							 }
    							 productOutlet.setOrgCode(Constans.filterOrg(outleBank.getBankOrg()));	 
    						 }else{
    							 isOutlet=false;
    						 }
    					 
    						if(isOutlet){
    	    					 if(productCityMap.containsKey(orgOutlet.getCityId())){
    	    						 ProductCityOutlet orgOutlet1= productCityMap.get(orgOutlet.getCityId());
    	    						 List<ProductOutlet> productOutlets1=orgOutlet1.getOrgOutlets();
    	    						 productOutlets1.add(productOutlet);
    	    						 for(int i=0;i<orgOutlets.size();i++){
    	    							 
    	    								 long cityid=orgOutlets.get(i).getCityId();
    	        							 if(cityid==orgOutlet.getCityId()){
    	        								 orgOutlets.remove(i);
    	        								 break;
    	        							 }
    	    						 }
    	        					 orgOutlet1.setOrgOutlets(productOutlets1);
    	        					 orgOutlets.add(orgOutlet1);
    	        				//	 productCityMap.put(orgOutlet.getCityId(), orgOutlet1);
    	    					 }else{
    	    						 List<ProductOutlet> productOutlets=new ArrayList<ProductOutlet>();
    	    						 productOutlets.add(productOutlet);
    	        					 orgOutlet.setOrgOutlets(productOutlets);
    	        					 orgOutlets.add(orgOutlet);
    	        					 productCityMap.put(orgOutlet.getCityId(), orgOutlet); 
    	    					 }
    	    						
    	    					 }
    					
    				 }
    				 if(orgCodes!=null&&orgCodes.size()>0){
    					 productDetail.setOrgCodes(orgCodes);
    					 //将key移除
        				 for(int i=0;i<orgCodes.size();i++){
        					 orgMap.remove(orgCodes.get(i));
        				 }
    				 }
    			//	 productDetail.setOutletInfo(productOutlets);
    				 //设置out_info ID
    				// m=setOutInfo(productOutlets,productcg,productDetail,productCGMapper);
    				 if(cityAreas.size()>0){
    					 productDetail.setCityAreas(cityAreas);
    				 }
    				 if(orgOutlets.size()>0){
    					 productDetail.setOrgOutlets(orgOutlets);
    				 } 
    				 flag=true;
    				
    			 }
    			 
    		 } else{
    			 List<MerchantOutlet> merchantOutlets=merchantOutletMapper.selectCommonProductOutlet(product.getId());
    			 List<String> orgCodes=new ArrayList<String>();
    			 List<ProductCityArea> cityAreas=new ArrayList<ProductCityArea>();//新增区域
    			 List<ProductCityOutlet> orgOutlets=new ArrayList<ProductCityOutlet>();;//市级-门店关系
    			 Map<Long,ProductCityArea> cityAreaMap=new HashMap<Long,ProductCityArea>();//记录是否有同市不同区的map
    			 Map<Long,ProductCityOutlet> productCityMap=new HashMap<Long,ProductCityOutlet>();//记录是否有同市不同区的map
    			
    			 if(merchantOutlets!=null&&merchantOutlets.size()>0){
    				 Map<String,String> orgMap=new HashMap<String,String>();
    				 for(MerchantOutlet merchantOutlet:merchantOutlets ){
    					 boolean isOutlet=false;
    					 ProductCityArea cityArea=new ProductCityArea();
    					 ProductCityOutlet orgOutlet=new ProductCityOutlet();
    					// List<ProductArea> countys=new ArrayList<ProductArea>();//城市下的区
    					 ProductOutlet productOutlet=new ProductOutlet();
    					 productOutlet.setOutletName(merchantOutlet.getName());
    					 //Transfer tranferOutlet=transferMapper.queryNewId(String.valueOf(merchantOutlet.getId()), TransferTypeEnum.outlet_id);
    					 Transfer tranferOutlet=transferTypeEnumMap.get(String.valueOf(merchantOutlet.getId())+TransferTypeEnum.outlet_id.getCode());
    					 productOutlet.setOutletId(tranferOutlet.getNewId());
    					 productOutlet.setAddress(merchantOutlet.getAddress());
    					 productOutlet.setPhone(merchant.getPhone());
    				//	 productOutlet.setOrgCode();
    					 if(merchantOutlet.getAreaId()!=null&&!"".equals(merchantOutlet.getAreaId())){
    						 Transfer tranferarea=transferTypeEnumMap.get(String.valueOf(merchantOutlet.getAreaId())+TransferTypeEnum.area_id.getCode());
    						 areaIds.add(Long.parseLong(tranferarea.getNewId()));
    					//	 productOutlet.setAreaId(Long.parseLong(tranferarea.getNewId()));
    						//先判断map是否含有相同市的ID,treepath存在3级才判断,如果是相同市的话就将不同区放入ProductArea的list中
    						     AreaCG newarea=(AreaCG)areaMap.get(Long.parseLong(tranferarea.getNewId()));
    							 if(newarea!=null){
    								 String[] treepaths=splitAreaTreePath(newarea.getTreePath());
    								 if(treepaths.length==3){
    									 if(cityAreaMap.containsKey(Long.parseLong(treepaths[1]))){
        									 boolean hasArea=true;
        									 ProductCityArea productCityArea=cityAreaMap.get(Long.parseLong(treepaths[1]));
        									 List<ProductArea> productAreas=productCityArea.getCountys();
        									 ProductArea productArea=new ProductArea();
        									 /**********************去除重复的地区ID*********************/
        										for(int k=0;k<productAreas.size();k++){
            										if(Long.parseLong(treepaths[2])==productAreas.get(k).getAreaId()){
            											hasArea=false;
            										}
            									}
        									 /**********************去除重复的地区ID*********************/	
        										//如果区已经存在就不用加了
        										if(hasArea){
        											productArea.setAreaId(Long.parseLong(treepaths[2]));
        											productArea.setAreaName(newarea.getName());
        											productAreas.add(productArea);
        											productCityArea.setCountys(productAreas);
        											cityAreaMap.put(Long.parseLong(treepaths[1]), productCityArea);
        											/**********************从之前的list中剔除已有的area在重新set*********************/
        											for(int i=0;i<cityAreas.size();i++){
        												ProductCityArea cityArea1=  cityAreas.get(i);  
        												if(cityArea1.getCityId()==Long.parseLong(treepaths[1])){
        													cityAreas.remove(i);
        													break;
        												}
        											}
        											cityAreas.add(productCityArea);
        										}
        										productOutlet.setAreaId(Long.parseLong(treepaths[2]));
        									 orgOutlet.setCityId(Long.parseLong(treepaths[1]));
        									 isOutlet=true;
        									 /**********************从之前的list中剔除已有的area在重新set*********************/
        								 }else{
        									 //第一次初始化入口
        									 cityArea.setCityId(Long.parseLong(treepaths[1]));
        									 AreaCG areaCG=(AreaCG)areaMap.get(Long.parseLong(treepaths[1]));
        									 cityArea.setCityName(areaCG.getName());
        									 List<ProductArea> productAreas=new ArrayList<ProductArea>();
        									 ProductArea productArea=new ProductArea();
        									 productArea.setAreaId(newarea.getId());
        									 productArea.setAreaName(newarea.getName());
        									 productAreas.add(productArea);
        									 cityArea.setCountys(productAreas);
        									 cityAreas.add(cityArea);
        									 cityAreaMap.put(Long.parseLong(treepaths[1]), cityArea);
        									 orgOutlet.setCityId(cityArea.getCityId());
        									 orgOutlet.setCityName(cityArea.getCityName());
        									 productOutlet.setAreaId(productArea.getAreaId());
        									 isOutlet=true;
        								 }
        							 }
//    								 else if(treepaths.length==2){
//    									 isOutlet=false;
//        							 }
    							 }
    							 
    						 
    					 }
    					 
    					
    					
    					 /**************************普通商品门店转换查询*****************************/
//    					 Org orgold=new Org();
//    					 orgold.setClientId(productcg.getClientId());
//    					 orgold.setOutletId(tranferOutlet.getNewId());
//    					 Org org=orgMapper.findOrgById(orgold);
    					 OutleBank outleBank=outletBankMapper.selectByOutletId(merchantOutlet.getId());
    					 if(outleBank!=null){
    						 String bankOrgCode=Constans.filterOrg(outleBank.getParentBankOrg());
    							 if(!orgMap.containsKey(bankOrgCode)){
    								 orgCodes.add(bankOrgCode);
    								 orgMap.put(bankOrgCode,bankOrgCode);
    							 }
    							 productOutlet.setOrgCode(Constans.filterOrg(outleBank.getBankOrg()));	 
    						 }else{
    							 isOutlet=false;
    						 }
    					 
    				if(isOutlet){
    					 if(productCityMap.containsKey(orgOutlet.getCityId())){
    						 ProductCityOutlet orgOutlet1= productCityMap.get(orgOutlet.getCityId());
    						 List<ProductOutlet> productOutlets1=orgOutlet1.getOrgOutlets();
    						 productOutlets1.add(productOutlet);
    						 for(int i=0;i<orgOutlets.size();i++){
    							 
    								 long cityid=orgOutlets.get(i).getCityId();
    									 LogCvt.info("cityid====>"+orgOutlets.get(i).getCityId()+",orgOutlet==>"+orgOutlet.getCityId());
        							 if(cityid==orgOutlet.getCityId()){
        								 orgOutlets.remove(i);
        								 break;
        							 }
    						 }
        					 orgOutlet1.setOrgOutlets(productOutlets1);
        					 orgOutlets.add(orgOutlet1);
        				//	 productCityMap.put(orgOutlet.getCityId(), orgOutlet1);
    					 }else{
    						 List<ProductOutlet> productOutlets=new ArrayList<ProductOutlet>();
    						 productOutlets.add(productOutlet);
        					 orgOutlet.setOrgOutlets(productOutlets);
        					 orgOutlets.add(orgOutlet);
        					 productCityMap.put(orgOutlet.getCityId(), orgOutlet); 
    					 }
    						
    					 }
    				 }
    				 if(orgCodes!=null&&orgCodes.size()>0){
    					 productDetail.setOrgCodes(orgCodes);
    					 //将key移除
        				 for(int i=0;i<orgCodes.size();i++){
        					 orgMap.remove(orgCodes.get(i));
        				 }
    				 }
    				 if(cityAreas.size()>0){
    					 productDetail.setCityAreas(cityAreas);
    				 }
    				 if(orgOutlets.size()>0){
    					 productDetail.setOrgOutlets(orgOutlets);
    				 }
    				// productDetail.setOutletInfo(productOutlets);
    				// flag=true;
    				
    			 }
    		 }
    		 /**
    		  * 添加省ID，区ID，县ID
    		  */
    		 if(productDetail.getProductType().equals("1")&&merchant.getAreaId()!=null){
    			 setGroupArea(productDetail,merchant.getAreaId());
    		 }
    		 
//    		 AreaCG areas=(AreaCG)areaMap.get(merchant.getAreaId());
//    		 String areaids=areas.getTreePath();
//    		 if(areaids!=null){
//    			 String[] areaids1=splitAreaTreePath(areaids);
//        		 if(areaids1.length==2){
//        			 productDetail.setProvinceId(Long.parseLong(areaids1[1]));
//        		 }else if(areaids1.length==3){
//        			 productDetail.setProvinceId(Long.parseLong(areaids1[1]));
//        			 productDetail.setCityId(Long.parseLong(areaids1[2]));
//        		 }else if(areaids1.length==4){
//        			 productDetail.setProvinceId(Long.parseLong(areaids1[1]));
//        			 productDetail.setCityId(Long.parseLong(areaids1[2]));
//        			 productDetail.setAreaId(Long.parseLong(areaids1[3]));
//        		 }
//    		 }
    		
    		 
    		 
    		 
    		 boolean  isSuccess = mongo.add(productDetail, MongoTableName.CB_PRODUCT_DETAIL) > -1;// 向mongodb插入数据
    		 LogCvt.info("isSuccess:"+isSuccess);
//    		 if(flag){
//    			 /**
//    			  * 添加区域提货点,如果有相同的商品对应同一个提货点则不处理
//    			  */
//    			 //指定商品类型可以根据区域查询逗号分隔比如1,2,3,4,5
//    			 if(isExist(productcg.getType(),"5")&&product.getIsMarketable()&&product.getIsEnable()){
//    				 if(areaIds!=null && areaIds.size()>0){
//    					 DBObject pushObj = new BasicDBObject();
//    					 DBObject whereObj = new BasicDBObject();
//    					 whereObj.put("client_id", productcg.getClientId());
//    					 AreaProducts areaProducts = null;
//    					 for(Long areaId : areaIds){
//    						 whereObj.put("area_id", areaId);
//    						 areaProducts = mongo.findOne(whereObj, MongoTableName.CB_AREA_PRODUCTS, AreaProducts.class);
//    						 if(areaProducts==null){
//    							 AreaCG areas1 =(AreaCG)areaMap.get(areaId);
//    							 Long oldparenId=areas1.getParentId();
//    							// String[] oldparenIds= splitAreaTreePath(oldparenId);
//    							 areaProducts = new AreaProducts();
//    							 if(oldparenId!=null){
//    								 areaProducts.setAreaParentId(oldparenId);
//    							 }
//    							 areaProducts.setAreaId(areaId);
//    							 areaProducts.setClientId(productcg.getClientId());
//    							 List<String> productIds = new ArrayList<String>();
//    							 productIds.add(productcg.getProductId());
//    							 areaProducts.setProductIds(productIds);
//    							 mongo.add(areaProducts, MongoTableName.CB_AREA_PRODUCTS);
//    						 } else {
//    							//如果有就不更新了
//        							 List<String> productIdsz=areaProducts.getProductIds();
//        							 boolean flagid=true;
//        							 boolean flagarea=true;
//        	 						 for(int i=0;i<productIdsz.size();i++){
//        	 							 String productId=productIdsz.get(i);
//        	 							 if(productId.equals(productcg.getProductId())){
//        	 								flagid=false;
//        	 							 }
//        	 						 } 
//        	 						 if(flagid){
//        	 							 pushObj.put("product_ids", productcg.getProductId()); 
//        	 							mongo.update(pushObj, whereObj, MongoTableName.CB_AREA_PRODUCTS, "$push", true, false);
//        	 						 }
//        	 						    
//    						 }
//    					 }
//    				 }
//    			 }
//    		 }
    		 
    	 }catch(Exception e){
    		 LogCvt.error("DetailErro"+e.getMessage()+"ProductId"+product.getId(),e);
    	 }
    
         
     }
     public String changeOrgCode(String org){
    	 if(org!=null&&!"".equals(org)){
    		 String[] orgCode=org.split("_");
    		 return orgCode[1];
    	 }
    	 return "";
     }
     //商品基本信息缓存
     private void productRedis(ProductCG product) {
    	 SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	 boolean flag=false;
         if(product!=null){
             /* 缓存 */
             Map<String, String> hash = new HashMap<String, String>();
             hash.put("is_marketable", ObjectUtils.toString(product.getIsMarketable(), ""));
             hash.put("product_type", ObjectUtils.toString(product.getType(), ""));
             hash.put("price", ObjectUtils.toString(product.getPrice(), ""));
             hash.put("marketPrice", ObjectUtils.toString(product.getMarketPrice(), ""));
             hash.put("product_name", ObjectUtils.toString(product.getName(), ""));
             if(product.getStartTime()!=null){
                 hash.put("start_time", ObjectUtils.toString(product.getStartTime().getTime(), ""));
             }
             if(product.getEndTime()!=null){
                 hash.put("end_time", ObjectUtils.toString(product.getEndTime().getTime(), ""));
             }
             if(product.getDeliveryMoney()!=null&&product.getDeliveryMoney()>0){
                 hash.put("delivery_money", ObjectUtils.toString(product.getDeliveryMoney(), ""));
             }
                 ProductGroup groupProduct = product.getProductGroup();
                 if(groupProduct!=null){
                     hash.put("true_buyer_number", ObjectUtils.toString(groupProduct.getTrueBuyerNumber(), ""));
                     hash.put("virtual_buyer_number", ObjectUtils.toString(groupProduct.getVirtualBuyerNumber(), ""));
                     String start_time="2001-01-01 00:00:00";
                         try {
							hash.put("expire_start_time", ObjectUtils.toString(format.parse(start_time).getTime(), ""));
						} catch (ParseException e) {
							e.printStackTrace();
						}
                     if(groupProduct.getExpireTime()!=null){
                         hash.put("expire_end_time", ObjectUtils.toString(groupProduct.getExpireTime().getTime(), ""));
                     }
                 }
              
                 ProductPresell presellProduct = product.getProductPresell();
                 if(presellProduct!=null){
                     if(presellProduct.getDeliveryStartTime()!=null){
                         hash.put("delivery_start_time", ObjectUtils.toString(presellProduct.getDeliveryStartTime().getTime(), ""));
                     }
                     if(presellProduct.getDeliveryEndTime()!=null){
                         hash.put("delivery_end_time", ObjectUtils.toString(presellProduct.getDeliveryEndTime().getTime(), ""));
                     }
                     hash.put("true_buyer_number", ObjectUtils.toString(presellProduct.getTrueBuyerNumber(), ""));
                     hash.put("virtual_buyer_number", ObjectUtils.toString(presellProduct.getVirtualBuyerNumber(), ""));
                     String start_time="2001-01-01 00:00:00";
                         try {
							hash.put("expire_start_time", ObjectUtils.toString(format.parse(start_time).getTime(), ""));
						} catch (ParseException e) {
							e.printStackTrace();
						}
                     if(presellProduct.getTicketExpireTime()!=null){
                         hash.put("expire_end_time", ObjectUtils.toString(presellProduct.getTicketExpireTime().getTime(), ""));
                     }
                     if(presellProduct.getClusterState()!=null){
         				if(presellProduct.getClusterState().getCode().equals("1")){
         					flag=true;
         				}
         				if(presellProduct.getClusterState().getCode().equals("2")){
         					flag=false;
         				}
         				if(presellProduct.getClusterState().getCode().equals("0")){
         					flag=false;
         				}
         			}
                     hash.put("cluster_state", ObjectUtils.toString(BooleanUtils.toInteger(flag, 1, 0, 0), ""));
                 }
             String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(product.getClientId(), product.getMerchantId(), product.getProductId());
             LogCvt.info("缓存ID,Merchant_ID:"+product.getMerchantId()+",Product_ID:"+product.getProductId());
             redis.putMap(key, hash);
         }
     }


     public Map<String,Object> setOutInfo(List<ProductOutlet> productOutlets,ProductCG p,ProductDetail productDetail,com.froad.db.chonggou.mappers.ProductMapper productCGMapper){
    	 Map<String,Object> productDetailMap = new HashMap<String,Object>();
         if(productOutlets!=null && productOutlets.size()>0){
             List<ProductOutlet> tempOutlets = new ArrayList<ProductOutlet>();
             
             Set<Long> areaIds = new HashSet<Long>();
             
             int pageNum = productOutlets.size() / 20;
             if (productOutlets.size() % 20 > 0) {
                 pageNum += 1;
             }
             Map<String,Object> param = new HashMap<String,Object>();
             param.put("clientId", p.getClientId());
             List<ProductOutletInfo> productOutletIfos = null;
             List<String> outletIds = null;
             int num;
             for (int i = 1; i <= pageNum; i++) {
                 outletIds = new ArrayList<String>();
                 if (i == pageNum && productOutlets.size() % 20 > 0) {
                     num = 20 * (i - 1) + productOutlets.size()% 20;
                 } else {
                     num = 20 * i;
                 }
                 for (int j = (i - 1) * 20; j < num; j++) {
                     outletIds.add(productOutlets.get(j).getOutletId());
                 }
                 if(outletIds.size()>0){
                     param.put("outletIds", outletIds);
                     productOutletIfos = productCGMapper.findOutlets(param);
                     
                     if(productOutletIfos!=null && productOutletIfos.size()>0){
                         for(ProductOutletInfo poi : productOutletIfos){
                             ProductOutlet po = new ProductOutlet();
                             po.setOutletId(poi.getOutletId());
                             po.setAddress(poi.getAddress());
                             po.setOutletName(poi.getOutletName());
                             tempOutlets.add(po);
                             if(poi.getAreaId()!=null &&poi.getAreaId()>0 && p.getIsMarketable().equals(ProductStatus.onShelf.toString())){
                                 areaIds.add(poi.getAreaId());
                             }
                         }
                     } 
                 }
             }

             productDetailMap.put("areaIds", areaIds);
             
             List<ProductOutlet> outletInfo = new ArrayList<ProductOutlet>();
             for(ProductOutlet productOutlet : tempOutlets){
                 outletInfo.add(productOutlet);
             }
             productDetail.setOutletInfo(outletInfo);
         }
         productDetailMap.put("productDetail", productDetail);
         return productDetailMap;
     }
     private Boolean isExist(String value,String constantsStr){
         if(constantsStr!=null && !"".equals(constantsStr) && value!=null && !"".equals(value)){
             String[] strs = constantsStr.split(",");
             if(strs!=null && strs.length>0){
                 for(String str : strs){
                     if(value.equals(str)){
                         return true;
                     }
                 }
             }
         }
         return false;
     }
     //金额转换成int
     public  Integer DealWithPrice(String price,boolean flag){
 		Double newPrice=Double.parseDouble(price);
 		if(flag){//为true则需要乘以1000
 			Double newPrice1=newPrice*1000;
 			int newPrice2=newPrice1.intValue();
 			 return newPrice2;
 		}else{
 			int newPrice2=newPrice.intValue();
 			return newPrice2;
 		}
 	       
 	}
     public int setAvgPoint(ProductCommentMapper productCommentMapper,Product productAnhui){
    	    int Point=0;
    	    double total=0;
			List<ProductComment> productComments=productCommentMapper.selectProductCommentByProductId(productAnhui.getId());
			if(productComments!=null&&productComments.size()>0){
				for(ProductComment productComment:productComments){
					int startLevel=0;
					double pricePoint=0;
					double qualityPoint=0;
					double servePoint=0;
					double nowtotal=0;
					if(productComment.getEnvironmentPoint()!=null){
						if(productComment.getEnvironmentPoint().name().equals("one")){
							startLevel=1;
						}else if(productComment.getEnvironmentPoint().name().equals("two")){
							startLevel=2;
						}else if(productComment.getEnvironmentPoint().name().equals("three")){
							startLevel=3;
						}else if(productComment.getEnvironmentPoint().name().equals("four")){
							startLevel=4;
						}else if(productComment.getEnvironmentPoint().name().equals("five")){
							startLevel=5;
						}
					}
					if(productComment.getPricePoint()!=null){
						if(productComment.getPricePoint().name().equals("one")){
							pricePoint=1;
						}else if(productComment.getPricePoint().name().equals("two")){
							pricePoint=2;
						}else if(productComment.getPricePoint().name().equals("three")){
							pricePoint=3;
						}else if(productComment.getPricePoint().name().equals("four")){
							pricePoint=4;
						}else if(productComment.getPricePoint().name().equals("five")){
							pricePoint=5;
						}
					}
					if(productComment.getQualityPoint()!=null){
						if(productComment.getQualityPoint().name().equals("one")){
							qualityPoint=1;
						}else if(productComment.getQualityPoint().name().equals("two")){
							qualityPoint=2;
						}else if(productComment.getQualityPoint().name().equals("three")){
							qualityPoint=3;
						}else if(productComment.getQualityPoint().name().equals("four")){
							qualityPoint=4;
						}else if(productComment.getQualityPoint().name().equals("five")){
							qualityPoint=5;
						}
					}
					if(productComment.getServePoint()!=null){
						if(productComment.getServePoint().name().equals("one")){
							servePoint=1;
						}else if(productComment.getServePoint().name().equals("two")){
							servePoint=2;
						}else if(productComment.getServePoint().name().equals("three")){
							servePoint=3;
						}else if(productComment.getServePoint().name().equals("four")){
							servePoint=4;
						}else if(productComment.getServePoint().name().equals("five")){
							servePoint=5;
						}
					}	
					nowtotal=startLevel+pricePoint+qualityPoint+servePoint;
					total+=nowtotal;
				}
				Point=changInt(total,productComments.size()*4);
			}
    	 
    	 return Point;
     }
     public int changInt(double total,int size){
    	 double d=total/size;
    	 return Integer.parseInt(new java.text.DecimalFormat("0").format(d));
     }
 	public  String[] splitAreaTreePath(String treePath){
		String[] areas=treePath.split(",");
		return areas;
	}
 	public void setGroupArea(ProductDetail productDetail,Long areaId){
 		AreaCG areas=(AreaCG)areaMap.get(areaId);
 		 if(areas.getTreePath()!=null){
 			List<ProductCityArea> cityAreas=new ArrayList<ProductCityArea>();//新增区域
 			List<ProductArea> countys=new ArrayList<ProductArea>();
 			ProductCityArea productCityArea=new ProductCityArea();
 			ProductArea productArea=new ProductArea();
			 String[] areaids1=splitAreaTreePath(areas.getTreePath());
    		 if(areaids1.length==2){
    			 productCityArea.setCityId(areas.getId());
    			 productCityArea.setCityName(areas.getName());
    			 cityAreas.add(productCityArea);
    			 productDetail.setCityAreas(cityAreas);
    		 }else if(areaids1.length==3){
    			 productCityArea.setCityId(Long.parseLong(areaids1[1]));
    			 AreaCG areas1=(AreaCG)areaMap.get(productCityArea.getCityId());
    			 productCityArea.setCityName(areas1.getName());
    			 productArea.setAreaId(areas.getId());
    			 productArea.setAreaName(areas.getName());
    			 countys.add(productArea);
    			 productCityArea.setCountys(countys);
    			 cityAreas.add(productCityArea);
    			 productDetail.setCityAreas(cityAreas);
    			 
    		 }
		 }
 		
 	}
 
}

