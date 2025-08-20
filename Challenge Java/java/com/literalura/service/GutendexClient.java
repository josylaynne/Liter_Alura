package com.literalura.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.dto.GutendexBookDto;
import com.literalura.dto.GutendexResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

/**
 * Cliente HTTP usando HttpClient/HttpRequest/HttpResponse para consultar a API Gutendex.
 */
@Component
public class GutendexClient {

    private final HttpClient http;
    private final ObjectMapper mapper;

    @Value("${app.gutendex.base-url}")
    private String baseUrl;

    public GutendexClient() {
        this.http = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Optional<GutendexBookDto> buscarPrimeiroPorTitulo(String titulo) throws IOException, InterruptedException {
        String q = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        URI uri = URI.create(baseUrl + "?search=" + q);
        HttpRequest req = HttpRequest.newBuilder(uri)
                .GET()
                .timeout(Duration.ofSeconds(20))
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new IOException("HTTP " + resp.statusCode() + " ao consultar Gutendex");
        }
        GutendexResponse parsed = mapper.readValue(resp.body(), GutendexResponse.class);
        if (parsed.getResults() == null || parsed.getResults().isEmpty()) return Optional.empty();
        return Optional.of(parsed.getResults().get(0));
    }
}
