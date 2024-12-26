package ogya.lokakarya.be.config.security.jwt.filter;

import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ogya.lokakarya.be.config.security.jwt.JwtService;
import ogya.lokakarya.be.service.AuthService;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private JwtService jwtSvc;

    @Autowired
    private AuthService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            final String jwt = authHeader.substring(7);
            final String subject = jwtSvc.extractSubject(jwt);

            if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null
                    && jwtSvc.isTokenValid(jwt)) {
                UserDetails userDetails = userDetailsService.loadByUserId(UUID.fromString(subject));
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
