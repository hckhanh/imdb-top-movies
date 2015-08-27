#!/bin/bash

set -ev

# Check whether 
if [ -d "$ANDROID_DIR/$ANDROID_PROJECT_NAME" ]; then


	cd $ANDROID_DIR/$ANDROID_PROJECT_NAME/
	chmod ugo+x ./gradlew

	# Build Android Apps
	#./gradlew build

	# Run android emulator
	$TRAVIS_BUILD_DIR/travis-script/run-emulator.sh

	# Run connectedCheck (cC) of Android Apps
	./gradlew :app:connectedCheck

	cd $TRAVIS_BUILD_DIR/

fi
