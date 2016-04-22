/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantVo.thrift文件 */
include "MerchantVo.thrift"

/* 引入MerchantServiceVo.thrift文件 */
include "MerchantServiceVo.thrift"

/* 引入MerchantDetailVo.thrift文件 */
include "MerchantDetailVo.thrift"

/* 引入CategoryInfoVo.thrift文件 */
include "CategoryInfoVo.thrift"

/* 引入TypeInfoVo.thrift文件 */
include "TypeInfoVo.thrift"

namespace java com.froad.thrift.service


/**
 * MerchantService
 */
service MerchantService extends BizMonitor.BizMonitorService {

    /**
     * 增加 Merchant
     * @param merchantVoReq 商户vo
     * @return merchantId 商户编号
     */
    MerchantServiceVo.MerchantAddVoRes addMerchant(1:Common.OriginVo originVo, 2:MerchantServiceVo.MerchantVoReq merchantVoReq);
    
    
    /**
     * 批量增加 Merchant
     * @param merchant
     * @return list<MerchantServiceVo.MerchantAddVoRes>    结果集
     */
    list<MerchantServiceVo.MerchantAddVoRes> addMerchantByBatch(1:Common.OriginVo originVo, 2:list<MerchantServiceVo.MerchantVoReq> merchantVoList);

    /**
     * 删除(逻辑删除,解约用) Merchant
     * @param merchant
     * @return boolean    
     */
    Common.ResultVo deleteMerchant(1:Common.OriginVo originVo, 2:MerchantVo.MerchantVo merchantVo);
    
    /**
     * 物理删除(数据回滚用) Merchant
     * @param merchantId
     * @return boolean    
     */
    Common.ResultVo removeMerchant(1:Common.OriginVo originVo, 2:string merchantId);

    /**
     * 修改 Merchant
     * @param merchantVoReq
     * @return boolean    
     */
    Common.ResultVo updateMerchant(1:Common.OriginVo originVo, 2:MerchantServiceVo.MerchantVoReq merchantVoReq);
    
    /**
     * 禁用指定商户id()
     * @param merchantId 商户id
     * @return boolean   
     */
    Common.ResultVo disableMerchantByMerchantId(1:Common.OriginVo originVo, 2:MerchantVo.MerchantVo merchantVo);
    
    /**
     * 禁用指定客户端的机构下的所有商户()
     * @param client_id 客户端id
     * @param org_code 机构码
     * @return boolean   
     */
    Common.ResultVo disableMerchant(1:Common.OriginVo originVo, 2:string client_id, 3:string org_code);
    
    /**
     * 启用商户
     * @param client_id 客户端id
     * @param merchantVo 商户对象(仅仅作为查询条件)
     * @return ResultVo   
     */
    Common.ResultVo enableMerchant(1:Common.OriginVo originVo, 2:MerchantVo.MerchantVo merchantVo);
    
    /**
     * 启用商户
     * @param merchantId 商户id
     * @return ResultVo   
     */
    Common.ResultVo enableMerchantByMerchantId(1:Common.OriginVo originVo, 2:string merchantId);
    
    /**
     * 续约商户
     * @param merchantId 商户id
     * @param contractEndtime 续约到期时间
     * @return boolean   
     */
    Common.ResultVo extensionMerchant(1:Common.OriginVo originVo, 2:string merchantId, 3:i64 contractEndtime);
    
    /**
     * 查询 Merchant详情
     * @param merchantId
     * @return MerchantDetailVo
     */
    MerchantDetailVo.MerchantDetailVo getMerchantDetail(1:string merchantId);
    
    /**
     * 查询 Merchant的分类信息
     * @param merchantId
     * @return CategoryInfoVo
     */
    list<CategoryInfoVo.CategoryInfoVo> getMerchantCategoryInfo(1:string merchantId);
    
    /**
     * 查询 Merchant的类型信息
     * @param merchantId
     * @return CategoryInfoVo
     */
    list<TypeInfoVo.TypeInfoVo> getMerchantTypeInfo(1:string merchantId);
    
    /**
     * 查询 Merchant
     * @param merchantId
     * @return MerchantVo
     */
    MerchantVo.MerchantVo getMerchantByMerchantId(1:string merchantId);

    /**
     * 查询 Merchant
     * @param merchant
     * @return List<MerchantVo>
     */
    list<MerchantVo.MerchantVo> getMerchant(1:MerchantVo.MerchantVo merchantVo);
    
    
    /**
     * 查询一个商户信息
     * @param merchantVo
     * @return MerchantVo
     */
    MerchantVo.MerchantVo getOneMerchant(1:MerchantVo.MerchantVo merchantVo);
    
    
    /**
     * 统计 Merchant
     * @param merchant
     * @return int
     */
    i32 countMerchant(1:MerchantVo.MerchantVo merchantVo);


    /**
     * 分页查询 Merchant
     * @param merchant
     * @return List<MerchantVo>
     */
    MerchantServiceVo.MerchantPageVoRes getMerchantByPage(1:Common.PageVo page, 2:MerchantVo.MerchantVo merchantVo);
    
    /**
     * 分页查询 Merchant详情
     * @param page
     * @param merchantDetailVo
     * @return MerchantDetailPageVoRes
     */
    MerchantServiceVo.MerchantDetailPageVoRes getMerchantDetailByPage(1:Common.PageVo page, 2:MerchantDetailVo.MerchantDetailVo merchantDetailVo);
    
    
    /**
     * 根据商户id集合查询详情
     * @param merchantIdList
     * @return list<MerchantDetailVo.MerchantDetailVo>
     */
    list<MerchantDetailVo.MerchantDetailVo> getMerchantDetailbyMerchantIdList(1:list<string> merchantIdList);
    
    /**
     * 根据客户端id和商户id集合商户简称(key为商户id,value为商户名称)
     * @param clientId
     * @param merchantIdList
     * @return map<string, string>
     */
    map<string, string> getMerchantNamebyMerchantIdList(1: string clientId, 2:list<string> merchantIdList);
    
    
    /**
     * 修改 Merchant 审核通过后的修改方法
     * @param merchantVoReq
     * @return boolean    
     */
    Common.ResultVo updateMerchantByAuditThrough(1:Common.OriginVo originVo, 2:MerchantServiceVo.MerchantVoReq merchantVoReq);


    /**
     * 商户报表导出
     * @param merchant
     * @return Common.ExportResultRes
     */
    Common.ExportResultRes getMerchantExport(1:MerchantVo.MerchantVo merchantVo);
    
    
    /**
     * 同步白名单操作时，根据客户端id和商户id同步商户信息.
     * @param merchantId 商户id
     * @param clientId    客户端id
     * @param isSynSucc  0-同步成功，1-同步失败
     * @param synType 0-同步通知，1-审核通知
     * @return Common.ResultVo
     */    
     Common.ResultVo syncMerchantInfo(1:string merchantId,2:string clientId,3:string isSynSucc,4:string synType);    
   }