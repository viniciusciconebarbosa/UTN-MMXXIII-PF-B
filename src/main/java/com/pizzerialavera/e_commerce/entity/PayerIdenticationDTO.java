package com.pizzerialavera.e_commerce.entity;

import jakarta.validation.constraints.NotNull;

public class PayerIdenticationDTO {

    @NotNull
    private String type;

    @NotNull
    private String number;

    public PayerIdenticationDTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
