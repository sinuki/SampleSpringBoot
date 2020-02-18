package com.parksw.app.global.security.handler;

import com.parksw.app.auth.exceptions.EmptyUsernameException;
import com.parksw.app.auth.exceptions.InvalidPasswordException;
import com.parksw.app.auth.exceptions.NoExistMemberException;
import com.parksw.app.user.domain.User;
import com.parksw.app.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(noRollbackFor = AuthenticationException.class)
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String credentials = (String) authentication.getCredentials();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(credentials))  throw new EmptyUsernameException();

        User member = memberRepository.findByUsername(username).orElseThrow(NoExistMemberException::new);
        checkCredential(member, credentials);
        member.loginSuccess();
        Set<SimpleGrantedAuthority> authorities = getDefaultAuthorities();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, member.getCredential(), authorities);
        token.setDetails(member);
        return token;
    }

    private void checkCredential(User member, String credential) {
        if (!passwordEncoder.matches(credential, member.getCredential())) {
            member.loginFailed();
            throw new InvalidPasswordException();
        }
    }

    private Set<SimpleGrantedAuthority> getDefaultAuthorities() {
        HashSet<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("MEMBER"));
        return authorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
