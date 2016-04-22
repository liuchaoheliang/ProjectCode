/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入ProcessNodeVo.thrift文件 */
include "ProcessNodeVo.thrift"

namespace java com.froad.thrift.vo


struct ProcessNodePageVoRes {
	1: optional Common.PageVo page;
	2: optional list<ProcessNodeVo.ProcessNodeVo> processNodeVoList,
}

