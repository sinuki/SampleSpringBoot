package com.parksw.app.global.security.handler;

import com.parksw.app.auth.dto.LoginForm;
import com.parksw.app.global.security.config.SecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final RedirectStrategy redirectStrategy;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter(SecurityConfig.LoginParameter.USERNAME.getParameterName());
        String credentials = request.getParameter(SecurityConfig.LoginParameter.CREDENTIAL.getParameterName());
        FlashMapManager flashManager = RequestContextUtils.getFlashMapManager(request);
        if (null == flashManager) {
            flashManager = new SessionFlashMapManager();
        }
        FlashMap flashMap = new FlashMap();
        flashMap.put("loginForm", new LoginForm(username, credentials, exception.getMessage()));
        flashManager.saveOutputFlashMap(flashMap, request, response);
        redirectStrategy.sendRedirect(request, response, "/auth/login");
    }
}
