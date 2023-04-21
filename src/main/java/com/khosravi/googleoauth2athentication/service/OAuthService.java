package com.khosravi.googleoauth2athentication.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.khosravi.googleoauth2athentication.dto.response.Response;
import com.khosravi.googleoauth2athentication.entity.User;
import com.khosravi.googleoauth2athentication.entity.UserDetails;
import com.khosravi.googleoauth2athentication.exceptions.*;
import com.khosravi.googleoauth2athentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Locale;

import static com.khosravi.googleoauth2athentication.entity.enums.Role.NORMAL;
import static com.khosravi.googleoauth2athentication.specification.EmailIs.emailIs;
import static java.time.LocalDateTime.now;
import static java.util.Objects.nonNull;
import static org.springframework.data.jpa.domain.Specification.where;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {
    private final UserRepository userRepository;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Transactional
    public Response signup(NetHttpTransport transport, GsonFactory factory, HttpServletRequest request) {

        GoogleIdToken.Payload payload = verifyGoogleIdToken(transport, factory, request).getPayload();

        String email = payload.getEmail();

        // check if the user signed up before
        userRepository.findOne(where(emailIs(email)))
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException();
                });
        // save user
        User user = User.createUserViaGoogleOAuth(email);

        UserDetails userDetails = UserDetails.builder()
                .name(payload.get("name").toString())
                .profileUrl(payload.get("picture").toString())
                .userLocale(payload.get("locale").toString())
                .build();

        user.setUserDetails(userDetails);

        userRepository.save(user);

        return Response.builder()
                .userLocale(new Locale(payload.get("locale").toString()))
                .build();

    }

    @Transactional
    public Response login(NetHttpTransport transport, GsonFactory factory, HttpServletRequest request) {

        GoogleIdToken.Payload payload = verifyGoogleIdToken(transport, factory, request).getPayload();

        String email = payload.getEmail();

        User user = userRepository.findOne(where(emailIs(email)))
                .orElseThrow(UserNotFoundException::new);

        checkIfExpired(user);

        return Response.builder()
                .userLocale(new Locale(payload.get("locale").toString()))
                .build();
    }

    private void checkIfExpired(User user) {
        if (nonNull(user.getRoleExpiresAt()) && user.getRoleExpiresAt().isBefore(now())) {
            user.setRole(NORMAL);
            user.setRoleExpiresAt(null);
            userRepository.save(user);
        }
    }

    private GoogleIdToken verifyGoogleIdToken(NetHttpTransport transport, GsonFactory factory, HttpServletRequest request) {

        // get id_token from Authorization Bearer
        String token = getTokenFromRequest(request);

        // Create verifier
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, factory)
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken idToken;

        try {
            // Verify it
            idToken = verifier.verify(token);
        } catch (GeneralSecurityException | IOException gse) {
            gse.printStackTrace();
            throw new GoogleIdTokenVerifierException();
        }

        if (idToken == null) {
            throw new InvalidGoogleIdTokenException();
        }

        return idToken;
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String[] parts = token.split(" ");
        if (parts.length != 2 || !parts[0].contains("Bearer")) {
            throw new InvalidGoogleBearerTokenException();
        }
        return parts[1];
    }

}
