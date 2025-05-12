package com.java2024.ecoscape.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    String stripeSecretKey = "sk_test_51RLSCYGPXE58N028wrhsU6XgXO5kA8GN8te5T27944UjZnNSagMf3NZ4GQmbewcN1gGoxGQykbbhoGvvb4Y4RYVa00653R0PCF";

    public StripeConfig() {
        if (stripeSecretKey != null && !stripeSecretKey.isEmpty()) {
            Stripe.apiKey = stripeSecretKey;  // تعيين مفتاح الـ Stripe API
            System.out.println("The Stripe key has been successfully set.");
        } else {
            System.err.println("The Stripe key is missing or empty.");
        }
    }
}
