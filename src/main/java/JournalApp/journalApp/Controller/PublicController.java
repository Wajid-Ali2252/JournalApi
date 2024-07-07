package JournalApp.journalApp.Controller;

import JournalApp.journalApp.Entity.Users;
import JournalApp.journalApp.Repo.UsersRepo;
import JournalApp.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;


    @PostMapping
    public ResponseEntity<?> saveusers(@RequestBody Users user)
    {
        try {
            userService.saveuser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }
}
