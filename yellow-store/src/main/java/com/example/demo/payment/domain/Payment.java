package com.example.demo.payment.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "\"payment\"", schema = "public")
public class Payment {

    @Schema(description = "결제 UUID")
    @Id
    private UUID id;

    @Schema(description = "결제 key")
    @Column(name = "payment_key", nullable = false, unique = true, length = 200)
    private String paymentKey;

    @Schema(description = "주문 ID")
    @Column(name = "order_id", nullable = false, length = 100)
    private String orderId;

    @Schema(description = "총 결제 금액")
    @Column(name = "total_amount", nullable = false)
    private Long amount;

    @Schema(description = "결제 방식")
    @Column(name = "method", length = 50)
    private String method;

//    @Schema(description = "결제 상태")
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false, length = 20)
//    private PaymentStatus status;

    @Schema(description = "결제 요청 시간")
    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Schema(description = "결제 승인 시간")
    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Schema(description = "실패 원인")
    @Column(name = "fail_reason")
    private String failReason;

    @Schema(description = "생성 날짜")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Schema(description = "수정 날짜")
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected Payment() {
    }

    private Payment(String paymentKey, String orderId, Long amount) {
        this.id = UUID.randomUUID();
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.amount = amount;
//        this.status = PaymentStatus.READY;
    }

    public static Payment create(String paymentKey, String orderId, Long amount) {
        return new Payment(paymentKey, orderId, amount);
    }

    public void markConfirmed(String method, LocalDateTime approvedAt, LocalDateTime requestedAt) {
//        this.status = PaymentStatus.CONFIRMED;
        this.method = method;
        this.approvedAt = approvedAt;
        this.requestedAt = requestedAt;
        this.failReason = null;
    }

    public void markFailed(String failReason) {
//        this.status = PaymentStatus.FAILED;
        this.failReason = failReason;
    }

    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (id == null) {
            id = UUID.randomUUID();
        }
        createdAt = now;
        updatedAt = now;
//        if (status == null) {
//            status = PaymentStatus.READY;
//        }
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}