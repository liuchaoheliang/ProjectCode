@echo off
set MAVEN_OPTS= -Xms256M -Xmx1024M -XX:PermSize=64M -XX:MaxPermSize=128M
set jar_version=0.0.1-SNAPSHOT

::call mvn clean
::call mvn install

::call mvn clean assembly:assembly

cd target

call mvn deploy:deploy-file  -DgroupId=com.froad.cbank  -DartifactId=froad-cbank-server-common  -Dversion=%jar_version% -Dpackaging=jar  -Dfile=froad-cbank-server-common-%jar_version%.jar -Durl=http://10.43.1.23:8081/content/repositories/froad-snapshot -DrepositoryId=froad-snapshot
pause

