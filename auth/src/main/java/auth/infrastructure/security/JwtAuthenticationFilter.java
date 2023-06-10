package auth.infrastructure.security;

import auth.domain.enums.TokenTypeEnum;
import auth.infrastructure.payload.AccessTokenPayload;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final TokenStorage tokenStorage;
    private final HttpServletRequest request;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, TokenStorage tokenStorage, HttpServletRequest request) {
        this.userDetailsService = userDetailsService;
        this.tokenStorage = tokenStorage;
        this.request = request;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authHeader.substring(7);
        SecurityContext securityContext = SecurityContextHolder.getContext();

        if (securityContext.getAuthentication() == null) {
            AccessTokenPayload tokenPayload = (AccessTokenPayload) tokenStorage.validate(jwtToken, TokenTypeEnum.ACCESS);
            if (tokenPayload.getExp().after(new Date())) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(tokenPayload.getUsername());
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                securityContext.setAuthentication(authToken);

                request.setAttribute("username", userDetails.getUsername());
                request.setAttribute("authorities", userDetails.getAuthorities());

            }
            filterChain.doFilter(request, response);
        }

    }
}
