var conn = new Mongo("localhost:27017");
var db = conn.getDB("admin");
db.createUser(
  {
    user: "admin",
    pwd: "admin",
    roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
  }
)

db.auth("admin", "admin");

var swarmmanagerDB = conn.getDB("swarmmanager");
swarmmanagerDB.createUser(
  {
    user: "swarmmanager",
    pwd: "-DBPASS-",
    roles: [ { role: "readWrite", db: "swarmmanager" } ]
  }
);

swarmmanagerDB = conn.getDB("swarmmanager");
swarmmanagerDB.auth("swarmmanager", "swarmmanager");
swarmmanagerDB.createCollection("users");
swarmmanagerDB.createCollection("usersData");
swarmmanagerDB.users.insert({  
   "_id":"admin",
   "_class":"com.swarmmanager.repository.model.User",
   "password":"$2a$10$oPtAOruF7ECBKYQnuE6lmOszbM9TiN4jZLkFuV4yWUYTjLHzFLMv6",
   "roles":[  
      "ADMIN",
      "USER",
      "VISITOR",
      "NONE"
   ]
});
swarmmanagerDB.usersData.insert({  
   "_id":"admin",
   "_class":"com.swarmmanager.repository.model.UserData",
   "displayName": "Administrator"
});
db.shutdownServer();
