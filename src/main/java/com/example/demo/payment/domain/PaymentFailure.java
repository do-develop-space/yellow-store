package com.example.demo.payment.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "결제 실패 정보")
@Getter
@Entity
@Table(name = "\"payment_failure\"", schema = "public")
public class PaymentFailure {

    @Schema(description = "실패 UUID")
    @Id
    private UUID id;

    @Schema(description = "주문 ID")
    @Column(name = "order_id", nullable = false, length = 100)
    private String orderId;

    @Schema(description = "프론트엔드 결제 키")
    @Column(name = "payment_key", length = 200)
    private String paymentKey;

    @Schema(description = "에러 코드")
    @Column(name = "error_code", length = 50)
    private String errorCode;

    @Schema(description = "에러 메세지")
    @Column(name = "error_message")
    private String errorMessage;

    @Schema(description = "결제 금액")
    @Column(name = "amount")
    private Long amount;

    @Lob
    @Column(name = "raw_payload", columnDefinition = "TEXT")
    private String rawPayload;

    @Schema(description = "생성 날짜")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected PaymentFailure() {
    }

    private PaymentFailure(String orderId,
                           String paymentKey,
                           String errorCode,
                           String errorMessage,
                           Long amount,
                           String rawPayload) {
        this.id = UUID.randomUUID();
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.amount = amount;
        this.rawPayload = rawPayload;
    }

    // from, PaymentFailure로 리턴
    public static PaymentFailure from(String orderId,
                                      String paymentKey,
                                      String errorCode,
                                      String errorMessage,
                                      Long amount,
                                      String rawPayload) {
        return new PaymentFailure(orderId, paymentKey, errorCode, errorMessage, amount, rawPayload);
    }

    @PrePersist
    public void onCreate() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        createdAt = LocalDateTime.now();
    }
}
