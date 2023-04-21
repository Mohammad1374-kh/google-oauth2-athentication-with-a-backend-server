package com.khosravi.googleoauth2athentication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class Response {
    private boolean success;
    private String message;
    private Locale userLocale;
}
