@title thrift�ļ�ת����java�ļ�
@ECHO off
rem set "exe_dir=C:\Windows\thrift.exe"
set currDir=%cd%
setlocal enabledelayedexpansion 
for /f "delims=" %%a in ('dir /a-d/b/s "%currDir%\*.thrift"') do ( 

    echo thrift -r -gen java %%a
 thrift -r -gen java %%a
rem ping -n 1 127.0.0.1>nul
)
echo thrift�ļ�ת����java�ļ����
pause
exit


rem java�ļ�Ŀ¼
set thriftDir=%currDir%\gen-java\com\froad\thrift\
cd %thriftDir% 
set CLASSPATH=.;F:\java\repository\org\apache\thrift\libthrift\0.9.2\libthrift-0.9.2.jar;
for /f "delims=" %%b in ('dir /a-d/b/s "%currDir%\*.java"') do ( 
    javac %%b
)
echo ����java�ļ����

cd %thriftDir%\bin
set jarName=froad-cbank-server-api-0.0.1-SNAPSHOT.jar
jar -cvfm jarName com 
copy jarName %currDir%\
del -f jarName
del -f %thriftDir%\bin
echo ���jar�ļ����

pause
exit
