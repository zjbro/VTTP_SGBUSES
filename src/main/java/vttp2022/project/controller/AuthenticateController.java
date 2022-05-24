package vttp2022.project.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vttp2022.project.services.UsersService;

@Controller
@RequestMapping("/authenticate")
public class AuthenticateController {
    
    @Autowired
    private UsersService usersSvc;

    @GetMapping("/logout")
    public ModelAndView getLogout(HttpSession sess) {
        sess.invalidate();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        mav.setStatus(HttpStatus.OK);
        return mav;
    }

    @PostMapping
    public ModelAndView postMethodName(@RequestBody MultiValueMap<String, String> payload
            , HttpSession sess) {

        String username = payload.getFirst("username");
        String password = payload.getFirst("password");

        System.out.printf("+++ username: %s, password: %s\n", username, password);

        ModelAndView mvc = new ModelAndView();

        if (!usersSvc.authenticate(username, password)) {
            // Not successful
            mvc.setViewName("error");
            mvc.addObject("error","Invalid username or password, please try again.");
            mvc.setStatus(HttpStatus.FORBIDDEN);
            return mvc;

        } else {
            // Successful
            sess.setAttribute("username", username);
            mvc = new ModelAndView("redirect:/protected/bookmarks");
        
        return mvc;
        }
    }
    
}
