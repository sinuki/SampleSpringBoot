package com.parksw.app.global.security.handler.redirect.impl;

import com.parksw.app.global.security.handler.redirect.RedirectUrlDecider;
import lombok.NonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessDeniedUrlDecider extends RedirectUrlDecider {

    public AccessDeniedUrlDecider(@NonNull String defaultUrl) {
        super(defaultUrl);
    }

    @Override
    public String decide(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        return super.getRedirectUrlFromHeader(request);
    }
}
