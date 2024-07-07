package JournalApp.journalApp.Service;

import JournalApp.journalApp.Entity.Users;
import JournalApp.journalApp.Repo.UsersRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class UserService {

    @Autowired
   private UsersRepo usersRepo;
    private String UserName;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public void saveuser(Users user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList("ADMIN"));
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
  public Users findByUserName(String userName)
    {
        return usersRepo.findByUserName(userName);
    }
}
