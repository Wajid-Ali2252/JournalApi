package JournalApp.journalApp.Controller;

import JournalApp.journalApp.Entity.Users;
import JournalApp.journalApp.Entity.WeatherResponse;
import JournalApp.journalApp.Repo.UsersRepo;
import JournalApp.journalApp.Service.UserService;
import JournalApp.journalApp.Service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    WeatherService weatherService;


    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        WeatherResponse res = weatherService.getWeather("Karachi");
        return ResponseEntity.ok(res);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteuser(@RequestBody Users users){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        Users userinDn=usersRepo.deleteByUserName(userName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody Users users){
     Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
     String userName=authentication.getName();
      Users userinDn=userService.findByUserName(userName);
            userinDn.setUserName(users.getUserName());
            userinDn.setPassword(users.getPassword());
            userService.saveuser(userinDn);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
