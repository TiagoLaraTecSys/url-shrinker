package com.ls.url_shorter.service;

import com.ls.url_shorter.controller.ShortUrlRequest;
import com.ls.url_shorter.controller.ShortUrlResponse;
import com.ls.url_shorter.repository.UrlShortRepository;
import com.ls.url_shorter.repository.UrlShorterEntity;
import org.springframework.stereotype.Service;

@Service
public class ShorterUrlService {

    private final GenerateShortedCode generateShortedCode;
    private final UrlShortRepository repository;

    public ShorterUrlService(GenerateShortedCode generateShortedCode, UrlShortRepository repository) {
        this.generateShortedCode = generateShortedCode;
        this.repository = repository;
    }

    public String generateShortUrl(ShortUrlRequest input) {

        String urlPath = this.generateShortedCode.generateShortCode();

        UrlShorterEntity entity = new UrlShorterEntity();
        entity.setOriginalUrl(input.url());
        entity.setShortCode(urlPath);

        repository.save(entity);
        return urlPath;
    }
}