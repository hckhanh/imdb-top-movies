#!/bin/bash

set +e

# Add dependencies here
echo no | android create avd --force -n test -t $ANDROID_TARGET --abi $ANDROID_ABIS
emulator -avd test -no-skin -no-audio -no-window &
android-wait-for-emulator
