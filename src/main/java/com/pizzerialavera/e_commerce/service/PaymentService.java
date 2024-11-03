package com.pizzerialavera.e_commerce.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.pizzerialavera.e_commerce.entity.PaymentRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    public Payment createPayment(PaymentRequest paymentRequest) {
        MercadoPagoConfig.setAccessToken("YOUR_ACCESS_TOKEN");
        PaymentClient client = new PaymentClient();

        PaymentCreateRequest createRequest = PaymentCreateRequest.builder()
                .transactionAmount(paymentRequest.getTransactionAmount())
                .token(paymentRequest.getToken())
                .description(paymentRequest.getDescription())
                .installments(paymentRequest.getInstallments())
                .paymentMethodId(paymentRequest.getPaymentMethodId())
                .payer(PaymentPayerRequest.builder().email(paymentRequest.getPayerEmail()).build())
                .build();

        try {
            return client.create(createRequest);
        } catch (MPApiException ex) {
            // Manejo de errores
            System.out.printf("MercadoPago Error. Status: %s, Content: %s%n",
                    ex.getApiResponse().getStatusCode(), ex.getApiResponse().getContent());
            return null; // o lanzar una excepción personalizada
        } catch (MPException ex) {
            ex.printStackTrace();
            return null; // o lanzar una excepción personalizada
        }
    }
}

