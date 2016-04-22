package com.froad.CB.po.merchant;

import java.io.Serializable;
import java.util.List;

import com.froad.CB.common.Pager;
import com.froad.CB.po.Store;
import com.froad.CB.po.tag.TagClassifyA;
import com.froad.CB.po.tag.TagClassifyB;
import com.froad.CB.po.tag.TagDistrictA;
import com.froad.CB.po.tag.TagDistrictB;
import com.froad.CB.po.tag.TagMAP;
import com.froad.CB.po.user.User;


	/**
	 * 类描述：商户实体
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:31:02 PM 
	 */
public class Merchant extends Pager implements Serializable{
    
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private MerchantTrain merchantTrain;
    private String dcHyperlink;//商户直连URL，超链接点击直接进商户界面，唯一标识
    private String companyFullName;//公司全名
    private String companyShortName;//公司短名
    private String companyLogoUrl;//公司logo图片路径
    private String companyLogoFormat;//公司logo图片规格
    private String companyAddress;//公司地址
    private String companyZip;//公司邮编
    private String companyFax;//公司传真
    private String companyTel;//公司电话
    private String companyLegalName;//公司法人名
    private String companyLegalCredentType;//公司法人证件类型
    private String companyLegalCredentNo;//公司法人证件号
    private String companyContactName;//公司联络人姓名
    private String companyContactTel1;//公司联络人电话1
    private String companyContactTel2;//公司联络人电话2
    private String mcompanyContactEmail;//公司联络人邮件
    private String mstoreFullName;//门店全名
    private String mstoreShortName;//门店短名
    private String mstoreLogoUrl;//门店logo图片路径
    private String mstoreLogoFormat;//门店logo图片规格
    private String mstoreAddress;//门店地址
    private String mstoreZip;//门店邮编
    private String mstoreFax;//门店传真
    private String mstoreTel;//门店电话
    private String mstoreContactName;//门店联络人姓名
    private String mstoreContactTel1;//门店联络人电话1
    private String mstoreContactTel2;//门店联络人电话2
    private String mstoreContactEmail;//门店联络人邮件
    private String mcontractBegintime;//签约时间
    private String mcontractEndtime;//到期时间
    private String localStationId;//地方站id
    private String localBankId;//推荐银行id
    private String mcontractStaff;//签约业务人员
    private String auditStaff;//复核业务人员
    private String clientImg1;//手机端用的小图 184*141
    private String clientImg2;//手机端用的小图 640*293
    private String mreviewStaff;
    private String state;
    private String createTime;
    private String updateTime;
    private String userId;
    private String remark;
    private List<MerchantPhoto> photoList;//商户相册
    private List<MerchantProduct> productList;//商户商品
    private MerchantPresent merchantPresent;//商户简介
    private List<TagMAP> tagMapList;
    private List<Store> storeList;//商户门店列表
    
    private String hotLevel;
    
    private String isHot;
    
    private User user;
    private String tagValue;//标签值
    private List<TagClassifyA> tagClassifyAList;    
    private List<TagClassifyB> tagClassifyBList;   
    private List<TagDistrictA> tagDistrictAList;   
    private List<TagDistrictB> tagDistrictBList;
    private List<MerchantPreferential> mPreferentialList;
    private MerchantPresent mPresent;
    private String respCode;
	private String respMsg;	
	private String preferentialType;//优惠类型
	private String preferenceDesc;//优惠描述
	private String pointRate;//积分率
	private String preferentialRate;//折扣率
	private String merchantPriority;//优先级
	
	/**tmp field**/
	private String beginTime;	
	private String endTime;	
	private String tagDistrictBId;	
	private String tagDistrictAId;	
	private String tagClassifyAId;	
	private String tagClassifyBId;
	private List<String> stateList;//交易状态 作查询条件用的字段
	
	
	//13.2.27
	private String isInternalAccount;
	private String businessLicenseUrl;
	private String taxRegistrationCertificateUrl;
	private String organizationCodeCertificateUrl;
	private String legalIdcardUrl;
	
	//商户活动标识
	private String activityId;	//商户参加的活动标识暂时只有两种0：未参加送影票活动 1:参加送影票活动			
	
	//是否参加春节抽奖活动
	private String isSpringActivity;

    //支持精品预售开关
    private  String isAffordPresellFlag;
	
	public String getIsInternalAccount() {
		return isInternalAccount;
	}

	public void setIsInternalAccount(String isInternalAccount) {
		this.isInternalAccount = isInternalAccount;
	}

	public String getBusinessLicenseUrl() {
		return businessLicenseUrl;
	}

	public void setBusinessLicenseUrl(String businessLicenseUrl) {
		this.businessLicenseUrl = businessLicenseUrl;
	}

	public String getTaxRegistrationCertificateUrl() {
		return taxRegistrationCertificateUrl;
	}

	public void setTaxRegistrationCertificateUrl(
			String taxRegistrationCertificateUrl) {
		this.taxRegistrationCertificateUrl = taxRegistrationCertificateUrl;
	}

	public String getOrganizationCodeCertificateUrl() {
		return organizationCodeCertificateUrl;
	}

	public void setOrganizationCodeCertificateUrl(
			String organizationCodeCertificateUrl) {
		this.organizationCodeCertificateUrl = organizationCodeCertificateUrl;
	}

	public String getLegalIdcardUrl() {
		return legalIdcardUrl;
	}

	public void setLegalIdcardUrl(String legalIdcardUrl) {
		this.legalIdcardUrl = legalIdcardUrl;
	}

	public String getDcHyperlink() {
		return dcHyperlink;
	}

	public void setDcHyperlink(String dcHyperlink) {
		this.dcHyperlink = dcHyperlink;
	}

	public String getCompanyFullName() {
		return companyFullName;
	}

	public void setCompanyFullName(String companyFullName) {
		this.companyFullName = companyFullName;
	}

	public String getCompanyShortName() {
		return companyShortName;
	}

	public void setCompanyShortName(String companyShortName) {
		this.companyShortName = companyShortName;
	}

	public String getCompanyLogoUrl() {
		return companyLogoUrl;
	}

	public void setCompanyLogoUrl(String companyLogoUrl) {
		this.companyLogoUrl = companyLogoUrl;
	}

	public String getCompanyLogoFormat() {
		return companyLogoFormat;
	}

	public void setCompanyLogoFormat(String companyLogoFormat) {
		this.companyLogoFormat = companyLogoFormat;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyZip() {
		return companyZip;
	}

	public void setCompanyZip(String companyZip) {
		this.companyZip = companyZip;
	}

	public String getCompanyFax() {
		return companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}

	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public String getCompanyLegalName() {
		return companyLegalName;
	}

	public void setCompanyLegalName(String companyLegalName) {
		this.companyLegalName = companyLegalName;
	}

	public String getCompanyLegalCredentType() {
		return companyLegalCredentType;
	}

	public void setCompanyLegalCredentType(String companyLegalCredentType) {
		this.companyLegalCredentType = companyLegalCredentType;
	}

	public String getCompanyLegalCredentNo() {
		return companyLegalCredentNo;
	}

	public void setCompanyLegalCredentNo(String companyLegalCredentNo) {
		this.companyLegalCredentNo = companyLegalCredentNo;
	}

	public String getCompanyContactName() {
		return companyContactName;
	}

	public void setCompanyContactName(String companyContactName) {
		this.companyContactName = companyContactName;
	}

	public String getCompanyContactTel1() {
		return companyContactTel1;
	}

	public void setCompanyContactTel1(String companyContactTel1) {
		this.companyContactTel1 = companyContactTel1;
	}

	public String getCompanyContactTel2() {
		return companyContactTel2;
	}

	public void setCompanyContactTel2(String companyContactTel2) {
		this.companyContactTel2 = companyContactTel2;
	}

	public String getMstoreShortName() {
		return mstoreShortName;
	}

	public void setMstoreShortName(String mstoreShortName) {
		this.mstoreShortName = mstoreShortName;
	}


	public String getLocalStationId() {
		return localStationId;
	}

	public void setLocalStationId(String localStationId) {
		this.localStationId = localStationId;
	}

	public String getLocalBankId() {
		return localBankId;
	}

	public void setLocalBankId(String localBankId) {
		this.localBankId = localBankId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTagValue() {
		return tagValue;
	}

	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}

	public List<TagClassifyA> getTagClassifyAList() {
		return tagClassifyAList;
	}

	public void setTagClassifyAList(List<TagClassifyA> tagClassifyAList) {
		this.tagClassifyAList = tagClassifyAList;
	}

	public List<TagClassifyB> getTagClassifyBList() {
		return tagClassifyBList;
	}

	public void setTagClassifyBList(List<TagClassifyB> tagClassifyBList) {
		this.tagClassifyBList = tagClassifyBList;
	}

	public List<TagDistrictA> getTagDistrictAList() {
		return tagDistrictAList;
	}

	public void setTagDistrictAList(List<TagDistrictA> tagDistrictAList) {
		this.tagDistrictAList = tagDistrictAList;
	}

	public List<TagDistrictB> getTagDistrictBList() {
		return tagDistrictBList;
	}

	public void setTagDistrictBList(List<TagDistrictB> tagDistrictBList) {
		this.tagDistrictBList = tagDistrictBList;
	}

	public List<MerchantPreferential> getmPreferentialList() {
		return mPreferentialList;
	}

	public void setmPreferentialList(List<MerchantPreferential> mPreferentialList) {
		this.mPreferentialList = mPreferentialList;
	}

	public void setmPresent(MerchantPresent mPresent) {
		this.mPresent = mPresent;
	}

	public MerchantPresent getmPresent() {
		return mPresent;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getPreferentialType() {
		return preferentialType;
	}

	public void setPreferentialType(String preferentialType) {
		this.preferentialType = preferentialType;
	}

	public String getPointRate() {
		return pointRate;
	}

	public void setPointRate(String pointRate) {
		this.pointRate = pointRate;
	}

	public String getPreferentialRate() {
		return preferentialRate;
	}

	public void setPreferentialRate(String preferentialRate) {
		this.preferentialRate = preferentialRate;
	}

	public String getMerchantPriority() {
		return merchantPriority;
	}

	public void setMerchantPriority(String merchantPriority) {
		this.merchantPriority = merchantPriority;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAuditStaff() {
		return auditStaff;
	}

	public void setAuditStaff(String auditStaff) {
		this.auditStaff = auditStaff;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTagDistrictBId() {
		return tagDistrictBId;
	}

	public void setTagDistrictBId(String tagDistrictBId) {
		this.tagDistrictBId = tagDistrictBId;
	}

	public String getTagClassifyAId() {
		return tagClassifyAId;
	}

	public void setTagClassifyAId(String tagClassifyAId) {
		this.tagClassifyAId = tagClassifyAId;
	}

	public String getTagDistrictAId() {
		return tagDistrictAId;
	}

	public void setTagDistrictAId(String tagDistrictAId) {
		this.tagDistrictAId = tagDistrictAId;
	}

	public String getTagClassifyBId() {
		return tagClassifyBId;
	}

	public void setTagClassifyBId(String tagClassifyBId) {
		this.tagClassifyBId = tagClassifyBId;
	}

	public List<TagMAP> getTagMapList() {
		return tagMapList;
	}

	public void setTagMapList(List<TagMAP> tagMapList) {
		this.tagMapList = tagMapList;
	}

	public String getHotLevel() {
		return hotLevel;
	}

	public void setHotLevel(String hotLevel) {
		this.hotLevel = hotLevel;
	}

	public String getIsHot() {
		return isHot;
	}

	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}

	public MerchantPresent getMerchantPresent() {
		return merchantPresent;
	}

	public void setMerchantPresent(MerchantPresent merchantPresent) {
		this.merchantPresent = merchantPresent;
	}

	public List<MerchantPhoto> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<MerchantPhoto> photoList) {
		this.photoList = photoList;
	}

	public List<MerchantProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<MerchantProduct> productList) {
		this.productList = productList;
	}

	public List<String> getStateList() {
		return stateList;
	}

	public void setStateList(List<String> stateList) {
		this.stateList = stateList;
	}

	public String getClientImg1() {
		return clientImg1;
	}

	public void setClientImg1(String clientImg1) {
		this.clientImg1 = clientImg1;
	}

	public String getClientImg2() {
		return clientImg2;
	}

	public void setClientImg2(String clientImg2) {
		this.clientImg2 = clientImg2;
	}

	public MerchantTrain getMerchantTrain() {
		return merchantTrain;
	}

	public void setMerchantTrain(MerchantTrain merchantTrain) {
		this.merchantTrain = merchantTrain;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMstoreFullName() {
		return mstoreFullName;
	}

	public void setMstoreFullName(String mstoreFullName) {
		this.mstoreFullName = mstoreFullName;
	}

	public String getMstoreLogoUrl() {
		return mstoreLogoUrl;
	}

	public void setMstoreLogoUrl(String mstoreLogoUrl) {
		this.mstoreLogoUrl = mstoreLogoUrl;
	}

	public String getMstoreLogoFormat() {
		return mstoreLogoFormat;
	}

	public void setMstoreLogoFormat(String mstoreLogoFormat) {
		this.mstoreLogoFormat = mstoreLogoFormat;
	}

	public String getMstoreAddress() {
		return mstoreAddress;
	}

	public void setMstoreAddress(String mstoreAddress) {
		this.mstoreAddress = mstoreAddress;
	}

	public String getMstoreZip() {
		return mstoreZip;
	}

	public void setMstoreZip(String mstoreZip) {
		this.mstoreZip = mstoreZip;
	}

	public String getMstoreFax() {
		return mstoreFax;
	}

	public void setMstoreFax(String mstoreFax) {
		this.mstoreFax = mstoreFax;
	}

	public String getMstoreTel() {
		return mstoreTel;
	}

	public void setMstoreTel(String mstoreTel) {
		this.mstoreTel = mstoreTel;
	}

	public String getMstoreContactName() {
		return mstoreContactName;
	}

	public void setMstoreContactName(String mstoreContactName) {
		this.mstoreContactName = mstoreContactName;
	}

	public String getMstoreContactTel1() {
		return mstoreContactTel1;
	}

	public void setMstoreContactTel1(String mstoreContactTel1) {
		this.mstoreContactTel1 = mstoreContactTel1;
	}

	public String getMstoreContactTel2() {
		return mstoreContactTel2;
	}

	public void setMstoreContactTel2(String mstoreContactTel2) {
		this.mstoreContactTel2 = mstoreContactTel2;
	}

	public String getMstoreContactEmail() {
		return mstoreContactEmail;
	}

	public void setMstoreContactEmail(String mstoreContactEmail) {
		this.mstoreContactEmail = mstoreContactEmail;
	}

	public String getMcontractBegintime() {
		return mcontractBegintime;
	}

	public void setMcontractBegintime(String mcontractBegintime) {
		this.mcontractBegintime = mcontractBegintime;
	}

	public String getMcontractEndtime() {
		return mcontractEndtime;
	}

	public void setMcontractEndtime(String mcontractEndtime) {
		this.mcontractEndtime = mcontractEndtime;
	}

	public String getMcontractStaff() {
		return mcontractStaff;
	}

	public void setMcontractStaff(String mcontractStaff) {
		this.mcontractStaff = mcontractStaff;
	}

	public String getMreviewStaff() {
		return mreviewStaff;
	}

	public void setMreviewStaff(String mreviewStaff) {
		this.mreviewStaff = mreviewStaff;
	}

	public String getMcompanyContactEmail() {
		return mcompanyContactEmail;
	}

	public void setMcompanyContactEmail(String mcompanyContactEmail) {
		this.mcompanyContactEmail = mcompanyContactEmail;
	}

	

	public List<Store> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<Store> storeList) {
		this.storeList = storeList;
	}

	public String getPreferenceDesc() {
		return preferenceDesc;
	}

	public void setPreferenceDesc(String preferenceDesc) {
		this.preferenceDesc = preferenceDesc;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getIsSpringActivity() {
		return isSpringActivity;
	}

	public void setIsSpringActivity(String isSpringActivity) {
		this.isSpringActivity = isSpringActivity;
	}

        public String getIsAffordPresellFlag()
        {
            return isAffordPresellFlag;
        }

        public void setIsAffordPresellFlag(String isAffordPresellFlag)
        {
            this.isAffordPresellFlag = isAffordPresellFlag;
        }
    }