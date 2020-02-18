package com.parksw.app.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {

    @NotEmpty(message = "로그인 ID는 필수 입력값입니다.")
    private String username;

    @NotEmpty(message = "로그인 비밀번호는 필수 입력값입니다.")
    private String credential;

    private String errorMessage;
}
