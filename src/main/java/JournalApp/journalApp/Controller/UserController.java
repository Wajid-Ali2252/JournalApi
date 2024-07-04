package JournalApp.journalApp.Controller;

import JournalApp.journalApp.Entity.Users;
import JournalApp.journalApp.Service.UserService;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<?> saveusers(@RequestBody Users user)
    {
      try {
          userService.saveuser(user);
          return new ResponseEntity<>(user,HttpStatus.CREATED);
      }catch (Exception e)
      {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }


    }

    @GetMapping
    public List<Users> getusers()
    {
        return userService.getalluser();

    }
    @DeleteMapping("/id/{id}")
    public boolean deleteuser(ObjectId id)
    {
        userService.deleteuser(id);
        return true;
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody Users users)
    {
      Users userinDn=userService.findByUserName(users.getUserName());
        if(userinDn != null)
        {
            userinDn.setUserName(users.getUserName());
            userinDn.setPassword(users.getPassword());
            userService.saveuser(userinDn);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
