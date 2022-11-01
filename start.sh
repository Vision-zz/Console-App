#!/bin/bash

echo "Starting Console App"

cd build/

# if ! [ -e ./defaultSession.json ]; then
# 	echo "defaultSession.json not found. Created a empty file"
# 	touch defaultSession.json
# fi

if ! [ -e ./previousSession.json ]; then
	echo "previousSession.json not found. Created a empty file"
	touch previousSession.json
fi

if [ -e ./ConsoleApp.jar ]; then
	java -jar ./ConsoleApp.jar
else
	echo "JAR file not found. Try running build.sh before starting up"
fi
