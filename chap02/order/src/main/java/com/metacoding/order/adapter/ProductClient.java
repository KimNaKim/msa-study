package com.metacoding.order.adapter;

import com.metacoding.order.adapter.dto.ProductRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductClient {

    private final RestClient restClient;

    public ProductClient(RestClient.Builder restClientBuilder, @Value("${client.product.url}") String baseUrl) {
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    public void decreaseQuantity(ProductRequest requestDTO) {
        restClient.put()
                .uri("/api/products/{productId}/decrease", requestDTO.productId())
                .body(requestDTO)
                .retrieve()
                .toBodilessEntity();
    }

    public void increaseQuantity(ProductRequest requestDTO) {
        restClient.put()
                .uri("/api/products/{productId}/increase", requestDTO.productId())
                .body(requestDTO)
                .retrieve()
                .toBodilessEntity();
    }
}