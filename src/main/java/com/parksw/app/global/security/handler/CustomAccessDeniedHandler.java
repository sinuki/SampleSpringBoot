package com.parksw.app.global.security.handler;

import com.parksw.app.global.security.handler.redirect.RedirectUrlDecider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final RedirectUrlDecider redirectUrlDecider;
    private final RedirectStrategy redirectStrategy;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String redirectUrl = redirectUrlDecider.decide(request, response);
        redirectStrategy.sendRedirect(request, response, redirectUrl);
    }
}
