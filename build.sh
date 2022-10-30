#!/bin/bash

echo "Building source file"
find ./com -name "*.java" > source.txt # Building a source.txt which contains all the locations of the .java files

if ! [ -e bin/ ]; then
	echo "bin folder not found. Creating..."
	mkdir bin # Creating a bin folder inside the root directory to hold the compiled .class files
fi

echo "Compiling java files"
if javac -d bin/ -cp lib/*.jar @source.txt; then # Compiling .java files to .class files
	echo "Successfully compiled java files"
else
	echo "Error while compiling java file"
	return
fi

echo "Copying JSON files to bin folder"
for json_file in $(find ./com -name '*.json')
do
	cp -v $json_file bin/$json_file # Copying .json files into bin folder to add these into the JAR
done

echo "Building JAR"
if ! [ -e build/ ]; then
	echo "build folder not found. Creating..."
	mkdir build # Creating a build folder inside the root directory to hold the .jar files
fi

# WARNING! If you do not see a Manifext.txt in the root directory, you probably need to construct it or repull the repository
jar cvfm build/ConsoleApp.jar Manifest.txt -C bin/ ./com # Creating the jar file with custom Manifest.txt

cd bin/ # cd into the bin folder to add with external libraries 

echo "Adding dependecies from lib folder"
if ! [ -e lib/ ]; then
	echo "lib folder not found. Creating..."
	mkdir lib # Creating a lib folder to hold all the extracted files from the external libraries
fi

cd lib/ # cd into lib folder to extract jars

# (╯°□°）╯︵ ┻━┻ Lazy to explain. Learn shell script to understand
for jar_file in ../../lib/*.jar
do

	jar_name=`basename $jar_file .jar`
	if [ $jar_name == "*" ];
		then continue
	fi

	if ! [ -e $jar_name ]; then
		mkdir $jar_name
	fi

	cd $jar_name
	jar xf "../$jar_file"

	rm -rf META-INF
	cd ../
	jar uf ../../build/ConsoleApp.jar -C $jar_name .

done

echo "Process complete. Jar file is created into the build/ folder"