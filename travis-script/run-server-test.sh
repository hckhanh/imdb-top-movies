#!/bin/bash

set -ev

if [ -f "server/package.json" ]; then

	cd server/

	npm install
	npm test

	cd $TRAVIS_BUILD_DIR/

fi