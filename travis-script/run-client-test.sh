#!/bin/bash

set -ev

# Run android emulator
./travis-script/run-emulator.sh

cd $ANDROID_DIR/$ANDROID_PROJECT_NAME/
chmod ugo+x ./gradlew

# Run connectedCheck of Android App
./gradlew :$ANDROID_APP_MODULE_NAME:connectedCheck

cd $TRAVIS_BUILD_DIR/
