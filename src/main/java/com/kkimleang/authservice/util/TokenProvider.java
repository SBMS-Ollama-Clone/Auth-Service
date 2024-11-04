package com.kkimleang.authservice.util;

import com.kkimleang.authservice.config.properties.TokenProperties;
import com.kkimleang.authservice.model.User;
import com.kkimleang.authservice.service.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {
    @Value("${token.issuer}")
    private String issuer;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    private final TokenProperties tokenProperties;

    public String createAccessToken(Authentication authentication) {
        return buildToken(authentication, tokenProperties.getAccessTokenExpiresHours());
    }

    public String createAccessToken(User user) {
        Instant now = Instant.now();
        var jwtClaimsSet = JwtClaimsSet.builder()
                .issuer(issuer)
                .subject(user.getEmail())
                .issuedAt(now)
                .expiresAt(now.plus(tokenProperties.getAccessTokenExpiresHours(), ChronoUnit.HOURS))
                .claim(JwtUtils.EMAIL.getProperty(), user.getEmail())
                .claim(JwtUtils.SCOPE.getProperty(), user.getRoles());
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet.build())).getTokenValue();
    }

    public String createRefreshToken(Authentication authentication) {
        return buildToken(authentication, tokenProperties.getRefreshTokenExpiresHours());
    }

    private String buildToken(Authentication authentication, Integer expiresHours) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Instant now = Instant.now();
        var jwtClaimsSet = JwtClaimsSet.builder()
                .issuer(issuer)
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiresAt(now.plus(expiresHours, ChronoUnit.HOURS))
                .claim(JwtUtils.EMAIL.getProperty(), userDetails.getEmail())
                .claim(JwtUtils.SCOPE.getProperty(), authentication.getAuthorities());
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet.build())).getTokenValue();
    }

    public String getUserEmailFromToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            return jwt.getClaim(JwtUtils.EMAIL.getProperty());
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isTokenValid(String token, User user) {
        final String username = getUserEmailFromToken(token);
        return (username.equals(user.getEmail())) && isTokenNotExpired(token);
    }

    public boolean isTokenNotExpired(String token) {
        try {
            if (token != null) {
                Instant exp = jwtDecoder.decode(token).getExpiresAt();
                if (exp != null) {
                    return !(exp.isBefore(Instant.now()));
                } else {
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("Token is expired or invalid: {}", e.getMessage());
            return false;
        }
    }

    public Instant getExpirationDateFromToken(String accessToken) {
        try {
            return jwtDecoder.decode(accessToken).getExpiresAt();
        } catch (Exception e) {
            return null;
        }
    }
}
