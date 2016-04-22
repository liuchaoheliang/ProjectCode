package com.froad.fft.api.support;

import com.froad.fft.bean.SystemConfig;
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
import com.froad.fft.dto.PayDto;
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
import com.froad.fft.dto.SysClientDto;
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
import com.froad.fft.enums.AccountType;
import com.froad.fft.enums.ClusterState;
import com.froad.fft.enums.FriendLinkType;
import com.froad.fft.enums.PayMethodShow;
import com.froad.fft.enums.RestrictType;
import com.froad.fft.enums.trans.PayRole;
import com.froad.fft.enums.trans.PayState;
import com.froad.fft.enums.trans.PayType;
import com.froad.fft.enums.trans.PayTypeDetails;
import com.froad.fft.enums.trans.TransCreateSource;
import com.froad.fft.enums.trans.TransPayChannel;
import com.froad.fft.enums.trans.TransPayMethod;
import com.froad.fft.enums.trans.TransPayState;
import com.froad.fft.enums.trans.TransState;
import com.froad.fft.enums.trans.TransType;
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
import com.froad.fft.util.NullValueCheckUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 实体bean转换dto
 *
 * @author FQ
 */
public class BeanToDtoSupport
{

    /**
     * 广告
     *
     * @return
     */
    public static AdDto loadByAdDto(Ad ad)
    {
        if (ad == null)
        {
            return null;
        }
        AdDto adDto = new AdDto();

        adDto.setId(ad.getId());
        adDto.setCreateTime(ad.getCreateTime());
        adDto.setTitle(ad.getTitle());
        adDto.setType(ad.getType() != null ? com.froad.fft.enums.AdType.valueOf(ad.getType().name()) : null);
        adDto.setBeginTime(ad.getBeginTime());
        adDto.setEndTime(ad.getEndTime());
        adDto.setContent(ad.getContent());
        adDto.setPath(ad.getPath());
        adDto.setLink(ad.getLink());
        adDto.setIsEnabled(ad.getIsEnabled());
        adDto.setIsBlankTarge(ad.getIsBlankTarge());
        adDto.setClickCount(ad.getClickCount());
        adDto.setOrderValue(ad.getOrderValue());
        adDto.setAdPositionId(ad.getAdPositionId());

        return adDto;
    }

    /**
     * 广告位
     *
     * @param adPosition
     * @return
     */
    public static AdPositionDto loadByAdPositionDto(AdPosition adPosition)
    {
        if (adPosition == null)
        {
            return null;
        }
        AdPositionDto adPositionDto = new AdPositionDto();

        adPositionDto.setId(adPosition.getId());
        adPositionDto.setCreateTime(adPosition.getCreateTime());
        adPositionDto.setName(adPosition.getName());
        adPositionDto.setWidth(adPosition.getWidth());
        adPositionDto.setHeight(adPosition.getHeight());
        adPositionDto.setDescription(adPosition.getDescription());
        adPositionDto.setPositionStyle(adPosition.getPositionStyle());
        adPositionDto.setClientId(adPosition.getClientId());

        return adPositionDto;
    }

    /**
     * 协议
     *
     * @param agreement
     * @return
     */
    public static AgreementDto loadByAgreementDto(Agreement agreement)
    {
        if (agreement == null)
        {
            return null;
        }
        AgreementDto agreementDto = new AgreementDto();

        agreementDto.setId(agreement.getId());
        agreementDto.setCreateTime(agreement.getCreateTime());
        agreementDto.setType(agreement.getType() != null ? com.froad.fft.enums.AgreementType.valueOf(agreement.getType().name()) : null);
        agreementDto.setContent(agreement.getContent());
        agreementDto.setClientId(agreement.getClientId());

        return agreementDto;
    }

    /**
     * 地区
     *
     * @param areaDto
     * @return
     */
    public static AreaDto loadByAreaDto(Area area)
    {
        if (area == null)
        {
            return null;
        }
        AreaDto areaDto = new AreaDto();

        areaDto.setId(area.getId());
        areaDto.setCreateTime(area.getCreateTime());
        areaDto.setName(area.getName());
        areaDto.setOrderValue(area.getOrderValue());
        areaDto.setTreePath(area.getTreePath());
        areaDto.setParentId(area.getParentId());

        return areaDto;
    }

    /**
     * 文章分类
     *
     * @param articleCategory
     * @return
     */
    public static ArticleCategoryDto loadByArticleCategoryDto(ArticleCategory articleCategory)
    {
        if (articleCategory == null)
        {
            return null;
        }
        ArticleCategoryDto articleCategoryDto = new ArticleCategoryDto();

        articleCategoryDto.setId(articleCategory.getId());
        articleCategoryDto.setCreateTime(articleCategory.getCreateTime());
        articleCategoryDto.setName(articleCategory.getName());
        articleCategoryDto.setSeoTitle(articleCategory.getSeoTitle());
        articleCategoryDto.setSeoKeywords(articleCategory.getSeoKeywords());
        articleCategoryDto.setSeoDescription(articleCategory.getSeoDescription());
        articleCategoryDto.setOrderValue(articleCategory.getOrderValue());
        articleCategoryDto.setTreePath(articleCategory.getTreePath());
        articleCategoryDto.setParentId(articleCategory.getParentId());

        return articleCategoryDto;
    }

    /**
     * 文章
     *
     * @param article
     * @return
     */
    public static ArticleDto loadByArticleDto(Article article)
    {
        if (article == null)
        {
            return null;
        }
        ArticleDto articleDto = new ArticleDto();

        articleDto.setId(article.getId());
        articleDto.setCreateTime(article.getCreateTime());
        articleDto.setTitle(article.getTitle());
        articleDto.setAuthor(article.getAuthor());
        articleDto.setContent(article.getContent());
        articleDto.setSeoTitle(article.getSeoTitle());
        articleDto.setSeoKeywords(article.getSeoKeywords());
        articleDto.setSeoDescription(article.getSeoDescription());
        articleDto.setIsPublication(article.getIsPublication());
        articleDto.setIsTop(article.getIsTop());
        articleDto.setIsRecommend(article.getIsRecommend());
        articleDto.setPageCount(article.getPageCount());
        articleDto.setHtmlPath(article.getHtmlPath());
        articleDto.setHits(article.getHits());
        articleDto.setArticleCategoryId(article.getArticleCategoryId());
        articleDto.setClientId(article.getClientId());

        return articleDto;
    }

    /**
     * 友情链接
     *
     * @param friendLink
     * @return
     */
    public static FriendLinkDto loadByFriendLinkDto(FriendLink friendLink)
    {
        if (friendLink == null)
        {
            return null;
        }
        FriendLinkDto friendLinkDto = new FriendLinkDto();

        friendLinkDto.setId(friendLink.getId());
        friendLinkDto.setCreateTime(friendLink.getCreateTime());
        friendLinkDto.setName(friendLink.getName());
        friendLinkDto.setType(friendLink.getType() != null ? FriendLinkType.valueOf(friendLink.getType().name()) : null);
        friendLinkDto.setLogo(friendLink.getLogo());
        friendLinkDto.setUrl(friendLink.getUrl());
        friendLinkDto.setOrderValue(friendLink.getOrderValue());
        friendLinkDto.setClientId(friendLink.getClientId());

        return friendLinkDto;
    }

    /**
     * 日志
     *
     * @param log
     * @return
     */
    public static LogDto loadByLogDto(Log log)
    {
        if (log == null)
        {
            return null;
        }
        LogDto logDto = new LogDto();

        logDto.setId(log.getId());
        logDto.setCreateTime(log.getCreateTime());
        logDto.setOperation(log.getOperation());
        logDto.setOperator(log.getOperator());
        logDto.setContent(log.getContent());
        logDto.setParameter(log.getParameter());
        logDto.setIp(log.getIp());

        return logDto;
    }

    /**
     * 商户分类
     *
     * @param merchantCategory
     * @return
     */
    public static MerchantCategoryDto loadByMerchantCategoryDto(MerchantCategory merchantCategory)
    {
        if (merchantCategory == null)
        {
            return null;
        }
        MerchantCategoryDto merchantCategoryDto = new MerchantCategoryDto();

        merchantCategoryDto.setId(merchantCategory.getId());
        merchantCategoryDto.setCreateTime(merchantCategory.getCreateTime());
        merchantCategoryDto.setName(merchantCategory.getName());
        merchantCategoryDto.setOrderValue(merchantCategory.getOrderValue());
        merchantCategoryDto.setTreePath(merchantCategory.getTreePath());
        merchantCategoryDto.setParentId(merchantCategory.getParentId());

        return merchantCategoryDto;
    }

    /**
     * 商户点评
     *
     * @param merchantComment
     * @return
     */
    public static MerchantCommentDto loadByMerchantCommentDto(MerchantComment merchantComment)
    {
        if (merchantComment == null)
        {
            return null;
        }
        MerchantCommentDto merchantCommentDto = new MerchantCommentDto();

        merchantCommentDto.setId(merchantComment.getId());
        merchantCommentDto.setCreateTime(merchantComment.getCreateTime());
        merchantCommentDto.setStarLevel(merchantComment.getStarLevel() != null ? com.froad.fft.dto.MerchantCommentDto.StarLevel.valueOf(merchantComment.getStarLevel().toString()) : null);
        merchantCommentDto.setProductPoint(merchantComment.getProductPoint() != null ? com.froad.fft.dto.MerchantCommentDto.Point.valueOf(merchantComment.getProductPoint().toString()) : null);
        merchantCommentDto.setEnvironmentPoint(merchantComment.getEnvironmentPoint() != null ? com.froad.fft.dto.MerchantCommentDto.Point.valueOf(merchantComment.getEnvironmentPoint().toString()) :
                null);
        merchantCommentDto.setServePoint(merchantComment.getServePoint() != null ? com.froad.fft.dto.MerchantCommentDto.Point.valueOf(merchantComment.getServePoint().toString()) : null);
        merchantCommentDto.setCommentDescription(merchantComment.getCommentDescription());
        merchantCommentDto.setMerchantOutletId(merchantComment.getMerchantOutletId());
        merchantCommentDto.setMemberCode(merchantComment.getMemberCode());

        return merchantCommentDto;
    }

    /**
     * 商户
     *
     * @param merchant
     * @return
     */
    public static MerchantDto loadByMerchantDto(Merchant merchant)
    {
        MerchantDto merchantDto = new MerchantDto();
        if (merchant == null)
        {
            return merchantDto;
        }
        merchantDto.setId(merchant.getId());
        merchantDto.setCreateTime(merchant.getCreateTime());
        merchantDto.setName(merchant.getName());
        merchantDto.setFullName(merchant.getFullName());
        merchantDto.setLogo(merchant.getLogo());
        merchantDto.setAddress(merchant.getAddress());
        merchantDto.setZip(merchant.getZip());
        merchantDto.setFax(merchant.getFax());
        merchantDto.setTel(merchant.getTel());
        merchantDto.setContactName(merchant.getContactName());
        merchantDto.setContactPhone(merchant.getContactPhone());
        merchantDto.setContactEmail(merchant.getContactEmail());
        merchantDto.setLegalName(merchant.getLegalName());
        merchantDto.setLegalCredentType(merchant.getLegalCredentType());
        merchantDto.setLegalCredentNo(merchant.getLegalCredentNo());
        merchantDto.setContractBegintime(merchant.getContractBegintime());
        merchantDto.setContractEndtime(merchant.getContractEndtime());
        merchantDto.setContractStaff(merchant.getContractStaff());
        merchantDto.setReviewStaff(merchant.getReviewStaff());
        merchantDto.setIsAudit(merchant.getIsAudit());
        merchantDto.setOrderValue(merchant.getOrderValue());
        merchantDto.setType(merchant.getType() != null ? com.froad.fft.enums.MerchantType.valueOf(merchant.getType().name()) : null);
        merchantDto.setAreaId(merchant.getAreaId());
        merchantDto.setMerchantCategoryId(merchant.getMerchantCategoryId());
        merchantDto.setClientId(merchant.getClientId());
        merchantDto.setDataState(merchant.getDataState() != null ? com.froad.fft.enums.DataState.valueOf(merchant.getDataState().name()) : null);
        return merchantDto;
    }

    /**
     * 商户在线申请
     *
     * @param merchantOnlineApply
     * @return
     */
    public static MerchantOnlineApplyDto loadByMerchantOnlineApplyDto(MerchantOnlineApply merchantOnlineApply)
    {
        if (merchantOnlineApply == null)
        {
            return null;
        }
        MerchantOnlineApplyDto merchantOnlineApplyDto = new MerchantOnlineApplyDto();

        merchantOnlineApplyDto.setId(merchantOnlineApply.getId());
        merchantOnlineApplyDto.setCreateTime(merchantOnlineApply.getCreateTime());
        merchantOnlineApplyDto.setCompanyName(merchantOnlineApply.getCompanyName());
        merchantOnlineApplyDto.setLinkman(merchantOnlineApply.getLinkman());
        merchantOnlineApplyDto.setMobile(merchantOnlineApply.getMobile());
        merchantOnlineApplyDto.setPhone(merchantOnlineApply.getPhone());
        merchantOnlineApplyDto.setAddress(merchantOnlineApply.getAddress());
        merchantOnlineApplyDto.setAreaId(merchantOnlineApply.getAreaId());
        merchantOnlineApplyDto.setCooperationType(merchantOnlineApply.getCooperationType() != null ? com.froad.fft.enums.MerchantType.valueOf(merchantOnlineApply.getCooperationType().name()) : null);
        merchantOnlineApplyDto.setCooperativePurpose(merchantOnlineApply.getCooperativePurpose());
        merchantOnlineApplyDto.setIsRelation(merchantOnlineApply.getIsRelation());
        merchantOnlineApplyDto.setRelationAccount(merchantOnlineApply.getRelationAccount());
        merchantOnlineApplyDto.setRelationTime(merchantOnlineApply.getRelationTime());
        merchantOnlineApplyDto.setRelationMark(merchantOnlineApply.getRelationMark());
        merchantOnlineApplyDto.setClientId(merchantOnlineApply.getClientId());

        return merchantOnlineApplyDto;
    }

    /**
     * 商户门店
     *
     * @param merchantOutlet
     * @return
     */
    public static MerchantOutletDto loadByMerchantOutletDto(MerchantOutlet merchantOutlet)
    {
        MerchantOutletDto merchantOutletDto = new MerchantOutletDto();
        if (merchantOutlet == null)
        {
            return merchantOutletDto;
        }
        merchantOutletDto.setId(merchantOutlet.getId());
        merchantOutletDto.setCreateTime(merchantOutlet.getCreateTime());
        merchantOutletDto.setName(merchantOutlet.getName());
        merchantOutletDto.setFullName(merchantOutlet.getFullName());
        merchantOutletDto.setLogo(merchantOutlet.getLogo());
        merchantOutletDto.setAddress(merchantOutlet.getAddress());
        merchantOutletDto.setBusinessHours(merchantOutlet.getBusinessHours());
        merchantOutletDto.setZip(merchantOutlet.getZip());
        merchantOutletDto.setFax(merchantOutlet.getFax());
        merchantOutletDto.setTel(merchantOutlet.getTel());
        merchantOutletDto.setContactName(merchantOutlet.getContactName());
        merchantOutletDto.setContactPhone(merchantOutlet.getContactPhone());
        merchantOutletDto.setContactEmail(merchantOutlet.getContactEmail());
        merchantOutletDto.setServiceProvider(merchantOutlet.getServiceProvider());
        merchantOutletDto.setCoordinate(merchantOutlet.getCoordinate());
        merchantOutletDto.setOrderValue(merchantOutlet.getOrderValue());
        merchantOutletDto.setMerchantId(merchantOutlet.getMerchantId());

        return merchantOutletDto;
    }

    /**
     * 商户相册
     *
     * @param merchantPhoto
     * @return
     */
    public static MerchantPhotoDto loadByMerchantPhotoDto(MerchantPhoto merchantPhoto)
    {
        if (merchantPhoto == null)
        {
            return null;
        }
        MerchantPhotoDto merchantPhotoDto = new MerchantPhotoDto();

        merchantPhotoDto.setId(merchantPhoto.getId());
        merchantPhotoDto.setCreateTime(merchantPhoto.getCreateTime());
        merchantPhotoDto.setTitle(merchantPhoto.getTitle());
        merchantPhotoDto.setUrl(merchantPhoto.getUrl());
        merchantPhotoDto.setWidth(merchantPhoto.getWidth());
        merchantPhotoDto.setHeight(merchantPhoto.getHeight());
        merchantPhotoDto.setOrderValue(merchantPhoto.getOrderValue());
        merchantPhotoDto.setDescription(merchantPhoto.getDescription());
        merchantPhotoDto.setMerchantOutletId(merchantPhoto.getMerchantOutletId());

        return merchantPhotoDto;
    }

    /**
     * 商户优惠活动
     *
     * @param merchantPreferential
     * @return
     */
    public static MerchantPreferentialDto loadByMerchantPreferentialDto(MerchantPreferential merchantPreferential)
    {
        if (merchantPreferential == null)
        {
            return null;
        }
        MerchantPreferentialDto merchantPreferentialDto = new MerchantPreferentialDto();

        merchantPreferentialDto.setId(merchantPreferential.getId());
        merchantPreferentialDto.setCreateTime(merchantPreferential.getCreateTime());
        merchantPreferentialDto.setTitle(merchantPreferential.getTitle());
        merchantPreferentialDto.setContent(merchantPreferential.getContent());
        merchantPreferentialDto.setOrderValue(merchantPreferential.getOrderValue());
        merchantPreferentialDto.setMerchantOutletId(merchantPreferential.getMerchantOutletId());

        return merchantPreferentialDto;
    }

    /**
     * 商户介绍
     *
     * @param merchantPresent
     * @return
     */
    public static MerchantPresentDto loadByMerchantPresentDto(MerchantPresent merchantPresent)
    {
        if (merchantPresent == null)
        {
            return null;
        }
        MerchantPresentDto merchantPresentDto = new MerchantPresentDto();

        merchantPresentDto.setId(merchantPresent.getId());
        merchantPresentDto.setCreateTime(merchantPresent.getCreateTime());
        merchantPresentDto.setTitle(merchantPresent.getTitle());
        merchantPresentDto.setContent(merchantPresent.getContent());
        merchantPresentDto.setMerchantOutletId(merchantPresent.getMerchantOutletId());

        return merchantPresentDto;
    }

    /**
     * 导航
     *
     * @param navigation
     * @return
     */
    public static NavigationDto loadByNavigationDto(Navigation navigation)
    {
        if (navigation == null)
        {
            return null;
        }
        NavigationDto navigationDto = new NavigationDto();

        navigationDto.setId(navigation.getId());
        navigationDto.setCreateTime(navigation.getCreateTime());
        navigationDto.setName(navigation.getName());
        navigationDto.setPosition(navigation.getPosition() != null ? com.froad.fft.enums.NavigationPosition.valueOf(navigation.getPosition().name()) : null);
        navigationDto.setUrl(navigation.getUrl());
        navigationDto.setIsVisible(navigation.getIsVisible());
        navigationDto.setIsBlankTarget(navigation.getIsBlankTarget());
        navigationDto.setOrderValue(navigation.getOrderValue());
        navigationDto.setClientId(navigation.getClientId());

        return navigationDto;
    }

    /**
     * 商品属性
     *
     * @param productAttribute
     * @return
     */
    public static ProductAttributeDto loadByProductAttributeDto(ProductAttribute productAttribute)
    {
        if (productAttribute == null)
        {
            return null;
        }
        ProductAttributeDto productAttributeDto = new ProductAttributeDto();

        productAttributeDto.setId(productAttribute.getId());
        productAttributeDto.setCreateTime(productAttribute.getCreateTime());
        productAttributeDto.setName(productAttribute.getName());
        productAttributeDto.setAttributeType(productAttribute.getAttributeType() != null ? com.froad.fft.dto.ProductAttributeDto.AttributeType.valueOf(productAttribute.getAttributeType().toString()
        ) : null);
        productAttributeDto.setIsRequired(productAttribute.getIsRequired());
        productAttributeDto.setIsEnabled(productAttribute.getIsEnabled());
        productAttributeDto.setOrderValue(productAttribute.getOrderValue());
        productAttributeDto.setProductTypeId(productAttribute.getProductTypeId());

        return productAttributeDto;
    }

    /**
     * 商品分类
     *
     * @param productCategory
     * @return
     */
    public static ProductCategoryDto loadByProductCategoryDto(ProductCategory productCategory)
    {
        ProductCategoryDto productCategoryDto = new ProductCategoryDto();
        if (productCategory == null)
        {
            return productCategoryDto;
        }
        productCategoryDto.setId(productCategory.getId());
        productCategoryDto.setCreateTime(productCategory.getCreateTime());
        productCategoryDto.setName(productCategory.getName());
        productCategoryDto.setSeoTitle(productCategory.getSeoTitle());
        productCategoryDto.setSeoKeywords(productCategory.getSeoKeywords());
        productCategoryDto.setSeoDescription(productCategory.getSeoDescription());
        productCategoryDto.setOrderValue(productCategory.getOrderValue());
        productCategoryDto.setTreePath(productCategory.getTreePath());
        productCategoryDto.setParentId(productCategory.getParentId());

        return productCategoryDto;
    }

    /**
     * 商品
     *
     * @param product
     * @return
     */
    public static ProductDto loadByProductDto(Product product)
    {
        ProductDto productDto = new ProductDto();
        if (product == null)
        {
            return productDto;
        }
        productDto.setId(product.getId());
        productDto.setCreateTime(product.getCreateTime());
        productDto.setSn(product.getSn());
        productDto.setName(product.getName());
        productDto.setFullName(product.getFullName());
        productDto.setPrice(product.getPrice());
        productDto.setCost(product.getCost());
        productDto.setMarketPrice(product.getMarketPrice());
        productDto.setImage(product.getImage());
        productDto.setWeight(product.getWeight());
        productDto.setWeightUnit(product.getWeightUnit());
        productDto.setStore(product.getStore());
        productDto.setFreezeStore(product.getFreezeStore());
        productDto.setWarnNumber(product.getWarnNumber());
        productDto.setIsMarketable(product.getIsMarketable());
        productDto.setIsBest(product.getIsBest());
        productDto.setIsNew(product.getIsNew());
        productDto.setIsHot(product.getIsHot());
        productDto.setHits(product.getHits());
        productDto.setIntroduction(product.getIntroduction());
        productDto.setDescription(product.getDescription());
        productDto.setKeyword(product.getKeyword());
        productDto.setSeoTitle(product.getSeoTitle());
        productDto.setSeoKeywords(product.getSeoKeywords());
        productDto.setSeoDescription(product.getSeoDescription());
        productDto.setRackTime(product.getRackTime());
        productDto.setInspectors(product.getInspectors());
        productDto.setProductCategoryId(product.getProductCategoryId());
        productDto.setProductTypeId(product.getProductTypeId());
        productDto.setIsEnableGroup(product.getIsEnableGroup());
        productDto.setProductGroupId(product.getProductGroupId());
        productDto.setIsEnablePresell(product.getIsEnablePresell());
        productDto.setProductPresellId(product.getProductPresellId());
        productDto.setOrderValue(product.getOrderValue());
        productDto.setClientId(product.getClientId());
        productDto.setMerchantId(product.getMerchantId());
        productDto.setCollectToFroad(product.getCollectToFroad());

        return productDto;
    }

    /**
     * *******************************************************
     * <p> 描述: *-- <b>将批量的商品entity 转换成   批量的dto</b> --* </p>
     * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
     * <p> 时间: 2014年4月4日 上午10:05:49 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */

    public static List<ProductDto> loadByProductDtos(List<Product> products)
    {
        if (NullValueCheckUtil.isListArrayEmpty(products))
        {
            return null;
        }
        ProductDto productDto = null;
        List<ProductDto> productDtos = new ArrayList<ProductDto>();
        for (Product product : products)
        {
            productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setCreateTime(product.getCreateTime());
            productDto.setSn(product.getSn());
            productDto.setName(product.getName());
            productDto.setFullName(product.getFullName());
            productDto.setPrice(product.getPrice());
            productDto.setCost(product.getCost());
            productDto.setMarketPrice(product.getMarketPrice());
            productDto.setImage(product.getImage());
            productDto.setWeight(product.getWeight());
            productDto.setWeightUnit(product.getWeightUnit());
            productDto.setStore(product.getStore());
            productDto.setFreezeStore(product.getFreezeStore());
            productDto.setWarnNumber(product.getWarnNumber());
            productDto.setIsMarketable(product.getIsMarketable());
            productDto.setIsBest(product.getIsBest());
            productDto.setIsNew(product.getIsNew());
            productDto.setIsHot(product.getIsHot());
            productDto.setHits(product.getHits());
            productDto.setIntroduction(product.getIntroduction());
            productDto.setDescription(product.getDescription());
            productDto.setKeyword(product.getKeyword());
            productDto.setSeoTitle(product.getSeoTitle());
            productDto.setSeoKeywords(product.getSeoKeywords());
            productDto.setSeoDescription(product.getSeoDescription());
            productDto.setRackTime(product.getRackTime());
            productDto.setInspectors(product.getInspectors());
            productDto.setProductCategoryId(product.getProductCategoryId());
            productDto.setProductTypeId(product.getProductTypeId());
            productDto.setIsEnableGroup(product.getIsEnableGroup());
            productDto.setProductGroupId(product.getProductGroupId());
            productDto.setIsEnablePresell(product.getIsEnablePresell());
            productDto.setProductPresellId(product.getProductPresellId());
            productDto.setOrderValue(product.getOrderValue());
            productDto.setClientId(product.getClientId());
            productDto.setMerchantId(product.getMerchantId());
            productDto.setCollectToFroad(product.getCollectToFroad());
            productDtos.add(productDto);
        }

        return productDtos;
    }

    /**
     * 商品团购
     *
     * @param productGroup
     * @return
     */
    public static ProductGroupDto loadByProductGroupDto(ProductGroup productGroup)
    {
        if (productGroup == null)
        {
            return null;
        }
        ProductGroupDto productGroupDto = new ProductGroupDto();

        productGroupDto.setId(productGroup.getId());
        productGroupDto.setCreateTime(productGroup.getCreateTime());
        productGroupDto.setTitle(productGroup.getTitle());
        productGroupDto.setSummary(productGroup.getSummary());
        productGroupDto.setAreaName(productGroup.getAreaName());
        productGroupDto.setPerNumber(productGroup.getPerNumber());
        productGroupDto.setPerminNumber(productGroup.getPerminNumber());
        productGroupDto.setStartTime(productGroup.getStartTime());
        productGroupDto.setEndTime(productGroup.getEndTime());
        //        productGroupDto.setPrice(productGroup.getPrice());
        productGroupDto.setBuyKnow(productGroup.getBuyKnow());
        productGroupDto.setGeneralizeImage(productGroup.getGeneralizeImage());
        productGroupDto.setDetailsImage(productGroup.getDetailsImage());
        productGroupDto.setDescription(productGroup.getDescription());
        productGroupDto.setOrderValue(productGroup.getOrderValue());

        return productGroupDto;
    }

    /**
     * 商品预售
     *
     * @param productPresell
     * @return
     */
    public static ProductPresellDto loadByProductPresellDto(ProductPresell productPresell)
    {
        if (productPresell == null)
        {
            return null;
        }
        ProductPresellDto productPresellDto = new ProductPresellDto();

        productPresellDto.setId(productPresell.getId());
        productPresellDto.setCreateTime(productPresell.getCreateTime());
        productPresellDto.setTitle(productPresell.getTitle());
        productPresellDto.setSummary(productPresell.getSummary());
        productPresellDto.setPerNumber(productPresell.getPerNumber());
        productPresellDto.setPerminNumber(productPresell.getPerminNumber());
        productPresellDto.setStartTime(productPresell.getStartTime());
        productPresellDto.setEndTime(productPresell.getEndTime());
        productPresellDto.setDeliveryStartTime(productPresell.getDeliveryStartTime());
        productPresellDto.setDeliveryEndTime(productPresell.getDeliveryEndTime());
        //        productPresellDto.setPrice(productPresell.getPrice());
        productPresellDto.setClusteringNumber(productPresell.getClusteringNumber());
        productPresellDto.setTrueBuyerNumber(productPresell.getTrueBuyerNumber());
        productPresellDto.setVirtualBuyerNumber(productPresell.getVirtualBuyerNumber());
        productPresellDto.setClusterState(null != productPresell.getClusterState() ? com.froad.fft.enums.ClusterState.valueOf(productPresell.getClusterState().name()) : null);
        productPresellDto.setClusterType(productPresell.getClusterType() != null ? com.froad.fft.dto.ProductPresellDto.ClusterType.valueOf(productPresell.getClusterType().toString()) : null);
        productPresellDto.setDetailsImage(productPresell.getDetailsImage());
        productPresellDto.setGeneralizeImage(productPresell.getGeneralizeImage());
        productPresellDto.setBuyKnow(productPresell.getBuyKnow());
        productPresellDto.setDescription(productPresell.getDescription());
        productPresellDto.setOrderValue(productPresell.getOrderValue());

        return productPresellDto;
    }

    /**
     * 预售提供点
     *
     * @param presellDelivery
     * @return
     */
    public static PresellDeliveryDto loadByPresellDeliveryDto(PresellDelivery presellDelivery)
    {
        if (presellDelivery == null)
        {
            return null;
        }
        PresellDeliveryDto presellDeliveryDto = new PresellDeliveryDto();

        presellDeliveryDto.setId(presellDelivery.getId());
        presellDeliveryDto.setCreateTime(presellDelivery.getCreateTime());
        presellDeliveryDto.setName(presellDelivery.getName());
        presellDeliveryDto.setAddress(presellDelivery.getAddress());
        presellDeliveryDto.setTelephone(presellDelivery.getTelephone());
        presellDeliveryDto.setBusinessTime(presellDelivery.getBusinessTime());
        presellDeliveryDto.setCoordinate(presellDelivery.getCoordinate());
        presellDeliveryDto.setOrderValue(presellDelivery.getOrderValue());

        presellDeliveryDto.setClientId(presellDelivery.getClientId());
        presellDeliveryDto.setBusinessCircleId(presellDelivery.getBusinessCircleId());
        presellDeliveryDto.setDataState(null != presellDelivery.getDataState() ? com.froad.fft.enums.DataState.valueOf(presellDelivery.getDataState().name()) : null);

       presellDeliveryDto.setDirector(presellDelivery.getDirector());
        return presellDeliveryDto;
    }

    /**
     * 商品图片
     *
     * @param productImage
     * @return
     */
    public static ProductImageDto loadByProductImageDto(ProductImage productImage)
    {
        if (productImage == null)
        {
            return null;
        }
        ProductImageDto productImageDto = new ProductImageDto();

        productImageDto.setId(productImage.getId());
        productImageDto.setCreateTime(productImage.getCreateTime());
        productImageDto.setTitle(productImage.getTitle());
        productImageDto.setSource(productImage.getSource());
        productImageDto.setLarge(productImage.getLarge());
        productImageDto.setMedium(productImage.getMedium());
        productImageDto.setThumbnail(productImage.getThumbnail());
        productImageDto.setOrderValue(productImage.getOrderValue());
        productImageDto.setProductId(productImage.getProductId());

        return productImageDto;
    }

    /**
     * 商品类型
     *
     * @param productType
     * @return
     */
    public static ProductTypeDto loadByProductTypeDto(ProductType productType)
    {
        if (productType == null)
        {
            return null;
        }
        ProductTypeDto productTypeDto = new ProductTypeDto();

        productTypeDto.setId(productType.getId());
        productTypeDto.setCreateTime(productType.getCreateTime());
        productTypeDto.setName(productType.getName());
        productTypeDto.setDataState(productType.getDataState() != null ? com.froad.fft.enums.DataState.valueOf(productType.getDataState().name()) : null);

        return productTypeDto;
    }

    /**
     * 短信模板
     *
     * @param msContent
     * @return
     */
    public static SmsContentDto loadBySmsContentDto(SmsContent smsContent)
    {
        if (smsContent == null)
        {
            return null;
        }
        SmsContentDto smsContentDto = new SmsContentDto();

        smsContentDto.setId(smsContent.getId());
        smsContentDto.setCreateTime(smsContent.getCreateTime());
        smsContentDto.setSmsType(smsContent.getSmsType() != null ? com.froad.fft.enums.SmsType.valueOf(smsContent.getSmsType().name()) : null);
        smsContentDto.setClientId(smsContent.getClientId());
        smsContentDto.setContent(smsContent.getContent());
        smsContentDto.setMsgSuffix(smsContent.getMsgSuffix());
        smsContentDto.setIsEnableSuffix(smsContent.getIsEnableSuffix());

        return smsContentDto;
    }

    /**
     * 短信日志
     *
     * @param smsLog
     * @return
     */
    public static SmsLogDto loadBySmsLogDto(SmsLog smsLog)
    {
        if (smsLog == null)
        {
            return null;
        }
        SmsLogDto smsLogDto = new SmsLogDto();

        smsLogDto.setId(smsLog.getId());
        smsLogDto.setCreateTime(smsLog.getCreateTime());
        smsLogDto.setSmsType(smsLog.getSmsType() != null ? com.froad.fft.enums.SmsType.valueOf(smsLog.getSmsType().name()) : null);
        smsLogDto.setMobile(smsLog.getMobile());
        smsLogDto.setContent(smsLog.getContent());
        smsLogDto.setIsSuccess(smsLog.getIsSuccess());
        smsLogDto.setSendUser(smsLog.getSendUser());
        smsLogDto.setClientId(smsLog.getClientId());

        return smsLogDto;
    }

    /**
     * 资源
     *
     * @param sysResource
     * @return
     */
    public static SysResourceDto loadBySysResourceDto(SysResource sysResource)
    {
        if (sysResource == null)
        {
            return null;
        }
        SysResourceDto sysResourceDto = new SysResourceDto();

        sysResourceDto.setId(sysResource.getId());
        sysResourceDto.setCreateTime(sysResource.getCreateTime());
        sysResourceDto.setName(sysResource.getName());
        sysResourceDto.setIcon(sysResource.getIcon());
        sysResourceDto.setCode(sysResource.getCode());
        sysResourceDto.setUrl(sysResource.getUrl());
        sysResourceDto.setType(sysResource.getType() != null ? com.froad.fft.dto.SysResourceDto.Type.valueOf(sysResource.getType().toString()) : null);
        sysResourceDto.setParentId(sysResource.getParentId());
        sysResourceDto.setTreePath(sysResource.getTreePath());
        sysResourceDto.setIsEnabled(sysResource.getIsEnabled());
        sysResourceDto.setIsSystem(sysResource.getIsSystem());
        sysResourceDto.setDescription(sysResource.getDescription());
        sysResourceDto.setOrderValue(sysResource.getOrderValue());
        sysResourceDto.setClientId(sysResource.getClientId());

        return sysResourceDto;
    }

    /**
     * 角色
     *
     * @param sysRole
     * @return
     */
    public static SysRoleDto loadBySysRoleDto(SysRole sysRole)
    {
        if (sysRole == null)
        {
            return null;
        }

        SysRoleDto sysRoleDto = new SysRoleDto();

        sysRoleDto.setId(sysRole.getId());
        sysRoleDto.setCreateTime(sysRole.getCreateTime());
        sysRoleDto.setName(sysRole.getName());
        sysRoleDto.setValue(sysRole.getValue());
        sysRoleDto.setIsSystem(sysRole.getIsSystem());
        sysRoleDto.setDescription(sysRole.getDescription());

        return sysRoleDto;
    }

    /**
     * 用户
     *
     * @param sysUser
     * @return
     */
    public static SysUserDto loadBySysUserDto(SysUser sysUser)
    {
        SysUserDto sysUserDto = new SysUserDto();
        if (sysUser == null)
        {
            return sysUserDto;
        }
        sysUserDto.setId(sysUser.getId());
        sysUserDto.setCreateTime(sysUser.getCreateTime());
        sysUserDto.setUsername(sysUser.getUsername());
        sysUserDto.setPassword(sysUser.getPassword());
        sysUserDto.setAlias(sysUser.getAlias());
        sysUserDto.setEmail(sysUser.getEmail());
        sysUserDto.setPhone(sysUser.getPhone());
        sysUserDto.setDepartment(sysUser.getDepartment());
        sysUserDto.setIsEnabled(sysUser.getIsEnabled());
        sysUserDto.setIsLocked(sysUser.getIsLocked());
        sysUserDto.setLoginFailureCount(sysUser.getLoginFailureCount());
        sysUserDto.setLockedDate(sysUser.getLockedDate());
        sysUserDto.setLoginDate(sysUser.getLoginDate());
        sysUserDto.setLoginIp(sysUser.getLoginIp());
        sysUserDto.setClientId(sysUser.getClientId());
        return sysUserDto;
    }

    /**
     * 系统配置
     *
     * @param systemConfig
     * @return
     */
    public static SystemConfigDto loadBySystemConfigDto(SystemConfig systemConfig)
    {
        if (systemConfig == null)
        {
            return null;
        }
        SystemConfigDto systemConfigDto = new SystemConfigDto();

        systemConfigDto.setSystemName(systemConfig.getSystemName());
        systemConfigDto.setSystemVersion(systemConfig.getSystemVersion());
        systemConfigDto.setIsSystemDebug(systemConfig.getIsSystemDebug());
        systemConfigDto.setIsLoginFailureLock(systemConfig.getIsLoginFailureLock());
        systemConfigDto.setLoginFailureLockCount(systemConfig.getLoginFailureLockCount());
        systemConfigDto.setLoginFailureLockTime(systemConfig.getLoginFailureLockTime());
        systemConfigDto.setUploadMaxSize(systemConfig.getUploadMaxSize());
        systemConfigDto.setAllowedUploadImageExtension(systemConfig.getAllowedUploadImageExtension());
        systemConfigDto.setAllowedUploadFileExtension(systemConfig.getAllowedUploadFileExtension());
        systemConfigDto.setCookiePath(systemConfig.getCookiePath());
        systemConfigDto.setCookieDomain(systemConfig.getCookieDomain());
        systemConfigDto.setFtpHost(systemConfig.getFtpHost());
        systemConfigDto.setFtpPort(systemConfig.getFtpPort());
        systemConfigDto.setFtpUsername(systemConfig.getFtpUsername());
        systemConfigDto.setFtpPassword(systemConfig.getFtpPassword());
        systemConfigDto.setFtpEncoding(systemConfig.getFtpEncoding());
        systemConfigDto.setFtpDirectoryPath(systemConfig.getFtpDirectoryPath());
        systemConfigDto.setFtpUrl(systemConfig.getFtpUrl());

        systemConfigDto.setClientSiteUrl_243(systemConfig.getClientSiteUrl_243());

        return systemConfigDto;
    }

    /**
     * 标签
     *
     * @param tag
     * @return
     */
    public static TagDto loadByTagDto(Tag tag)
    {
        if (tag == null)
        {
            return null;
        }
        TagDto tagDto = new TagDto();

        tagDto.setId(tag.getId());
        tagDto.setCreateTime(tag.getCreateTime());
        tagDto.setName(tag.getName());
        tagDto.setType(tag.getType() != null ? com.froad.fft.enums.TagType.valueOf(tag.getType().name()) : null);
        tagDto.setIcon(tag.getIcon());
        tagDto.setDescription(tag.getDescription());
        tagDto.setOrderValue(tag.getOrderValue());

        return tagDto;
    }

    /**
     * 商圈
     *
     * @param temp 商圈数据实体
     * @return 转换后的dto
     */
    public static BusinessCircleDto loadBusinessCircleDto(BusinessCircle temp)
    {
        if (temp == null)
        {
            return null;
        }
        BusinessCircleDto dto = new BusinessCircleDto();
        dto.setId(temp.getId());
        dto.setCreateTime(temp.getCreateTime());

        dto.setName(temp.getName());
        dto.setOrderValue(temp.getOrderValue());
        dto.setParentId(temp.getParentId());
        dto.setAreaId(temp.getAreaId());
        dto.setTreePath(temp.getTreePath());
        return dto;
    }

    /**
     * 送积分转换
     *
     * @param temp 数据temp
     * @return 转换后的商圈实体dto
     */
    public static GivePointRuleDto loadGivePointRuleDto(GivePointRule temp)
    {
        if (temp == null)
        {
            return null;
        }
        GivePointRuleDto dto = new GivePointRuleDto();
        dto.setId(temp.getId());
        dto.setCreateTime(temp.getCreateTime());

        dto.setName(temp.getName());
        dto.setType(temp.getType() != null ? com.froad.fft.enums.GivePointRuleType.valueOf(temp.getType().name()) : null);
        dto.setPointValue(temp.getPointValue());
        dto.setActiveTime(temp.getActiveTime());
        dto.setExpireTime(temp.getExpireTime());
        dto.setRemark(temp.getRemark());
        return dto;
    }

    /**
     * 商圈用户组转换
     *
     * @param temp 数据实体
     * @return 转换后的商圈实体dto
     */
    public static MerchantGroupUserDto loadMerchantGroupUserDto(MerchantGroupUser temp)
    {
        if (temp == null)
        {
            return null;
        }
        MerchantGroupUserDto dto = new MerchantGroupUserDto();
        dto.setId(temp.getId());
        dto.setCreateTime(temp.getCreateTime());

        dto.setMerchantId(temp.getMerchantId());
        dto.setMerchantOutletId(temp.getMerchantOutletId());
        dto.setSysUserId(temp.getSysUserId());
        return dto;
    }

    /**
     * 退款
     *
     * @param tag
     * @return
     */
    public static RefundsDto loadByRefundsDto(Refunds refunds)
    {
        if (refunds == null)
        {
            return null;
        }
        RefundsDto refundsDto = new RefundsDto();
        refundsDto.setId(refunds.getId());
        refundsDto.setCreateTime(refunds.getCreateTime());
        refundsDto.setSn(refunds.getSn());
        refundsDto.setTransId(refunds.getTransId());
        refundsDto.setState(refunds.getState() != null ? com.froad.fft.dto.RefundsDto.State.valueOf(refunds.getState().name()) : null);
        refundsDto.setReason(refunds.getReason());
        refundsDto.setSysUserId(refunds.getSysUserId());
        refundsDto.setRemark(refunds.getRemark());
        return refundsDto;
    }

    /**
     * @param sysClient
     * @return
     * @author larry
     * <p> 客户端信息转换</p>
     * <p>entity to dto</p>
     */
    public static SysClientDto loadbySysClientDto(SysClient sysClient)
    {
        if (sysClient == null)
        {
            return null;
        }
        SysClientDto sysClientDto = new SysClientDto();
        sysClientDto.setId(sysClient.getId());
        sysClientDto.setArea(sysClient.getArea());
        sysClientDto.setCreateTime(sysClient.getCreateTime());
        sysClientDto.setIsEnabled(sysClient.getIsEnabled());
        sysClientDto.setName(sysClient.getName());
        sysClientDto.setNumber(sysClient.getNumber());
        sysClientDto.setRemark(sysClient.getRemark());
        sysClientDto.setShortLetter(sysClient.getShortLetter());
        return sysClientDto;
    }

    /**
     * 方法描述：库存转换
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月28日 下午8:04:29
     */
    public static StockPileDto loadByStockPileDto(StockPile stockPile)
    {
        if (stockPile == null)
        {
            return null;
        }
        StockPileDto stockPileDto = new StockPileDto();
        stockPileDto.setId(stockPile.getId());
        stockPileDto.setCreateTime(stockPile.getCreateTime());
        stockPileDto.setProductId(stockPile.getProductId());
        stockPileDto.setMerchantOutletId(stockPile.getMerchantOutletId());
        stockPileDto.setQuantity(stockPile.getQuantity());
        stockPileDto.setLastIncomeTime(stockPile.getLastIncomeTime());
        stockPileDto.setLastOutcomeTime(stockPile.getLastOutcomeTime());
        stockPileDto.setWarnType(stockPile.getWarnType() != null ? com.froad.fft.dto.StockPileDto.WarnType.valueOf(stockPile.getWarnType().name()) : null);
        stockPileDto.setWarnValue(stockPile.getWarnValue());
        stockPileDto.setTotalQuantity(stockPile.getTotalQuantity());
        stockPileDto.setRemark(stockPile.getRemark());
        stockPileDto.setMerchantOutletIds(stockPile.getMerchantOutletIds());

        return stockPileDto;
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
    public static StockPileLogDto loadByStockPileLogDto(StockPileLog stockPileLog)
    {
        if (stockPileLog == null)
        {
            return null;
        }
        StockPileLogDto stockPileLogDto = new StockPileLogDto();
        stockPileLogDto.setId(stockPileLog.getId());
        stockPileLogDto.setCreateTime(stockPileLog.getCreateTime());
        stockPileLogDto.setType(stockPileLog.getType() != null ? com.froad.fft.dto.StockPileLogDto.Type.valueOf(stockPileLog.getType().name()) : null);
        stockPileLogDto.setProductId(stockPileLog.getProductId());
        stockPileLogDto.setMerchantOutletId(stockPileLog.getMerchantOutletId());
        stockPileLogDto.setQuantity(stockPileLog.getQuantity());
        stockPileLogDto.setContent(stockPileLog.getContent());
        stockPileLogDto.setOperator(stockPileLog.getOperator());
        return stockPileLogDto;
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
    public static OutletPresellDeliveryDto loadByOutletPresellDeliveryDto(OutletPresellDelivery outletPresellDelivery)
    {
        if (outletPresellDelivery == null)
        {
            return null;
        }
        OutletPresellDeliveryDto deliveryDto = new OutletPresellDeliveryDto();
        deliveryDto.setMerchantOutletId(outletPresellDelivery.getMerchantOutletId());
        deliveryDto.setPresellDeliveryId(outletPresellDelivery.getPresellDeliveryId());
        return deliveryDto;
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
    public static CashPointsRatioDto loadByCashPointsRatioDto(CashPointsRatio cashPointsRatio)
    {
        if (cashPointsRatio == null)
        {
            return null;
        }
        CashPointsRatioDto cashPointsRatioDto = new CashPointsRatioDto();
        cashPointsRatioDto.setId(cashPointsRatio.getId());
        cashPointsRatioDto.setCreateTime(cashPointsRatio.getCreateTime());
        cashPointsRatioDto.setFftPoints(cashPointsRatio.getFftPoints());
        cashPointsRatioDto.setBankPoints(cashPointsRatio.getBankPoints());
        cashPointsRatioDto.setSysClientId(cashPointsRatio.getSysClientId());
        return cashPointsRatioDto;
    }

    /**
     * 商户账户转换
     *
     * @param merchantAccount 商户账户dto
     * @return 商户账户实体
     */
    public static MerchantAccountDto loadByMerchantAccountDto(MerchantAccount merchantAccount)
    {
        if (merchantAccount == null)
        {
            return null;
        }
        MerchantAccountDto merchantAccountDto = new MerchantAccountDto();

        merchantAccountDto.setId(merchantAccount.getId());
        merchantAccountDto.setCreateTime(merchantAccount.getCreateTime());

        merchantAccountDto.setMerchantId(merchantAccount.getMerchantId());
        merchantAccountDto.setAcctName(merchantAccount.getAcctName());
        merchantAccountDto.setAcctNumber(merchantAccount.getAcctNumber());
        merchantAccountDto.setFundsChannelId(merchantAccount.getFundsChannelId());
        merchantAccountDto.setIsEnabled(merchantAccount.getIsEnabled());
        merchantAccountDto.setAcctType(null != merchantAccount.getAcctType() ? AccountType.valueOf(merchantAccount.getAcctType().name()) : null);

        return merchantAccountDto;
    }

    /**
     * @param sysRoleResource <p>系统角色资源</p>
     * @return
     * @author larry
     */
    public static SysRoleResourceDto loadBySysRoleResourceDto(SysRoleResource sysRoleResource)
    {
        if (sysRoleResource == null)
        {
            return null;
        }
        SysRoleResourceDto sysRoleResourceDto = new SysRoleResourceDto();
        sysRoleResourceDto.setRoleId(sysRoleResource.getRoleId());
        sysRoleResourceDto.setResourceId(sysRoleResource.getResourceId());
        return sysRoleResourceDto;
    }

    public static FundsChannelDto loadByFundsChannelDto(FundsChannel fundsChannel)
    {
        if (fundsChannel == null)
        {
            return null;
        }
        FundsChannelDto fundsChannelDto = new FundsChannelDto();
        fundsChannelDto.setId(fundsChannel.getId());
        fundsChannelDto.setCreateTime(fundsChannel.getCreateTime());

        fundsChannelDto.setShortName(fundsChannel.getShortName());
        fundsChannelDto.setFullName(fundsChannel.getFullName());
        fundsChannelDto.setChannelType(null == fundsChannel.getChannelType() ? null : TransPayChannel.valueOf(fundsChannel.getChannelType().name()));
        fundsChannelDto.setPayOrg(fundsChannel.getPayOrg());

        return fundsChannelDto;
    }

    public static SysUserRoleDto loadBySysUserRoleDto(SysUserRole sysUserRole)
    {
        SysUserRoleDto sysUserRoleDto = new SysUserRoleDto();
        sysUserRoleDto.setRoleId(sysUserRole.getRoleId());
        sysUserRoleDto.setUserId(sysUserRole.getUserId());
        return sysUserRoleDto;
    }

    public static MerchantGroupUserDto loadByMerchantGroupUserDto(MerchantGroupUser merchantGroupUser)
    {
        MerchantGroupUserDto merchantGroupUserDto = new MerchantGroupUserDto();
        if (merchantGroupUser == null)
        {
            return merchantGroupUserDto;
        }
        merchantGroupUserDto.setId(merchantGroupUser.getId());
        merchantGroupUserDto.setMerchantId(merchantGroupUser.getMerchantId());
        merchantGroupUserDto.setMerchantOutletId(merchantGroupUser.getMerchantOutletId());
        merchantGroupUserDto.setSysUserId(merchantGroupUser.getSysUserId());
        merchantGroupUserDto.setCreateTime(merchantGroupUser.getCreateTime());
        return merchantGroupUserDto;
    }

    /**
     * 预售商品与提货点关联表
     *
     * @param productPresellDelivery 实体
     * @return dto
     */
    public static ProductPresellDeliveryDto loadByProductPresellDeliveryDto(ProductPresellDelivery entity)
    {
        if (entity == null)
        {
            return null;
        }
        ProductPresellDeliveryDto dto = new ProductPresellDeliveryDto();
        dto.setId(entity.getId());
        dto.setCreateTime(entity.getCreateTime());

        dto.setPresellDeliveryId(entity.getPresellDeliveryId());
        dto.setProductPresellId(entity.getProductPresellId());
        return dto;
    }

    /**
     * 交易数据转换
     *
     * @param entity 实体
     * @return dto
     */
    public static TransQueryDto loadByTransDto(Trans entity)
    {
        if (entity == null)
        {
            return null;
        }
        TransQueryDto dto = new TransQueryDto();
        dto.setId(entity.getId());
        dto.setCreateTime(entity.getCreateTime());
        dto.setSn(entity.getSn());
        dto.setCreateSource(null == entity.getCreateSource() ? null : TransCreateSource.valueOf(entity.getCreateSource().name()));
        dto.setType(null == entity.getType() ? null : TransType.valueOf(entity.getType().name()));
        dto.setPayMethod(null == entity.getPayMethod() ? null : PayMethodShow.valueOf(entity.getPayMethod().toString()));
        dto.setPayChannel(null == entity.getPayChannel() ? null : TransPayChannel.valueOf(entity.getPayChannel().name()));
        dto.setState(null == entity.getState() ? null : TransState.valueOf(entity.getState().name()));
        dto.setMemberCode(entity.getMemberCode());
        dto.setPayState(null == entity.getPayState() ? null : TransPayState.valueOf(entity.getPayState().name()));
        dto.setClientId(entity.getClientId());
        dto.setClientNumber(entity.getClientNumber());
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setRealPrice(entity.getRealPrice());
        dto.setFftPoints(entity.getFftPoints());
        dto.setBankPoints(entity.getBankPoints());
        dto.setPointsAmountValue(entity.getPointsAmountValue());
        dto.setPointsAmountRealValue(entity.getPointsAmountRealValue());
        dto.setBuyPoints(entity.getBuyPoints());
        dto.setBuyPointsFactorage(entity.getBuyPointsFactorage());
        dto.setGatheringValue(entity.getGatheringValue());
        dto.setPointsWithdrawFactorage(entity.getPointsWithdrawFactorage());
        dto.setGivePoints(entity.getGivePoints());
        dto.setMerchantId(entity.getMerchantId());
        dto.setReason(entity.getReason());
        dto.setFilmMobile(entity.getFilmMobile());
        dto.setPhone(entity.getPhone());
        dto.setRemark(entity.getRemark());
        dto.setDeliveryId(entity.getDeliveryId());
        dto.setDeliveryName(entity.getDeliveryName());
        dto.setSysUserIds(entity.getSysUserIds());
        List<com.froad.fft.enums.trans.TransPayState> transPayStates = null;
        if (entity.getPayStates() != null)
        {
            transPayStates = new ArrayList<com.froad.fft.enums.trans.TransPayState>();
            for (com.froad.fft.persistent.common.enums.TransPayState transPayState : entity.getPayStates())
            {//api 枚举转 entity枚举
                transPayStates.add(com.froad.fft.enums.trans.TransPayState.valueOf(transPayState.name()));
            }
        }
        dto.setPayStates(transPayStates);
        return dto;

    }

    /**
     * 交易明细数据转换
     *
     * @param entity 交易明细数据
     * @return dto
     */
    public static TransDetailDto loadByTransDetailDto(TransDetails entity)
    {
        if (entity == null)
        {
            return null;
        }
        TransDetailDto dto = new TransDetailDto();
        dto.setId(entity.getId());
        dto.setCreateTime(entity.getCreateTime());

        dto.setTransId(entity.getTransId());
        dto.setProductId(entity.getProductId());
        dto.setProductName(entity.getProductName());
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());
        dto.setSingle(entity.getSingle());
        dto.setSupplyMerchantId(entity.getSupplyMerchantId());
        dto.setGatherMerchantId(entity.getGatherMerchantId());

        return dto;

    }

    /**
     * 支付信息转换
     *
     * @param entity 支付信息实体
     * @return dto
     */
    public static PayDto loadByPayDto(Pay entity)
    {
        if (entity == null)
        {
            return null;
        }
        PayDto dto = new PayDto();
        dto.setId(entity.getId());
        dto.setCreateTime(entity.getCreateTime());
        dto.setSn(entity.getSn());
        dto.setTransId(entity.getTransId());
        dto.setPayType(null == entity.getPayType() ? null : PayType.valueOf(entity.getPayType().name()));
        dto.setPayTypeDetails(null == entity.getPayTypeDetails() ? null : PayTypeDetails.valueOf(entity.getPayTypeDetails().name()));
        dto.setPayState(null == entity.getPayState() ? null : PayState.valueOf(entity.getPayState().name()));
        dto.setFromRole(null == entity.getFromRole() ? null : PayRole.valueOf(entity.getFromRole().name()));
        dto.setToRole(null == entity.getToRole() ? null : PayRole.valueOf(entity.getToRole().name()));
        dto.setStep(entity.getStep());
        dto.setPayOrg(entity.getPayOrg());
        dto.setBillNo(entity.getBillNo());
        dto.setRefundOrderId(entity.getRefundOrderId());
        dto.setPointBillNo(entity.getPointBillNo());
        dto.setRefundPointNo(entity.getRefundPointNo());
        dto.setPayValue(entity.getPayValue());
        dto.setFromAccountName(entity.getFromAccountName());
        dto.setFromAccountNo(entity.getFromAccountNo());
        dto.setToAccountName(entity.getToAccountName());
        dto.setToAccountNo(entity.getToAccountNo());
        dto.setFromPhone(entity.getFromPhone());
        dto.setToPhone(entity.getToPhone());
        dto.setFromUserName(entity.getFromUserName());
        dto.setToUserName(entity.getToUserName());
        dto.setMerchantId(entity.getMerchantId());
        dto.setResultCode(entity.getResultCode());
        dto.setResultDesc(entity.getResultDesc());
        dto.setRemark(entity.getRemark());

        return dto;

    }

    /**
     * <p>退换货</p>
     *
     * @return ReturnSaleDto
     * @throws example<br/>
     * @author larry
     * @datetime 2014-4-3下午06:14:48
     */
    public static ReturnSaleDto loadByReturnSaleDto(ReturnSale returnSale)
    {
        ReturnSaleDto returnSaleDto = new ReturnSaleDto();
        if (returnSale == null)
        {
            return returnSaleDto;
        }
        returnSaleDto.setId(returnSale.getId());
        returnSaleDto.setMerchantOutletId(returnSale.getMerchantOutletId());
        returnSaleDto.setReason(returnSale.getReason());
        returnSaleDto.setRemark(returnSale.getRemark());
        returnSaleDto.setSn(returnSale.getSn());
        returnSaleDto.setSysUserId(returnSale.getSysUserId());
        returnSaleDto.setType(returnSale.getType() != null ? com.froad.fft.dto.ReturnSaleDto.Type.valueOf(returnSale.getType().name()) : null);
        returnSaleDto.setCreateTime(returnSale.getCreateTime());
        returnSaleDto.setSysUserIds(returnSale.getSysUserIds());
        return returnSaleDto;
    }

    /**
     * <p>退换货详情</p>
     *
     * @return ReturnSaleDetailDto
     * @throws example<br/>
     * @author larry
     * @datetime 2014-4-3下午08:10:42
     */
    public static ReturnSaleDetailDto loadByReSaleDetailDto(ReturnSaleDetail returnSaleDetail)
    {
        ReturnSaleDetailDto returnSaleDetailDto = new ReturnSaleDetailDto();
        if (returnSaleDetail == null)
        {
            return returnSaleDetailDto;
        }
        returnSaleDetailDto.setId(returnSaleDetail.getId());
        returnSaleDetailDto.setProductId(returnSaleDetail.getProductId());
        returnSaleDetailDto.setQuantity(returnSaleDetail.getQuantity());
        returnSaleDetailDto.setReturnSaleId(returnSaleDetail.getReturnSaleId());
        returnSaleDetailDto.setSecuritiesNo(returnSaleDetail.getSecuritiesNo());
        return returnSaleDetailDto;
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
    public static TransSecurityTicketDto loadByTransSecurityTicketDto(TransSecurityTicket ticket)
    {
        if (ticket == null)
        {
            return null;
        }
        TransSecurityTicketDto transSecurityTicket = new TransSecurityTicketDto();
        transSecurityTicket.setId(ticket.getId());
        transSecurityTicket.setCreateTime(ticket.getCreateTime());
        transSecurityTicket.setTransId(ticket.getTransId());
        transSecurityTicket.setMemberCode(ticket.getMemberCode());
        transSecurityTicket.setTransType(ticket.getTransType() != null ? TransType.valueOf(ticket.getTransType().name()) : null);
        transSecurityTicket.setSecuritiesNo(ticket.getSecuritiesNo());
        transSecurityTicket.setIsConsume(ticket.getIsConsume());
        transSecurityTicket.setExpireTime(ticket.getExpireTime());
        transSecurityTicket.setConsumeTime(ticket.getConsumeTime());
        transSecurityTicket.setSmsNumber(ticket.getSmsNumber());
        transSecurityTicket.setSmsTime(ticket.getSmsTime());
        transSecurityTicket.setSysUserId(ticket.getSysUserId());
        transSecurityTicket.setMerchantId(ticket.getMerchantId());
        return transSecurityTicket;
    }

    /**
     * 限购规则
     *
     * @param temp
     * @return
     */
    public static ProductRestrictRuleDto loadByProductRestrictRuleDto(ProductRestrictRule temp)
    {
        if (temp == null)
        {
            return null;
        }
        ProductRestrictRuleDto dto = new ProductRestrictRuleDto();
        dto.setId(temp.getId());
        dto.setCreateTime(temp.getCreateTime());
        dto.setProductId(temp.getProductId());
        dto.setQuantity(temp.getQuantity());
        dto.setRemark(temp.getRemark());
        dto.setRestrictType(temp.getRestrictType() != null ? RestrictType.valueOf(temp.getRestrictType().name()) : null);
        return dto;

    }

    /**
     *注册送积分规则
     *
     * @param temp
     * @return
     */
    public static RegisterGivePointRuleDto loadByRegisterGivePointRuleDto(RegisterGivePointRule temp)
    {
        if (temp == null)
        {
            return null;
        }
        RegisterGivePointRuleDto dto = new RegisterGivePointRuleDto();
        dto.setId(temp.getId());
        dto.setCreateTime(temp.getCreateTime());
        dto.setClientId(temp.getClientId());
        dto.setBegineTime(temp.getBegineTime());
        dto.setEndTime(temp.getEndTime());
        dto.setGivePoint(temp.getGivePoint());
        dto.setIsEnable(temp.getIsEnable());

        return dto;

    }

}
