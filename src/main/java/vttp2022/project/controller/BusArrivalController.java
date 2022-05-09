package vttp2022.project.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;

import vttp2022.project.model.ArrivalBus;
import vttp2022.project.model.Bookmark;
import vttp2022.project.repositories.BusRepository;
import vttp2022.project.services.BusService;

@Controller
@RequestMapping
public class BusArrivalController {

    @Autowired
    private BusService bService;

    @Autowired
    private BusRepository bRepo;
    
    
    @GetMapping(path="/busStop")
    public ModelAndView getBusArrival(@RequestParam String busStopCode){
        ModelAndView mav = new ModelAndView();
        Optional<List<ArrivalBus>> optBuses = bService.arrivingBus(busStopCode);
        if(optBuses.isEmpty()){
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            mav.setViewName("error");
            return mav;
        }
        String description = bRepo.getDescription(busStopCode);
        mav.setStatus(HttpStatus.OK);
        mav.addObject("busStopCode",busStopCode);
        mav.addObject("description", description);
        mav.addObject("arrivalbuses",optBuses.get());
        mav.setViewName("busarrivaltiming");
        return mav;
    }

    @PostMapping(path="/addBookmark")
    public ModelAndView addBookmark(@RequestBody MultiValueMap<String, String> payload, HttpSession sess){
        ModelAndView mav = new ModelAndView();
        String username = (String)sess.getAttribute("username");
        bService.addBookMark(payload, sess);
        List<Bookmark> bookmarks = bService.retrieveBookmarks(username);
        mav.addObject("bookmarks", bookmarks);
        mav.addObject("username", username);
        mav.setStatus(HttpStatus.OK);
        mav.setViewName("bookmarks");
        return mav;
    }

    @GetMapping(path="/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping(path="/register")
    public String registrationPage(){
        return "register";
    }

    @PostMapping(path="/adduser")
    public String registrationNewUser(@RequestBody MultiValueMap<String, String> payload){
        if(bService.addUser(payload) == true){
        return "registersuccess";
        } 
        return "registerfail";
    }

    @GetMapping(path="/protected/bookmarks")
    public ModelAndView loginFirst(HttpSession sess){
        ModelAndView mav = new ModelAndView();
        String username = (String)sess.getAttribute("username");
        if((null == username) || (username.trim().length() <= 0)){
            mav.setViewName("login");
            return mav;
        }
        List<Bookmark> bookmarks = bService.retrieveBookmarks(username);
        mav.addObject("username", username);
        mav.addObject("bookmarks", bookmarks);
        mav.setViewName("bookmarks");
        return mav;
    }

    @GetMapping(path="/goback")
    public String goBack(HttpSession sess){
        String username = (String)sess.getAttribute("username");
        if((null == username) || (username.trim().length() <= 0)){
            return "index";
        }
        return "redirect:/protected/bookmarks";
    }

}
