@title thrift�ļ�ת����java�ļ�
@ECHO off

echo ==========thrift to java (begin)================

setlocal enabledelayedexpansion 
for /f "delims=" %%a in ('dir /a-d/b/s *.thrift') do ( 
	thrift -r -gen java %%a
	echo thrift -r -gen java %%a
)
echo ==========thrift to java (end)================

rmdir /S/Q gen-java

pause
