package com.example.demo.settlement.infrastructure;

import com.example.demo.settlement.domain.SellerSettlement;
import com.example.demo.settlement.domain.SettlementStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SellerSettlementJpaRepository extends JpaRepository<SellerSettlement, UUID> {

    List<SellerSettlement> findByStatus(SettlementStatus status);

    List<SellerSettlement> findByStatusAndSellerId(SettlementStatus status, UUID sellerId);
}
