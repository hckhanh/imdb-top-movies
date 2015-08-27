#!/bin/bash

set -ev

# Check whether 
if [ "$TEST_ANDROID_APP" == "true" ]; then

	# Run android emulator
	./travis-script/run-emulator.sh

	cd $ANDROID_DIR/$ANDROID_PROJECT_NAME/
	chmod ugo+x ./gradlew

	# Run connectedCheck of Android Apps
	./gradlew connectedCheck

	# Run infer test
	infer --fail-on-bug -- ./gradlew build

	cd $TRAVIS_BUILD_DIR/

fi
