package com.agungwicaksono.co.id.auth.security.jwt;

import com.agungwicaksono.co.id.auth.security.config.TokenManager;
import com.agungwicaksono.co.id.auth.security.implement.UserDetailsServiceImplement;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private UserDetailsServiceImplement userDetailsServiceImplement;
    private TokenManager tokenManager;

    public JwtFilter(UserDetailsServiceImplement userDetailsServiceImplement, TokenManager tokenManager) {
        this.userDetailsServiceImplement = userDetailsServiceImplement;
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        log.info("Ke 1 di doFilter : Mengambil data {}", tokenHeader);
        String userName = null;
        log.info("Ke 2 di doFilter : Inisialisasi username dengan value {}", userName);
        String token = null;
        log.info("Ke 3 di doFilter : Inisialisasi token dengan value {}", token);
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            token = tokenHeader.substring(7);
            log.info("Ke 4 Jika tokenHeader tidak null dan token header start with Bearer Ambil value token {} tanpa Bearer", token);
            try {
                userName = tokenManager.getUserNameFromToken(token);
                log.info("Mengambil username melalui token manager mengirim data token {} dan mendapat return username {}", token, userName);
            } catch (IllegalArgumentException e) {
                log.error("Gagal mengambil input header yang membawa token dengan error {}", e);
                System.out.println("Unable to get JWT token");
            } catch (ExpiredJwtException e) {
                log.error("Error Expired Json web token {}", e);
                System.out.println("JWT Token has expired");
            }
        } else {
            log.error("Header authorization tidak menggunakan Bearer");
            System.out.println("Bearer String Not Found In Token");
        }
        if (null != userName && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsServiceImplement.loadUserByUsername(userName);
            log.info("user details ketika username tidak null dan {} sama dengan null", SecurityContextHolder.getContext().getAuthentication());
            if (tokenManager.validateJwtToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null,userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("setelah validate jwt token true generate {}", authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("ngeset authentication di securitycontextholder dan jadinya {}", SecurityContextHolder.getContext());
            }
        }
        log.info("isi dari request {} dan response {}", request, response);
        filterChain.doFilter(request, response);
    }
}
