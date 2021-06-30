package com.nadjagv.paymentservice.service;

import com.nadjagv.paymentservice.domain.Payment;
import com.nadjagv.paymentservice.domain.PaymentType;
import com.nadjagv.paymentservice.exception.AlreadyExistsException;
import com.nadjagv.paymentservice.exception.NotFoundException;
import com.nadjagv.paymentservice.payment.PaymentCard;
import com.nadjagv.paymentservice.payment.PaymentCash;
import com.nadjagv.paymentservice.payment.PaymentInterface;
import com.nadjagv.paymentservice.repository.PaymentRepository;
import com.nadjagv.playerservice.client.PlayerClient;
import com.nadjagv.playerservice.dto.PlayerDTO;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {
    @Autowired
    private final PaymentRepository paymentRepository;


    private final PlayerClient playerClient = Feign.builder()
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .target(PlayerClient.class, "http://localhost:8081");

    public List<Payment> findAll(){
        return paymentRepository.findAll();
    }

    public Payment findPaymentById(Long id){
        return paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found."));

    }

    public void pay(Payment payment){

        List<PlayerDTO> allPlayers = playerClient.getAll();
        PlayerDTO player =  allPlayers.stream()
                .filter(p -> p.getId() == payment.getPlayerId())
                .findFirst()
                .orElse(null);
        if(player == null){
            throw new NotFoundException("Player does not exist.");
        }

        if(paymentRepository.existsByPlayerId(payment.getPlayerId())){
            throw new AlreadyExistsException("Player has already payed.");
        };

        PaymentInterface payer;
        if (payment.getType().equals(PaymentType.CASH)){
            payer = new PaymentCash();
        }else {
            payer = new PaymentCard();
        }

        Payment newPayment = payer.pay(payment);
        paymentRepository.save(newPayment);
    }
}
