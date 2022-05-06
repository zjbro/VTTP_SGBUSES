package vttp2022.project.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.project.model.ArrivalBus;

@Service
public class BusService {
    private static final String URL = "http://datamall2.mytransport.sg";

    @Value("${AccountKey}")
    private String apiKey;    

    public Optional<List<ArrivalBus>> arrivingBus (String busStopCode){
        String busArrivalUrl = UriComponentsBuilder.fromUriString(URL)
            .path("/ltaodataservice/BusArrivalv2")
            .queryParam("BusStopCode", busStopCode)
            .toUriString();
        System.out.println(">>>>>>>>>>"+ busArrivalUrl);



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
        return Optional.of(busServices);       
    }

}
