package com.parksw.app.global.security.config;

import com.parksw.app.global.security.handler.CustomAccessDeniedHandler;
import com.parksw.app.global.security.handler.CustomAuthenticationFailureHandler;
import com.parksw.app.global.security.handler.CustomAuthenticationProvider;
import com.parksw.app.global.security.handler.CustomAuthenticationSuccessHandler;
import com.parksw.app.global.security.handler.redirect.impl.AccessDeniedUrlDecider;
import com.parksw.app.global.security.handler.redirect.impl.DefaultRedirectUrlDecider;
import com.parksw.app.user.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Getter
    @RequiredArgsConstructor
    public enum LoginParameter {
        USERNAME("username"),
        CREDENTIAL("credential");
        private final String parameterName;
    }

    private final MemberRepository memberRepository;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .usernameParameter(LoginParameter.USERNAME.getParameterName())
                .passwordParameter(LoginParameter.CREDENTIAL.getParameterName())
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/loginProcess")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
            .and()
                .logout()
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/")
            .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler())
            .and()
                .authorizeRequests()
                    .antMatchers("/", "/auth/login*").permitAll()
                    .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(memberRepository, passwordEncoder());
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(new DefaultRedirectUrlDecider("/"), new DefaultRedirectStrategy());
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler(new DefaultRedirectStrategy());
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler(new AccessDeniedUrlDecider("/"), new DefaultRedirectStrategy());
    }
}
