package com.example.currencyexchange.util;

public class Utils {

    private static final String BEARER = "Bearer ";

    public static String prepareAuthorizationHeader(String token) {
        return BEARER + token;
    }
}
