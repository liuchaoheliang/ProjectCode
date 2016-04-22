@title ����AND��װAPI
@ECHO off
color 0a

::����maven����
set MAVEN_OPTS= -Xms256M -Xmx1024M -XX:PermSize=64M -XX:MaxPermSize=128M

:: api����İ汾��
set jar_version=0.0.1-SNAPSHOT

:: apiģ����
set api_module=froad-cbank-server-api

:: Ҫ�����ģ��, ��ģ��֮���Կո����
set module=froad-cbank-server-common froad-cbank-server-activities froad-cbank-server-goods froad-cbank-server-support froad-cbank-server-merchant froad-cbank-server-order froad-cbank-server-qrcode froad-cbank-server-support froad-cbank-server-bank froad-cbank-server-fallow froad-cbank-server-finity froad-cbank-server-report froad-cbank-server-coremodule

:: ÿ��ģ���thrift�ļ����ڵ�Ŀ¼
set thrift_pkg=src\main\java\com\froad\thrift

echo API��jar����:%api_module%-%jar_version%.jar


echo ****************************�����������Ƿ����****************************
::for %%a in (notepad.exe calc.exe explorer.exe) do echo ����%%a�ĵ�һ������ǣ�%%~$PATH:a
::for %%a in (thrift.exe) do echo ����%%a�ĵ�һ������ǣ�%%~$PATH:a
for %%a in (svn.exe) do (
	if not EXIST %%~$PATH:a (
		echo %%a�������ڻ�������Path��,�����Ƿ�װsvn�����й���
		goto end
	)
)
for %%a in (thrift.exe) do (
	if not EXIST %%~$PATH:a (
		echo %%a�������ڻ�������Path��,�����Ƿ�װthrift���뻷��
		goto end
	)
)
for %%a in (mvn.bat) do (
	if not EXIST %%~$PATH:a (	
		for %%a in (mvn.cmd) do (
			if not EXIST %%~$PATH:a (
				echo %%a�������ڻ�������Path��,�����Ƿ�װmaven����
				goto end
			)
		)
	)
)

set currDir=%cd%

:: ��˼���ñ������ػ������ñ����ӳ���չ���ܣ�������Ҫ�ӳ���չ�ĵط�����һ��! ����һ��%ȡ����ֵ����
setlocal enabledelayedexpansion 
for %%m in (%module%) do (
	if exist %%m (
		echo ���ڱ���ģ��[%%m]...
		echo ****************************svn����Ŀ¼:%currDir%\%%m\%thrift_pkg%\****************************		
		call svn update %currDir%\%%m\%thrift_pkg%\  -r HEAD --force
		
		for /f "delims=" %%a in ('dir /a-d/b/s "%currDir%\%%m\%thrift_pkg%\*.thrift"') do ( 
			echo ����ת��[%%a]...
			call thrift -r -gen java %%a
			if errorlevel 1 (
				echo ת��[%%a]��java����!
				goto end
			)
			rem ping -n 1 127.0.0.1>nul
		)
		echo ģ��[%%m]�������...
		echo 
	) else (
		echo Ŀ¼[%currDir%\%%m]������...
	)
)

echo ****************************thrift�ļ�ת����java�ļ����****************************

echo ****************************svn����api\****************************
call svn update %currDir%\%api_module%\ -r HEAD --force


echo ���Ʊ�����thrift�ļ���api�����·��
xcopy %currDir%\gen-java\com %currDir%\%api_module%\src\main\java\com\ /s /e /y

echo ɾ��gen-javaĿ¼
rd /s /q gen-java
::move /y "gen-java\com" %currDir%\%api_module%\src\main\java

cd %currDir%\%api_module%\

:: svn�ύsrc�ļ���
echo ****************************svn�ύsrc�ļ���****************************
call svn update %currDir%\%api_module%\ -r HEAD --force
::call svn add src/main/java

REM ��ʾĿ¼��δcommit���ļ�״̬����ʱ�ļ���ÿ��״̬һ��
svn status %currDir%\%api_module%\ > %currDir%\svn.txt
REM ������ʱ�ļ����Ը�״̬���н������½���Ŀ¼����add
for /f "delims=" %%i in (%currDir%\svn.txt) do (
	call %currDir%\svn_commit_one.bat "%%i"	
)
del %currDir%\svn.txt

call svn commit -m "����ű��Զ��ύ" src/main/java

echo ��ǰĿ¼:%cd%

:: ʹ��maven����api
echo ****************************ʹ��maven����api****************************

:: mvn -Dfile.encoding=UTF-8 compile

echo ********************ִ��mvn clean source:jar install������Ŀ*************************
rem call mvn -Dfile.encoding=UTF-8 clean source:jar install
call mvn -Dfile.encoding=UTF-8 clean install
echo ********************ִ��mvn deploy�ϴ���˽��*************************
call mvn deploy
goto end

::cd target
::echo **************************ִ��maven˽���ϴ�*****************************
::call mvn install:install-file -Dfile=./%api_module%-%jar_version%.jar -DgroupId=com.froad.cbank -DartifactId=%api_module% -Dversion=%jar_version% -Dpackaging=jar
::call mvn deploy:deploy-file  -DgroupId=com.froad.cbank  -DartifactId=%api_module%  -Dversion=%jar_version% -Dpackaging=jar  -Dfile=%api_module%-%jar_version%.jar -Durl=http://10.43.1.23:8081/content/repositories/froad-snapshot -DrepositoryId=froad-snapshot

:end
echo
echo ************API����ϴ���ϣ��밴������˳�****************
pause>nul

