#!/bin/bash

echo "Building source file"
find ./com -name "*.java" > source.txt

if ! [ -e bin/ ]; then
	echo "bin folder not found. Creating..."
	mkdir bin
fi

echo "Compiling java files"
if javac -d bin/ -cp lib/**/*.jar @source.txt; then
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

jar cvfm build/ConsoleApp.jar Manifest.txt -C bin/ . lib/

cd ../
# jar uf ./build/ConsoleApp.jar lib/

echo "Process complete"