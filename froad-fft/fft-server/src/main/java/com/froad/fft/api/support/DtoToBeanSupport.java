package com.froad.fft.api.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.froad.fft.bean.SystemConfig;
import com.froad.fft.bean.trans.TransDto;
import com.froad.fft.dto.AdDto;
import com.froad.fft.dto.AdPositionDto;
import com.froad.fft.dto.AgreementDto;
import com.froad.fft.dto.AreaDto;
import com.froad.fft.dto.ArticleCategoryDto;
import com.froad.fft.dto.ArticleDto;
import com.froad.fft.dto.BusinessCircleDto;
import com.froad.fft.dto.CashPointsRatioDto;
import com.froad.fft.dto.FriendLinkDto;
import com.froad.fft.dto.FundsChannelDto;
import com.froad.fft.dto.GivePointRuleDto;
import com.froad.fft.dto.LogDto;
import com.froad.fft.dto.MerchantAccountDto;
import com.froad.fft.dto.MerchantCategoryDto;
import com.froad.fft.dto.MerchantCommentDto;
import com.froad.fft.dto.MerchantDto;
import com.froad.fft.dto.MerchantGroupUserDto;
import com.froad.fft.dto.MerchantOnlineApplyDto;
import com.froad.fft.dto.MerchantOutletDto;
import com.froad.fft.dto.MerchantPhotoDto;
import com.froad.fft.dto.MerchantPreferentialDto;
import com.froad.fft.dto.MerchantPresentDto;
import com.froad.fft.dto.NavigationDto;
import com.froad.fft.dto.OutletPresellDeliveryDto;
import com.froad.fft.dto.PresellDeliveryDto;
import com.froad.fft.dto.ProductAttributeDto;
import com.froad.fft.dto.ProductCategoryDto;
import com.froad.fft.dto.ProductDto;
import com.froad.fft.dto.ProductGroupDto;
import com.froad.fft.dto.ProductImageDto;
import com.froad.fft.dto.ProductPresellDeliveryDto;
import com.froad.fft.dto.ProductPresellDto;
import com.froad.fft.dto.ProductRestrictRuleDto;
import com.froad.fft.dto.ProductTypeDto;
import com.froad.fft.dto.RefundsDto;
import com.froad.fft.dto.RegisterGivePointRuleDto;
import com.froad.fft.dto.ReturnSaleDetailDto;
import com.froad.fft.dto.ReturnSaleDto;
import com.froad.fft.dto.SmsContentDto;
import com.froad.fft.dto.SmsLogDto;
import com.froad.fft.dto.StockPileDto;
import com.froad.fft.dto.StockPileLogDto;
import com.froad.fft.dto.SysResourceDto;
import com.froad.fft.dto.SysRoleDto;
import com.froad.fft.dto.SysRoleResourceDto;
import com.froad.fft.dto.SysUserDto;
import com.froad.fft.dto.SysUserRoleDto;
import com.froad.fft.dto.SystemConfigDto;
import com.froad.fft.dto.TagDto;
import com.froad.fft.dto.TransDetailDto;
import com.froad.fft.dto.TransQueryDto;
import com.froad.fft.dto.TransSecurityTicketDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.trans.TransCreateSource;
import com.froad.fft.enums.trans.TransPayChannel;
import com.froad.fft.enums.trans.TransPayMethod;
import com.froad.fft.enums.trans.TransType;
import com.froad.fft.persistent.common.enums.PayChannel;
import com.froad.fft.persistent.common.enums.PayMethod;
import com.froad.fft.persistent.common.enums.PayRole;
import com.froad.fft.persistent.common.enums.PayState;
import com.froad.fft.persistent.common.enums.PayType;
import com.froad.fft.persistent.common.enums.PayTypeDetails;
import com.froad.fft.persistent.common.enums.RestrictType;
import com.froad.fft.persistent.common.enums.TransPayState;
import com.froad.fft.persistent.entity.Ad;
import com.froad.fft.persistent.entity.AdPosition;
import com.froad.fft.persistent.entity.Agreement;
import com.froad.fft.persistent.entity.Area;
import com.froad.fft.persistent.entity.Article;
import com.froad.fft.persistent.entity.ArticleCategory;
import com.froad.fft.persistent.entity.BusinessCircle;
import com.froad.fft.persistent.entity.CashPointsRatio;
import com.froad.fft.persistent.entity.FriendLink;
import com.froad.fft.persistent.entity.FundsChannel;
import com.froad.fft.persistent.entity.GivePointRule;
import com.froad.fft.persistent.entity.Log;
import com.froad.fft.persistent.entity.Merchant;
import com.froad.fft.persistent.entity.MerchantAccount;
import com.froad.fft.persistent.entity.MerchantCategory;
import com.froad.fft.persistent.entity.MerchantComment;
import com.froad.fft.persistent.entity.MerchantGroupUser;
import com.froad.fft.persistent.entity.MerchantOnlineApply;
import com.froad.fft.persistent.entity.MerchantOutlet;
import com.froad.fft.persistent.entity.MerchantPhoto;
import com.froad.fft.persistent.entity.MerchantPreferential;
import com.froad.fft.persistent.entity.MerchantPresent;
import com.froad.fft.persistent.entity.Navigation;
import com.froad.fft.persistent.entity.OutletPresellDelivery;
import com.froad.fft.persistent.entity.Pay;
import com.froad.fft.persistent.entity.PresellDelivery;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.persistent.entity.ProductAttribute;
import com.froad.fft.persistent.entity.ProductCategory;
import com.froad.fft.persistent.entity.ProductGroup;
import com.froad.fft.persistent.entity.ProductImage;
import com.froad.fft.persistent.entity.ProductPresell;
import com.froad.fft.persistent.entity.ProductPresellDelivery;
import com.froad.fft.persistent.entity.ProductRestrictRule;
import com.froad.fft.persistent.entity.ProductType;
import com.froad.fft.persistent.entity.Refunds;
import com.froad.fft.persistent.entity.RegisterGivePointRule;
import com.froad.fft.persistent.entity.ReturnSale;
import com.froad.fft.persistent.entity.ReturnSaleDetail;
import com.froad.fft.persistent.entity.SmsContent;
import com.froad.fft.persistent.entity.SmsLog;
import com.froad.fft.persistent.entity.StockPile;
import com.froad.fft.persistent.entity.StockPileLog;
import com.froad.fft.persistent.entity.SysClient;
import com.froad.fft.persistent.entity.SysResource;
import com.froad.fft.persistent.entity.SysRole;
import com.froad.fft.persistent.entity.SysRoleResource;
import com.froad.fft.persistent.entity.SysUser;
import com.froad.fft.persistent.entity.SysUserRole;
import com.froad.fft.persistent.entity.Tag;
import com.froad.fft.persistent.entity.Trans;
import com.froad.fft.persistent.entity.TransDetails;
import com.froad.fft.persistent.entity.TransSecurityTicket;
import com.froad.fft.service.SysClientService;
import com.froad.fft.util.SpringUtil;

/**
 * dto转化bean
 *
 * @author FQ
 */
public class DtoToBeanSupport
{

    /**
     * 广告
     *
     * @param adDto
     * @return
     */
    public static Ad loadByAd(AdDto adDto)
    {
        Ad ad = new Ad();

        ad.setId(adDto.getId());
        ad.setTitle(adDto.getTitle());
        ad.setType(adDto.getType() != null ? com.froad.fft.persistent.common.enums.AdType.valueOf(adDto.getType().name()) : null);
        ad.setBeginTime(adDto.getBeginTime());
        ad.setEndTime(adDto.getEndTime());
        ad.setContent(adDto.getContent());
        ad.setPath(adDto.getPath());
        ad.setLink(adDto.getLink());
        ad.setIsEnabled(adDto.getIsEnabled());
        ad.setIsBlankTarge(adDto.getIsBlankTarge());
        ad.setClickCount(adDto.getClickCount());
        ad.setOrderValue(adDto.getOrderValue());
        ad.setAdPositionId(adDto.getAdPositionId());

        return ad;
    }

    /**
     * 广告位
     *
     * @param adPositionDto
     * @return
     */
    public static AdPosition loadByAdPosition(AdPositionDto adPositionDto)
    {
        AdPosition adPosition = new AdPosition();

        adPosition.setId(adPositionDto.getId());
        adPosition.setCreateTime(adPositionDto.getCreateTime());
        adPosition.setName(adPositionDto.getName());
        adPosition.setWidth(adPositionDto.getWidth());
        adPosition.setHeight(adPositionDto.getHeight());
        adPosition.setDescription(adPositionDto.getDescription());
        adPosition.setPositionStyle(adPositionDto.getPositionStyle());
        adPosition.setClientId(adPositionDto.getClientId());

        return adPosition;
    }

    /**
     * 协议
     *
     * @param agreementDto
     * @return
     */
    public static Agreement loadByAgreement(AgreementDto agreementDto)
    {
        Agreement agreement = new Agreement();

        agreement.setId(agreementDto.getId());
        agreement.setCreateTime(agreementDto.getCreateTime());
        agreement.setType(agreementDto.getType() != null ? com.froad.fft.persistent.common.enums.AgreementType.valueOf(agreementDto.getType().name()) : null);
        agreement.setContent(agreementDto.getContent());
        agreement.setClientId(agreementDto.getClientId());

        return agreement;
    }

    /**
     * 地区
     *
     * @param areaDto
     * @return
     */
    public static Area loadByArea(AreaDto areaDto)
    {
        Area area = new Area();

        area.setId(areaDto.getId());
        area.setCreateTime(areaDto.getCreateTime());
        area.setName(areaDto.getName());
        area.setOrderValue(areaDto.getOrderValue());
        area.setTreePath(areaDto.getTreePath());
        area.setParentId(areaDto.getParentId());

        return area;
    }

    /**
     * 文章分类
     *
     * @param articleCategoryDto
     * @return
     */
    public static ArticleCategory loadByArticleCategory(ArticleCategoryDto articleCategoryDto)
    {
        ArticleCategory articleCategory = new ArticleCategory();

        articleCategory.setId(articleCategoryDto.getId());
        articleCategory.setCreateTime(articleCategoryDto.getCreateTime());
        articleCategory.setName(articleCategoryDto.getName());
        articleCategory.setSeoTitle(articleCategoryDto.getSeoTitle());
        articleCategory.setSeoKeywords(articleCategoryDto.getSeoKeywords());
        articleCategory.setSeoDescription(articleCategoryDto.getSeoDescription());
        articleCategory.setOrderValue(articleCategoryDto.getOrderValue());
        articleCategory.setTreePath(articleCategoryDto.getTreePath());
        articleCategory.setParentId(articleCategoryDto.getParentId());

        return articleCategory;
    }

    /**
     * 文章
     *
     * @param articleDto
     * @return
     */
    public static Article loadByArticle(ArticleDto articleDto)
    {
        Article article = new Article();

        article.setId(articleDto.getId());
        article.setCreateTime(articleDto.getCreateTime());
        article.setTitle(articleDto.getTitle());
        article.setAuthor(articleDto.getAuthor());
        article.setContent(articleDto.getContent());
        article.setSeoTitle(articleDto.getSeoTitle());
        article.setSeoKeywords(articleDto.getSeoKeywords());
        article.setSeoDescription(articleDto.getSeoDescription());
        article.setIsPublication(articleDto.getIsPublication());
        article.setIsTop(articleDto.getIsTop());
        article.setIsRecommend(articleDto.getIsRecommend());
        article.setPageCount(articleDto.getPageCount());
        article.setHtmlPath(articleDto.getHtmlPath());
        article.setHits(articleDto.getHits());
        article.setArticleCategoryId(articleDto.getArticleCategoryId());
        article.setClientId(articleDto.getClientId());

        return article;
    }

    /**
     * 友情链接
     *
     * @param friendLinkDto
     * @return
     */
    public static FriendLink loadByFriendLink(FriendLinkDto friendLinkDto)
    {
        FriendLink friendLink = new FriendLink();

        friendLink.setId(friendLinkDto.getId());
        friendLink.setCreateTime(friendLinkDto.getCreateTime());
        friendLink.setName(friendLinkDto.getName());
        friendLink.setType(friendLinkDto.getType() != null ? com.froad.fft.persistent.common.enums.FriendLinkType.valueOf(friendLinkDto.getType().name()) : null);
        friendLink.setLogo(friendLinkDto.getLogo());
        friendLink.setUrl(friendLinkDto.getUrl());
        friendLink.setOrderValue(friendLinkDto.getOrderValue());
        friendLink.setClientId(friendLinkDto.getClientId());

        return friendLink;
    }

    /**
     * 日志
     *
     * @param logDto
     * @return
     */
    public static Log loadByLog(LogDto logDto)
    {
        Log log = new Log();

        log.setId(logDto.getId());
        log.setCreateTime(logDto.getCreateTime());
        log.setOperation(logDto.getOperation());
        log.setOperator(logDto.getOperator());
        log.setContent(logDto.getContent());
        log.setParameter(logDto.getParameter());
        log.setIp(logDto.getIp());

        return log;
    }

    /**
     * 商户分类
     *
     * @param merchantCategoryDto
     * @return
     */
    public static MerchantCategory loadByMerchantCategory(MerchantCategoryDto merchantCategoryDto)
    {
        MerchantCategory merchantCategory = new MerchantCategory();

        merchantCategory.setId(merchantCategoryDto.getId());
        merchantCategory.setCreateTime(merchantCategoryDto.getCreateTime());
        merchantCategory.setName(merchantCategoryDto.getName());
        merchantCategory.setOrderValue(merchantCategoryDto.getOrderValue());
        merchantCategory.setTreePath(merchantCategoryDto.getTreePath());
        merchantCategory.setParentId(merchantCategoryDto.getParentId());

        return merchantCategory;
    }

    /**
     * 商户点评
     *
     * @param merchantComment
     * @return
     */
    public static MerchantComment loadByMerchantComment(MerchantCommentDto merchantCommentDto)
    {
        MerchantComment merchantComment = new MerchantComment();

        merchantComment.setId(merchantCommentDto.getId());
        merchantComment.setCreateTime(merchantCommentDto.getCreateTime());
        merchantComment.setStarLevel(merchantCommentDto.getStarLevel() != null ? com.froad.fft.persistent.entity.MerchantComment.StarLevel.valueOf(merchantCommentDto.getStarLevel().toString()) :
                null);
        merchantComment.setProductPoint(merchantCommentDto.getProductPoint() != null ? com.froad.fft.persistent.entity.MerchantComment.Point.valueOf(merchantCommentDto.getProductPoint().toString())
                : null);
        merchantComment.setEnvironmentPoint(merchantCommentDto.getEnvironmentPoint() != null ? com.froad.fft.persistent.entity.MerchantComment.Point.valueOf(merchantCommentDto.getEnvironmentPoint()
                .toString()) : null);
        merchantComment.setServePoint(merchantCommentDto.getServePoint() != null ? com.froad.fft.persistent.entity.MerchantComment.Point.valueOf(merchantCommentDto.getServePoint().toString()) : null);
        merchantComment.setCommentDescription(merchantCommentDto.getCommentDescription());
        merchantComment.setMerchantOutletId(merchantCommentDto.getMerchantOutletId());
        merchantComment.setMemberCode(merchantCommentDto.getMemberCode());

        return merchantComment;
    }

    /**
     * 商户
     *
     * @param merchantDto
     * @return
     */
    public static Merchant loadByMerchant(MerchantDto merchantDto)
    {
        Merchant merchant = new Merchant();

        merchant.setId(merchantDto.getId());
        merchant.setCreateTime(merchantDto.getCreateTime());
        merchant.setName(merchantDto.getName());
        merchant.setFullName(merchantDto.getFullName());
        merchant.setLogo(merchantDto.getLogo());
        merchant.setAddress(merchantDto.getAddress());
        merchant.setZip(merchantDto.getZip());
        merchant.setFax(merchantDto.getFax());
        merchant.setTel(merchantDto.getTel());
        merchant.setContactName(merchantDto.getContactName());
        merchant.setContactPhone(merchantDto.getContactPhone());
        merchant.setContactEmail(merchantDto.getContactEmail());
        merchant.setLegalName(merchantDto.getLegalName());
        merchant.setLegalCredentType(merchantDto.getLegalCredentType());
        merchant.setLegalCredentNo(merchantDto.getLegalCredentNo());
        merchant.setContractBegintime(merchantDto.getContractBegintime());
        merchant.setContractEndtime(merchantDto.getContractEndtime());
        merchant.setContractStaff(merchantDto.getContractStaff());
        merchant.setReviewStaff(merchantDto.getReviewStaff());
        merchant.setIsAudit(merchantDto.getIsAudit());
        merchant.setOrderValue(merchantDto.getOrderValue());
        merchant.setType(merchantDto.getType() != null ? com.froad.fft.persistent.common.enums.MerchantType.valueOf(merchantDto.getType().name()) : null);
        merchant.setAreaId(merchantDto.getAreaId());
        merchant.setMerchantCategoryId(merchantDto.getMerchantCategoryId());
        merchant.setClientId(merchantDto.getClientId());
        merchant.setDataState(merchantDto.getDataState() != null ? com.froad.fft.persistent.common.enums.DataState.valueOf(merchantDto.getDataState().name()) : null);
        return merchant;
    }

    /**
     * 商户在线申请
     *
     * @param merchantOnlineApply
     * @return
     */
    public static MerchantOnlineApply loadByMerchantOnlineApply(MerchantOnlineApplyDto merchantOnlineApplyDto)
    {
        MerchantOnlineApply merchantOnlineApply = new MerchantOnlineApply();

        merchantOnlineApply.setId(merchantOnlineApplyDto.getId());
        merchantOnlineApply.setCreateTime(merchantOnlineApplyDto.getCreateTime());
        merchantOnlineApply.setCompanyName(merchantOnlineApplyDto.getCompanyName());
        merchantOnlineApply.setLinkman(merchantOnlineApplyDto.getLinkman());
        merchantOnlineApply.setMobile(merchantOnlineApplyDto.getMobile());
        merchantOnlineApply.setPhone(merchantOnlineApplyDto.getPhone());
        merchantOnlineApply.setAddress(merchantOnlineApplyDto.getAddress());
        merchantOnlineApply.setAreaId(merchantOnlineApplyDto.getAreaId());
        merchantOnlineApply.setCooperationType(merchantOnlineApplyDto.getCooperationType() != null ? com.froad.fft.persistent.common.enums.MerchantType.valueOf(merchantOnlineApplyDto
                .getCooperationType().name()) : null);
        merchantOnlineApply.setCooperativePurpose(merchantOnlineApplyDto.getCooperativePurpose());
        merchantOnlineApply.setIsRelation(merchantOnlineApplyDto.getIsRelation());
        merchantOnlineApply.setRelationAccount(merchantOnlineApplyDto.getRelationAccount());
        merchantOnlineApply.setRelationTime(merchantOnlineApplyDto.getRelationTime());
        merchantOnlineApply.setRelationMark(merchantOnlineApplyDto.getRelationMark());
        merchantOnlineApply.setClientId(merchantOnlineApplyDto.getClientId());

        return merchantOnlineApply;
    }

    /**
     * 商户门店
     *
     * @param merchantOutlet
     * @return
     */
    public static MerchantOutlet loadByMerchantOutlet(MerchantOutletDto merchantOutletDto)
    {
        MerchantOutlet merchantOutlet = new MerchantOutlet();

        merchantOutlet.setId(merchantOutletDto.getId());
        merchantOutlet.setCreateTime(merchantOutletDto.getCreateTime());
        merchantOutlet.setName(merchantOutletDto.getName());
        merchantOutlet.setFullName(merchantOutletDto.getFullName());
        merchantOutlet.setLogo(merchantOutletDto.getLogo());
        merchantOutlet.setAddress(merchantOutletDto.getAddress());
        merchantOutlet.setBusinessHours(merchantOutletDto.getBusinessHours());
        merchantOutlet.setZip(merchantOutletDto.getZip());
        merchantOutlet.setFax(merchantOutletDto.getFax());
        merchantOutlet.setTel(merchantOutletDto.getTel());
        merchantOutlet.setContactName(merchantOutletDto.getContactName());
        merchantOutlet.setContactPhone(merchantOutletDto.getContactPhone());
        merchantOutlet.setContactEmail(merchantOutletDto.getContactEmail());
        merchantOutlet.setServiceProvider(merchantOutletDto.getServiceProvider());
        merchantOutlet.setCoordinate(merchantOutletDto.getCoordinate());
        merchantOutlet.setOrderValue(merchantOutletDto.getOrderValue());
        merchantOutlet.setMerchantId(merchantOutletDto.getMerchantId());

        return merchantOutlet;
    }

    /**
     * 商户相册
     *
     * @param merchantPhoto
     * @return
     */
    public static MerchantPhoto loadByMerchantPhoto(MerchantPhotoDto merchantPhotoDto)
    {
        MerchantPhoto merchantPhoto = new MerchantPhoto();

        merchantPhoto.setId(merchantPhotoDto.getId());
        merchantPhoto.setCreateTime(merchantPhotoDto.getCreateTime());
        merchantPhoto.setTitle(merchantPhotoDto.getTitle());
        merchantPhoto.setUrl(merchantPhotoDto.getUrl());
        merchantPhoto.setWidth(merchantPhotoDto.getWidth());
        merchantPhoto.setHeight(merchantPhotoDto.getHeight());
        merchantPhoto.setOrderValue(merchantPhotoDto.getOrderValue());
        merchantPhoto.setDescription(merchantPhotoDto.getDescription());
        merchantPhoto.setMerchantOutletId(merchantPhotoDto.getMerchantOutletId());

        return merchantPhoto;
    }

    /**
     * 商户优惠活动
     *
     * @param merchantPreferential
     * @return
     */
    public static MerchantPreferential loadByMerchantPreferential(MerchantPreferentialDto merchantPreferentialDto)
    {
        MerchantPreferential merchantPreferential = new MerchantPreferential();

        merchantPreferential.setId(merchantPreferentialDto.getId());
        merchantPreferential.setCreateTime(merchantPreferentialDto.getCreateTime());
        merchantPreferential.setTitle(merchantPreferentialDto.getTitle());
        merchantPreferential.setContent(merchantPreferentialDto.getContent());
        merchantPreferential.setOrderValue(merchantPreferentialDto.getOrderValue());
        merchantPreferential.setMerchantOutletId(merchantPreferentialDto.getMerchantOutletId());

        return merchantPreferential;
    }

    /**
     * 商户介绍
     *
     * @param merchantPresent
     * @return
     */
    public static MerchantPresent loadByMerchantPresent(MerchantPresentDto merchantPresentDto)
    {
        MerchantPresent merchantPresent = new MerchantPresent();

        merchantPresent.setId(merchantPresentDto.getId());
        merchantPresent.setCreateTime(merchantPresentDto.getCreateTime());
        merchantPresent.setTitle(merchantPresentDto.getTitle());
        merchantPresent.setContent(merchantPresentDto.getContent());
        merchantPresent.setMerchantOutletId(merchantPresentDto.getMerchantOutletId());

        return merchantPresent;
    }

    /**
     * 导航
     *
     * @param navigation
     * @return
     */
    public static Navigation loadByNavigation(NavigationDto navigationDto)
    {
        Navigation navigation = new Navigation();

        navigation.setId(navigationDto.getId());
        navigation.setCreateTime(navigationDto.getCreateTime());
        navigation.setName(navigationDto.getName());
        navigation.setPosition(navigationDto.getPosition() != null ? com.froad.fft.persistent.common.enums.NavigationPosition.valueOf(navigationDto.getPosition().name()) : null);
        navigation.setUrl(navigationDto.getUrl());
        navigation.setIsVisible(navigationDto.getIsVisible());
        navigation.setIsBlankTarget(navigationDto.getIsBlankTarget());
        navigation.setOrderValue(navigationDto.getOrderValue());
        navigation.setClientId(navigationDto.getClientId());

        return navigation;
    }

    /**
     * 商品属性
     *
     * @param productAttribute
     * @return
     */
    public static ProductAttribute loadByProductAttribute(ProductAttributeDto productAttributeDto)
    {
        ProductAttribute productAttribute = new ProductAttribute();

        productAttribute.setId(productAttributeDto.getId());
        productAttribute.setCreateTime(productAttributeDto.getCreateTime());
        productAttribute.setName(productAttributeDto.getName());
        productAttribute.setAttributeType(productAttributeDto.getAttributeType() != null ? com.froad.fft.persistent.entity.ProductAttribute.AttributeType.valueOf(productAttributeDto
                .getAttributeType().toString()) : null);
        productAttribute.setIsRequired(productAttributeDto.getIsRequired());
        productAttribute.setIsEnabled(productAttributeDto.getIsEnabled());
        productAttribute.setOrderValue(productAttributeDto.getOrderValue());
        productAttribute.setProductTypeId(productAttributeDto.getProductTypeId());

        return productAttribute;
    }

    /**
     * 商品分类
     *
     * @param productCategory
     * @return
     */
    public static ProductCategory loadByProductCategory(ProductCategoryDto productCategoryDto)
    {
        ProductCategory productCategory = new ProductCategory();

        productCategory.setId(productCategoryDto.getId());
        productCategory.setCreateTime(productCategoryDto.getCreateTime());
        productCategory.setName(productCategoryDto.getName());
        productCategory.setSeoTitle(productCategoryDto.getSeoTitle());
        productCategory.setSeoKeywords(productCategoryDto.getSeoKeywords());
        productCategory.setSeoDescription(productCategoryDto.getSeoDescription());
        productCategory.setOrderValue(productCategoryDto.getOrderValue());
        productCategory.setTreePath(productCategoryDto.getTreePath());
        productCategory.setParentId(productCategoryDto.getParentId());

        return productCategory;
    }

    /**
     * 商品
     *
     * @param product
     * @return
     */
    public static Product loadByProduct(ProductDto productDto)
    {
        Product product = new Product();

        product.setId(productDto.getId());
        product.setCreateTime(productDto.getCreateTime());
        product.setSn(productDto.getSn());
        product.setName(productDto.getName());
        product.setFullName(productDto.getFullName());
        product.setPrice(productDto.getPrice());
        product.setCost(productDto.getCost());
        product.setMarketPrice(productDto.getMarketPrice());
        product.setImage(productDto.getImage());
        product.setWeight(productDto.getWeight());
        product.setWeightUnit(productDto.getWeightUnit());
        product.setStore(productDto.getStore());
        product.setFreezeStore(productDto.getFreezeStore());
        product.setWarnNumber(productDto.getWarnNumber());
        product.setIsMarketable(productDto.getIsMarketable());
        product.setIsBest(productDto.getIsBest());
        product.setIsNew(productDto.getIsNew());
        product.setIsHot(productDto.getIsHot());
        product.setHits(productDto.getHits());
        product.setIntroduction(productDto.getIntroduction());
        product.setDescription(productDto.getDescription());
        product.setKeyword(productDto.getKeyword());
        product.setSeoTitle(productDto.getSeoTitle());
        product.setSeoKeywords(productDto.getSeoKeywords());
        product.setSeoDescription(productDto.getSeoDescription());
        product.setRackTime(productDto.getRackTime());
        product.setInspectors(productDto.getInspectors());
        product.setProductCategoryId(productDto.getProductCategoryId());
        product.setProductTypeId(productDto.getProductTypeId());
        product.setIsEnableGroup(productDto.getIsEnableGroup());
        product.setProductGroupId(productDto.getProductGroupId());
        product.setIsEnablePresell(productDto.getIsEnablePresell());
        product.setProductPresellId(productDto.getProductPresellId());
        product.setOrderValue(productDto.getOrderValue());
        product.setClientId(productDto.getClientId());
        product.setMerchantId(productDto.getMerchantId());
        product.setCollectToFroad(productDto.getCollectToFroad());
        return product;
    }

    /**
     * 商品团购
     *
     * @param productGroupDto
     * @return
     */
    public static ProductGroup loadByProductGroup(ProductGroupDto productGroupDto)
    {
        ProductGroup productGroup = new ProductGroup();

        productGroup.setId(productGroupDto.getId());
        productGroup.setCreateTime(productGroupDto.getCreateTime());
        productGroup.setTitle(productGroupDto.getTitle());
        productGroup.setSummary(productGroupDto.getSummary());
        productGroup.setAreaName(productGroupDto.getAreaName());
        productGroup.setPerNumber(productGroupDto.getPerNumber());
        productGroup.setPerminNumber(productGroupDto.getPerminNumber());
        productGroup.setStartTime(productGroupDto.getStartTime());
        productGroup.setEndTime(productGroupDto.getEndTime());
        //        productGroup.setPrice(productGroupDto.getPrice());
        productGroup.setBuyKnow(productGroupDto.getBuyKnow());
        productGroup.setGeneralizeImage(productGroupDto.getGeneralizeImage());
        productGroup.setDetailsImage(productGroupDto.getDetailsImage());
        productGroup.setDescription(productGroupDto.getDescription());
        productGroup.setOrderValue(productGroupDto.getOrderValue());

        return productGroup;
    }

    /**
     * 商品预售
     *
     * @param productPresellDto
     * @return
     */
    public static ProductPresell loadByProductPresell(ProductPresellDto productPresellDto)
    {
        ProductPresell productPresell = new ProductPresell();

        productPresell.setId(productPresellDto.getId());
        productPresell.setCreateTime(productPresellDto.getCreateTime());
        productPresell.setTitle(productPresellDto.getTitle());
        productPresell.setSummary(productPresellDto.getSummary());
        productPresell.setPerNumber(productPresellDto.getPerNumber());
        productPresell.setPerminNumber(productPresellDto.getPerminNumber());
        productPresell.setStartTime(productPresellDto.getStartTime());
        productPresell.setEndTime(productPresellDto.getEndTime());
        productPresell.setDeliveryStartTime(productPresellDto.getDeliveryStartTime());
        productPresell.setDeliveryEndTime(productPresellDto.getDeliveryEndTime());
        //        productPresell.setPrice(productPresellDto.getPrice());
        productPresell.setClusteringNumber(productPresellDto.getClusteringNumber());
        productPresell.setTrueBuyerNumber(productPresellDto.getTrueBuyerNumber());
        productPresell.setVirtualBuyerNumber(productPresellDto.getVirtualBuyerNumber());
        productPresell.setClusterState(null != productPresellDto.getClusterState() ? com.froad.fft.persistent.common.enums.ClusterState.valueOf(productPresellDto.getClusterState().name()) : null);
        productPresell.setClusterType(productPresellDto.getClusterType() != null ? com.froad.fft.persistent.entity.ProductPresell.ClusterType.valueOf(productPresellDto.getClusterType().toString())
                : null);
        productPresell.setDetailsImage(productPresellDto.getDetailsImage());
        productPresell.setGeneralizeImage(productPresellDto.getGeneralizeImage());
        productPresell.setBuyKnow(productPresellDto.getBuyKnow());
        productPresell.setDescription(productPresellDto.getDescription());
        productPresell.setOrderValue(productPresellDto.getOrderValue());

        return productPresell;
    }

    /**
     * 预售提供点
     *
     * @param presellDelivery
     * @return
     */
    public static PresellDelivery loadByPresellDelivery(PresellDeliveryDto presellDeliveryDto)
    {
        PresellDelivery presellDelivery = new PresellDelivery();

        presellDelivery.setId(presellDeliveryDto.getId());
        presellDelivery.setCreateTime(presellDeliveryDto.getCreateTime());
        presellDelivery.setName(presellDeliveryDto.getName());
        presellDelivery.setAddress(presellDeliveryDto.getAddress());
        presellDelivery.setTelephone(presellDeliveryDto.getTelephone());
        presellDelivery.setBusinessTime(presellDeliveryDto.getBusinessTime());
        presellDelivery.setCoordinate(presellDeliveryDto.getCoordinate());
        presellDelivery.setOrderValue(presellDeliveryDto.getOrderValue());

        presellDelivery.setClientId(presellDeliveryDto.getClientId());
        presellDelivery.setBusinessCircleId(presellDeliveryDto.getBusinessCircleId());
        presellDelivery.setDataState(null != presellDeliveryDto.getDataState() ? com.froad.fft.persistent.common.enums.DataState.valueOf(presellDeliveryDto.getDataState().name()) : null);
        presellDelivery.setDirector(presellDeliveryDto.getDirector());

        return presellDelivery;
    }

    /**
     * 商品图片
     *
     * @param productImage
     * @return
     */
    public static ProductImage loadByProductImage(ProductImageDto productImageDto)
    {
        ProductImage productImage = new ProductImage();

        productImage.setId(productImageDto.getId());
        productImage.setCreateTime(productImageDto.getCreateTime());
        productImage.setTitle(productImageDto.getTitle());
        productImage.setSource(productImageDto.getSource());
        productImage.setLarge(productImageDto.getLarge());
        productImage.setMedium(productImageDto.getMedium());
        productImage.setThumbnail(productImageDto.getThumbnail());
        productImage.setOrderValue(productImageDto.getOrderValue());
        productImage.setProductId(productImageDto.getProductId());

        return productImage;
    }

    /**
     * 商品类型
     *
     * @param productType
     * @return
     */
    public static ProductType loadByProductType(ProductTypeDto productTypeDto)
    {
        ProductType productType = new ProductType();

        productType.setId(productTypeDto.getId());
        productType.setCreateTime(productTypeDto.getCreateTime());
        productType.setName(productTypeDto.getName());
        productType.setDataState(productTypeDto.getDataState() != null ? com.froad.fft.persistent.common.enums.DataState.valueOf(productTypeDto.getDataState().name()) : null);
        return productType;
    }

    /**
     * 短信模板
     *
     * @param msContent
     * @return
     */
    public static SmsContent loadBySmsContent(SmsContentDto smsContentDto)
    {
        SmsContent smsContent = new SmsContent();

        smsContent.setId(smsContentDto.getId());
        smsContent.setCreateTime(smsContentDto.getCreateTime());
        smsContent.setSmsType(smsContentDto.getSmsType() != null ? com.froad.fft.persistent.common.enums.SmsType.valueOf(smsContentDto.getSmsType().name()) : null);
        smsContent.setClientId(smsContentDto.getClientId());
        smsContent.setContent(smsContentDto.getContent());
        smsContent.setMsgSuffix(smsContentDto.getMsgSuffix());
        smsContent.setIsEnableSuffix(smsContentDto.getIsEnableSuffix());

        return smsContent;
    }

    /**
     * 短信日志
     *
     * @param smsLog
     * @return
     */
    public static SmsLog loadBySmsLog(SmsLogDto smsLogDto)
    {
        SmsLog smsLog = new SmsLog();

        smsLog.setId(smsLogDto.getId());
        smsLog.setCreateTime(smsLogDto.getCreateTime());
        smsLog.setSmsType(smsLogDto.getSmsType() != null ? com.froad.fft.persistent.common.enums.SmsType.valueOf(smsLogDto.getSmsType().name()) : null);
        smsLog.setMobile(smsLogDto.getMobile());
        smsLog.setContent(smsLogDto.getContent());
        smsLog.setIsSuccess(smsLogDto.getIsSuccess());
        smsLog.setSendUser(smsLogDto.getSendUser());
        smsLog.setClientId(smsLogDto.getClientId());

        return smsLog;
    }

    /**
     * 资源
     *
     * @param sysResource
     * @return
     */
    public static SysResource loadBySysResource(SysResourceDto sysResourceDto)
    {
        SysResource sysResource = new SysResource();

        sysResource.setId(sysResourceDto.getId());
        sysResource.setCreateTime(sysResourceDto.getCreateTime());
        sysResource.setName(sysResourceDto.getName());
        sysResource.setIcon(sysResourceDto.getIcon());
        sysResource.setCode(sysResourceDto.getCode());
        sysResource.setUrl(sysResourceDto.getUrl());
        sysResource.setType(sysResourceDto.getType() != null ? com.froad.fft.persistent.entity.SysResource.Type.valueOf(sysResourceDto.getType().toString()) : null);
        sysResource.setParentId(sysResourceDto.getParentId());
        sysResource.setTreePath(sysResourceDto.getTreePath());
        sysResource.setIsEnabled(sysResourceDto.getIsEnabled());
        sysResource.setIsSystem(sysResourceDto.getIsSystem());
        sysResource.setDescription(sysResourceDto.getDescription());
        sysResource.setOrderValue(sysResourceDto.getOrderValue());
        sysResource.setClientId(sysResourceDto.getClientId());

        return sysResource;
    }

    /**
     * 角色
     *
     * @param sysRole
     * @return
     */
    public static SysRole loadBySysRole(SysRoleDto sysRoleDto)
    {
        SysRole sysRole = new SysRole();

        sysRole.setId(sysRoleDto.getId());
        sysRole.setCreateTime(sysRoleDto.getCreateTime());
        sysRole.setName(sysRoleDto.getName());
        sysRole.setValue(sysRoleDto.getValue());
        sysRole.setIsSystem(sysRoleDto.getIsSystem());
        sysRole.setDescription(sysRoleDto.getDescription());

        return sysRole;
    }

    /**
     * 用户
     *
     * @param sysUser
     * @return
     */
    public static SysUser loadBySysUser(SysUserDto sysUserDto)
    {
        SysUser sysUser = new SysUser();

        sysUser.setId(sysUserDto.getId());
        sysUser.setCreateTime(sysUserDto.getCreateTime());
        sysUser.setUsername(sysUserDto.getUsername());
        sysUser.setPassword(sysUserDto.getPassword());
        sysUser.setAlias(sysUserDto.getAlias());
        sysUser.setEmail(sysUserDto.getEmail());
        sysUser.setPhone(sysUserDto.getPhone());
        sysUser.setDepartment(sysUserDto.getDepartment());
        sysUser.setIsEnabled(sysUserDto.getIsEnabled());
        sysUser.setIsLocked(sysUserDto.getIsLocked());
        sysUser.setLoginFailureCount(sysUserDto.getLoginFailureCount());
        sysUser.setLockedDate(sysUserDto.getLockedDate());
        sysUser.setLoginDate(sysUserDto.getLoginDate());
        sysUser.setLoginIp(sysUserDto.getLoginIp());
        sysUser.setClientId(sysUserDto.getClientId());
        return sysUser;
    }

    /**
     * 系统配置
     *
     * @param systemConfig
     * @return
     */
    public static SystemConfig loadBySystemConfig(SystemConfigDto systemConfigDto)
    {
        SystemConfig systemConfig = new SystemConfig();

        systemConfig.setSystemName(systemConfigDto.getSystemName());
        systemConfig.setSystemVersion(systemConfigDto.getSystemVersion());
        systemConfig.setIsSystemDebug(systemConfigDto.getIsSystemDebug());
        systemConfig.setIsLoginFailureLock(systemConfigDto.getIsLoginFailureLock());
        systemConfig.setLoginFailureLockCount(systemConfigDto.getLoginFailureLockCount());
        systemConfig.setLoginFailureLockTime(systemConfigDto.getLoginFailureLockTime());
        systemConfig.setUploadMaxSize(systemConfigDto.getUploadMaxSize());
        systemConfig.setAllowedUploadImageExtension(systemConfigDto.getAllowedUploadImageExtension());
        systemConfig.setAllowedUploadFileExtension(systemConfigDto.getAllowedUploadFileExtension());
        systemConfig.setCookiePath(systemConfigDto.getCookiePath());
        systemConfig.setCookieDomain(systemConfigDto.getCookieDomain());
        systemConfig.setFtpHost(systemConfigDto.getFtpHost());
        systemConfig.setFtpPort(systemConfigDto.getFtpPort());
        systemConfig.setFtpUsername(systemConfigDto.getFtpUsername());
        systemConfig.setFtpPassword(systemConfigDto.getFtpPassword());
        systemConfig.setFtpEncoding(systemConfigDto.getFtpEncoding());
        systemConfig.setFtpDirectoryPath(systemConfigDto.getFtpDirectoryPath());
        systemConfig.setFtpUrl(systemConfigDto.getFtpUrl());

        return systemConfig;
    }

    /**
     * 标签
     *
     * @param tag
     * @return
     */
    public static Tag loadByTag(TagDto tagDto)
    {
        Tag tag = new Tag();

        tag.setId(tagDto.getId());
        tag.setCreateTime(tagDto.getCreateTime());
        tag.setName(tagDto.getName());
        tag.setType(tagDto.getType() != null ? com.froad.fft.persistent.common.enums.TagType.valueOf(tagDto.getType().name()) : null);
        tag.setIcon(tagDto.getIcon());
        tag.setDescription(tagDto.getDescription());
        tag.setOrderValue(tagDto.getOrderValue());

        return tag;
    }

    public static Trans loadByTrans(TransDto transDto)
    {
        Trans trans = new Trans();
        if (transDto.getClientAccessType() != null)
        {
            trans.setClientNumber(transDto.getClientAccessType().getCode());
        }
        Long clientId = queryClientId(transDto.getClientAccessType());
        trans.setClientId(clientId);
        TransCreateSource createSource = transDto.getCreateSource();
        if (createSource != null)
        {
            trans.setCreateSource(com.froad.fft.persistent.common.enums.TransCreateSource.valueOf(createSource.name()));
        }
        trans.setDeliveryId(transDto.getDeliveryId());
        trans.setDeliveryName(transDto.getDeliveryName());
        trans.setFilmMobile(transDto.getFilmMobile());
        trans.setGatheringValue(transDto.getGatheringValue());
        trans.setMemberCode(transDto.getMemberCode());
        trans.setMerchantId(transDto.getMerchantId());
        trans.setPhone(transDto.getMobile());
        TransPayChannel payChannel = transDto.getPayChannel();
        if (payChannel != null)
        {
            trans.setPayChannel(PayChannel.valueOf(payChannel.name()));
        }
        TransPayMethod payMethod = transDto.getPayMethod();
        if (payMethod != null)
        {
            trans.setPayMethod(PayMethod.valueOf(payMethod.name()));
        }
        trans.setFftPoints(transDto.getFftPoints());
        trans.setBankPoints(transDto.getBankPoints());
        Map<Long, Integer> productMap = transDto.getProductMap();
        if (productMap != null)
        {
            List<TransDetails> detailsList = new ArrayList<TransDetails>();
            TransDetails details = null;
            Iterator<Long> keyIter = productMap.keySet().iterator();
            Long key = null;
            while (keyIter.hasNext())
            {
                key = keyIter.next();
                details = new TransDetails();
                details.setProductId(key);
                details.setQuantity(productMap.get(key));
                detailsList.add(details);
            }
            trans.setDetailsList(detailsList);
        }
        trans.setReason(transDto.getReason());
        TransType transType = transDto.getTransType();
        if (transType != null)
        {
            trans.setType(com.froad.fft.persistent.common.enums.TransType.valueOf(transType.name()));
        }
        return trans;
    }

    /**
     * 商圈转换
     *
     * @param dto 数据dto
     * @return 转换后的商圈实体
     */
    public static BusinessCircle loadBusinessCircle(BusinessCircleDto dto)
    {
        BusinessCircle temp = new BusinessCircle();
        temp.setId(dto.getId());
        temp.setCreateTime(dto.getCreateTime());
        temp.setName(dto.getName());
        temp.setOrderValue(dto.getOrderValue());
        temp.setParentId(dto.getParentId());
        temp.setAreaId(dto.getAreaId());
        temp.setTreePath(dto.getTreePath());
        return temp;
    }

    /**
     * 送积分转换
     *
     * @param dto 数据dto
     * @return 转换后的商圈实体
     */
    public static GivePointRule loadGivePointRule(GivePointRuleDto dto)
    {
        GivePointRule temp = new GivePointRule();
        temp.setId(dto.getId());
        temp.setCreateTime(dto.getCreateTime());


        temp.setName(dto.getName());
        temp.setType(dto.getType() != null ? com.froad.fft.persistent.common.enums.GivePointRuleType.valueOf(dto.getType().name()) : null);
        temp.setPointValue(dto.getPointValue());
        temp.setActiveTime(dto.getActiveTime());
        temp.setExpireTime(dto.getExpireTime());
        temp.setRemark(dto.getRemark());
        return temp;
    }

    /**
     * 商圈用户组转换
     *
     * @param dto 数据dto
     * @return 转换后的商圈实体
     */
    public static MerchantGroupUser loadMerchantGroupUser(MerchantGroupUserDto dto)
    {
        MerchantGroupUser temp = new MerchantGroupUser();
        temp.setId(dto.getId());
        temp.setCreateTime(dto.getCreateTime());

        temp.setMerchantId(dto.getMerchantId());
        temp.setMerchantOutletId(dto.getMerchantOutletId());
        temp.setSysUserId(dto.getSysUserId());
        return temp;
    }

    /**
     * 退款
     *
     * @param tag
     * @return
     */
    public static Refunds loadByRefunds(RefundsDto refundsDto)
    {
        Refunds refunds = new Refunds();
        refunds.setId(refundsDto.getId());
        refunds.setCreateTime(refundsDto.getCreateTime());
        refunds.setSn(refundsDto.getSn());
        refunds.setTransId(refundsDto.getTransId());
        refunds.setState(refundsDto.getState() != null ? com.froad.fft.persistent.entity.Refunds.State.valueOf(refundsDto.getState().name()) : null);
        refunds.setReason(refundsDto.getReason());
        refunds.setSysUserId(refundsDto.getSysUserId());
        refunds.setRemark(refundsDto.getRemark());
        return refunds;
    }

    /**
     * 方法描述：库存转换
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月28日 下午8:15:14
     */
    public static StockPile loadByStockPile(StockPileDto stockPileDto)
    {
        StockPile stockPile = new StockPile();
        stockPile.setId(stockPileDto.getId());
        stockPile.setCreateTime(stockPileDto.getCreateTime());
        stockPile.setProductId(stockPileDto.getProductId());
        stockPile.setMerchantOutletId(stockPileDto.getMerchantOutletId());
        stockPile.setQuantity(stockPileDto.getQuantity());
        stockPile.setLastIncomeTime(stockPileDto.getLastIncomeTime());
        stockPile.setLastOutcomeTime(stockPileDto.getLastOutcomeTime());
        stockPile.setWarnType(stockPileDto.getWarnType() != null ? com.froad.fft.persistent.entity.StockPile.WarnType.valueOf(stockPileDto.getWarnType().name()) : null);
        stockPile.setWarnValue(stockPileDto.getWarnValue());
        stockPile.setTotalQuantity(stockPileDto.getTotalQuantity());
        stockPile.setRemark(stockPileDto.getRemark());
        stockPile.setMerchantOutletIds(stockPileDto.getMerchantOutletIds());
        return stockPile;
    }

    /**
     * 方法描述：库存日志转换
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月28日 下午8:04:29
     */
    public static StockPileLog loadByStockPileLog(StockPileLogDto stockPileLogDto)
    {
        StockPileLog stockPileLog = new StockPileLog();
        stockPileLog.setId(stockPileLogDto.getId());
        stockPileLog.setCreateTime(stockPileLogDto.getCreateTime());
        stockPileLog.setType(stockPileLogDto.getType() != null ? com.froad.fft.persistent.entity.StockPileLog.Type.valueOf(stockPileLogDto.getType().name()) : null);
        stockPileLog.setProductId(stockPileLogDto.getProductId());
        stockPileLog.setMerchantOutletId(stockPileLogDto.getMerchantOutletId());
        stockPileLog.setQuantity(stockPileLogDto.getQuantity());
        stockPileLog.setContent(stockPileLogDto.getContent());
        stockPileLog.setOperator(stockPileLogDto.getOperator());
        return stockPileLog;
    }

    /**
     * 方法描述：门店提货点关联表
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月29日 下午2:15:59
     */
    public static OutletPresellDelivery loadByOutletPresellDelivery(OutletPresellDeliveryDto outletPresellDeliveryDto)
    {
        OutletPresellDelivery outletPresellDelivery = new OutletPresellDelivery();
        outletPresellDelivery.setMerchantOutletId(outletPresellDeliveryDto.getMerchantOutletId());
        outletPresellDelivery.setPresellDeliveryId(outletPresellDeliveryDto.getPresellDeliveryId());
        return outletPresellDelivery;
    }

    private static Long queryClientId(ClientAccessType accessType)
    {
        if (accessType == null)
        {
            return null;
        }
        SysClientService sysClientService = (SysClientService) SpringUtil.getBean("sysClientServiceImpl");
        SysClient client = sysClientService.findSysClientByNumber(accessType.getCode());
        return client == null ? null : client.getId();
    }

    /**
     * 方法描述：现金和积分比例
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月31日 下午3:25:30
     */
    public static CashPointsRatio loadByCashPointsRatio(CashPointsRatioDto cashPointsRatioDto)
    {
        CashPointsRatio cashPointsRatio = new CashPointsRatio();
        cashPointsRatio.setId(cashPointsRatioDto.getId());
        cashPointsRatio.setCreateTime(cashPointsRatioDto.getCreateTime());
        cashPointsRatio.setFftPoints(cashPointsRatioDto.getFftPoints());
        cashPointsRatio.setBankPoints(cashPointsRatioDto.getBankPoints());
        cashPointsRatio.setSysClientId(cashPointsRatioDto.getSysClientId());
        return cashPointsRatio;
    }

    /**
     * 商户账户转换
     *
     * @param merchantAccountDto 商户账户dto
     * @return 商户账户实体
     */
    public static MerchantAccount loadByMerchantAccount(MerchantAccountDto merchantAccountDto)
    {
        MerchantAccount merchantAccount = new MerchantAccount();

        merchantAccount.setId(merchantAccountDto.getId());
        merchantAccount.setCreateTime(merchantAccountDto.getCreateTime());

        merchantAccount.setMerchantId(merchantAccountDto.getMerchantId());
        merchantAccount.setAcctName(merchantAccountDto.getAcctName());
        merchantAccount.setAcctNumber(merchantAccountDto.getAcctNumber());
        merchantAccount.setFundsChannelId(merchantAccountDto.getFundsChannelId());
        merchantAccount.setIsEnabled(merchantAccountDto.getIsEnabled());
        merchantAccount.setAcctType(null != merchantAccountDto.getAcctType() ? com.froad.fft.persistent.common.enums.AccountType.valueOf(merchantAccountDto.getAcctType().name()) : null);

        return merchantAccount;
    }

    public static SysRoleResource loadBySysRoleResource(SysRoleResourceDto sysRoleResourceDto)
    {
        SysRoleResource sysRoleResource = new SysRoleResource();
        sysRoleResource.setResourceId(sysRoleResourceDto.getResourceId());
        sysRoleResource.setRoleId(sysRoleResourceDto.getRoleId());
        return sysRoleResource;
    }

    public static FundsChannel loadByFundsChannel(FundsChannelDto fundsChannelDto)
    {
        FundsChannel fundsChannel = new FundsChannel();
        fundsChannel.setId(fundsChannelDto.getId());
        fundsChannel.setCreateTime(fundsChannelDto.getCreateTime());

        fundsChannel.setShortName(fundsChannelDto.getShortName());
        fundsChannel.setFullName(fundsChannelDto.getFullName());
        fundsChannel.setChannelType(null == fundsChannelDto.getChannelType() ? null : PayChannel.valueOf(fundsChannelDto.getChannelType().name()));
        fundsChannel.setPayOrg(fundsChannelDto.getPayOrg());

        return fundsChannel;
    }

    /**
     * <p>用户角色</p>
     *
     * @return SysUserRole
     * @throws example<br/>
     * @author larry
     * @datetime 2014-4-2下午04:45:40
     */
    public static SysUserRole loadBySysUserRole(SysUserRoleDto sysUserRole)
    {
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(sysUserRole.getUserId());
        userRole.setRoleId(sysUserRole.getRoleId());
        return userRole;
    }

    /**
     * 预售商品与提货点关联表
     *
     * @param dto dto
     * @return 实体
     */
    public static ProductPresellDelivery loadByProductPresellDelivery(ProductPresellDeliveryDto dto)
    {
        ProductPresellDelivery entity = new ProductPresellDelivery();
        entity.setId(dto.getId());
        entity.setCreateTime(dto.getCreateTime());
        entity.setPresellDeliveryId(dto.getPresellDeliveryId());
        entity.setProductPresellId(dto.getProductPresellId());
        return entity;
    }

    /**
     * 交易数据转换
     *
     * @param entity 实体
     * @return dto
     */
    public static Trans loadByTransFromTransQueryDto(TransQueryDto dto)
    {
        Trans entity = new Trans();
        entity.setId(dto.getId());
        entity.setCreateTime(dto.getCreateTime());
        entity.setSn(dto.getSn());
        entity.setCreateSource(null == dto.getCreateSource() ? null : com.froad.fft.persistent.common.enums.TransCreateSource.valueOf(dto.getCreateSource().name()));
        entity.setType(null == dto.getType() ? null : com.froad.fft.persistent.common.enums.TransType.valueOf(dto.getType().name()));
        entity.setPayMethod(null == dto.getPayMethod() ? null : com.froad.fft.persistent.common.enums.PayMethod.valueOf(dto.getPayMethod().toString()));
        entity.setPayChannel(null == dto.getPayChannel() ? null : com.froad.fft.persistent.common.enums.PayChannel.valueOf(dto.getPayChannel().name()));
        entity.setState(null == dto.getState() ? null : com.froad.fft.persistent.common.enums.TransState.valueOf(dto.getState().name()));
        entity.setMemberCode(dto.getMemberCode());
        entity.setPayState(null == dto.getPayState() ? null : com.froad.fft.persistent.common.enums.TransPayState.valueOf(dto.getPayState().name()));
        entity.setClientId(dto.getClientId());
        entity.setClientNumber(dto.getClientNumber());
        entity.setTotalPrice(dto.getTotalPrice());
        entity.setRealPrice(dto.getRealPrice());
        entity.setFftPoints(dto.getFftPoints());
        entity.setBankPoints(dto.getBankPoints());
        entity.setPointsAmountValue(dto.getPointsAmountValue());
        entity.setPointsAmountRealValue(dto.getPointsAmountRealValue());
        entity.setBuyPoints(dto.getBuyPoints());
        entity.setBuyPointsFactorage(dto.getBuyPointsFactorage());
        entity.setGatheringValue(dto.getGatheringValue());
        entity.setPointsWithdrawFactorage(dto.getPointsWithdrawFactorage());
        entity.setGivePoints(dto.getGivePoints());
        entity.setMerchantId(dto.getMerchantId());
        entity.setReason(dto.getReason());
        entity.setFilmMobile(dto.getFilmMobile());
        entity.setPhone(dto.getPhone());
        entity.setRemark(dto.getRemark());
        entity.setDeliveryId(dto.getDeliveryId());
        entity.setDeliveryName(dto.getDeliveryName());
        entity.setSysUserIds(dto.getSysUserIds());
        List<TransPayState> transPayStates = null;
        if (dto.getPayStates() != null)
        {
            transPayStates = new ArrayList<TransPayState>();
            for (com.froad.fft.enums.trans.TransPayState transPayState : dto.getPayStates())
            {//api 枚举转 entity枚举
                transPayStates.add(com.froad.fft.persistent.common.enums.TransPayState.valueOf(transPayState.name()));
            }
        }
        entity.setPayStates(transPayStates);
        return entity;

    }

    /**
     * 交易明细数据转换
     *
     * @param dto 交易明细数据
     * @return entity
     */
    public static TransDetails loadByTransDetail(TransDetailDto dto)
    {
        TransDetails entity = new TransDetails();
        entity.setId(dto.getId());
        entity.setCreateTime(dto.getCreateTime());

        entity.setTransId(dto.getTransId());
        entity.setProductId(dto.getProductId());
        entity.setProductName(dto.getProductName());
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());
        entity.setSingle(dto.getSingle());
        entity.setSupplyMerchantId(dto.getSupplyMerchantId());
        entity.setGatherMerchantId(dto.getGatherMerchantId());

        return entity;

    }

    /**
     * 支付信息转换
     *
     * @param entity 支付信息实体
     * @return dto
     */
    public static Pay loadByPayDto(Pay dto)
    {
        Pay entity = new Pay();
        entity.setId(dto.getId());
        entity.setCreateTime(dto.getCreateTime());
        entity.setSn(dto.getSn());
        entity.setTransId(dto.getTransId());
        entity.setPayType(null == dto.getPayType() ? null : PayType.valueOf(dto.getPayType().toString()));
        entity.setPayTypeDetails(null == dto.getPayTypeDetails() ? null : PayTypeDetails.valueOf(dto.getPayTypeDetails().toString()));
        entity.setPayState(null == dto.getPayState() ? null : PayState.valueOf(dto.getPayState().toString()));
        entity.setFromRole(null == dto.getFromRole() ? null : PayRole.valueOf(dto.getFromRole().toString()));
        entity.setToRole(null == dto.getToRole() ? null : PayRole.valueOf(dto.getToRole().toString()));
        entity.setStep(dto.getStep());
        entity.setPayOrg(dto.getPayOrg());
        entity.setBillNo(dto.getBillNo());
        entity.setRefundOrderId(dto.getRefundOrderId());
        entity.setPointBillNo(dto.getPointBillNo());
        entity.setRefundPointNo(dto.getRefundPointNo());
        entity.setPayValue(dto.getPayValue());
        entity.setFromAccountName(dto.getFromAccountName());
        entity.setFromAccountNo(dto.getFromAccountNo());
        entity.setToAccountName(dto.getToAccountName());
        entity.setToAccountNo(dto.getToAccountNo());
        entity.setFromPhone(dto.getFromPhone());
        entity.setToPhone(dto.getToPhone());
        entity.setFromUserName(dto.getFromUserName());
        entity.setToUserName(dto.getToUserName());
        entity.setMerchantId(dto.getMerchantId());
        entity.setResultCode(dto.getResultCode());
        entity.setResultDesc(dto.getResultDesc());
        entity.setRemark(dto.getRemark());

        return dto;

    }

    /**
     * <p>退换货</p>
     *
     * @return ReturnSale
     * @throws example<br/>
     * @author larry
     * @datetime 2014-4-3下午05:54:34
     */
    public static ReturnSale loadByReturnSale(ReturnSaleDto returnSaleDto)
    {
        ReturnSale returnSale = new ReturnSale();
        if (returnSaleDto == null)
        {
            return returnSale;
        }
        returnSale.setId(returnSaleDto.getId());
        returnSale.setMerchantOutletId(returnSaleDto.getMerchantOutletId());
        returnSale.setReason(returnSaleDto.getReason());
        returnSale.setRemark(returnSaleDto.getRemark());
        returnSale.setSn(returnSaleDto.getSn());
        returnSale.setSysUserId(returnSaleDto.getSysUserId());
        returnSale.setCreateTime(returnSaleDto.getCreateTime());
        returnSale.setType(returnSaleDto.getType() != null ? com.froad.fft.persistent.entity.ReturnSale.Type.valueOf(returnSaleDto.getType().name()) : null);
        returnSale.setSysUserIds(returnSaleDto.getSysUserIds());
        return returnSale;
    }

    /**
     * 方法描述：退换货详情转换
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年4月7日 下午10:25:52
     */
    public static ReturnSaleDetail loadByReSaleDetail(ReturnSaleDetailDto returnSaleDetailDto)
    {
        if (returnSaleDetailDto == null)
        {
            return null;
        }
        ReturnSaleDetail returnSaleDetail = new ReturnSaleDetail();
        returnSaleDetail.setId(returnSaleDetailDto.getId());
        returnSaleDetail.setProductId(returnSaleDetailDto.getProductId());
        returnSaleDetail.setQuantity(returnSaleDetailDto.getQuantity());
        returnSaleDetail.setReturnSaleId(returnSaleDetailDto.getReturnSaleId());
        returnSaleDetail.setSecuritiesNo(returnSaleDetailDto.getSecuritiesNo());
        return returnSaleDetail;
    }

    /**
     * 方法描述：交易认证券
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年4月7日 下午1:50:33
     */
    public static TransSecurityTicket loadByTransSecurityTicket(TransSecurityTicketDto ticketDto)
    {
        TransSecurityTicket transSecurityTicket = new TransSecurityTicket();
        transSecurityTicket.setId(ticketDto.getId());
        transSecurityTicket.setCreateTime(ticketDto.getCreateTime());
        transSecurityTicket.setTransId(ticketDto.getTransId());
        transSecurityTicket.setMemberCode(ticketDto.getMemberCode());
        transSecurityTicket.setTransType(ticketDto.getTransType() != null ? com.froad.fft.persistent.common.enums.TransType.valueOf(ticketDto.getTransType().name()) : null);
        transSecurityTicket.setSecuritiesNo(ticketDto.getSecuritiesNo());
        transSecurityTicket.setIsConsume(ticketDto.getIsConsume());
        transSecurityTicket.setConsumeTime(ticketDto.getConsumeTime());
        transSecurityTicket.setExpireTime(ticketDto.getExpireTime());
        transSecurityTicket.setSmsNumber(ticketDto.getSmsNumber());
        transSecurityTicket.setSmsTime(ticketDto.getSmsTime());
        transSecurityTicket.setSysUserId(ticketDto.getSysUserId());
        transSecurityTicket.setMerchantId(ticketDto.getMerchantId());
        return transSecurityTicket;
    }

    /**
     * 注册送积分规则
     *
     * @param temp
     * @return
     */
    public static RegisterGivePointRule loadByRegisterGivePointRule(RegisterGivePointRuleDto dto)
    {
        if (dto == null)
        {
            return null;
        }
        RegisterGivePointRule temp = new RegisterGivePointRule();
        temp.setId(dto.getId());
        temp.setCreateTime(dto.getCreateTime());
        temp.setClientId(dto.getClientId());
        temp.setBegineTime(dto.getBegineTime());
        temp.setEndTime(dto.getEndTime());
        temp.setGivePoint(dto.getGivePoint());
        temp.setIsEnable(dto.getIsEnable());
        return temp;

    }

    /**
     * 限购规则
     *
     * @param temp
     * @return
     */
    public static ProductRestrictRule loadByProductRestrictRule(ProductRestrictRuleDto dto)
    {
        if (dto == null)
        {
            return null;
        }
        ProductRestrictRule temp = new ProductRestrictRule();
        temp.setId(dto.getId());
        temp.setCreateTime(dto.getCreateTime());
        temp.setProductId(dto.getProductId());
        temp.setQuantity(dto.getQuantity());
        temp.setRemark(dto.getRemark());
        temp.setRestrictType(dto.getRestrictType() != null ? RestrictType.valueOf(dto.getRestrictType().name()) : null);
        return temp;

    }
}
