package com.parksw.app.global.security.handler.redirect.impl;

import com.parksw.app.global.security.handler.redirect.RedirectUrlDecider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DefaultRedirectUrlDecider extends RedirectUrlDecider {

    private final List<RedirectMethod> redirectOrders = Arrays.asList(RedirectMethod.URL_PARAMETER,
                                                                      RedirectMethod.SESSION,
                                                                      RedirectMethod.HEADER,
                                                                      RedirectMethod.DEFAULT_URL);

    public DefaultRedirectUrlDecider(String defaultUrl) {
        super(defaultUrl);
    }

    @Override
    public String decide(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectUrl = "";
        for (RedirectMethod order : redirectOrders) {
            switch (order) {
                case URL_PARAMETER: redirectUrl = super.getRedirectUrlFromParameter(request, response); break;
                case SESSION: redirectUrl = super.getRedirectUrlFromSession(request, response); break;
                case HEADER: redirectUrl = super.getRedirectUrlFromHeader(request); break;
                case DEFAULT_URL: redirectUrl = super.getDefaultUrl(); break;
                default: // nothing
            }
            if (!redirectUrl.isEmpty()) break;
        }
        return redirectUrl;
    }
}
