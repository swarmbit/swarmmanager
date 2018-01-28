<img align="right" alt="Swarm Manager logo" width="250" src="SwarmManager.png">

# README #

The Swarm Manager is a web application to manage [docker swarms](https://docs.docker.com/engine/swarm/). The goal of this web app is to support all the swarm configurations that the docker CLI supports and to add new functionalities like audit logs, user management and others.

Her you can find all the instructions needed to run the Swarm Manager development environment.

## Dependencies
* Java 8
* Maven
* Docker CE **17.12** (support from 1.12)
* Node 6.11.2
* Yarn

## Set Up
1. Create mongo db database, execute:
```
$ cd swarmmanagercontroller/mongo
$ ./docker-build.sh
$ docker run --name swarm-manager-db -d -p 27017:27017 swarmmanager/db
```
2. Run swarm manager controller, execute:
```
$ cd swarmmanagercontroller
$ ./run.sh
```
3. Run swarm manager frontend, execute:
```
$ cd swarmmanagerfrontend
$ yarn install
$ ./run.sh
```
4. At this point the web app should be available on: http://localhost:4201.
Login with username *admin* and password *test*.

## Project Maintainers
* Bruno Vale
* Flávia Penim

## Contributing
* Writing tests //TODO
* Code review //TODO

## License
For more information and licensing details please see [License](LICENSE.md).
