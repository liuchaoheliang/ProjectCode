namespace java com.froad.thrift.vo


/**
 * InstanceVo
 */
struct InstanceVo {
    1: optional i64 id,    /** id */
    2: optional i64 createTime,    /** createTime */
    3: optional i64 updateTime,    /** updateTime */
    4: optional string clientId,    /** clientId */
    5: optional string instanceId,    /** instanceId */
    6: optional string processId,    /** processId */
    7: optional string creator,    /** creator */
    8: optional i64 expireTime,    /** expireTime */
    9: optional string lastUpdator,    /** lastUpdator */
    10: optional i32 orderValue,    /** orderValue */
    11: optional i32 version    /** version */
}
