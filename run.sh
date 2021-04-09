#!/usr/bin/env bash

javac Lab10McCumber.java

if [ $? -eq 0 ]
then
	java -cp .:/usr/share/java/mysql-connector-java.jar Lab10McCumber
fi
