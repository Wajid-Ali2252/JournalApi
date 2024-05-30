package JournalApp.journalApp.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Healthcheck {

    @GetMapping("/health-check")
    public String healthcheck()
    {
        return  "ok";
    }
}
