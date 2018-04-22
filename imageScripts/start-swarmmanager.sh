#!/bin/ash

if [ -z "$(ls -A /data)" ]; then
  echo "Creating swarmmanagercontroller database"
  mongod --fork --logpath /log/mongod.log --dbpath /data  --port 27017
  DBPASS=$1
  sed -i -e 's/-DBPASS-/'$DBPASS'/g' /scripts/createSwarmManagerDB.js
  mongo /scripts/createSwarmManagerDB.js
fi

mongod --auth --fork --logpath /log/mongod.log --dbpath /data  --port 27017

echo "Start swarmmanagercontroller..."
java -cp "/swarmmanagercontroller/swarmmanagercontroller.jar:/config" -Dspring.profiles.active=prod org.springframework.boot.loader.JarLauncher &

echo "Waiting 30 seconds for swarmmanagercontroller to start"
/bin/sleep 30

echo "Checking swarmmanagercontroller status"
curl http://127.0.0.1:8080/health

echo "Start swarmmanagerfrontend..."
node /swarmmanagerfrontend/server.js
