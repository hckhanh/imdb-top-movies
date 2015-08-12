#!/bin/bash

set -ev

# Run infer test
cd $ANDROID_DIR/$ANDROID_PROJECT_NAME/
chmod ugo+x ./gradlew

# Run infer test of Android App 1
if [ "$TEST_ANDROID_APP1" == "true" ]; then

	infer -- ./gradlew :$ANDROID_APP1_MODULE_NAME:assembleDebug

fi

# Run infer test of Android App 2
if [ "$TEST_ANDROID_APP1" == "true" ]; then

	infer -- ./gradlew :$ANDROID_APP2_MODULE_NAME:assembleDebug
	
fi

cd $TRAVIS_BUILD_DIR/
