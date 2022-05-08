package vttp2022.project.controller;

import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.MediaTray;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import vttp2022.project.model.ArrivalBus;
import vttp2022.project.services.BusService;

@Controller
@RequestMapping
public class BusArrivalController {

    @Autowired
    private BusService bService;
    
    
    @GetMapping(path="/busStop")
    public ModelAndView getBusArrival(@RequestParam String busStopCode){
        ModelAndView mav = new ModelAndView();
        Optional<List<ArrivalBus>> optBuses = bService.arrivingBus(busStopCode);
        if(optBuses.isEmpty()){
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            mav.setViewName("error");
            return mav;
        }
        mav.setStatus(HttpStatus.OK);
        mav.addObject("busStopCode",busStopCode);
        mav.addObject("arrivalbuses",optBuses.get());
        mav.setViewName("busarrivaltiming");
        return mav;
    }

    @PostMapping(path="/addBookmark")
    public ModelAndView addBookmark(@RequestBody MultiValueMap<String, String> payload, HttpSession sess){
        ModelAndView mav = new ModelAndView();
        bService.addBookMark(payload, sess);
        mav.setStatus(HttpStatus.OK);
        mav.setViewName("homepage");
        return mav;
    }

    // @GetMapping(path="/BusServices")
    // public ResponseEntity<String> getBusRoutes(){
    //     String URL = "http://datamall2.mytransport.sg/ltaodataservice/BusServices";
    //     RestTemplate restTemplate = new RestTemplate();
    //     RequestEntity<Void> req = RequestEntity.get(URL)
    //         .header("AccountKey", apiKey)
    //         .build();

    //     ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
    //     return ResponseEntity.ok().body(resp.getBody());


    // }
}
