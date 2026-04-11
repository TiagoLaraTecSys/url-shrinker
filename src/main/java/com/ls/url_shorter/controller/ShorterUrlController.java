package com.ls.url_shorter.controller;

import com.ls.url_shorter.service.ShorterUrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;

@RestController
public class ShorterUrlController {

    private final ShorterUrlService service;

    public ShorterUrlController(ShorterUrlService service) {
        this.service = service;
    }

    @PostMapping(value = "/api/v1/shorter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShortUrlResponse> shortUrl(@Valid @RequestBody ShortUrlRequest input, HttpServletRequest request) {

        String urlPath = this.service.generateShortUrl(input);
        String url = request.getRequestURL().toString()
                .replace(request.getRequestURI(), "");

        ShortUrlResponse shortUrlResponse = new ShortUrlResponse(
                url.concat("/").concat(urlPath)
        );

        return ResponseEntity.ok(shortUrlResponse);

    }

    @GetMapping(value = "/{shortCode}")
    public ResponseEntity<Void> getOriginalUrlRedirect(
            @PathVariable String shortCode,
            HttpServletResponse response
    ) throws IOException {

        String originalUrl = this.service.getOriginalUrl(shortCode);

        return ResponseEntity
                .status(302)
                .location(URI.create(originalUrl))
                .build();
    }
}