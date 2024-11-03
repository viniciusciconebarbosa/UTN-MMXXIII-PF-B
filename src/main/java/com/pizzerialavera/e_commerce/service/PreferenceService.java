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

    @Value("${mercado_pago_access_token}")
    private String accessToken;

    public Preference createPreference() {
        MercadoPagoConfig.setAccessToken(accessToken); // Inyectar Access Token desde application.properties
        PreferenceClient client = new PreferenceClient();

        // Crear un item de preferencia
        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .id("1234")
                .title("Dummy Title")
                .description("Dummy description")
                .pictureUrl("https://www.myapp.com/myimage.jpg")
                .categoryId("car_electronics")
                .quantity(1)
                .currencyId("BRL") // Cambia a la moneda que estés utilizando
                .unitPrice(new BigDecimal("10"))
                .build();

        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

        // Crear la solicitud de preferencia
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(PreferenceBackUrlsRequest.builder()
                        .success("https://test.com/success")
                        .failure("https://test.com/failure")
                        .pending("https://test.com/pending")
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
            return client.create(preferenceRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
