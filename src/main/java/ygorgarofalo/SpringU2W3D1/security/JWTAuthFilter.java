package ygorgarofalo.SpringU2W3D1.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import ygorgarofalo.SpringU2W3D1.entities.User;
import ygorgarofalo.SpringU2W3D1.exceptions.UnhautorizedExc;
import ygorgarofalo.SpringU2W3D1.services.UserService;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // verifico se nell'header ci sia un campo authorization
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnhautorizedExc("Per favore allega il token nell'Authorization header");
        } else {

            //estraggo dall'header il token togliendo la parte "Bearer "
            String accessToken = authHeader.substring(7);

            //verifico se il token sia scaduto o meno
            jwtTools.verifyToken(accessToken);


            //ricerco lo user sul db usando l'id cifrato all'interno del token

            String userId = jwtTools.extractIdFromToken(accessToken);
            User found = userService.findById(Long.parseLong(userId));


            //informo spring security che lo user è autenticato
            Authentication authentication = new UsernamePasswordAuthenticationToken(found, null,
                    found.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        }

    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
