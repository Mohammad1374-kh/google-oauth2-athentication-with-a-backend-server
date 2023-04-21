package com.khosravi.googleoauth2athentication.controller;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.khosravi.googleoauth2athentication.service.OAuthService;
import com.khosravi.googleoauth2athentication.dto.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

import static java.lang.Boolean.TRUE;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("oauth")
@RequiredArgsConstructor
public class OAuthController {
    private final OAuthService oAuthService;
    private final MessageSource messageSource;

    @ResponseStatus(OK)
    @PostMapping("signup")
    public Response signup(@Autowired NetHttpTransport transport, @Autowired GsonFactory factory, HttpServletRequest request) {

        Response response = oAuthService.signup(transport, factory, request);

        return Response.builder()
                .message(messageSource.getMessage("auth.signup.success", null, response.getUserLocale()))
                .success(TRUE)
                .build();

    }

    @PostMapping("login")
    @ResponseStatus(OK)
    public Response login(@Autowired NetHttpTransport transport, @Autowired GsonFactory factory, HttpServletRequest request) {

        Response response = oAuthService.login(transport, factory, request);

        return Response.builder()
                .message(messageSource.getMessage("auth.login.success", null, response.getUserLocale()))
                .success(TRUE)
                .build();
    }
}
