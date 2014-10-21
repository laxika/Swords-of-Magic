package com.swords.controller;

import com.swords.controller.response.LoginResponse;
import com.swords.model.Expansion;
import com.swords.model.User;
import com.swords.model.repository.ExpansionRepository;
import com.swords.model.repository.UserRepository;
import com.swords.session.SessionType;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

    private static Logger log = Logger.getLogger(AdminController.class.getName());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpansionRepository expansionRepository;

    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
    public String login() {
        return "admin/login";
    }

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public String home() {
        return "admin/home";
    }

    @RequestMapping(value = "/admin/carddata", method = RequestMethod.GET)
    public String carddata() throws MalformedURLException, IOException {
        log.debug("Started loading the cards!");

        try {
            URL setdata = new URL("http://mtgjson.com/json/AllSets-x.json");
            Reader in = new InputStreamReader(setdata.openStream());

            JSONObject json = new JSONObject(new JSONTokener(in));
            log.debug(json.keySet().size());
            Iterator<?> keys = json.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                log.debug(key);

                JSONObject obj = (JSONObject) json.get(key);
                log.debug(obj.getString("name"));

                Expansion expansion = new Expansion(obj.getString("code"));
                expansion.setName(obj.getString("name"));
                expansion.setReleaseDate(obj.getString("releaseDate"));
                expansion.setBorder(obj.getString("border"));
                expansion.setType(obj.getString("type"));

                if (obj.has("block")) {
                    expansion.setBlock(obj.getString("block"));
                }

                if (obj.has("onlineOnly")) {
                    expansion.setOnlineOnly(true);
                }

                if (obj.has("booster")) {
                    //Todo
                }

                expansionRepository.save(expansion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "admin/home";
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
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
