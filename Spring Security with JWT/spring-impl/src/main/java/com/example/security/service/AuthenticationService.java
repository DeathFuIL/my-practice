package com.example.security.service;

import com.example.security.dto.AuthenticationRequest;
import com.example.security.dto.AuthenticationResponse;
import com.example.security.provider.JwtAccessTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationProvider authenticationProvider;

    private final JwtAccessTokenProvider jwtAccessTokenProvider;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken tokenToAuthenticate = UsernamePasswordAuthenticationToken.unauthenticated(
            authenticationRequest.email(),
            authenticationRequest.password()
        );

        Authentication authentication;
        try {
            authentication = authenticationProvider.authenticate(tokenToAuthenticate);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid login or password");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtAccessTokenProvider.createAccessToken(
                "user",
                Map.of(
                        "email", authentication.getName()
                )
        );
        String refreshToken = null;
        return new AuthenticationResponse(accessToken, refreshToken);
    }

}
