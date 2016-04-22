@title thrift文件转换成java文件
@ECHO off
rem set "exe_dir=C:\Windows\thrift.exe"
set currDir=%cd%
setlocal enabledelayedexpansion 
for /f "delims=" %%a in ('dir /a-d/b/s "%currDir%\*.thrift"') do ( 

    echo thrift -r -gen java %%a
 thrift -r -gen java %%a
rem ping -n 1 127.0.0.1>nul
)
echo thrift文件转换成java文件完毕
pause
exit


rem java文件目录
set thriftDir=%currDir%\gen-java\com\froad\thrift\
cd %thriftDir% 
set CLASSPATH=.;F:\java\repository\org\apache\thrift\libthrift\0.9.2\libthrift-0.9.2.jar;
for /f "delims=" %%b in ('dir /a-d/b/s "%currDir%\*.java"') do ( 
    javac %%b
)
echo 编译java文件完毕

cd %thriftDir%\bin
set jarName=froad-cbank-server-api-0.0.1-SNAPSHOT.jar
jar -cvfm jarName com 
copy jarName %currDir%\
del -f jarName
del -f %thriftDir%\bin
echo 打包jar文件完毕

pause
exit
