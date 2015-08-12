#!/bin/bash

set -ev

# Check whether this is a tag build or not
if [ -n "$TRAVIS_TAG" ]; then

	# Variables
	RELESE_APK_ANDROID_APP=$ANDROID_APP_MODULE_NAME/$RELEASE_PATH_FILE/$ANDROID_APP_MODULE_NAME-$BUILD_TYPE.apk

	cd $ANDROID_DIR/$ANDROID_PROJECT_NAME/

	# Generate release file of Android App
	./gradlew :$ANDROID_APP_MODULE_NAME:assemble$BUILD_TYPE
	mv -v $RELESE_APK_ANDROID_APP $ANDROID_APP_NAME.apk

	# Update file to FPT server
	curl --ftp-create-dirs -T $ANDROID_APP_NAME.apk -u $FPT_USER:$FPT_PASSWORD $FPT_URL

	cd $TRAVIS_BUILD_DIR/

fi
