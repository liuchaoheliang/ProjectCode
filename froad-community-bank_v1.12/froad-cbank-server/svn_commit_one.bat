REM ��ȡ����
SET SVN_PARA=%1

REM ��ȡ��һλ״̬
SET SVN_TYPE=%SVN_PARA:~1,1%

REM ��ȡ����״̬���ļ���
SET SVN_FILE=%SVN_PARA:~9%

echo "SVN_PARA--->[%SVN_PARA%]    SVN_TYPE--->[%SVN_TYPE%]   SVN_FILE--->[%SVN_FILE%]"
REM ���״̬��?����Ŀ¼��δ��״̬�������
IF "%SVN_TYPE%" == "?" (
	echo ���%SVN_FILE%�ļ�
	svn add %SVN_FILE%
)
