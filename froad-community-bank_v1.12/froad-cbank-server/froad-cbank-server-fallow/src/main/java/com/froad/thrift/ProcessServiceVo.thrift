/* 引入Common.thrift文件 */
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"

/* 引入ProcessVo.thrift文件 */
include "ProcessVo.thrift"

namespace java com.froad.thrift.vo


struct ProcessPageVoRes {
	1: optional Common.PageVo page;
	2: optional list<ProcessVo.ProcessVo> processVoList,
}

