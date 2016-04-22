package com.froad.action.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.action.support.*;
import com.froad.client.goodsExchangeRack.GoodsExchangeRack;
import com.froad.client.goodsGroupRack.GoodsGroupRack;
import com.froad.util.Assert;
import com.froad.util.command.GoodsCommand;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.froad.baseAction.BaseActionSupport;
import com.froad.client.Store.Store;
import com.froad.client.advert.Advert;
import com.froad.client.announcement.Announcement;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchant.TagMAP;
import com.froad.client.merchantPreferential.MerchantPreferential;
import com.froad.client.searches.Searches;
import com.froad.util.Command;
import com.froad.util.JsonUtil;

/**
 * @author FQ
 * @version 1.0
 * @date 2013-2-19 下午01:38:58
 */
public class IndexAction extends BaseActionSupport {

    /**
     * UID
     */
    private static final long serialVersionUID = 6002160263444909107L;

    private GoodsExchangeRackActionSupport goodsExchangeRackActionSupport;
    private GoodsGroupRackActionSupport goodsGroupRackActionSupport;
    private AnnouncementActionSupport announcementActionSupport;
    private AdvertActionSupport advertActionSupport;//广告
    private List<Advert> advertList;//广告
    private Announcement announcement;
    private SearchesActionSupport searchesActionSupport;
    private MerchantActionSupport merchantActionSupport;
    private MerchantPreferentialActionSupport merchantPreferentialActionSupport;
    private StoreActionSupport storeActionSupport;
    //新增最重要的公告链
    private List<Announcement> announcementList = new ArrayList<Announcement>();

    private List goodsGroupRackList = new ArrayList();

    private List goodsExchangeRackList = new ArrayList();


    /**
     * 首页
     *
     * @return
     */
    public String index() {
        //公告
//		announcement=announcementActionSupport.getAnnouncementOrderByImportant();
        announcementList = announcementActionSupport.getAnnouncementOrderListByImportant();
        //团购的列表
        goodsGroupRackList = goodsGroupRackActionSupport.getIndexGoodsRack();
        //积分兑换列表
        goodsExchangeRackList = goodsExchangeRackActionSupport.getIndexGoodsRack();

        return "index";
    }

    /**
     * 商户换一批
     *
     * @return
     */
    public void ajaxMerchant() {
        Merchant pager = new Merchant();

        //积分返利  的商户
        pager.setPageSize(6);
        pager.setState(Command.FBU_USERED_STATE);
        pager.setPreferentialType("2");
        pager.setOrderBy("merchant_priority*1");//优先级
        pager.setOrderType(com.froad.client.merchant.OrderType.DESC);
        pager = merchantActionSupport.getMerchantByPager(pager);

        List merchantPagerList = pager.getList();
        List list = new ArrayList();
        if (merchantPagerList != null && !merchantPagerList.isEmpty()) {

            for (Object merchant : merchantPagerList) {
                Map<String, Object> merchantMap = new HashMap<String, Object>();
                Merchant m = (Merchant) merchant;
                merchantMap.put("merchant_id", m.getId());
                List<Store> slist = storeActionSupport.getStoresByMerchantId(m.getId());
//				int count=2;
                if (m.getMstoreAddress() == null) {
                    m.setMstoreAddress("");
                }
                if (m.getMstoreShortName() == null) {
                    m.setMstoreShortName("");
                }
                if (m.getMstoreTel() == null) {
                    m.setMstoreTel("");
                }
                /*
                 * merchantMap.put("merchant_short_name", m.getCompanyShortName());
				 * 替换为门店短名
				 */
                if (slist.size() > 0) {
                    merchantMap.put("merchant_address", "<a style='text-decoration:none;color:#660000;' target='_blank' href=\"merchant_annex_info.action?pager.merchantId=" + m.getId() + "&pager.pageNumber=1\">查看全部" + slist.size() + "家门店地址</a>");
                    merchantMap.put("merchant_tel", "<a style='text-decoration:none;color:#660000;' target='_blank' href=\"merchant_annex_info.action?pager.merchantId=" + m.getId() + "&pager.pageNumber=1\">查看全部" + slist.size() + "家门店电话</a>");
                } else {
                    merchantMap.put("merchant_address", m.getMstoreAddress());
                    merchantMap.put("merchant_tel", m.getMstoreTel());
                }
                merchantMap.put("merchant_short_name", m.getMstoreShortName());
                merchantMap.put("merchant_present", m.getMerchantPresent() != null ? m.getMerchantPresent().getTxt() : "");

                //分店数量多于一个显示查看全部门店地址和电话
                //地区和标签
                List districtbList = new ArrayList();
                List classifybList = new ArrayList();

                List tagMapList = m.getTagMapList();
                int flag = 0;
                for (Object tagmap : tagMapList) {

                    if (flag == 3) {
                        break;
                    }

                    Map<String, Object> districtbMap = new HashMap<String, Object>();
                    Map<String, Object> classifybMap = new HashMap<String, Object>();
                    TagMAP tm = (TagMAP) tagmap;

                    //二级地区
                    districtbMap.put("district_b_id", tm.getTagDistrictBId() != null ? tm.getTagDistrictB().getId() : tm.getTagDistrictA().getId() + "");
                    districtbMap.put("district_b_value", tm.getTagDistrictBId() != null ? tm.getTagDistrictB().getTagValue() : tm.getTagDistrictA().getTagValue());

                    //二级分类
                    classifybMap.put("classify_b_id", tm.getTagClassifyBId() != null ? tm.getTagClassifyB().getId() : tm.getTagClassifyA().getId() + "");
                    classifybMap.put("classify_b_value", tm.getTagClassifyBId() != null ? tm.getTagClassifyB().getTagValue() : tm.getTagClassifyA().getTagValue() + "");

                    districtbList.add(districtbMap);
                    classifybList.add(classifybMap);

                    flag++;
                }
                merchantMap.put("merchant_district_b_list", districtbList);
                merchantMap.put("merchant_classify_b_list", classifybList);

                //返利优惠信息
                MerchantPreferential merchantPreferential = new MerchantPreferential();
                merchantPreferential.setState(Command.FBU_USERED_STATE);//启用
                merchantPreferential.setMerchantId(m.getId() + "");
                merchantPreferential.setPageSize(2);

                merchantPreferential = merchantPreferentialActionSupport.queryMerchantPreferentialByPager(merchantPreferential);
                List merchantPreferentialPagerList = merchantPreferential.getList();
                List merchantPreferentialList = new ArrayList();
                if (merchantPreferentialPagerList != null && !merchantPreferentialPagerList.isEmpty()) {
                    for (Object mp : merchantPreferentialPagerList) {
                        Map<String, Object> mpMap = new HashMap<String, Object>();
                        MerchantPreferential merchantP = (MerchantPreferential) mp;

                        mpMap.put("mp_id", merchantP.getId());
                        mpMap.put("mp_url", Command.IMAGE_SERVER_URL + merchantP.getPhotoUrl());
                        mpMap.put("mp_title", merchantP.getTxt1());
                        mpMap.put("mp_point_rate", merchantP.getPointRate());

                        merchantPreferentialList.add(mpMap);
                    }
                }

                merchantMap.put("merchant_mp_list", merchantPreferentialList);

                list.add(merchantMap);
            }
        }

        String jsonStr = JsonUtil.getJsonString4List(list);
        //log.info("首页换一批："+jsonStr);

        ajaxJson(jsonStr);
    }

    /**
     * 方法描述：热门搜索
     *
     * @param:
     * @return: json
     */
    public void findHotSearchesList() {
        log.info("查询热门搜索开始！");
        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();
        try {
            //热门搜索
            com.froad.client.searches.Pager searchesPager = new com.froad.client.searches.Pager();
            searchesPager.setPageNumber(1);
            searchesPager.setPageSize(4);
            searchesPager.setOrderBy("search_count*1");
            searchesPager.setOrderType(com.froad.client.searches.OrderType.DESC);
            searchesPager = searchesActionSupport.querySearchesByPager(searchesPager);
            List searchesPagerList = searchesPager.getList();
            if (searchesPagerList != null && searchesPagerList.size() > 0) {
                json.put("reno", Command.respCode_SUCCESS);
                Searches m = null;
                for (Object searches : searchesPagerList) {
                    m = (Searches) searches;
                    JSONObject orgs = new JSONObject();
                    orgs.put("id", m.getId());
                    orgs.put("tagClassifyAId", m.getTagClassifyAId() == null || "".equals(m.getTagClassifyAId()) ? "" : m.getTagClassifyAId());
                    orgs.put("tagDistrictBId", m.getTagDistrictBId() == null || "".equals(m.getTagDistrictBId()) ? "" : m.getTagDistrictBId());
                    orgs.put("tagClassifyAValue", m.getTagClassifyAValue() == null || "".equals(m.getTagClassifyAValue()) ? "" : m.getTagClassifyAValue());
                    orgs.put("tagDistrictBValue", m.getTagDistrictBValue() == null || "".equals(m.getTagDistrictBValue()) ? "" : m.getTagDistrictBValue());
                    array.add(orgs);
                }
                json.put("hotSearchesList", array);
            } else {
                json.put("reno", Command.respCode_FAIL);
                json.put("points", "0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ajaxJson(json.toString());
    }


    public Announcement getAnnouncement() {
        return announcement;
    }


    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }

    public AnnouncementActionSupport getAnnouncementActionSupport() {
        return announcementActionSupport;
    }

    public void setAnnouncementActionSupport(
            AnnouncementActionSupport announcementActionSupport) {
        this.announcementActionSupport = announcementActionSupport;
    }

    public AdvertActionSupport getAdvertActionSupport() {
        return advertActionSupport;
    }

    public void setAdvertActionSupport(AdvertActionSupport advertActionSupport) {
        this.advertActionSupport = advertActionSupport;
    }

    public List<Advert> getAdvertList() {
        return advertList;
    }

    public void setAdvertList(List<Advert> advertList) {
        this.advertList = advertList;
    }

    public MerchantActionSupport getMerchantActionSupport() {
        return merchantActionSupport;
    }

    public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
        this.merchantActionSupport = merchantActionSupport;
    }

    public MerchantPreferentialActionSupport getMerchantPreferentialActionSupport() {
        return merchantPreferentialActionSupport;
    }

    public void setMerchantPreferentialActionSupport(
            MerchantPreferentialActionSupport merchantPreferentialActionSupport) {
        this.merchantPreferentialActionSupport = merchantPreferentialActionSupport;
    }

    public SearchesActionSupport getSearchesActionSupport() {
        return searchesActionSupport;
    }

    public void setSearchesActionSupport(SearchesActionSupport searchesActionSupport) {
        this.searchesActionSupport = searchesActionSupport;
    }

    public StoreActionSupport getStoreActionSupport() {
        return storeActionSupport;
    }

    public void setStoreActionSupport(StoreActionSupport storeActionSupport) {
        this.storeActionSupport = storeActionSupport;
    }

    public List<Announcement> getAnnouncementList() {
        return announcementList;
    }

    public void setAnnouncementList(List<Announcement> announcementList) {
        this.announcementList = announcementList;
    }

    public List getGoodsGroupRackList() {
        return goodsGroupRackList;
    }

    public List getGoodsExchangeRackList() {
        return goodsExchangeRackList;
    }

    public GoodsExchangeRackActionSupport getGoodsExchangeRackActionSupport() {
        return goodsExchangeRackActionSupport;
    }

    public void setGoodsExchangeRackActionSupport(GoodsExchangeRackActionSupport goodsExchangeRackActionSupport) {
        this.goodsExchangeRackActionSupport = goodsExchangeRackActionSupport;
    }

    public GoodsGroupRackActionSupport getGoodsGroupRackActionSupport() {
        return goodsGroupRackActionSupport;
    }

    public void setGoodsGroupRackActionSupport(GoodsGroupRackActionSupport goodsGroupRackActionSupport) {
        this.goodsGroupRackActionSupport = goodsGroupRackActionSupport;
    }

    public void setGoodsGroupRackList(List goodsGroupRackList) {
        this.goodsGroupRackList = goodsGroupRackList;
    }

    public void setGoodsExchangeRackList(List goodsExchangeRackList) {
        this.goodsExchangeRackList = goodsExchangeRackList;
    }
}
