package com.ls.url_shorter.exceptions;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String shortCode) {
        super("Short URL not found: " + shortCode);
    }
}