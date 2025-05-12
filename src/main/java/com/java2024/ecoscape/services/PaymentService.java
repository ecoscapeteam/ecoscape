package com.java2024.ecoscape.services;

import com.java2024.ecoscape.controllers.PaymentController;
import com.java2024.ecoscape.dto.BookingResponse;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    //String stripeSecretKey = "sk_test_51RLSCYGPXE58N028wrhsU6XgXO5kA8GN8te5T27944UjZnNSagMf3NZ4GQmbewcN1gGoxGQykbbhoGvvb4Y4RYVa00653R0PCF";


    public PaymentService(PaymentRepository paymentRepository,

                          AuthenticationService authenticationService,
                          UserRepository userRepository,
                          BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;

        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    // طريقة لإنشاء PaymentIntent من Stripe
    public PaymentIntent createPaymentIntent(long amount, String currency) throws StripeException {


        String stripeSecretKey = "sk_test_51RLSCYGPXE58N028wrhsU6XgXO5kA8GN8te5T27944UjZnNSagMf3NZ4GQmbewcN1gGoxGQykbbhoGvvb4Y4RYVa00653R0PCF";
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                .build();

        return PaymentIntent.create(params);
    }

    public PaymentResponse createPayment(PaymentRequest paymentRequest) throws StripeException {

        User authenticateUser = authenticationService.authenticateMethods();

        Booking booking = bookingRepository.findById(paymentRequest.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getUser().getId().equals(authenticateUser.getId())) {
            throw new SecurityException("You are not authorized to perform this payment");
        }

        List<String> error = new ArrayList<>();
        if (paymentRequest.getAmount() == null || paymentRequest.getAmount() < 0) {
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

        // إنشاء PaymentIntent باستخدام Stripe
        PaymentIntent stripePaymentIntent = createPaymentIntent(paymentRequest.getAmount(), paymentRequest.getCurrency());
        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setRemainingAmount(paymentRequest.getAmount());
        payment.setCurrency(paymentRequest.getCurrency());
        payment.setPaymentType(PaymentType.STRIPE);

        payment.setPaymentStatus(paymentRequest.getPaymentStatus());
        payment.setPaymentIntentId(stripePaymentIntent.getId()); // استخدام ID من PaymentIntent
        payment.setCreatedAt(LocalDateTime.now());
        payment.setBooking(booking);
        payment.setUser(authenticateUser);

        if (stripePaymentIntent.getStatus().equals("succeeded")) {
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
        }
        //paymentRepository.save(payment);
        Payment savedPayment = paymentRepository.save(payment);

        // إنشاء كائن Payment وتخزينه في قاعدة البيانات
       // Payment payment = new Payment();
      /*  payment.setAmount(paymentRequest.getAmount());
        payment.setRemainingAmount(paymentRequest.getAmount());
        payment.setCurrency(paymentRequest.getCurrency());
        payment.setPaymentType(paymentRequest.getPaymentType());
        payment.setPaymentStatus(paymentRequest.getPaymentStatus());
        payment.setPaymentIntentId(stripePaymentIntent.getId()); // استخدام ID من PaymentIntent
        payment.setCreatedAt(LocalDateTime.now());
        payment.setBooking(booking);
        payment.setUser(authenticateUser);
        Payment savedPayment = paymentRepository.save(payment);

       /* // تحويل الكائن المحفوظ إلى PaymentResponse
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setId(savedPayment.getId());
        paymentResponse.setAmount(savedPayment.getAmount());
        paymentResponse.setCurrency(savedPayment.getCurrency());
        paymentResponse.setPaymentType(savedPayment.getPaymentType());
        paymentResponse.setPaymentStatus(savedPayment.getPaymentStatus());
        paymentResponse.setPaymentIntentId(savedPayment.getPaymentIntentId());
        paymentResponse.setCreatedAt(savedPayment.getCreatedAt());*/

        PaymentResponse paymentResponse = convertPaymentToResponse(savedPayment);

        paymentResponse.setMessage("Payment has been processed successfully with ID:" + savedPayment.getId());

        return paymentResponse;
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







/* use PaymentIntent becuse  it is an object in stripe that helps  to ensure payments are
securely processed, and provider flexibility in executing payments.
*/
