namespace java com.froad.thrift.vo


/**
 * 审核任务订单表
 */
struct AuditTaskVo {

	/** 主键ID */
    1:  optional i64     id;
    /** 创建时间 */
    2:  optional i64     createTime;
    /** 客户端ID */
    3:  optional string clientId;
    /** 1-商户审核记录商户id(merchant_id) */
    4:  optional string  thridId;
    /** 创建人，银行登录用户名 */
    5:  optional string  userName;
    /** 审核流水号 */
    6:  optional string  auditId;
    /** 提交审核人所属机构 */
    7:  optional string  orgCode;
    /** 审核类型：1-商户审核*/
    8:  optional string  type;
    /** 名称：如果为商户审核则是商户名称 */
    9:  optional string  name;
    /** 业务号码：如果为商户审核则是商户的营业执照号。 */
    10: optional string    busCode;
    /** 审核开始机构*/
    11: optional string  auditStartOrgCode;
    /** 审核结束机构 */
    12: optional string  auditEndOrgCode;
    /** 当前审核机构 */
    13: optional string   auditOrgCode;
    /** 最终审核状态：0-审核中1-审核通过2-未通过*/
    14: optional string  auditState;
    /** 0-在途  audit_state=0审核中1-归档  audit_state=1or 2 */
    15: optional string  state;
    /** 归档时间 */
    16: optional i64  auditTime;
}
