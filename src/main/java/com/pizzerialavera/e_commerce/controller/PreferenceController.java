package com.pizzerialavera.e_commerce.controller;


import com.mercadopago.resources.preference.Preference;
import com.pizzerialavera.e_commerce.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;

    @GetMapping("/create_preference")
    public Preference createPreference() {
        return preferenceService.createPreference();
    }
}
