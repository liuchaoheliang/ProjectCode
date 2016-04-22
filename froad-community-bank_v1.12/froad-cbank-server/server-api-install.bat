@title 编译AND安装API
@ECHO off
color 0a

::设置maven参数
set MAVEN_OPTS= -Xms256M -Xmx1024M -XX:PermSize=64M -XX:MaxPermSize=128M

:: api打包的版本号
set jar_version=0.0.1-SNAPSHOT

:: api模块名
set api_module=froad-cbank-server-api

:: 要打包的模块, 各模块之间以空格隔开
set module=froad-cbank-server-common froad-cbank-server-activities froad-cbank-server-goods froad-cbank-server-support froad-cbank-server-merchant froad-cbank-server-order froad-cbank-server-qrcode froad-cbank-server-support froad-cbank-server-bank froad-cbank-server-fallow froad-cbank-server-finity froad-cbank-server-report froad-cbank-server-coremodule

:: 每个模块的thrift文件所在的目录
set thrift_pkg=src\main\java\com\froad\thrift

echo API的jar包名:%api_module%-%jar_version%.jar


echo ****************************检查相关命令是否存在****************************
::for %%a in (notepad.exe calc.exe explorer.exe) do echo 查找%%a的第一个结果是：%%~$PATH:a
::for %%a in (thrift.exe) do echo 查找%%a的第一个结果是：%%~$PATH:a
for %%a in (svn.exe) do (
	if not EXIST %%~$PATH:a (
		echo %%a不存在于环境变量Path中,请检查是否安装svn命令行工具
		goto end
	)
)
for %%a in (thrift.exe) do (
	if not EXIST %%~$PATH:a (
		echo %%a不存在于环境变量Path中,请检查是否安装thrift编译环境
		goto end
	)
)
for %%a in (mvn.bat) do (
	if not EXIST %%~$PATH:a (	
		for %%a in (mvn.cmd) do (
			if not EXIST %%~$PATH:a (
				echo %%a不存在于环境变量Path中,请检查是否安装maven工具
				goto end
			)
		)
	)
)

set currDir=%cd%

:: 意思设置变量本地化并启用变量延迟扩展功能，则在需要延迟扩展的地方，用一对! 代替一对%取变量值即可
setlocal enabledelayedexpansion 
for %%m in (%module%) do (
	if exist %%m (
		echo 正在编译模块[%%m]...
		echo ****************************svn更新目录:%currDir%\%%m\%thrift_pkg%\****************************		
		call svn update %currDir%\%%m\%thrift_pkg%\  -r HEAD --force
		
		for /f "delims=" %%a in ('dir /a-d/b/s "%currDir%\%%m\%thrift_pkg%\*.thrift"') do ( 
			echo 正在转换[%%a]...
			call thrift -r -gen java %%a
			if errorlevel 1 (
				echo 转换[%%a]成java出错!
				goto end
			)
			rem ping -n 1 127.0.0.1>nul
		)
		echo 模块[%%m]编译完毕...
		echo 
	) else (
		echo 目录[%currDir%\%%m]不存在...
	)
)

echo ****************************thrift文件转换成java文件完毕****************************

echo ****************************svn更新api\****************************
call svn update %currDir%\%api_module%\ -r HEAD --force


echo 复制编译后的thrift文件到api中相关路径
xcopy %currDir%\gen-java\com %currDir%\%api_module%\src\main\java\com\ /s /e /y

echo 删除gen-java目录
rd /s /q gen-java
::move /y "gen-java\com" %currDir%\%api_module%\src\main\java

cd %currDir%\%api_module%\

:: svn提交src文件夹
echo ****************************svn提交src文件夹****************************
call svn update %currDir%\%api_module%\ -r HEAD --force
::call svn add src/main/java

REM 显示目录下未commit各文件状态到临时文件，每个状态一行
svn status %currDir%\%api_module%\ > %currDir%\svn.txt
REM 解析临时文件，对各状态进行解析，新建的目录进行add
for /f "delims=" %%i in (%currDir%\svn.txt) do (
	call %currDir%\svn_commit_one.bat "%%i"	
)
del %currDir%\svn.txt

call svn commit -m "编译脚本自动提交" src/main/java

echo 当前目录:%cd%

:: 使用maven编译api
echo ****************************使用maven编译api****************************

:: mvn -Dfile.encoding=UTF-8 compile

echo ********************执行mvn clean source:jar install编译项目*************************
rem call mvn -Dfile.encoding=UTF-8 clean source:jar install
call mvn -Dfile.encoding=UTF-8 clean install
echo ********************执行mvn deploy上传到私服*************************
call mvn deploy
goto end

::cd target
::echo **************************执行maven私服上传*****************************
::call mvn install:install-file -Dfile=./%api_module%-%jar_version%.jar -DgroupId=com.froad.cbank -DartifactId=%api_module% -Dversion=%jar_version% -Dpackaging=jar
::call mvn deploy:deploy-file  -DgroupId=com.froad.cbank  -DartifactId=%api_module%  -Dversion=%jar_version% -Dpackaging=jar  -Dfile=%api_module%-%jar_version%.jar -Durl=http://10.43.1.23:8081/content/repositories/froad-snapshot -DrepositoryId=froad-snapshot

:end
echo
echo ************API打包上传完毕，请按任意键退出****************
pause>nul

