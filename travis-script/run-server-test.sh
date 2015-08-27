#!/bin/bash

set -ev

cd server/
npm install

# Specify what you need to run the server project test cases.
# ...

cd $TRAVIS_BUILD_DIR/
