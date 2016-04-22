package com.froad.CB.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import com.froad.CB.AppException.AppException;
import com.froad.CB.common.Command;
import com.froad.CB.common.PreferType;
import com.froad.CB.common.ReMerchantResultBean;
import com.froad.CB.dao.merchant.MerchantDAO;
import com.froad.CB.dao.merchant.MerchantPreferentialDAO;
import com.froad.CB.dao.merchant.MerchantPresentDAO;
import com.froad.CB.dao.tag.TagClassifyADAO;
import com.froad.CB.dao.tag.TagClassifyBDAO;
import com.froad.CB.dao.tag.TagDistrictADAO;
import com.froad.CB.dao.tag.TagDistrictBDAO;
import com.froad.CB.dao.user.UserDao;
import com.froad.CB.po.MerchantUserSet;
import com.froad.CB.po.merchant.Merchant;
import com.froad.CB.po.merchant.MerchantPreferential;
import com.froad.CB.po.merchant.MerchantPresent;
import com.froad.CB.po.merchant.MerchantTrain;
import com.froad.CB.po.tag.TagClassifyA;
import com.froad.CB.po.tag.TagClassifyB;
import com.froad.CB.po.tag.TagDistrictA;
import com.froad.CB.po.tag.TagDistrictB;
import com.froad.CB.po.tag.TagMAP;
import com.froad.CB.po.user.User;
import com.froad.CB.service.MerchantPresentService;
import com.froad.CB.service.MerchantService;
import com.froad.CB.service.MerchantTrainService;
import com.froad.CB.service.MerchantUserSetService;
import com.froad.CB.service.TagMapService;
import com.froad.CB.service.user.MUserService;
import com.froad.util.Assert;
import com.froad.util.DateUtil;

@WebService(endpointInterface="com.froad.CB.service.MerchantService")
public class MerchantServiceImpl implements MerchantService{

	private Logger logger=Logger.getLogger(MerchantServiceImpl.class);
	
	private MerchantDAO merchantDAO;
	private MerchantPresentDAO merchantPresentDAO;
	private MerchantPreferentialDAO merchantPreferentialDAO;
	private TagClassifyADAO tagClassifyADAO;
	private TagClassifyBDAO tagClassifyBDAO;
	private TagDistrictADAO tagDistrictADAO;
	private TagDistrictBDAO tagDistrictBDAO;
	private UserDao userDao;
	
	private MUserService mUserService;
	private MerchantPresentService merchantPresentService;
	private MerchantTrainService merchantTrainService;
	private TagMapService tagMapService;
	private MerchantUserSetService merchantUserSetService;
	

	@Override
	public Integer addMerchant(Merchant merchant) {
		if(merchant==null){
			logger.error("商户信息为空！");
			return -1;
		}
		String userId=merchant.getUserId();
		if(userId==null||"".equals(userId)){
			logger.error("用户编号为空！");
			return -1;
		}
		Integer merchantId=-1;
		User user=userDao.queryUserAllByUserID(userId);
		if(user==null){//用户不存在
			logger.error("用户不存在！商户添加失败！");
			return merchantId;
		}
		if(!isMerchant(userId)){//如果不是商户
			logger.info("用户编号为："+userId+"还不是商户，创建商户...");
			try {
				String time=DateUtil.formatDate2Str(new Date());
				merchant.setCreateTime(time);
				merchant.setUpdateTime(time);
				merchant.setState(merchant.getState());
				return merchantDAO.insert(merchant);
			} catch (Exception e) {
				logger.error("添加商户时出现错误,用户编号为："+userId,e);
				return -1;
			}
		}else{
			logger.info("用户编号为："+userId+"已经是商户了，不需要重新创建...");
			merchantId= merchantDAO.getMerchantIdByUserId(userId);
		}
		return merchantId;
	}

	@Override
	public List<Merchant> getMerchantByUserId(String userId) {
		List<Merchant> merchantRes = null;
		if(userId==null||"".equals(userId)){
			logger.error("用户编号为空！");
			return null;
		}
		logger.info("正在查询商户信息，userId: "+userId);
		merchantRes = merchantDAO.getMerchantByUserId(userId);
		return merchantRes;
	}

	@Override
	public boolean isMerchant(String userId) {
		if(userId==null||"".equals(userId)){
			logger.error("用户编号为空！");
			return false;
		}
		Integer merchantId=merchantDAO.getMerchantIdByUserId(userId);
		if(merchantId!=null){
			logger.info("用户编号为: "+userId+" 对应的商户编号为： "+merchantId);
			return true;
		}else{
			logger.error("该用户不是商户。userId: "+userId);
			return false;
		}
	}

	
	/**
	 * 描述：根据标签ID(分类ID或者商圈ID)查询商户 
	 * @param 
	 * @return List<Merchant>
	 */
	public List<Merchant> getMerchantListByTagId(Merchant merchant){
		return merchantDAO.getMerchantListByTagId(merchant);
	}
	public Integer getMerchantListByTagIdCount(Merchant merchant){
		return merchantDAO.getMerchantListByTagIdCount(merchant);
	}
	
	/**
	 * 描述：根据标签ID(分类AID和分类BID商圈bID)查询商户 
	 * @param 
	 * @return List<Merchant>
	 */
	public List<Merchant> getMerchantListByClassAClassBDistrictBId(Merchant merchant){
		return merchantDAO.getMerchantListByClassAClassBDistrictBId(merchant);
	}
	public Integer getMerchantListByClassAClassBDistrictBIdCount(Merchant merchant){
		return merchantDAO.getMerchantListByClassAClassBDistrictBIdCount(merchant);
	}
	
	/**
	 * 描述：根据(分类A标签id和分类B标签id和商圈A标签id商圈B标签id)查找商户
	 * @param 
	 * @return List<Merchant>
	 */
	public List<Merchant> getMerchantListByAllTagId(Merchant merchant){
		return merchantDAO.getMerchantListByAllTagId(merchant);
	}	
	public Integer getMerchantListByAllTagIdCount(Merchant merchant){
		return merchantDAO.getMerchantListByAllTagIdCount(merchant);
	}
	
	
	/**
	  * 方法描述：根据商家分类，商圈下来条件搜索商户搜索
	  * @param: Merchant
	  * @return: List<Merchant>
	  * @version: 1.0
	  * @time: Oct 30, 2012 6:00:15 PM
	  */
	public Merchant getMerchantListIndex(Merchant merchant){		
		//updSearchHis(merchant);//搜索条件更新
		List<Merchant> merchantList = new ArrayList<Merchant>();
		Integer totalCount = null;
		if(!Assert.empty(merchant.getTagDistrictBId()) && !Assert.empty(merchant.getTagClassifyAId()) 
				&& !Assert.empty(merchant.getTagClassifyBId())){
			totalCount = getMerchantListByClassAClassBDistrictBIdCount(merchant);
			merchantList = getMerchantListByClassAClassBDistrictBId(merchant);
		}else if(!Assert.empty(merchant.getTagDistrictBId()) && !Assert.empty(merchant.getTagClassifyAId()) 
				&& !Assert.empty(merchant.getTagClassifyBId()) && !Assert.empty(merchant.getTagDistrictAId())){
			totalCount = getMerchantListByAllTagIdCount(merchant);
			merchantList = getMerchantListByAllTagId(merchant);
		}else if((merchant.getTagDistrictBId()!=null && !"".equals(merchant.getTagDistrictBId().trim())) ||
				(merchant.getTagClassifyAId()!=null && !"".equals(merchant.getTagClassifyAId().trim())) ||
				(merchant.getPreferentialType()!=null && !"".equals(merchant.getPreferentialType().trim()))){
			totalCount = getMerchantListByTagIdCount(merchant);
			merchantList = getMerchantListByTagId(merchant);
		}else{
			merchant.setTagDistrictAId("100001001");//默认珠海市
			totalCount = getMerchantListByTagIdCount(merchant);
			merchantList = getMerchantListByTagId(merchant);
		}
		if(totalCount != null && merchantList.size()>0){
			merchant.setTotalCount(totalCount);
		}
		merchantList = getTagAndPreValueInfo(merchantList);//加入优惠、标签、介绍等信息
		merchant.setList(merchantList);
		return merchant;
	}
	
	/**
	  * 方法描述：关键词搜索，显示商户列表
	  * @param: merchant
	  * @return: true:提交成功 false：提交失败
	  * @version: 1.0
	  * @time: Oct 30, 2012 6:00:15 PM
	  */
	public Merchant getMerchantList(Merchant merchant){
		//updSearchHis(merchant);//搜索条件更新
		if(!Assert.empty(merchant.getTagValue())){
			TagClassifyA queryCon = new TagClassifyA();
			queryCon.setTagValue(merchant.getTagValue());
			
			TagClassifyB queryCon2 = new TagClassifyB();
			queryCon2.setTagValue(merchant.getTagValue());
			
			TagDistrictA queryCon3 = new TagDistrictA();
			queryCon3.setTagValue(merchant.getTagValue());
			
			TagDistrictB queryCon4 = new TagDistrictB();
			queryCon4.setTagValue(merchant.getTagValue());
			List<TagClassifyA> listClassA = tagClassifyADAO.selectTagClassifyA(queryCon);
			List<TagClassifyB> listClassB =	tagClassifyBDAO.selectTagClassifyB(queryCon2);
			List<TagDistrictA> listDistrictA = tagDistrictADAO.selectTagDistrictA(queryCon3);
			List<TagDistrictB> listDistrictB = tagDistrictBDAO.selectTagDistrictB(queryCon4);
			
			if(listClassA!=null && listClassA.size()>0){
				merchant.setTagClassifyAId(String.valueOf(listClassA.get(0).getId()));
			}
			if(listClassB!=null && listClassB.size()>0){
				merchant.setTagClassifyBId(String.valueOf(listClassB.get(0).getId()));
			}
			if(listDistrictA!=null && listDistrictA.size()>0){
				merchant.setTagDistrictAId(String.valueOf(listDistrictA.get(0).getId()));
			}
			if(listDistrictB!=null && listDistrictB.size()>0){
				merchant.setTagDistrictBId(String.valueOf(listDistrictB.get(0).getId()));
			}
		}
		merchant = getMerchantListIndex(merchant);
		return merchant;
	}
	
	/**
	 * 商户列表加入优惠、标签、介绍等信息
	 * @param merchantId
	 */
	public List<Merchant> getTagAndPreValueInfo(List<Merchant> merchantList){
		List<Merchant> merchantListTo = new ArrayList<Merchant>();
		if(merchantList!=null && merchantList.size()>0){
			Integer totalCount = merchantList.get(0).getTotalCount();
			for(Merchant merchant:merchantList){
				//获取商户商户标签、介绍、优惠信息、
				Merchant merchantreq = getMerchantTagAndPre(String.valueOf(merchant.getId()));
				
				merchant.setmPreferentialList(merchantreq.getmPreferentialList());
				merchant.setmPresent(merchantreq.getmPresent());
				merchant.setTagClassifyAList(merchantreq.getTagClassifyAList());
				merchant.setTagClassifyBList(merchantreq.getTagClassifyBList());
				merchant.setTagDistrictAList(merchantreq.getTagDistrictAList());
				merchant.setTagDistrictBList(merchantreq.getTagDistrictBList());
				merchant.setTotalCount(totalCount);//设置记录数  如果没有设置为0
				merchantListTo.add(merchant);
			}
		}else{
			return merchantList;
		}
		return merchantListTo;
	}
	
	/**
	 * 查询商户标签、介绍、优惠信息
	 * @param merchantId
	 */
	public Merchant getMerchantTagAndPre(String merchantId){
		Merchant merchant = new Merchant();
		List<MerchantPreferential> mPreList = merchantPreferentialDAO.getMerchantPreferential(merchantId);
		List<TagClassifyA> tagClassifyAList = tagClassifyADAO.getMerchantTagClassifyA(merchantId);
		List<TagClassifyB> tagClassifyBList = tagClassifyBDAO.getMerchantTagClassifyB(merchantId);
		List<TagDistrictA> tagDistrictAList = tagDistrictADAO.getMerchantDistrictA(merchantId);
		List<TagDistrictB> tagDistrictBList = tagDistrictBDAO.getMerchantDistrictB(merchantId);
		MerchantPresent merchantPresent = merchantPresentDAO.getMerchantPresent(merchantId);
		if(mPreList!=null && mPreList.size()>0){
			merchant.setmPreferentialList(mPreList);
		}
		if(tagClassifyAList!=null && tagClassifyAList.size()>0){
			merchant.setTagClassifyAList(tagClassifyAList);
		}
		if(tagClassifyBList!=null && tagClassifyBList.size()>0){
			merchant.setTagClassifyBList(tagClassifyBList);
		}
		if(tagDistrictAList!=null && tagDistrictAList.size()>0){
			merchant.setTagDistrictAList(tagDistrictAList);
		}
		if(tagDistrictBList!=null && tagDistrictBList.size()>0){
			merchant.setTagDistrictBList(tagDistrictBList);
		}
		if(merchantPresent!=null){
			merchant.setmPresent(merchantPresent);
		}
		return merchant;
	}
	
//	/**
//	 * 修改搜索条件历史记录
//	 * @param search
//	 * @return Integer
//	 */
//	public Integer updSearchHis(Merchant merchant){
//		Searches search = new Searches();
//		search.setmPreferentialType(merchant.getMerchantPriority());
//		search.setSearchKeywords(merchant.getTagValue());
//		search.setTagClassifyAId(merchant.getTagClassifyAId());
//		search.setTagDistrictAId(merchant.getTagDistrictAId());
//		search.setTagClassifyBId(merchant.getTagClassifyBId());
//		search.setTagDistrictBId(merchant.getTagDistrictBId());
//		Integer flag =null;
//		if((search.getmPreferentialType()==null || "".equals(search.getmPreferentialType())) 
//				&& (search.getSearchKeywords()==null || "".equals(search.getSearchKeywords())) 
//				&& (search.getTagClassifyAId()==null || "".equals(search.getTagClassifyAId()))
//				&& (search.getTagDistrictAId()==null || "".equals(search.getTagDistrictAId()))
//				&& (search.getTagClassifyBId()==null || "".equals(search.getTagClassifyBId()))
//				&& (search.getTagDistrictBId()==null || "".equals(search.getTagDistrictBId()))){
//			return 0;
//		}
//		List<Searches> searchList = searchesDao.getSearchesByHis(search);
//		if(searchList!=null && searchList.size()>0){
//			Integer scount = Integer.valueOf(searchList.get(0).getSearchCount()==null?"0":searchList.get(0).getSearchCount());
//			search.setSearchCount(String.valueOf(scount+1));
//			search.setId(searchList.get(0).getId());
//			flag = searchesDao.updSearchesByHis(search);
//		}else{
//			search.setSearchCount("1");
//			flag = searchesDao.addSearches(search);
//		}
//		return flag;
//	}
	
	public void setMerchantDAO(MerchantDAO merchantDAO) {
		this.merchantDAO = merchantDAO;
	}

	public void setMerchantPresentDAO(MerchantPresentDAO merchantPresentDAO) {
		this.merchantPresentDAO = merchantPresentDAO;
	}

	public void setTagClassifyADAO(TagClassifyADAO tagClassifyADAO) {
		this.tagClassifyADAO = tagClassifyADAO;
	}

	public void setTagClassifyBDAO(TagClassifyBDAO tagClassifyBDAO) {
		this.tagClassifyBDAO = tagClassifyBDAO;
	}

	public void setTagDistrictADAO(TagDistrictADAO tagDistrictADAO) {
		this.tagDistrictADAO = tagDistrictADAO;
	}

	public void setTagDistrictBDAO(TagDistrictBDAO tagDistrictBDAO) {
		this.tagDistrictBDAO = tagDistrictBDAO;
	}

	public void setMerchantPreferentialDAO(
			MerchantPreferentialDAO merchantPreferentialDAO) {
		this.merchantPreferentialDAO = merchantPreferentialDAO;
	}

	@Override
	public List<Merchant> select(Merchant queryCon) {
		try {
			logger.info("查询商户列表......");
			return this.merchantDAO.select(queryCon);
		} catch (SQLException e) {
			logger.error("查询商户列表时出现异常......",e);
		}
		return null;
	}

	@Override
	public List<Merchant> selectByRegTime(String beginTime, String endTime) {
		if(beginTime==null||endTime==null){
			logger.error("起始时间或结束时间为空！");
			return null;
		}
		try {
			logger.info("查询商户......");
			return this.merchantDAO.selectByRegTime(beginTime, endTime);
		} catch (SQLException e) {
			logger.error("按注册时间查询商户列表时出现异常",e);
		}
		return null;
	}

	@Override
	public Merchant getMerchantByPager(Merchant merchant) {  //首页左下角ajax请求使用到该方法
		if(merchant==null){
			logger.error("参数为空，分页查询失败！");
			return null;
		}
		return merchantDAO.getMerchantByPager(merchant);
	}

	@Override
	public Merchant getMerchantById(Integer id) {
		logger.info("ID查找商户");
		if(id==null){
			logger.info("ID不能为空!");
			return null;
		}
		return merchantDAO.selectByPrimaryKey(id);
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public boolean auditMerchant(Merchant merchant) {
		if(merchant==null||merchant.getId()==null||merchant.getAuditStaff()==null){
			logger.error("商户审核信息不完整！");
			return false;
		}
		logger.info("审核商户...");
		merchant.setState(Command.STATE_RECORD);
		try {
			merchantDAO.auditMerchant(merchant);
			return true;
		} catch (SQLException e) {
			logger.info("审核商户出现异常！商户编号为："+merchant.getId(),e);
		}
		return false;
	}
	
	@Override
	public boolean reviewMerchant(Merchant merchant) {
		if(merchant==null||merchant.getId()==null||merchant.getMreviewStaff()==null){
			logger.error("商户复核信息不完整！");
			return false;
		}
		logger.info("复核商户...");
		merchant.setState(Command.STATE_START);
		try {
			merchantDAO.reviewMerchant(merchant);
			return true;
		} catch (SQLException e) {
			logger.info("复核商户出现异常！商户编号为："+merchant.getId(),e);
		}
		return false;
	}

	@Override
	public List<Merchant> getAllMerchant(Merchant merchant) {
		return merchantDAO.getAllMerchant(merchant);
	}
	
	@Override
	public int updateById(Merchant record) {
		int temp = 0;
		try {
			temp = merchantDAO.updateById(record);
		} catch (Exception e) {
			logger.error("更新商户异常",e);
		}
		return temp;
	}

	@Override
	public List<Merchant> getMerchantByType(PreferType preferType) {
		if(preferType==null){
			logger.error("参数为空，查询失败");
			return null;
		}
		return merchantDAO.getMerchantByType(preferType);
	}

	@Override
	public ReMerchantResultBean addMerchantByMap(boolean isSendSms, User user,
			Merchant merchant, MerchantPresent merchantPresent,
			MerchantTrain merchantTrain, TagMAP tagMap,
			MerchantUserSet merchantUserSet) throws AppException{
		logger.info("isSendSms："+isSendSms);
		logger.info("user："+JSONObject.fromObject(user));
		logger.info("merchant："+JSONObject.fromObject(merchant));
		logger.info("merchantPresent："+JSONObject.fromObject(merchantPresent));
		logger.info("merchantTrain："+JSONObject.fromObject(merchantTrain));
		logger.info("tagMap："+JSONObject.fromObject(tagMap));
		logger.info("merchantUserSet："+JSONObject.fromObject(merchantUserSet));
		String cosUserName = user.getUsername();//用户自定义用户名
		ReMerchantResultBean reMerchantResultBean=new ReMerchantResultBean();
		
		if(user==null || merchant==null || merchantPresent==null ||
				merchantTrain==null || tagMap==null || merchantUserSet==null){
			logger.info("参数为空");
			
			reMerchantResultBean.setSuccess(false);
			reMerchantResultBean.setRemark("参数为空");
			
			return reMerchantResultBean;
		}
		
		try {
			
			//=======================================新逻辑部分 ： 实现已存在手机号码的会员自动关联，并且可自定义商户用户名
			
			boolean isUserExist = false;
			
			User userRes = userDao.queryUserByMobilephoneOrMail(user.getMobilephone());
			if(Command.respCode_SUCCESS.equals(userRes.getRespCode())){
				user = userRes;
				isUserExist = true;
				Merchant queryCon = new Merchant();
				queryCon.setUserId(userRes.getUserID());
				List<Merchant> rsMerchantList = merchantDAO.select(queryCon);
				if(!(rsMerchantList == null || rsMerchantList.size() == 0)){

					logger.info("注册商户手机号码为:"+user.getMobilephone() +"已关联商户");
					reMerchantResultBean.setSuccess(false);
					reMerchantResultBean.setRemark("该手机号码下已经关联商户");

					return reMerchantResultBean;
				}
			}
			
			Integer merchantId = null;
			if(isUserExist){
				logger.info("respCode:"+user.getRespCode()+"userId:" + user.getUserID() + "  userName:"
						+ user.getUsername());

				// 增加商户
				merchant.setUserId(user.getUserID());
				merchantId = merchantDAO.insert(merchant);
				if (merchantId == null) {
					logger.info("增加商户失败");

					reMerchantResultBean.setSuccess(false);
					reMerchantResultBean.setRemark("增加商户失败");

					return reMerchantResultBean;
				}
			}else{
				// 注册用户
				user = mUserService.autoAddMerchantUser(user, isSendSms);
				if ("0".equals(user.getRespCode())) {
					logger.info("注册用户成功");
				}
				else if("1".equals(user.getRespCode())){
					List<Merchant> merchantData = merchantDAO.getMerchantByUserId(user.getUserID());
					
					if(merchantData != null && merchantData.size()>0){
						logger.info("注册用户已存在"); //注册用户存在错误提示
						reMerchantResultBean.setSuccess(false);
						reMerchantResultBean.setRemark("注册用户已存在！");
						return reMerchantResultBean;
					}
				}
				else{
					reMerchantResultBean.setSuccess(false);
					reMerchantResultBean.setRemark("注册用户失败");
					return reMerchantResultBean;
				}
				logger.info("respCode:"+user.getRespCode()+"userId:" + user.getUserID() + "  userName:"
						+ user.getUsername());

				// 增加商户
				merchant.setUserId(user.getUserID());
				merchantId = merchantDAO.insert(merchant);
				if (merchantId == null) {
					logger.info("增加商户失败");

					reMerchantResultBean.setSuccess(false);
					reMerchantResultBean.setRemark("增加商户失败");

					return reMerchantResultBean;
				}
			}			

			
			logger.info("merchantId:" + merchantId);

			// 增加商户介绍
			merchantPresent.setMerchantId(merchantId + "");
			Integer merchantPresentId = merchantPresentService
					.addMerchantPresent(merchantPresent);
			if (merchantPresentId == null) {
				throw new AppException("增加商户介绍失败");
			}
			logger.info("merchantPresentId:" + merchantPresentId);

			// 增加商户标签
			tagMap.setMerchantId(merchantId + "");
			Integer tagMapId = tagMapService.add(tagMap);
			if (tagMapId == null) {
				throw new AppException("增加商户标签失败");
			}
			logger.info("tagMapId:" + tagMapId);
			
			// 增加商户直通车
			merchantTrain.setMerchantId(merchantId + "");
			Integer merchantTrainId = merchantTrainService
					.addMerchantTrain(merchantTrain);
			if (merchantTrainId == null) {
				throw new AppException("增加商户标签失败");
			}
			logger.info("merchantTrainId:" + merchantTrainId);
			
			// 增加商户操作组
			merchantUserSet.setMerchantId(merchantId + "");
			merchantUserSet.setLoginName(isUserExist?cosUserName : user.getUsername());
			merchantUserSet.setUserId(user.getUserID());
			String pw = new Md5PasswordEncoder().encodePassword(merchantUserSet.getBeCodepwd(), (isUserExist?cosUserName : user.getUsername())+merchantUserSet.getBeCode());
			merchantUserSet.setBeCodepwd(pw);
			Integer merchantUserSetId = merchantUserSetService
					.addMerchantUserSet(merchantUserSet);
			if (merchantTrainId == null) {
				throw new AppException("增加商户操作组失败");
			}
			logger.info("merchantUserSetId:" + merchantUserSetId);
			
			reMerchantResultBean.setSuccess(true);
			reMerchantResultBean.setMerchantId(merchantId + "");
			reMerchantResultBean.setRemark("成功");

			return reMerchantResultBean;
		} catch (Exception e) {
			logger.error("addMerchantByMap", e);
			throw new AppException("增加失败");
		}
	}

	public MerchantPresentService getMerchantPresentService() {
		return merchantPresentService;
	}

	public void setMerchantPresentService(
			MerchantPresentService merchantPresentService) {
		this.merchantPresentService = merchantPresentService;
	}

	public MerchantTrainService getMerchantTrainService() {
		return merchantTrainService;
	}

	public void setMerchantTrainService(MerchantTrainService merchantTrainService) {
		this.merchantTrainService = merchantTrainService;
	}

	public MerchantUserSetService getMerchantUserSetService() {
		return merchantUserSetService;
	}

	public void setMerchantUserSetService(
			MerchantUserSetService merchantUserSetService) {
		this.merchantUserSetService = merchantUserSetService;
	}
	
	public MUserService getmUserService() {
		return mUserService;
	}

	public void setmUserService(MUserService mUserService) {
		this.mUserService = mUserService;
	}
	
	public TagMapService getTagMapService() {
		return tagMapService;
	}

	public void setTagMapService(TagMapService tagMapService) {
		this.tagMapService = tagMapService;
	}

	@Override
	public List<Merchant> getInnerMerchant() {
		return merchantDAO.getInnerMerchant();
	}

	@Override
	public Merchant getMerchantsPreferentialType(Merchant merchant) {
		return merchantDAO.getMerchantsPreferentialType(merchant);
	}

}
