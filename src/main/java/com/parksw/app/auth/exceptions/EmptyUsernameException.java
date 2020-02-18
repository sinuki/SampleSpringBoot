package com.parksw.app.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class EmptyUsernameException extends AuthenticationException {

    public EmptyUsernameException() {
        super("로그인 정보가 올바르지 않아 로그인에 실패하였습니다.");
    }
}
