#!/bin/bash
echo "Building source file"
find . -name "*.java" > source.txt

echo "Compiling java files"
if javac -d bin/ @source.txt; then
	echo "Successfully compiled java files"
else 
	echo "Error while compiling java file"
	return
fi

echo "Building JAR"
jar cfve ./ConsoleApp.jar com/pitstop/App.class bin/
jar uf ConsoleApp.jar lib/

echo "Process complete"