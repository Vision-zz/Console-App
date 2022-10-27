#!/bin/bash

echo "Starting Console App" 
if [ -e ./build/ConsoleApp.jar ]; then
	java -jar ./build/ConsoleApp.jar
else
	echo "JAR file not found. Try running build.sh before starting up"
fi