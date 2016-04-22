namespace java com.froad.thrift.vo


/**
 * ProcessVo
 */
struct ProcessVo {
    /** id */
    1: optional i64 id,

    /** createTime */
    2: optional i64 createTime,

    /** updateTime */
    3: optional i64 updateTime,

    /** clientId */
    4: optional string clientId,

    /** processId */
    5: optional string processId,

    /** parentProcessId */
    6: optional string parentProcessId,

    /** name */
    7: optional string name,

    /** displayName */
    8: optional string displayName,

    /** type */
    9: optional string type,

    /** typeDetail */
    10: optional string typeDetail,

    /** status */
    11: optional string status,

    /** version */
    12: optional i32 version,

    /** creator */
    13: optional string creator

}
