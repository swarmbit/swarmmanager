#!/bin/ash

apk update
apk --update add curl
apk --update add openjdk8-jre
java -version
apk --update add nodejs=8.9.3-r1 nodejs-npm=8.9.3-r1
node -v
