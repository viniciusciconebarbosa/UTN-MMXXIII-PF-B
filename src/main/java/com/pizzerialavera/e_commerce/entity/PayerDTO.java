package com.pizzerialavera.e_commerce.entity;

import jakarta.validation.constraints.NotNull;

public class PayerDTO {
    @NotNull
    private String email;

    @NotNull
    private PayerIdenticationDTO identification;

    public PayerDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PayerIdenticationDTO getIdentification() {
        return identification;
    }

    public void setIdentification(PayerIdenticationDTO identification) {
        this.identification = identification;
    }
}
