package com.pizzerialavera.e_commerce.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PreferenceService {

    @Value("${mercado_pago_ACCESS_TOKEN}")
    private String accessToken;

    public Preference createPreference(com.pizzerialavera.e_commerce.entity.PreferenceRequest preferenceRequest) {
        MercadoPagoConfig.setAccessToken(accessToken);
        PreferenceClient client = new PreferenceClient();

        // Crear un item de preferencia
        List<PreferenceItemRequest> items = new ArrayList<>();
        for (com.pizzerialavera.e_commerce.entity.PreferenceRequest.PreferenceItem item : preferenceRequest.getItems()) {
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id(item.getId())
                    .title(item.getTitle())
                    .unitPrice(item.getUnitPrice())
                    .quantity(item.getQuantity())
                    .build();
            items.add(itemRequest);
        }

        // Crear la solicitud de preferencia
        PreferenceRequest preferenceRequestToSend = PreferenceRequest.builder()
                .items(items)
                .backUrls(PreferenceBackUrlsRequest.builder()
                        .success(preferenceRequest.getSuccessUrl())
                        .failure(preferenceRequest.getFailureUrl())
                        .pending(preferenceRequest.getPendingUrl())
                        .build())
                .autoReturn("all")
                .payer(PreferencePayerRequest.builder()
                        .name("Test")
                        .surname("User")
                        .email("your_test_email@example.com")
                        .build())
                .build();

        // Crear la preferencia
        try {
            return client.create(preferenceRequestToSend);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
