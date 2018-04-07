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
   "password":"$2a$10$4HBJhX/aPaV6Ji8ke20BUeaF.5hPopHFQ07kkKwes6otml58ZpjHG",
   "roles":[  
      "ADMIN",
      "USER",
      "VISITOR"
   ],
   "secret":[  
      BinData(0,
      "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKfOnpl3/73AJ+R+B3utpLOeZUYf52ne9t4z2Vo97c1Z/jhIBjxDrPgZBANhzEuFYTMtgUmndVr4NofymO+gpjZrfyCy/klpM02xImil6fmCUz4RUp3RrZp8bMiGplU0TqxB2fF3HRfC4aOEs49APkwVZXxmi4Fime1Y9+RAzsc/AgMBAAECgYBoW6nsfWExa3AjvHEA7HNSNT58RtkO0Verxoj0gzEBlWbxL3GZRukp/byuMcLwcImLX1BZMuhb46NeFnQgCkNy8MkROMK0YBtSAyt4ryN+jDf9NE2kTVsIp1S0aI46HznsIiFFTFSYghTbBHLDA9oVEKqED53paw/a6bHnsSBVeQJBAM/ShyuyUdf2wdEzsluDyjIEKexHFE4kX16DSjnBfszLprJxzChlaOfruvlgioWvtnfgeZo3Ww0feIfxPTG4fksCQQDOtVYzwHzI9VSHU+u7oMIhcShOJ/FihtAIqsPua/WOT2BCZcs9FK0ZP43S79SK1q+ghktDQXtko6eG4nokcvJdAkEAtfCq0+9+RWLLFbp7mDDNsHICvc8QkMS5FYY9Ukj7GJHl8206/daiPAAdJbuGKjnFtjc4XnG97cQyglkJaOEK9wJBAK/aIU3Bseccnx7JzEBBESZ2J/3liTtyyUS2Uh1hhvPcn12dT/vUvTY/lR4gnkbHQX5lAV5kNN93QqxDu/mKOJkCQG9kSo6+6KwHCIt5QTOuLxU/K2WRbbl28coCiB8qKiAFPF1JVEL3a1wc5rk0lMdKiJ8jqQWdZ2Ha3kmmpPAyir8="      ),
      BinData(0,
      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnzp6Zd/+9wCfkfgd7raSznmVGH+dp3vbeM9laPe3NWf44SAY8Q6z4GQQDYcxLhWEzLYFJp3Va+DaH8pjvoKY2a38gsv5JaTNNsSJopen5glM+EVKd0a2afGzIhqZVNE6sQdnxdx0XwuGjhLOPQD5MFWV8ZouBYpntWPfkQM7HPwIDAQAB"      )
   ]
});
swarmmanagerDB.usersData.insert({  
   "_id":"admin",
   "_class":"com.swarmmanager.repository.model.UserData",
   "displayName": "Administrator"
});
swarmmanagerDB.users.insert({  
   "_id":"tester",
   "_class":"com.swarmmanager.repository.model.User",
   "password":"$2a$10$4HBJhX/aPaV6Ji8ke20BUeaF.5hPopHFQ07kkKwes6otml58ZpjHG",
   "roles":[  
      "USER",
      "VISITOR"
   ],
   "secret": null
});
swarmmanagerDB.usersData.insert({  
   "_id":"tester",
   "_class":"com.swarmmanager.repository.model.UserData",
   "displayName": "Tester"
});
db.shutdownServer();
