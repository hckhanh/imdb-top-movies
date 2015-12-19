#!/bin/bash

set +e

echo "Go here"
# Check whether
if [ -d "$ANDROID_DIR/$ANDROID_PROJECT_NAME" ]; then
	echo "Go here, too"
	ls
	cd $ANDROID_DIR/$ANDROID_PROJECT_NAME/
	chmod ugo+x ./gradlew
	ls
	# Run unit tests for Android Apps
	./gradlew build connectedCheck

	cd $TRAVIS_BUILD_DIR/

fi
