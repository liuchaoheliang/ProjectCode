/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入BizInstanceAttrValueVo.thrift文件 */
include "BizInstanceAttrValueVo.thrift"

namespace java com.froad.thrift.vo


struct BizInstanceAttrValuePageVoRes {
	1: optional Common.PageVo page;
	2: optional list<BizInstanceAttrValueVo.BizInstanceAttrValueVo> bizInstanceAttrValueVoList,
}

