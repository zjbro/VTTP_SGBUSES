package vttp2022.project.controller;

import java.util.List;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        List<ArrivalBus> Buses = bService.arrivingBus(busStopCode);
        String description = bRepo.getDescription(busStopCode);
        if((null == description) || (description.trim().length() <= 0)){
            mav.setStatus(HttpStatus.NOT_FOUND);
            mav.addObject("error","Invalid entry, please enter a valid bus stop code.");
            mav.setViewName("invalidbusstopcode");
            return mav;
        } else {
        mav.setStatus(HttpStatus.OK);
        mav.addObject("busStopCode", busStopCode);
        mav.addObject("description", description);
        mav.addObject("arrivalbuses", Buses);
        mav.setViewName("busarrivaltiming");
        return mav;
        }
    }

    @PostMapping(path="/addBookmark")
    public ModelAndView addBookmark(@RequestBody MultiValueMap<String, String> payload, HttpSession sess){
        ModelAndView mav = new ModelAndView();
        String username = (String)sess.getAttribute("username");
        Boolean addBookmark = bService.addBookMark(payload, sess);
        if (addBookmark.hashCode()==1231){
        List<Bookmark> bookmarks = bService.retrieveBookmarks(username);
        mav.addObject("bookmarks", bookmarks);
        mav.addObject("username", username);
        mav.setStatus(HttpStatus.OK);
        mav.setViewName("bookmarks");
        return mav;
        } else
        mav.setViewName("error");
        mav.setStatus(HttpStatus.NOT_ACCEPTABLE);
        mav.addObject("error", "Bookmark already exists!");
        return mav;
    }

    @GetMapping(path="/deleteBookmark/{busStopCode}")
    public ModelAndView deleteBookmark(@PathVariable("busStopCode") String busStopCode, HttpSession sess){
        ModelAndView mav = new ModelAndView();
        String username = (String)sess.getAttribute("username");
        bService.deleteBookmark(busStopCode, sess);
        List<Bookmark> bookmarks = bService.retrieveBookmarks(username);
        mav.addObject("bookmarks", bookmarks);
        mav.addObject("username", username);
        mav.setStatus(HttpStatus.OK);
        mav.setViewName("bookmarks");
        return mav;
    }
        

    @GetMapping(path="/login")
    public ModelAndView loginPage(){
        ModelAndView mav = new ModelAndView();
        mav.setStatus(HttpStatus.OK);
        mav.setViewName("login");
        return mav;
    }


    @GetMapping(path="/register")
    public ModelAndView registrationPage(){
        ModelAndView mav = new ModelAndView();
        mav.setStatus(HttpStatus.OK);
        mav.setViewName("register");
        return mav;
    }

    @PostMapping(path="/adduser")
    public ModelAndView registrationNewUser(@RequestBody MultiValueMap<String, String> payload){
        if(bService.addUser(payload) == true){
            ModelAndView mav = new ModelAndView();
            mav.setStatus(HttpStatus.OK);
            mav.setViewName("registersuccess");
            return mav;
        } 
        ModelAndView mav = new ModelAndView();
        mav.setStatus(HttpStatus.BAD_REQUEST);
        mav.setViewName("registerfail");
        return mav;
    }

    @GetMapping(path="/deleteuser")
    public ModelAndView removeUser(HttpSession sess){
        ModelAndView mav = new ModelAndView();
        String username = (String)sess.getAttribute("username");
        if(bRepo.deleteUser(username) == true){
        sess.invalidate();
        mav.addObject("username", "username");
        mav.setViewName("deletesuccess");
        mav.setStatus(HttpStatus.OK);
        return mav;
        }
        mav.addObject("error", "There was an error trying to process your request. Please try again.");
        mav.setViewName("error");
        mav.setStatus(HttpStatus.NOT_ACCEPTABLE);
        return mav;
    }


    @GetMapping(path="/protected/bookmarks")
    public ModelAndView loginFirst(HttpSession sess){
        ModelAndView mav = new ModelAndView();
        String username = (String)sess.getAttribute("username");
        if((null == username) || (username.trim().length() <= 0)){
            mav.setViewName("login");
            mav.setStatus(HttpStatus.UNAUTHORIZED);
            return mav;
        }
        List<Bookmark> bookmarks = bService.retrieveBookmarks(username);
        mav.addObject("username", username);
        mav.addObject("bookmarks", bookmarks);
        mav.setViewName("bookmarks");
        return mav;
    }

    @GetMapping(path="/goback")
    public ModelAndView goBack(HttpSession sess){
        ModelAndView mav = new ModelAndView();
        String username = (String)sess.getAttribute("username");
        if((null == username) || (username.trim().length() <= 0)){
            mav.setStatus(HttpStatus.OK);
            mav.setViewName("index");
            return mav;
        }
        List<Bookmark> bookmarks = bService.retrieveBookmarks(username);
        mav.addObject("username", username);
        mav.addObject("bookmarks", bookmarks);
        mav.setStatus(HttpStatus.OK);
        mav.setViewName("bookmarks");
        return mav;
    }

    

}
