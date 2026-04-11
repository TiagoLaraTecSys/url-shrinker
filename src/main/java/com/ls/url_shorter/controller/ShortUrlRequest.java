package com.ls.url_shorter.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ShortUrlRequest(
        @Pattern(regexp = "^(http|https)://.*$",
                message = "URL inválida")
        @NotBlank(message = "URL não pode ser vazia")
        @Size(max = 2048, message = "URL muito longa")
        String url
) {
}