package com.metacoding.order.adapter;

import com.metacoding.order.adapter.dto.DeliveryRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class DeliveryClient {

    private final RestClient restClient;

    public DeliveryClient(RestClient.Builder restClientBuilder, @Value("${client.delivery.url}") String baseUrl) {
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    public void createDelivery(DeliveryRequest requestDTO) {
        restClient.post()
                .uri("/api/deliveries")
                .body(requestDTO)
                .retrieve()
                .toBodilessEntity();
    }

    public void cancelDelivery(int orderId) {
        restClient.put()
                .uri("/api/deliveries/{orderId}", orderId)
                .retrieve()
                .toBodilessEntity();
    }
}