package JournalApp.journalApp.Repo;

import JournalApp.journalApp.Entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepo extends MongoRepository<Users, ObjectId> {
  Users findByUserName(String userName);
}
