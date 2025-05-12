package com.java2024.ecoscape.repositories;

import com.java2024.ecoscape.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
