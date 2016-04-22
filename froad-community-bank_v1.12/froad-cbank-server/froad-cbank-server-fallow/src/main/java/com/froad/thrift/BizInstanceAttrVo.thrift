namespace java com.froad.thrift.vo


/**
 * BizInstanceAttrVo
 */
struct BizInstanceAttrVo {
    1: optional i64 id,    /** id */
    2: optional i64 createTime,    /** createTime */
    3: optional i64 updateTime,    /** updateTime */
    4: optional string clientId,    /** clientId */
    5: optional string name,    /** name */
    6: optional string fieldName    /** fieldName */
}
