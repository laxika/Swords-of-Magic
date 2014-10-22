package com.swords.controller;

import com.swords.controller.response.LoginResponse;
import com.swords.model.Card;
import com.swords.model.Expansion;
import com.swords.model.User;
import com.swords.model.repository.CardRepository;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpansionRepository expansionRepository;
    @Autowired
    private CardRepository cardRepository;

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public String home() {
        return "admin/home";
    }

    @RequestMapping(value = "/admin/carddata", method = RequestMethod.GET)
    public String carddata() throws MalformedURLException, IOException {
        logger.info("Started loading the cards!");

        //TODO: refactor the whole thing, it's a test pscheudo code
        try {
            URL setdata = new URL("http://mtgjson.com/json/AllSets-x.json");
            Reader in = new InputStreamReader(setdata.openStream());

            JSONObject json = new JSONObject(new JSONTokener(in));
            logger.info(Integer.toString(json.keySet().size()));
            Iterator<?> keys = json.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                logger.info(key);

                JSONObject obj = (JSONObject) json.get(key);
                logger.info(obj.getString("name"));

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

                expansionRepository.insert(expansion);

                JSONArray cards = obj.getJSONArray("cards");
                for (int i = 0; i < cards.length(); i++) {
                    JSONObject cardObj = cards.getJSONObject(i);

                    Card card = new Card(obj.getString("code") + "_" + cardObj.getString("imageName"));
                    card.setName(cardObj.getString("name"));

                    if (cardObj.has("colors")) {
                        JSONArray colors = cardObj.getJSONArray("colors");

                        String[] finalColors = new String[colors.length()];
                        for (int colorId = 0; colorId < colors.length(); colorId++) {
                            finalColors[colorId] = colors.getString(colorId);
                        }

                        card.setColor(finalColors);
                    }

                    if (cardObj.has("manaCost")) {
                        card.setManacost(cardObj.getString("manaCost"));
                    }

                    cardRepository.insert(card);
                }
            }
        } catch (IOException | JSONException e) {
            logger.error(e.getMessage());
        }

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
