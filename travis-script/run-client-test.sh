#!/bin/bash

set -ev

cd $ANDROID_DIR/

# Check whether 
if [ -d "$ANDROID_PROJECT_NAME" ]; then

	# Run android emulator
	$TRAVIS_BUILD_DIR/travis-script/run-emulator.sh

	cd $ANDROID_PROJECT_NAME/
	chmod ugo+x ./gradlew

	# Run connectedCheck of Android Apps
	./gradlew connectedCheck

	# Run infer test
	#infer --fail-on-bug -- ./gradlew build

fi
	
cd $TRAVIS_BUILD_DIR/
