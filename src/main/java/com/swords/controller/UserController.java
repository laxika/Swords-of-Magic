package com.swords.controller;


import com.swords.controller.response.UserDataResponse;
import com.swords.model.User;
import com.swords.session.SessionType;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @ResponseBody
    @RequestMapping(value = "/locale/{locale}")
    public RedirectView index(HttpSession session, @PathVariable String locale) {
        session.setAttribute(SessionType.LOCALE, LocaleUtils.toLocale(locale));

        return new RedirectView("/");
    }

    @ResponseBody
    @RequestMapping(value = "/user/data")
    public UserDataResponse userData(HttpSession session) {
        UserDataResponse response = new UserDataResponse();

        response.setLoggedIn(session.getAttribute(SessionType.USER) != null);

        return response;
    }
}
