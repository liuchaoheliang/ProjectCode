/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入InstanceVo.thrift文件 */
include "InstanceVo.thrift"

namespace java com.froad.thrift.vo


struct InstancePageVoRes {
	1: optional Common.PageVo page;
	2: optional list<InstanceVo.InstanceVo> instanceVoList,
}

