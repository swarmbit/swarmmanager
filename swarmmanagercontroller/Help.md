To run db execute:
	cd mongo && docker-build.sh
	docker run --name swarm-manager-db -p 27017:27017 swarmmanager/db

Create service options: 

     --constraint list                  Placement constraints (default [])
      --container-label list             Container labels (default [])
      --dns list                         Set custom DNS servers (default [])
      --dns-option list                  Set DNS options (default [])
      --dns-search list                  Set custom DNS search domains (default [])
      --endpoint-mode string             Endpoint mode (vip or dnsrr)
  -e, --env list                         Set environment variables (default [])
      --env-file list                    Read in a file of environment variables (default [])
      --group list                       Set one or more supplementary user groups for the container (default [])
      --health-cmd string                Command to run to check health
      --health-interval duration         Time between running the check (ns|us|ms|s|m|h)
      --health-retries int               Consecutive failures needed to report unhealthy
      --health-timeout duration          Maximum time to allow one check to run (ns|us|ms|s|m|h)
      --help                             Print usage
      --host list                        Set one or more custom host-to-IP mappings (host:ip) (default [])
      --hostname string                  Container hostname
  -l, --label list                       Service labels (default [])
      --limit-cpu decimal                Limit CPUs (default 0.000)
      --limit-memory bytes               Limit Memory (default 0 B)
      --log-driver string                Logging driver for service
      --log-opt list                     Logging driver options (default [])
      --mode string                      Service mode (replicated or global) (default "replicated")
      --mount mount                      Attach a filesystem mount to the service
      --name string                      Service name
      --network list                     Network attachments (default [])
      --no-healthcheck                   Disable any container-specified HEALTHCHECK
  -p, --publish port                     Publish a port as a node port
      --replicas uint                    Number of tasks
      --reserve-cpu decimal              Reserve CPUs (default 0.000)
      --reserve-memory bytes             Reserve Memory (default 0 B)
      --restart-condition string         Restart when condition is met (none, on-failure, or any)
      --restart-delay duration           Delay between restart attempts (ns|us|ms|s|m|h)
      --restart-max-attempts uint        Maximum number of restarts before giving up
      --restart-window duration          Window used to evaluate the restart policy (ns|us|ms|s|m|h)
      --secret secret                    Specify secrets to expose to the service
      --stop-grace-period duration       Time to wait before force killing a container (ns|us|ms|s|m|h)
  -t, --tty                              Allocate a pseudo-TTY
      --update-delay duration            Delay between updates (ns|us|ms|s|m|h) (default 0s)
      --update-failure-action string     Action on update failure (pause|continue) (default "pause")
      --update-max-failure-ratio float   Failure rate to tolerate during an update
      --update-monitor duration          Duration after each task update to monitor for failure (ns|us|ms|s|m|h) (default 0s)
      --update-parallelism uint          Maximum number of tasks updated simultaneously (0 to update all at once) (default 1)
  -u, --user string                      Username or UID (format: <name|uid>[:<group|gid>])
      --with-registry-auth               Send registry authentication details to swarm agents
  -w, --workdir string                   Working directory inside the container



Update service options:

 --args string                      Service command args
      --constraint-add list              Add or update a placement constraint (default [])
      --constraint-rm list               Remove a constraint (default [])
      --container-label-add list         Add or update a container label (default [])
      --container-label-rm list          Remove a container label by its key (default [])
      --dns-add list                     Add or update a custom DNS server (default [])
      --dns-option-add list              Add or update a DNS option (default [])
      --dns-option-rm list               Remove a DNS option (default [])
      --dns-rm list                      Remove a custom DNS server (default [])
      --dns-search-add list              Add or update a custom DNS search domain (default [])
      --dns-search-rm list               Remove a DNS search domain (default [])
      --endpoint-mode string             Endpoint mode (vip or dnsrr)
      --env-add list                     Add or update an environment variable (default [])
      --env-rm list                      Remove an environment variable (default [])
      --force                            Force update even if no changes require it
      --group-add list                   Add an additional supplementary user group to the container (default [])
      --group-rm list                    Remove a previously added supplementary user group from the container (default [])
      --health-cmd string                Command to run to check health
      --health-interval duration         Time between running the check (ns|us|ms|s|m|h)
      --health-retries int               Consecutive failures needed to report unhealthy
      --health-timeout duration          Maximum time to allow one check to run (ns|us|ms|s|m|h)
      --help                             Print usage
      --host-add list                    Add or update a custom host-to-IP mapping (host:ip) (default [])
      --host-rm list                     Remove a custom host-to-IP mapping (host:ip) (default [])
      --hostname string                  Container hostname
      --image string                     Service image tag
      --label-add list                   Add or update a service label (default [])
      --label-rm list                    Remove a label by its key (default [])
      --limit-cpu decimal                Limit CPUs (default 0.000)
      --limit-memory bytes               Limit Memory (default 0 B)
      --log-driver string                Logging driver for service
      --log-opt list                     Logging driver options (default [])
      --mount-add mount                  Add or update a mount on a service
      --mount-rm list                    Remove a mount by its target path (default [])
      --no-healthcheck                   Disable any container-specified HEALTHCHECK
      --publish-add port                 Add or update a published port
      --publish-rm port                  Remove a published port by its target port
      --replicas uint                    Number of tasks
      --reserve-cpu decimal              Reserve CPUs (default 0.000)
      --reserve-memory bytes             Reserve Memory (default 0 B)
      --restart-condition string         Restart when condition is met (none, on-failure, or any)
      --restart-delay duration           Delay between restart attempts (ns|us|ms|s|m|h)
      --restart-max-attempts uint        Maximum number of restarts before giving up
      --restart-window duration          Window used to evaluate the restart policy (ns|us|ms|s|m|h)
      --rollback                         Rollback to previous specification
      --secret-add secret                Add or update a secret on a service
      --secret-rm list                   Remove a secret (default [])
      --stop-grace-period duration       Time to wait before force killing a container (ns|us|ms|s|m|h)
  -t, --tty                              Allocate a pseudo-TTY
      --update-delay duration            Delay between updates (ns|us|ms|s|m|h) (default 0s)
      --update-failure-action string     Action on update failure (pause|continue) (default "pause")
      --update-max-failure-ratio float   Failure rate to tolerate during an update
      --update-monitor duration          Duration after each task update to monitor for failure (ns|us|ms|s|m|h) (default 0s)
      --update-parallelism uint          Maximum number of tasks updated simultaneously (0 to update all at once) (default 1)
  -u, --user string                      Username or UID (format: <name|uid>[:<group|gid>])
      --with-registry-auth               Send registry authentication details to swarm agents
  -w, --workdir string                   Working directory inside the container


Service List:
ID            NAME               MODE        REPLICAS  IMAGE
snx4h66iy14q  boring_mirzakhani  replicated  0/1       mongo:latest

Service ps:
ID            NAME                     IMAGE         NODE          DESIRED STATE  CURRENT STATE          ERROR                      PORTS
uydwzuqjk78v  boring_mirzakhani.1      mongo:latest  97ec3656d265  Ready          Ready 4 seconds ago                               
3l0w5c8cpj4w   \_ boring_mirzakhani.1  mongo:latest  97ec3656d265  Shutdown       Failed 4 seconds ago   "task: non-zero exit (1)"  
42sbbqul6tfr   \_ boring_mirzakhani.1  mongo:latest  97ec3656d265  Shutdown       Failed 10 seconds ago  "task: non-zero exit (1)"  
o8bka2a70ala   \_ boring_mirzakhani.1  mongo:latest  97ec3656d265  Shutdown       Failed 15 seconds ago  "task: non-zero exit (1)"  
ky1qko69e27s   \_ boring_mirzakhani.1  mongo:latest  97ec3656d265  Shutdown       Failed 21 seconds ago  "task: non-zero exit (1)"  



