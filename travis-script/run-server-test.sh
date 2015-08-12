#!/bin/bash

set -ev

cd server/
npm install

# Specify what you need to run the server project test cases.
# ...

if [ -d "node_modules" ]; then
	rm -r node_modules/
fi
cd $TRAVIS_BUILD_DIR/
