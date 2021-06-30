package com.nadjagv.paymentservice.repository;

import com.nadjagv.paymentservice.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByPlayerId(Long playerId);
}
