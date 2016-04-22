namespace java com.froad.thrift.vo


/**
 * 商户修改审核变更表
 */
struct AuditProcessVo {

	/** 主键ID */
    1:  optional i64     id;
    /** 创建时间 */
    2:  optional i64     createTime;
    /** 客户端ID */
    3:  optional string clientId;
    /** 审核流水号 */
    4:  optional i64  auditId;
    /** 任务流水号 */
    5:  optional string  taskId;
    /** 审核人所属机构*/
    6:  optional string  orgCode;
    /** 审核人所属机构名称 */
    7:  optional i64     orgName;
    /** 审核步骤(0-初审/1-复审)*/
    8:  optional string  auditStage;
    /** 审核时间 */
    9:  optional i64  auditTime;
    /** 审核备注 */
    10: optional string    auditComment;
    /** 审核人员*/
    11: optional string  auditStaff;
    /** 复核人员 */
    12: optional string  reviewStaff;
    /** 0-待审核1-审核通过2-未通过 */
    13: optional string   auditState;  
}
