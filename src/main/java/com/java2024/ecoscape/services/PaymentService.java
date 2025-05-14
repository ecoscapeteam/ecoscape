package com.java2024.ecoscape.services;

import com.java2024.ecoscape.dto.PaymentRequest;
import com.java2024.ecoscape.dto.PaymentResponse;
import com.java2024.ecoscape.models.*;
import com.java2024.ecoscape.repositories.BookingRepository;
import com.java2024.ecoscape.repositories.PaymentRepository;
import com.java2024.ecoscape.repositories.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    // قراءة مفتاح Stripe من ملف الخصائص
    // Read the Stripe secret key from application.properties
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public PaymentService(PaymentRepository paymentRepository,
                          AuthenticationService authenticationService,
                          UserRepository userRepository,
                          BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    /**
     * إنشاء Stripe PaymentIntent باستخدام Stripe API
     * Create a PaymentIntent using the Stripe API
     */
    public PaymentIntent createPaymentIntent(long amount, String currency) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                .build();

        return PaymentIntent.create(params);
    }

    /**
     * حفظ الدفع بعد نجاح العملية من Stripe
     * Finalize payment locally after Stripe confirmation
     */
    public PaymentResponse createPayment(PaymentRequest paymentRequest) throws StripeException {
        User authenticatedUser = authenticationService.authenticateMethods();

        Booking booking = bookingRepository.findById(paymentRequest.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getUser().getId().equals(authenticatedUser.getId())) {
            throw new SecurityException("You are not authorized to perform this payment");
        }

        // التحقق من صحة البيانات المدخلة - Validate input
        List<String> error = new ArrayList<>();
        if (paymentRequest.getAmount() == null || paymentRequest.getAmount() <= 0) {
            error.add("Amount must be a positive value");
        }
        if (paymentRequest.getCurrency() == null || paymentRequest.getCurrency().isEmpty()) {
            error.add("Currency is required");
        }
        if (paymentRequest.getPaymentType() == null) {
            error.add("Payment type is required");
        }
        if (!error.isEmpty()) {
            throw new IllegalArgumentException(String.join("\n", error));
        }

        // إنشاء كائن الدفع - Create payment entity
        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setRemainingAmount(paymentRequest.getAmount());
        payment.setCurrency(paymentRequest.getCurrency());
        payment.setPaymentType(PaymentType.STRIPE);
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setPaymentIntentId(paymentRequest.getPaymentIntentId());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setBooking(booking);
        payment.setUser(authenticatedUser);

        // حفظ الدفع في قاعدة البيانات - Save to DB
        Payment savedPayment = paymentRepository.save(payment);

        // تحويل الكائن إلى استجابة - Build response DTO
        PaymentResponse response = convertPaymentToResponse(savedPayment);
        response.setMessage("Payment has been processed successfully with ID: " + savedPayment.getId());

        return response;
    }

    private PaymentResponse convertPaymentToResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setId(payment.getId());
        response.setAmount(payment.getAmount());
        response.setRemainingAmount(payment.getRemainingAmount());
        response.setCurrency(payment.getCurrency());
        response.setPaymentType(payment.getPaymentType());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setPaymentIntentId(payment.getPaymentIntentId());
        response.setCreatedAt(payment.getCreatedAt());

        if (payment.getBooking() != null) {
            response.setBookingId(payment.getBooking().getId());
        }

        if (payment.getUser() != null) {
            response.setUserId(payment.getUser().getId());
        }

        return response;
    }
}
