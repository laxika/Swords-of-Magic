package com.swords.controller;

import com.swords.component.ExpansionLoader;
import com.swords.controller.response.LoginResponse;
import com.swords.model.User;
import com.swords.model.repository.UserRepository;
import com.swords.session.SessionType;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpansionLoader expansionLoader;

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public String home() {
        return "admin/home";
    }

    @RequestMapping(value = "/admin/carddata", method = RequestMethod.GET)
    public String carddata() throws MalformedURLException, IOException {
        expansionLoader.reloadExpansionData();

        return "admin/home";
    }

    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
    public String login() {
        return "admin/login";
    }

    @ResponseBody
    @RequestMapping(value = "/admin/login", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public LoginResponse index(HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password) {
        User login = userRepository.queryByNameAndPass(username, password);

        LoginResponse response = new LoginResponse();

        if (login != null) {
            response.setSuccess(true);

            session.setAttribute(SessionType.USER, login);
        } else {
            response.setError("Hibás felhasználónév vagy jelszó!");
        }

        return response;
    }
}
