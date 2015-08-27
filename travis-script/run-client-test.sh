#!/bin/bash

set -ev

# Check whether 
if [ -d "$ANDROID_DIR/$ANDROID_PROJECT_NAME" ]; then

	# Run android emulator
	./travis-script/run-emulator.sh

	cd $ANDROID_DIR/$ANDROID_PROJECT_NAME/
	chmod ugo+x ./gradlew

	# Run connectedCheck of Android Apps
	./gradlew :$ANDROID_APP_MODULE_NAME:connectedCheck

	# Run infer test
	#infer --fail-on-bug -- ./gradlew build
	
	cd $TRAVIS_BUILD_DIR/

fi
