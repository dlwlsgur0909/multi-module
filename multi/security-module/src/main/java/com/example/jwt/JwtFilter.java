package com.example.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("Invalid Authorization - {}", request.getRequestURL());
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorization.split(" ")[1];

        if (accessToken == null || jwtUtil.isInvalid(accessToken)) {
            log.error("Invalid Token: Token - {}", accessToken);
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 파싱
        TokenInterface parsedTokenInfo = jwtUtil.parseToken(accessToken);

//        List<SimpleGrantedAuthority> authorityList = parsedTokenInfo.getRoleTypeList().stream()
//                .map(roleType -> new SimpleGrantedAuthority(roleType.name()))
//                .toList();
//        Authentication authentication = new UsernamePasswordAuthenticationToken(parsedTokenInfo, null, authorityList);
        Authentication authentication = new UsernamePasswordAuthenticationToken(parsedTokenInfo, null, Collections.emptyList());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
