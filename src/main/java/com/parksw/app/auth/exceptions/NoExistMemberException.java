package com.parksw.app.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class NoExistMemberException extends AuthenticationException {

    public NoExistMemberException() {
        super("존재하지 않는 회원입니다.");
    }
}
