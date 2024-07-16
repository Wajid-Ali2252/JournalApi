package JournalApp.journalApp.Service;

import JournalApp.journalApp.Entity.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weatherAPIKEY}")
    private  String apiKey;
    @Value("${WeatherAPI}")
    private String API;


    @Autowired
    UserService userService;

    @Autowired
    RestTemplate restTemplate;

    public WeatherResponse getWeather(String City)
    {
        String apiurl = API.replace("CITY", City).replace("API_KEY",apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(apiurl, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
       return body;

    }
}
