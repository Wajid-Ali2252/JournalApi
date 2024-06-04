package JournalApp.journalApp.Controller;

import JournalApp.journalApp.Entity.JournalEntry;
import JournalApp.journalApp.Entity.ResponseMessage;
import JournalApp.journalApp.Entity.Users;
import JournalApp.journalApp.Service.JournalService;
import JournalApp.journalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    private HttpStatusCode JournalEntry;





    @GetMapping("{userName}")
   public ResponseEntity<?> getUserJournalentires(@PathVariable String userName)
   {
       Users finduser=userService.findByUserName(userName);
       List<JournalEntry> res= finduser.getJournalEntries();
       if(res != null)
       {
           return  new ResponseEntity<>(res,HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }

   @PostMapping("{userName}")
   public ResponseEntity createList(@RequestBody JournalEntry  myentry,@PathVariable String userName)
   {
       ResponseMessage response=new ResponseMessage();
       try {

           journalService.SaveEntry(myentry,userName);
           response.setServersides(HttpStatus.OK);
           response.setStatus("success");
           response.setMessage("Journal Entry added successfully");
           return ResponseEntity.ok().body(response);

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
    public ResponseEntity<?> deleteListbyid(@PathVariable ObjectId myid)
    {
        ResponseMessage responseMessage=new ResponseMessage();
        try{
            journalService.deleteByid(myid);
            responseMessage.setMessage("Journal Entries delete successfully");
            responseMessage.setStatus("success");
            responseMessage.setServersides(HttpStatus.OK);
            return ResponseEntity.ok().body(responseMessage);

        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/id/{myid}")
    public Optional<JournalEntry> updatebyid(@PathVariable ObjectId myid)
    {
        return Optional.ofNullable(journalService.getByid(myid).orElse(null));
    }


}
