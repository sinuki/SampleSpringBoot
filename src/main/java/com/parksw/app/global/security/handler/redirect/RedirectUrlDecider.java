package com.parksw.app.global.security.handler.redirect;

import lombok.NonNull;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class RedirectUrlDecider {

    public enum RedirectMethod {
        URL_PARAMETER,
        SESSION,
        HEADER,
        DEFAULT_URL;
    }

    public static final String REDIRECT_URL_PARAMETER_NAME = "redirectUrl";
    private final HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
    private final String defaultUrl;

    public RedirectUrlDecider(@NonNull String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    public abstract String decide(HttpServletRequest request, HttpServletResponse response) throws IOException;

    protected String getRedirectUrlFromParameter(HttpServletRequest request, HttpServletResponse response) {
        SavedRequest saved = requestCache.getRequest(request, response);
        if (null != saved) {
            requestCache.removeRequest(request, response);
        }
        String redirectUrl = request.getParameter(REDIRECT_URL_PARAMETER_NAME);
        return null == redirectUrl ? "" : redirectUrl;
    }

    protected String getRedirectUrlFromSession(HttpServletRequest request, HttpServletResponse response) {
        SavedRequest saved = requestCache.getRequest(request, response);
        return null == saved ? "" : saved.getRedirectUrl();
    }

    protected String getRedirectUrlFromHeader(HttpServletRequest request) {
        String redirectUrl = request.getHeader("REFERER");
        return null == redirectUrl ? "" : redirectUrl;
    }

    protected String getDefaultUrl() {
        return this.defaultUrl;
    }
}
