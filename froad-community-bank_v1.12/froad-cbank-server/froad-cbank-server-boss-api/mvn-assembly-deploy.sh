#!/bin/sh
mvn clean assembly:assembly
cd target
mvn install:install-file -Dfile=./froad-cbank-server-boss-api-1.2.0-SNAPSHOT.jar -DgroupId=com.froad.cbank -DartifactId=froad-cbank-server-boss-api -Dversion=1.2.0-SNAPSHOT -Dpackaging=jar
mvn deploy:deploy-file  -DgroupId=com.froad.cbank  -DartifactId=froad-cbank-server-boss-api  -Dversion=1.2.0-SNAPSHOT -Dpackaging=jar  -Dfile=./froad-cbank-server-boss-api-1.2.0-SNAPSHOT.jar -Durl=http://172.18.2.13:8081/content/repositories/froad-snapshot -DrepositoryId=froad-snapshot

