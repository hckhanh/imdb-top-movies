#!/bin/bash

set -e

# Check whether
if [ -d "$ANDROID_DIR/$ANDROID_PROJECT_NAME" ]; then

	cd $ANDROID_DIR/$ANDROID_PROJECT_NAME/
	chmod ugo+x ./gradlew

	# Run unit tests for Android Apps
	./gradlew build connectedCheck

	cd $TRAVIS_BUILD_DIR/

fi
