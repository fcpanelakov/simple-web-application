# Simple-web-application
Simple web application for viewing another databases.
This application is using Spring, ORACLE database.
### Add Oracle dependency to local maven repository
Download: https://www.oracle.com/database/technologies/appdev/jdbc-ucp-183-downloads.html

RUN: mvn install:install-file -Dfile=ojdbc8.jar -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=18.3.0.0 -Dpackaging=jar