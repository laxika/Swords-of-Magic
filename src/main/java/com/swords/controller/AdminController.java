package com.swords.controller;

import com.swords.component.ExpansionLoader;
import com.swords.controller.response.LoginResponse;
import com.swords.controller.response.StatisticsResponse;
import com.swords.model.*;
import com.swords.model.repository.*;
import com.swords.session.SessionType;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
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
    private CardCollectionRepository cardCollectionRepository;
    @Autowired
    private ExpansionCollectionRepository expansionCollectionRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private StatisticsRepository statisticsRepository;

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

    @ResponseBody
    @RequestMapping(value = "/admin/statistics", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public StatisticsResponse index(HttpSession session) {
        List<Statistics> statisticsList = statisticsRepository.findByBetweenDate(new DateTime().withTimeAtStartOfDay().minusDays(7).getMillis(), new DateTime().withTimeAtStartOfDay().getMillis());
        System.out.println("LIST: "+statisticsList.size());

        StatisticsResponse response = new StatisticsResponse();

        for(Statistics statistics : statisticsList) {
            System.out.println("ADDED: "+statistics.getRare());
            response.addDayData(statistics);
        }

        return response;
    }

    @RequestMapping(value = "/admin/collection/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResponseEntity collectionUpdate(@RequestParam("card") String cardId, @RequestParam("field") String field, @RequestParam("value") String value) {
        Card card = cardRepository.findById(cardId);

        if (value.isEmpty() || !StringUtils.isNumeric(value) || card == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        CardCollection collection = cardCollectionRepository.findByIdOrCreateIfNotExists(cardId);
        
        int difference = Integer.parseInt(value) - collection.getAmountByName(field);
        
        collection.setAmountByName(field, Integer.parseInt(value));
        
        ExpansionCollection expansionCollection = expansionCollectionRepository.findByIdOrCreateIfNotExists(card.getExpansion());
        expansionCollection.setRarityByName(card.getRarity(), expansionCollection.getRarityByName(card.getRarity()) + difference);
        
        cardCollectionRepository.save(collection);
        expansionCollectionRepository.save(expansionCollection);

        Statistics statistics = this.statisticsRepository.findToday();
        statistics.setRarityStatisticsByName(card.getRarity(), statistics.getRarityByName(card.getRarity()) + difference);

        statisticsRepository.save(statistics);

        return new ResponseEntity(HttpStatus.OK);
    }
}
