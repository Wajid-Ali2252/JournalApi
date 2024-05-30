package JournalApp.journalApp.Entity;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseMessage {
    private String status;
    private String message;
    private HttpStatus serversides;
}
