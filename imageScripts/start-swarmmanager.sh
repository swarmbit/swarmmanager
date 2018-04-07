#!/bin/ash

if [ -z "$(ls -A /swarmmanagercontroller/data)" ]; then
  echo "Creating swarmmanagercontroller database"
  mongod --fork --logpath /swarmmanagercontroller/log/mongod.log --dbpath /swarmmanagercontroller/data  --port 27017
  DBPASS=$1
  sed -i -e 's/-DBPASS-/'$DBPASS'/g' /scripts/createSwarmManagerDB.js
  mongo /scripts/createSwarmManagerDB.js
  mongod --shutdown
fi

mongod --auth --fork --logpath /swarmmanagercontroller/log/mongod.log --dbpath /swarmmanagercontroller/data  --port 27017
echo "Start swarmmanagerfrontend..."
node /swarmmanagerfrontend/server.js &
echo "Start swarmmanagercontroller..."
java -cp "/swarmmanagercontroller/swarmmanagercontroller.jar:/config" -Dspring.profiles.active=prod org.springframework.boot.loader.JarLauncher
