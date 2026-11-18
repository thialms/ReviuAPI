package br.edu.fatecpg.reviu.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SwaggerSessionFilter extends OncePerRequestFilter {

    public static final String SESSION_ATTRIBUTE = "SWAGGER_AUTHENTICATED";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getRequestURI();
        boolean swaggerResource =
                path.startsWith("/swagger-ui")
                        || path.startsWith("/v3/api-docs")
                        || "/swagger-ui.html".equals(path);

        if (swaggerResource) {
            HttpSession session = request.getSession(false);
            if (session == null || !Boolean.TRUE.equals(session.getAttribute(SESSION_ATTRIBUTE))) {
                response.sendRedirect("/swagger-login");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
