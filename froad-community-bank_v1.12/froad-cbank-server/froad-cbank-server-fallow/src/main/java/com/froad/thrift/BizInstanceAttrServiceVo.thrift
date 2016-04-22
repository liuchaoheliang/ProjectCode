/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入BizInstanceAttrVo.thrift文件 */
include "BizInstanceAttrVo.thrift"

namespace java com.froad.thrift.vo


struct BizInstanceAttrPageVoRes {
	1: optional Common.PageVo page;
	2: optional list<BizInstanceAttrVo.BizInstanceAttrVo> bizInstanceAttrVoList,
}

