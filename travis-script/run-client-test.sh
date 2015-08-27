#!/bin/bash

set -ev

# Check whether 
if [ -d "$ANDROID_DIR/$ANDROID_PROJECT_NAME" ]; then

	# Run android emulator
	./travis-script/run-emulator.sh

	cd $ANDROID_DIR/$ANDROID_PROJECT_NAME/
	chmod ugo+x ./gradlew

	# Run connectedCheck (cC) of Android Apps
	./gradlew build cC

	# Run infer test
	#infer --fail-on-bug -- ./gradlew build
	
	cd $TRAVIS_BUILD_DIR/

fi
