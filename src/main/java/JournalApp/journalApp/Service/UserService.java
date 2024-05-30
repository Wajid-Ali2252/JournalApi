package JournalApp.journalApp.Service;

import JournalApp.journalApp.Entity.Users;
import JournalApp.journalApp.Repo.UsersRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
   private UsersRepo usersRepo;
    private String UserName;

    public void saveuser(Users user)
    {
        usersRepo.save(user);
    }

    public List<Users> getalluser() {
        return usersRepo.findAll();

    }
    public void deleteuser(ObjectId id)
    {
        usersRepo.deleteById(id);
    }
    public void  Updateuser(Users users)
    {
        usersRepo.save(users);
    }
    public Users findByUserName(String username)
    {
        return usersRepo.findByUserName(UserName);
    }
}
