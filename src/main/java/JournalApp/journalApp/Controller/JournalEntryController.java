package JournalApp.journalApp.Controller;

import JournalApp.journalApp.Entity.JournalEntry;
import JournalApp.journalApp.Entity.ResponseMessage;
import JournalApp.journalApp.Entity.Users;
import JournalApp.journalApp.Service.JournalService;
import JournalApp.journalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@Component
public class JournalEntryController {


    @Autowired
    private JournalService journalService;

    @Autowired
    private  UserService userService;
    private HttpStatusCode JournalEntry;
    ResponseMessage response=new ResponseMessage();





    @GetMapping
   public ResponseEntity<?> getUserJournalentires()
   {
       Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
       String userName=authentication.getName();
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

   @PostMapping
   public ResponseEntity<?> createList(@RequestBody JournalEntry  myentry)
   {
       Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
       String userName=authentication.getName();
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

   @GetMapping("mid/{myid}")

  public ResponseEntity<JournalEntry> getbyid(@PathVariable ObjectId myid)
  {

      Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
      String userName=authentication.getName();
      Users user=userService.findByUserName(userName);
      List<JournalApp.journalApp.Entity.JournalEntry> entries=user.getJournalEntries().stream().filter(x->x.getId().equals(myid)).collect(Collectors.toList());
      if(!entries.isEmpty())
      {
          Optional<JournalApp.journalApp.Entity.JournalEntry> journalEntries =journalService.findByid(myid);
          if(journalEntries.isPresent())
          {
              return new  ResponseEntity<>(journalEntries.get(),HttpStatus.OK);
          }
      }
      return new  ResponseEntity<>(HttpStatus.NOT_FOUND);


  }


    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deleteListbyid(@PathVariable ObjectId myid)
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        try{
            journalService.deleteByid(myid,userName);
            response.setMessage("Journal Entries delete successfully");
            response.setStatus("success");
            response.setHttpStatus(HttpStatus.OK);
            return ResponseEntity.ok().body(response);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("id/{myid}")
    public ResponseEntity<?> updatebyid(@RequestBody JournalEntry entry,@PathVariable ObjectId myid)
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        Users user=userService.findByUserName(userName);
        List<JournalEntry> entries=user.getJournalEntries().stream().filter(x->x.getId().equals(myid)).collect(Collectors.toList());
        if(!entries.isEmpty())
        {
            Optional<JournalEntry> journalEntries =journalService.findByid(myid);
            if(journalEntries.isPresent())
            {
               JournalEntry oldentr= journalEntries.get();
               oldentr.setTitle(entry.getTitle() !=null && !entry.getTitle().equals("") ? entry.getTitle() : oldentr.getTitle());
               oldentr.setContent(entry.getContent() != null && !entry.getContent().equals("") ? entry.getContent() : oldentr.getContent());
               journalService.SaveEntry(oldentr);
               return new  ResponseEntity<>(HttpStatus.OK);
            }
            return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new  ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
