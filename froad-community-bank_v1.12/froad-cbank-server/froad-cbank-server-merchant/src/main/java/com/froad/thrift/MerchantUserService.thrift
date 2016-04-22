/* 引入BizMonitor.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"

/* 引入Common.thrift.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入MerchantUserVo.thrift文件 */
include "MerchantUserVo.thrift"

/* 引入MerchantUserServiceVo.thrift文件 */
include "MerchantUserServiceVo.thrift"

namespace java com.froad.thrift.service


/**
 * MerchantUserService
 */
service MerchantUserService extends BizMonitor.BizMonitorService {

    /**
     * 增加 MerchantUser
     * @param merchantUser
     * @return MerchantUserAddVoRes
     */
    MerchantUserServiceVo.MerchantUserAddVoRes addMerchantUser(1:Common.OriginVo originVo, 2:MerchantUserVo.MerchantUserVo merchantUserVo);

    /**
     * 删除 MerchantUser
     * @param merchantUser
     * @return boolean    
     */
    Common.ResultVo deleteMerchantUser(1:Common.OriginVo originVo, 2:MerchantUserVo.MerchantUserVo merchantUserVo);

    /**
     * 修改 MerchantUser
     * @param merchantUser
     * @return boolean    
     */
    Common.ResultVo updateMerchantUser(1:Common.OriginVo originVo, 2:MerchantUserVo.MerchantUserVo merchantUserVo);

	/**
     * 查询 MerchantUser
     * @param id
     * @return MerchantUser    
     */
    MerchantUserVo.MerchantUserVo getMerchantUserById(1:i64 id);

	/**
	 * 用户登录
	 * @param originVo
	 * @param username
	 * @param password
	 * @return MerchantUserLoginVoRes
	 */
	MerchantUserServiceVo.MerchantUserLoginVoRes login(1:Common.OriginVo originVo, 2:string username, 3:string password);
	
	/**
	 * 用户登出
	 * @param token
	 * @return boolean
	 */
	bool logout(1:Common.OriginVo originVo, 2:string token)

    /**
     * 查询 MerchantUser
     * @param merchantUser
     * @return List<MerchantUserVo>
     */
    list<MerchantUserVo.MerchantUserVo> getMerchantUser(1:MerchantUserVo.MerchantUserVo merchantUserVo);

    /**
     * 分页查询 MerchantUser
     * @param merchantUser
     * @return List<MerchantUserVo>
     */
    MerchantUserServiceVo.MerchantUserPageVoRes getMerchantUserByPage(1:Common.PageVo page, 2:MerchantUserVo.MerchantUserVo merchantUserVo);
    
    /**
     * 校验 token
     * @param token值 
     * @param userId
     * @return MerchantUserCheckVoRes
     */
    MerchantUserServiceVo.MerchantUserCheckVoRes tokenCheck(1:Common.OriginVo originVo, 2:string token, 3:i64 userId);
    
    /**
     * 查询 MerchantUserList
     * @param merchantUser
     * @return List<MerchantUserVo>
     */
    list<MerchantUserVo.MerchantUserVo> getMerchantUserByRoleIdAndMerchantIds(1:i64 roleId, 2:list<string> merchantIdList);
    
    /**
     * 查询 MerchantUser
     * @param username
     * @return MerchantUser    
     */
    MerchantUserVo.MerchantUserVo getMerchantUserByUsername(1:string username,2:Common.OriginVo originVo);
    
    
    /**
     * 统计 登录错误次数
     * @param clientId
     * @return merchantId
     * @return userId
     */
    i32 queryErrorCount(1:string clientId,2:string merchantId,3:i64 userId);
    
    
    
	/**
	 * 获取用户登录失败次数.
	 * @param originVo
	 * @param username	 
	 * @return MerchantUserLoginVoRes
	 */
	MerchantUserServiceVo.MerchantUserLoginVoRes getLoginFailureCount(1:Common.OriginVo originVo, 2:string username);
}