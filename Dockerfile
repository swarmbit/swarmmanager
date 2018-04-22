FROM node:8.11.1 as swarmmanagerfrontend
WORKDIR /swarmmanagerfrontend
COPY swarmmanagerfrontend .
RUN npm install -g yarn@v1.5.1
RUN yarn --pure-lockfile
RUN rm -rf node_modules && yarn install && yarn server-build

FROM maven:3.5.3-jdk-8 as swarmmanagercontroller
WORKDIR /swarmmanagercontroller
COPY swarmmanagercontroller .
RUN mvn clean install

FROM  swarmbit/swarmmanagerbase:alpine3.7-java8-mongo3.4.0-node8.9.3
ENV DBPASS swarmmanager
ENV HTTPS false
COPY --from=swarmmanagerfrontend /swarmmanagerfrontend/dist /swarmmanagerfrontend
COPY --from=swarmmanagercontroller /swarmmanagercontroller/target/swarmmanagercontroller.jar /swarmmanagercontroller/swarmmanagercontroller.jar
COPY ./mongo/createSwarmManagerDB.js /scripts/createSwarmManagerDB.js
COPY imageScripts/start-swarmmanager.sh start-swarmmanager.sh
RUN mkdir /data && mkdir /log

ENTRYPOINT ./start-swarmmanager.sh $DBPASS
