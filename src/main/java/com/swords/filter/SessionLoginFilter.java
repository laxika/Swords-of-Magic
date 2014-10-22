package com.swords.filter;

import com.swords.controller.AdminController;
import com.swords.session.SessionType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionLoginFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Override
    public void init(FilterConfig fc) throws ServletException {
        logger.info("Initialising SessionCheckerFilter");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (!request.getRequestURI().endsWith("login") && request.getSession().getAttribute(SessionType.USER) == null) {
            response.sendRedirect("/");
        }
        
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        logger.info("Destroying SessionCheckerFilter");
    }
}
