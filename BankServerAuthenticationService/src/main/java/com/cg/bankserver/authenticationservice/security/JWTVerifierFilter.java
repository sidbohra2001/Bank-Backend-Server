package com.cg.bankserver.authenticationservice.security;

import com.cg.bankserver.authenticationservice.dao.UserDAO;
import com.cg.bankserver.authenticationservice.entities.User;
import com.cg.bankserver.authenticationservice.services.UserService;
import com.cg.bankserver.authenticationservice.services.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cg.bankserver.authenticationservice.config.SecurityConstants;
import com.cg.bankserver.authenticationservice.config.Utilities;
import com.cg.bankserver.authenticationservice.entities.TokensEntity;
import com.cg.bankserver.authenticationservice.services.TokensRedisService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JWTVerifierFilter extends OncePerRequestFilter {

    private final TokensRedisService tokensRedisService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if(!(Utilities.validString(bearerToken) && bearerToken.startsWith(SecurityConstants.PREFIX))) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String authToken = bearerToken.replace(SecurityConstants.PREFIX, "");
        log.info("auth token : " + authToken);

        Optional<TokensEntity> tokensEntity = tokensRedisService.findById(authToken);

        if(tokensEntity.isEmpty()) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String token = tokensEntity.get().getAuthenticationToken();
        log.info("token : " + token);
        var key = Keys.hmacShaKeyFor(SecurityConstants.KEY.getBytes());
        Jws<Claims> authClaim = Jwts.parserBuilder().setSigningKey(key)
                .requireIssuer(SecurityConstants.ISSUER)
                .build().parseClaimsJws(token);

        String username = authClaim.getBody().getSubject();

        log.info("username : " + username);

        List<Map<String, String>> authorities = (List<Map<String, String>>) authClaim.getBody().get("authorities");
        List<GrantedAuthority> grantedAuthorities = authorities.stream().map(map -> new SimpleGrantedAuthority(map.get("authority")))
                .collect(Collectors.toList());
        log.info(grantedAuthorities.toString());
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
        log.info("authentication " + authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.getByUsrName(username).get();

        log.info("User : " + user);

        httpServletRequest.setAttribute("username", username);
        httpServletRequest.setAttribute("userId", user.getId());
        httpServletRequest.setAttribute("authorities", grantedAuthorities);

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}