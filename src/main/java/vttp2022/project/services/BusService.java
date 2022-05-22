package vttp2022.project.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.project.model.ArrivalBus;
import vttp2022.project.model.Bookmark;
import vttp2022.project.repositories.BusRepository;


@Service
public class BusService {
    private static final String URL = "http://datamall2.mytransport.sg";

    @Value("${AccountKey}")
    private String apiKey;
    
    @Autowired
    private BusRepository bRepo;

    public List<ArrivalBus> arrivingBus (String busStopCode){
        String busArrivalUrl = UriComponentsBuilder.fromUriString(URL)
            .path("/ltaodataservice/BusArrivalv2")
            .queryParam("BusStopCode", busStopCode)
            .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> req = RequestEntity.get(busArrivalUrl)
            .header("AccountKey", apiKey)
            .build();

        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
        List<ArrivalBus> busServices = new ArrayList<>();
        
        try {InputStream is = new ByteArrayInputStream(resp.getBody().getBytes());
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            for (int i = 0; i<o.getJsonArray("Services").size(); i++){
                    ArrivalBus arrivalBus = new ArrivalBus();
                    busServices.add(arrivalBus.create(o.getJsonArray("Services").getJsonObject(i)));
                }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return busServices;       
    }

    public boolean addBookMark(MultiValueMap<String, String> payload, HttpSession sess){
        String busStopCode = payload.getFirst("busStopCode");
        String description = payload.getFirst("description");
        if((null == description) || (description.trim().length() <= 0)){
            description = bRepo.getDescription(busStopCode);
        }
        String username = (String)sess.getAttribute("username");
        return bRepo.addBookmark(busStopCode, description, username);
    }

    public boolean deleteBookmark(String busStopCode, HttpSession sess){
        String username = (String)sess.getAttribute("username");
        return bRepo.deleteBookmark(busStopCode, username);
    }

    public List<Bookmark> retrieveBookmarks(String username){
        List<Bookmark> bookmarks = bRepo.getBookmarks(username);
        return bookmarks;
    }

    public boolean addUser (MultiValueMap<String, String> payload){
        return bRepo.addUser(payload.getFirst("username"), payload.getFirst("password"));
    }
    

    // public void populateBusStop (){
    //     for (int j=0; j<200; j++){
    //     String busArrivalUrl = UriComponentsBuilder.fromUriString(URL)
    //     .path("/ltaodataservice/BusStops")
    //     .queryParam("$skip", j*500)
    //     .toUriString();
    //     RestTemplate restTemplate = new RestTemplate();
    //     RequestEntity<Void> req = RequestEntity.get(busArrivalUrl)
    //         .header("AccountKey", apiKey)
    //         .build();

    //     ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

    //     try {InputStream is = new ByteArrayInputStream(resp.getBody().getBytes());
    //         JsonReader r = Json.createReader(is);
    //         JsonObject o = r.readObject();
    //         for (int i = 0; i<o.getJsonArray("value").size(); i++){
    //                 bRepo.populateBusStops(o.getJsonArray("value").getJsonObject(i).getString("BusStopCode"), o.getJsonArray("value").getJsonObject(i).getString("Description"));
    //             }
    //         } catch (Exception ex) {
    //             ex.printStackTrace();
    //         }
    //     }

    // }
}
