
<img align="right" alt="Ajv logo" width="320" src="https://github.com/swarmbit/swarmmanager/blob/master/swarm-manager-logo.png">

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

## Run swarm manager image
```
$ docker run --name swarmmanager -p 3000:3080 -e DBPASS=password -v /var/run/docker.sock:/var/run/docker.sock -v ~/swarmmanager/data:/swarmmanagercontroller/data -v ~/swarmmanager/config:/config -d swarmbit/swarmmanager
```
* docker.config.yml and application-prod.properties can be added to ~/swarmmanager/config

### Run https with redirect
```
$ docker run --name swarmmanager -p 80:3080 -p 443:30443 -e DBPASS=password -e HTTPS=true -v /var/run/docker.sock:/var/run/docker.sock -v ~/swarmmanager/data:/swarmmanagercontroller/data -v ~/swarmmanager/config:/config -d swarmbit/swarmmanager
```
* Certificate - *server.crt* - and key - *server.key* - can be added to ~/swarmmanager/config

## Run swarm manager light image
```
$ docker run --name swarmmanagerlight -p 80:3080 -v /var/run/docker.sock:/var/run/docker.sock -v ~/swarmmanager/config:/config -d swarmbit/swarmmanagerlight
```

## Project Maintainers
* Bruno Vale
* Flávia Penim

## Contributing
* Writing tests //TODO
* Code review //TODO

## License
For more information and licensing details please see [License](LICENSE.md).
