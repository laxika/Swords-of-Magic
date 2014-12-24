package com.swords.interceptor;

import com.swords.session.SessionType;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class SessionLocaleChangeInterceptor extends LocaleChangeInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        Locale newLocale = (Locale) request.getSession().getAttribute(SessionType.LOCALE);

        if (newLocale != null) {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver == null) {
                throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
            }

            localeResolver.setLocale(request, response, newLocale);
        }

        return true;
    }

}
