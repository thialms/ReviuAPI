package br.edu.fatecpg.reviu.controllers;

import br.edu.fatecpg.reviu.infra.security.SwaggerSessionFilter;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Hidden
@RestController
public class SwaggerLoginController {

    @Value("${swagger.username}")
    private String username;

    @Value("${swagger.password}")
    private String password;

    @GetMapping("/swagger-login")
    public void loginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null && Boolean.TRUE.equals(session.getAttribute(SwaggerSessionFilter.SESSION_ATTRIBUTE))) {
            response.sendRedirect("/swagger-ui/index.html");
            return;
        }

        response.sendRedirect("/swagger-login.html");
    }

    @PostMapping("/swagger-login")
    public void authenticate(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        boolean validUsername = MessageDigest.isEqual(
                this.username.getBytes(StandardCharsets.UTF_8),
                username.getBytes(StandardCharsets.UTF_8)
        );
        boolean validPassword = MessageDigest.isEqual(
                this.password.getBytes(StandardCharsets.UTF_8),
                password.getBytes(StandardCharsets.UTF_8)
        );

        if (!validUsername || !validPassword) {
            response.sendRedirect("/swagger-login.html?error");
            return;
        }

        request.getSession(true).setAttribute(SwaggerSessionFilter.SESSION_ATTRIBUTE, true);
        response.sendRedirect("/swagger-ui/index.html");
    }
}
