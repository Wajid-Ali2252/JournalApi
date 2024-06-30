package JournalApp.journalApp.Controller;

import JournalApp.journalApp.Entity.JournalEntry;
import JournalApp.journalApp.Entity.ResponseMessage;
import JournalApp.journalApp.Entity.Users;
import JournalApp.journalApp.Service.JournalService;
import JournalApp.journalApp.Service.UserService;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
@Component
public class JournalEntryController {


    @Autowired
    private JournalService journalService;

    @Autowired
    private  UserService userService;
//    private HttpStatusCode JournalEntry;
    ResponseMessage response=new ResponseMessage();





    @GetMapping("{userName}")
   public ResponseEntity<?> getUserJournalentires(@PathVariable String userName)
   {

       try {
           Users finduser=userService.findByUserName(userName);

           if(finduser != null) {
               List<JournalEntry> entries= finduser.getJournalEntries();
                return  ResponseEntity.ok().body(entries);
           }else {
               ResponseMessage response = new ResponseMessage();
               response.setHttpStatus(HttpStatus.NOT_FOUND);
               response.setStatus("error");
               response.setMessage(userName + " Not Found");
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
           }


       }catch (Exception e)
       {
           e.printStackTrace();
           return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }

   }

   @PostMapping("{userName}")
   public ResponseEntity<?> createList(@RequestBody JournalEntry  myentry,@PathVariable String userName)
   {

       try {
           Users finduser=userService.findByUserName(userName);
           if(finduser !=null) {
               journalService.SaveEntry(myentry, userName);
               response.setHttpStatus(HttpStatus.OK);
               response.setStatus("success");
               response.setMessage("Journal Entry added successfully");
               return ResponseEntity.ok().body(response);
           }else{
               response.setHttpStatus(HttpStatus.NOT_FOUND);
               response.setStatus("error");
               response.setMessage(userName+" not found");
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
           }

       }catch (Exception e)
       {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }

   }

   @GetMapping("/id/{userName}/{myid}")
  public ResponseEntity<JournalEntry> getbyid(@PathVariable ObjectId myid,@PathVariable String userName)
  {
      Optional<JournalEntry> byid=journalService.getByid(myid);
      if(byid.isPresent())
      {
          return  new ResponseEntity<>(byid.get(),HttpStatus.OK);
      }
      return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
  }


    @DeleteMapping("/id/{userName}/{myid}")
    public ResponseEntity<?> deleteListbyid(@PathVariable ObjectId myid,@PathVariable String userName)
    {

        try{
            Users user=userService.findByUserName(userName);
            if(user != null) {
                journalService.deleteByid(myid,userName);
                response.setMessage("Journal Entries delete successfully");
                response.setStatus("success");
                response.setHttpStatus(HttpStatus.OK);
                return ResponseEntity.ok().body(response);
            }else{
                response.setMessage("Invalid user");
                response.setStatus("error");
                response.setHttpStatus(HttpStatus.NO_CONTENT);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }

        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/id/{myid}")
    public Optional<JournalEntry> updatebyid(@PathVariable ObjectId myid)
    {
        return Optional.ofNullable(journalService.getByid(myid).orElse(null));
    }


}
