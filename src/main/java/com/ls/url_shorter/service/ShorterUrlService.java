package com.ls.url_shorter.service;

import com.ls.url_shorter.controller.ShortUrlRequest;
import com.ls.url_shorter.controller.ShortUrlResponse;
import org.springframework.stereotype.Service;

@Service
public class ShorterUrlService {

    private final GenerateShortedCode generateShortedCode;

    public ShorterUrlService(GenerateShortedCode generateShortedCode) {
        this.generateShortedCode = generateShortedCode;
    }

    public String generateShortUrl(ShortUrlRequest input) {

        String urlPath = this.generateShortedCode.generateShortCode();

        return urlPath;
    }
}