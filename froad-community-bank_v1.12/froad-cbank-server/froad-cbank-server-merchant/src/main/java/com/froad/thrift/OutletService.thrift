/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入OutletVo.thrift文件 */
include "OutletVo.thrift"

/* 引入OutletDetailVo.thrift文件 */
include "OutletDetailVo.thrift"

/* 引入OutletDetailSimpleInfoVo.thrift文件 */
include "OutletDetailSimpleInfoVo.thrift"

/* 引入OutletDetailSimpleVo.thrift文件 */
include "OutletDetailSimpleVo.thrift"


/* 引入OutletServiceVo.thrift文件 */
include "OutletServiceVo.thrift"

/* 引入OutletMongoInfoVo.thrift文件 */
include "OutletMongoInfoVo.thrift"

/* 引入OutletTempVo.thrift*/
include "OutletTempVo.thrift"

/* 引入MerchantServiceVo.thrift文件 */
include "OutletTempServiceVo.thrift"

/* 引入OutletPreferVo.thrift */
include "OutletPreferVo.thrift"

namespace java com.froad.thrift.service


/**
 * OutletService
 */
service OutletService extends BizMonitor.BizMonitorService {

    /**
     * 增加 Outlet
     * @param outlet
     * @return outletId 门店编号
     */
    OutletServiceVo.OutletAddVoRes addOutlet(1:Common.OriginVo originVo, 2:OutletVo.OutletVo outletVo);
    
    /**
     * 增加 Outlet
     * @param outlet
     * @return list<OutletServiceVo.OutletAddVoRes>    结果集
     */
    list<OutletServiceVo.OutletAddVoRes> addOutletByBatch(1:Common.OriginVo originVo, 2:list<OutletVo.OutletVo> outletVoList);

    /**
     * 删除 Outlet
     * @param outlet
     * @return boolean    
     */
    Common.ResultVo deleteOutlet(1:Common.OriginVo originVo, 2:OutletVo.OutletVo outletVo);
    
    /**
     * 物理删除(添加门店错误时回滚用) Outlet
     * @param outletId
     * @return boolean    
     */
    Common.ResultVo removeOutlet(1:Common.OriginVo originVo, 2:string outletId);

    /**
     * 修改 Outlet
     * @param outlet
     * @return boolean    
     */
    Common.ResultVo updateOutlet(1:Common.OriginVo originVo, 2:OutletVo.OutletVo outletVo);


	 /** 
	 * 根据门店id查询单个 Outlet
     * @param outletId
     * @return OutletVo
     */
	OutletVo.OutletVo getOutletByOutletId(1:string outletId);

    /**
     * 查询 Outlet
     * @param outlet
     * @return List<OutletVo>
     */
    list<OutletVo.OutletVo> getOutlet(1:OutletVo.OutletVo outletVo);
    
    /**
     * 统计 Outlet
     * @param outlet
     * @return int
     */
    i32 countOutlet(1:OutletVo.OutletVo outletVo);

	/**
     * 查询机构对应的虚拟门店信息
     * @param outlet
     * @return OutletVo
     */
    OutletVo.OutletVo getBankOutlet(1:string client_id, 2:string org_code);
    
    /**
     * 查询提货网点(预售用到)
     * @param outlet
     * @return List<OutletVo>
     */
    list<OutletVo.OutletVo> getSubBankOutlet(1:string client_id, 2:string org_code);

    /**
     * 分页查询 Outlet
     * @param outlet
     * @return OutletPageVoRes
     */
    OutletServiceVo.OutletPageVoRes getOutletByPage(1:Common.PageVo page, 2:OutletVo.OutletVo outletVo);
    
    /**
     * 查询 Outlet详情
     * @param outletId
     * @return OutletDetailVo
     */
    OutletDetailVo.OutletDetailVo getOutletDetail(1:string outletId);
    
    /**
     * 分页查询 OutletDetail
     * @param outletDetail
     * @return OutletDetailPageVoRes
     */
    OutletServiceVo.OutletDetailPageVoRes getOutletDetailByPage(1:Common.PageVo page, 2:OutletDetailVo.OutletDetailVo outletDetailVo)
    
    
    /**
     * 分页查询人气(收藏数)最好的 Outlet
     * @param outlet
     * @return OutletPageVoRes
     */
    /*OutletServiceVo.OutletPageVoRes getHottestOutletByPage(1:Common.PageVo page, 2:OutletVo.OutletVo outletVo);*/
    
     /**
     * 分页查询人气(收藏数)最好的 Outlet(个人H5用到)
     * @param outlet
     * @return OutletDetailPageVoRes
     */
    OutletServiceVo.OutletDetailSimpleInfoPageVoRes getHottestOutletDetailByPage(1:Common.PageVo page, 2:OutletDetailVo.OutletDetailVo outletDetailVo);
    
    /**
     * 分页搜索附近的 OutletDetail(个人H5用到)
     * @param outletDetail 门店查询条件
     * @param pageSize 每页显示多少条
     * @param distance 查询多少距离以内的门店
     * @param orderBy 排序 1-人气，2-距离
     * @return OutletDetailVo
     */
    OutletServiceVo.OutletDetailSimpleInfoPageVoRes getNearbyOutlet(1:Common.PageVo page, 2:OutletDetailVo.OutletDetailVo outletDetailVo, 3:double distance,4:i32 orderBy);
    
    
    /**
     * 分页搜索附近的 OutletDetailSimpleVo(个人H5特惠商品用)
     * @param outletDetail 门店查询条件
     * @param pageSize 每页显示多少条
     * @param distance 查询多少距离以内的门店
     * @return OutletDetailVo
     */
    OutletServiceVo.OutletDetailSimplePageVoRes getNearbyOutletByPage(1:Common.PageVo page, 2:OutletDetailVo.OutletDetailVo outletDetailVo, 3:double distance,4:i32 skip);
    
    
    /**
     * 根据门店id集合查询详情
     * @param outletIdList 门店id集合
     * @return OutletDetailVo
     */
    list<OutletDetailVo.OutletDetailVo> getOutletDetailbyOutletIdList(1:list<string> outletIdList)
    
    
    /**
     * 根据经纬度和商户ID查询门店信息列表和相对距离
     * @param latitude
     * @param longitude
     * @param merchantId
     * @return List<OutletVo>
     */
    OutletServiceVo.OutletMongoInfoPageVoRes getOutletMongoInfoVoByPage(1:Common.PageVo page,2:double longitude, 3:double latitude, 4:string merchantId);


      /**
     * 同步白名单操作时，根据客户端id和商户id同步商户门店信息.
     * @param merchantId 商户id
     * @param merchantId 门店id
     * @param clientId    客户端id
     * @param isSynSucc  0-同步成功，1-同步失败
     * @param synType 0-同步通知，1-审核通知
     * @return Common.ResultVo
     */    
     Common.ResultVo syncOutletInfo(1:OutletVo.OutletVo outletVo,2:string isSynSucc,3:string synType);    
    
    
    /**
     * 门店管理点击提交审核触发事件实现接口(录入审核：outlet表录入审核状态=非审核通过状态),(编辑审核：outlet表录入审核状态为审核通过)
     * @param outletId  门店Id
     * @param auditId   审核流水号
     * @return boolean    
     */
    Common.ResultVo commitAuditOutlet(1:string outletId,2:string auditId);
    
     /**
     * 门店录入审核通过后，点击修改操作接口.
     * @param outletTempVo 门店临时vo
     * @return 
     */
    OutletTempServiceVo.OutletTempAddVoRes saveOutletTempEditOutlet(1:Common.OriginVo originVo, 2:OutletTempVo.OutletTempVo outletTempVo);


    /**
     * 查询一个门店临时信息.
     * @param outletId 门店Id
     * @return OutletTempVo 
     */
    OutletTempVo.OutletTempVo getOutletModifyInfos(1:string outletId);    
    
    /**
     * 银行管理平台审核箱中门店审核根据审核流水号和门店id查询一个门店临时信息.
     * @param outletId 门店Id
     * @param auditId 审核流水号
     * @return OutletTempVo 
     */
    OutletTempVo.OutletTempVo getOutletModifyInfoByAuditBox(1:string outletId,2:string auditId);   
    
    /**
     * 门店惠付分页查询 OutletPreferVo
     * @param OutletPreferVo
     * @return OutletPreferPageVoRes
     */
    OutletServiceVo.OutletPreferPageVoRes getOutletPreferByPage(1:Common.PageVo page, 2:OutletPreferVo.OutletPreferVo outletPreferVo);
    
    /**
     * 门店报表导出
     * @param outletPreferVo
     * @return Common.ExportResultRes
     */
    Common.ExportResultRes getOutletExport(1:OutletPreferVo.OutletPreferVo outletPreferVo);
}