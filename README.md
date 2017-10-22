# README #

This README includes all the instractions needed to run Swarm Manager development environment.

### Dependencies ###
* Java 8
* Maven
* Docker CE 17.06
* Node 6.11.2

### What is this repository for? ###

* Swarm Manager is a Web App to manage docker swarms. The goal of this web app is to support all the swarm configurations that the docker CLI supports and to add new functionalities like audit logs, user management and others. 

### How do I get set up? ###

* Create mongo db database - execute: cd swarmmanagercontroller/mongo && ./docker-build.sh && docker run --name swarm-manager-db -d -p 27017:27017 swarmmanager/db && cd ../../
* Run swarm manager controller - execute: cd swarmmanagercontroller && ./run.sh
* Run swarm manager frontend - execute: cd swarmmanagerfrontend && ./run.sh
* At this point the web app should be available on: http://localhost:4200. Login using user "admin" and password "test"

### Contribution guidelines ###

* Writing tests
* Code review

### Who do I talk to? ###

* Bruno Vale
* Flávia Penim
