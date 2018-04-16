
<img align="right" alt="Swarm manager logo" width="320" src="https://github.com/swarmbit/swarmmanager/blob/master/swarm-manager-logo.png">

# README

The Swarm Manager is a web application to manage [docker swarms](https://docs.docker.com/engine/swarm/). The goal of this web app is to support all swarm configurations that the docker CLI supports and to add new functionalities like audit logs, user management and others.

Here you can find all the instructions needed to run the Swarm Manager development environment.

## Dependencies
* Java 8
* Maven
* Docker CE **17.12** (support from 1.12)
* Node 8.9.3
* Yarn

## Set up development environment
1. Create mongo db database, execute:
```
$ cd mongo
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
$ ./run.sh
```
4. At this point the web app should be available on: http://localhost:4201.
Login with username *admin* and password *administrator*.

## Run swarm manager full image (swarm manager controller, swarm manager frontend and database)

Available tags can be found here: https://hub.docker.com/r/swarmbit/swarmmanager/tags/
* Star swarm manager, this command does not persist data.
```
$ docker run --name swarmmanager -p 3000:3080 -v /var/run/docker.sock:/var/run/docker.sock -d swarmbit/swarmmanager:0.0.2-beta
```

* Start swarm manage with configuration and data directory.
```
$ docker run --name swarmmanager -p 3000:3080 -e HTTPS=true -e DBPASS=password -v /var/run/docker.sock:/var/run/docker.sock -v /opt/swarmmanager/data:/swarmmanagercontroller/data -v /opt/swarmmanager/config:/config -d swarmbit/swarmmanager:0.0.2-beta
```
  * Swarm manager stores data /swarmmanagercontroller/data and reads the configuration from /config.
    * Hosts directories can be mounted to persist data and to add configuration to swarm manager.
    * When *HTTPS=true* the frontend expects to find the certificate - *server.crt* - and key - *server.key* in */config* .
    * If *-e DBPASS=password* provided application-prod.properties with right password needs to be added */config* (spring.data.mongodb.password=password).
    * *docker.config.yml* is the swarm manager docker client configuration and it supports *daemon socket*, *tcp*, *tls* and *tlsverify*. Several swarm can be configured and the different swarms don't need to run on the same version of docker.


## Run swarm manager light image (swarm manager controller and swarm manager fronted)

* When running light image mongo db parameters need to be configured in application-prod.properties.
  * These properties are:
```
spring.data.mongodb.host=127.0.0.1
spring.data.mongodb.port=27017
spring.data.mongodb.username=swarmmanager
spring.data.mongodb.password=swarmmanager
spring.data.mongodb.database=swarmmanager
```
* Run light image
```
$ docker run --name swarmmanagerlight -p 80:3080 -v /var/run/docker.sock:/var/run/docker.sock -v /opt/swarmmanager/config:/config -d swarmbit/swarmmanagerlight:0.0.2-beta
```

## Project Maintainers
* Bruno Vale
* Flávia Penim

## Contributing
* Writing tests //TODO
* Code review //TODO

## License
For more information and licensing details please see [License](LICENSE.md).
