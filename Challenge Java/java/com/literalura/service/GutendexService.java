package com.literalura.service;

import com.literalura.domain.Livro;
import com.literalura.domain.Autor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GutendexService {

    private static final String URL_BASE = "https://gutendex.com/books/";

    public Livro buscarLivroPorTitulo(String titulo) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_BASE + "?search=" + titulo.replace(" ", "%20")))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());
        JsonNode results = root.get("results").get(0);

        Livro livro = new Livro();
        livro.setTitulo(results.get("title").asText());
        livro.setNumeroDownloads(results.get("download_count").asInt());
        livro.setIdioma(results.get("languages").get(0).asText());

        JsonNode authorNode = results.get("authors").get(0);
        Autor autor = new Autor();
        autor.setNome(authorNode.get("name").asText());
        autor.setAnoNascimento(authorNode.get("birth_year").isNull() ? null : authorNode.get("birth_year").asInt());
        autor.setAnoFalecimento(authorNode.get("death_year").isNull() ? null : authorNode.get("death_year").asInt());

        livro.setAutor(autor);

        return livro;
    }
}
