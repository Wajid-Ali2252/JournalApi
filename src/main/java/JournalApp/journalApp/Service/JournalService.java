package JournalApp.journalApp.Service;

import JournalApp.journalApp.Entity.JournalEntry;
import JournalApp.journalApp.Entity.ResponseMessage;
import JournalApp.journalApp.Entity.Users;
import JournalApp.journalApp.Repo.JournalEntryRepo;

import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class JournalService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;
    @Autowired
    private UserService userService;

    public void SaveEntry(JournalEntry entry, String userName)
    {
        try {
            Users user=userService.findByUserName(userName);
            JournalEntry saved = journalEntryRepo.save(entry);
            user.getJournalEntries().add(saved);
            userService.saveNewuser(user);
        }catch (Exception e)
        {
         e.printStackTrace();
        }
    }

    public List<JournalEntry> alldata()
    {
         return journalEntryRepo.findAll();

    }

    public void deleteByid(ObjectId id,String userName)
    {

        Users finduser=userService.findByUserName(userName);
        boolean removed=finduser.getJournalEntries().removeIf(x->x.getId().equals(id));
        if(removed) {
            userService.saveNewuser(finduser);
            journalEntryRepo.deleteById(id);
        }else{
            throw new RuntimeException("error occuring on delete entry");
        }


    }
    public Optional<JournalEntry> getByid(ObjectId id)
    {
       return journalEntryRepo.findById(id);
    }

    public Optional<JournalEntry> findByid(ObjectId myid) {
        return journalEntryRepo.findById(myid);
    }
    public  JournalEntry SaveEntry(JournalEntry entry)
    {
       return journalEntryRepo.save(entry);
    }
//    public JournalEntry updateByid(ObjectId id, @RequestBody JournalEntry myentry)
//    {
//        JournalEntry oldid=.findById(id).orElse(null);
//        if(oldid != null)
//        {
//            oldid.setName(myentry.getName() !=null && myentry.getName().equals("") ? myentry.getName() : oldid.getName());
//            oldid.setContent(myentry.getContent() != null && myentry.getContent().equals("") ? myentry.getContent() : oldid.getContent() );
//
//        }
//
//    }
}
