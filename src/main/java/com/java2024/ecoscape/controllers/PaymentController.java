package com.java2024.ecoscape.controllers;


import com.java2024.ecoscape.dto.PaymentRequest;
import com.java2024.ecoscape.dto.PaymentResponse;
import com.java2024.ecoscape.models.Payment;
import com.java2024.ecoscape.services.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);


    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<PaymentResponse> createPaymentIntent(@RequestBody PaymentRequest paymentRequest)
            throws StripeException {
        logger.info("Received payment request for amount: {} and currency: {}", paymentRequest.getAmount(), paymentRequest.getCurrency());

        try {
            // إنشاء PaymentIntent باستخدام الخدمة
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentRequest.getAmount(), paymentRequest.getCurrency());

            // إنشاء كائن استجابة مخصص يحتوي على البيانات المطلوبة
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setPaymentIntentId(paymentIntent.getId());
            paymentResponse.setAmount(paymentIntent.getAmount());
            paymentResponse.setCurrency(paymentIntent.getCurrency());
            paymentResponse.setMessage("PaymentIntent created successfully with ID: " + paymentIntent.getId());

            logger.info("PaymentIntent created successfully with ID: {}", paymentIntent.getId());
            return ok(paymentResponse);
        } catch (StripeException e) {
            logger.error("Error occurred during payment creation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PaymentResponse("Error: " + e.getMessage()));
        }
    }

    @PostMapping("/finalize-payment")
    public ResponseEntity<PaymentResponse> finalizaPayment(@RequestBody PaymentRequest paymentRequest){
        logger.info("Finalizing payment with intent ID: {}", paymentRequest.getPaymentIntentId());
        try {
            PaymentResponse paymentResponse = paymentService.createPayment(paymentRequest);
            return ResponseEntity.ok(paymentResponse);
        } catch (Exception e) {
            logger.error("Error occurred during payment finalization: {}", e.getMessage());
            return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR))
                    .body(new PaymentResponse("Error: " + e.getMessage()));

        }
    }
}