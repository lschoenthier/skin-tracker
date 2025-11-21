package com.ludwig.skintracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

@Component
public class CsFloatClient {

    private final WebClient webClient;

    public CsFloatClient(
            @Value("${csfloat.api-base}") String baseUrl,
            @Value("${csfloat.api-key}") String apiKey) {

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, apiKey)
                .build();
    }

    public Integer fetchLowestPrice(
            String marketHashName,
            Double minFloat,
            Double maxFloat,
            Integer paintSeed) {

        return webClient.get().uri(uri -> {
                    UriBuilder u = uri.path("/listings")
                            .queryParam("market_hash_name", marketHashName)
                            .queryParam("sort_by", "lowest_price")
                            .queryParam("limit", 1);
                    if (minFloat != null) u.queryParam("min_float", minFloat);
                    if (maxFloat != null) u.queryParam("max_float", maxFloat);
                    if (paintSeed != null) u.queryParam("paint_seed", paintSeed);
                    return u.build();
                })
                .retrieve()
                .onStatus(s -> s.value() == 429,
                        r -> Mono.error(new RuntimeException("Rate limit exceeded (429)")))
                .bodyToMono(JsonNode.class)
                .flatMap(node -> {
                    JsonNode arr = node.get("data");
                    if (arr == null || !arr.isArray() || arr.size() == 0) {
                        return Mono.empty();
                    }
                    int cents = arr.get(0).get("price").asInt();
                    return Mono.just(cents);
                })
                .blockOptional()
                .orElse(null);
    }

}
