package co.uk.swarmbit.repository;

import co.uk.swarmbit.repository.model.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDataRepository extends MongoRepository<UserData, String> {
    UserData findByUsername(String username);
}
