#!/bin/bash

echo "Building source file"
find ./com -name "*.java" > source.txt

echo "Compiling java files"
if ! [ -e bin/ ]; then
	echo "bin folder not found. Creating..."
	mkdir bin
fi

if javac -d bin/ @source.txt; then
	echo "Successfully compiled java files"
else 
	echo "Error while compiling java file"
	return
fi

echo "Building JAR"
if ! [ -e build/ ]; then
	echo "build folder not found. Creating..."
	mkdir build
fi

cd bin/
jar cfve ../build/ConsoleApp.jar com/pitstop/App com/

cd ../
jar uf ./build/ConsoleApp.jar lib/

echo "Process complete"