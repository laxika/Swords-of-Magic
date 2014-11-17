package com.swords.controller;

import com.swords.component.ExpansionLoader;
import com.swords.controller.response.LoginResponse;
import com.swords.model.User;
import com.swords.model.repository.CardRepository;
import com.swords.model.repository.CollectionRepository;
import com.swords.model.repository.UserRepository;
import com.swords.session.SessionType;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpansionLoader expansionLoader;
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private CardRepository cardRepository;

    @RequestMapping(value = "/admin/index", method = RequestMethod.GET)
    public String home() {
        return "admin/index";
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

    @RequestMapping(value = "/admin/collection/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResponseEntity collectionUpdate(@RequestParam("card") String card, @RequestParam("field") String field, @RequestParam("value") String value) {
        Query existsQuery = new Query();
        existsQuery.addCriteria(Criteria.where("id").is(card));
        
        if (value.isEmpty() || !StringUtils.isNumeric(value) || !cardRepository.exists(existsQuery)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Update update = new Update();
        update.set(field, value);

        collectionRepository.insertOrUpdateIfExists(existsQuery, update);

        return new ResponseEntity(HttpStatus.OK);
    }
}
