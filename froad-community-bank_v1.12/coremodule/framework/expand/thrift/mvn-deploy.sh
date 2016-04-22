#!/bin/sh
cd target
mvn deploy:deploy-file  -DgroupId=com.froad.cbank.coremodule.framework.expand  -DartifactId=expand-thrift  -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar  -Dfile=./expand-thrift-0.0.1-SNAPSHOT.jar -Durl=http://172.18.2.13:8081/content/repositories/froad-snapshot -DrepositoryId=froad-snapshot

