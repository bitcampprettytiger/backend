package com.example.bitcamptiger.payments.repository;

import com.example.bitcamptiger.payments.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payments, Long> {

    List<Payments> findByMemberIdOrderByPayDateDesc(String memberId);

}
