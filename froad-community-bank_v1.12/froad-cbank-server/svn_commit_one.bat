REM 读取参数
SET SVN_PARA=%1

REM 读取第一位状态
SET SVN_TYPE=%SVN_PARA:~1,1%

REM 读取后续状态的文件名
SET SVN_FILE=%SVN_PARA:~9%

echo "SVN_PARA--->[%SVN_PARA%]    SVN_TYPE--->[%SVN_TYPE%]   SVN_FILE--->[%SVN_FILE%]"
REM 如果状态是?，则目录是未绑定状态，先添加
IF "%SVN_TYPE%" == "?" (
	echo 添加%SVN_FILE%文件
	svn add %SVN_FILE%
)
