package com.java2024.ecoscape.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        if (stripeSecretKey != null && !stripeSecretKey.isEmpty()) {
            Stripe.apiKey = stripeSecretKey;  // تعيين مفتاح الـ Stripe API
            System.out.println("The Stripe key has been successfully set.");
        } else {
            System.err.println("The Stripe key is missing or empty.");
        }
    }
}