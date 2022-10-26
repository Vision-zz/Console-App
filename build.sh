#!/bin/bash

echo "Building source file"
find ./com -name "*.java" > source.txt

echo "Compiling java files"
if ! [ -e bin/ ]
	echo "bin folder not found. Creating..."
	mkdir bin

if javac -d bin/ @source.txt; then
	echo "Successfully compiled java files"
else 
	echo "Error while compiling java file"
	return
fi

echo "Building JAR"
if ! [ -e build/ ]
	echo "build folder not found. Creating..."
	mkdir build

cd bin/
jar cfve ../build/ConsoleApp.jar com/pitstop/App com/

cd ../
jar uf ./build/ConsoleApp.jar lib/

echo "Process complete"