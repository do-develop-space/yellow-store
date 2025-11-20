package com.example.demo.product.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "상품 정보")
@Data
@Entity
@Table(name = "\"product\"", schema = "public")
public class Product {

    @Schema(description = "상품의 UUID")
    @Id
    private UUID id;

    @Schema(description = "상품명")
    @Column(nullable = false, length = 100)
    private String name;

    @Schema(description = "상품의 설명")
    @Column(columnDefinition = "text")
    private String description;

    @Schema(description = "상품의 가격")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Schema(description = "상품의 재고")
    @Column(nullable = false)
    private Integer stock;

    @Schema(description = "상품의 상태")
    @Column(nullable = false, length = 20)
    private String status;

    @Schema(description = "상품의 등록자")
    @Column(name = "reg_id", nullable = false)
    private UUID regId;

    @Schema(description = "상품의 등록 날짜")
    @Column(name = "reg_dt", nullable = false)
    private LocalDateTime regDt;

    @Schema(description = "상품의 수정자")
    @Column(name = "modify_id", nullable = false)
    private UUID modifyId;

    @Schema(description = "상품의 수정 날짜")
    @Column(name = "modify_dt", nullable = false)
    private LocalDateTime modifyDt;

    protected Product() {
    }

    private Product(UUID id,
                    String name,
                    String description,
                    BigDecimal price,
                    Integer stock,
                    String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.status = status;
    }

    public static Product create(String name,
                                 String description,
                                 BigDecimal price,
                                 Integer stock,
                                 String status,
                                 UUID creatorId) {
        Product product = new Product(UUID.randomUUID(), name, description, price, stock, status);
        product.regId = creatorId;
        product.modifyId = creatorId;
        return product;
    }

    public void update(String name,
                       String description,
                       BigDecimal price,
                       Integer stock,
                       String status,
                       UUID modifierId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.modifyId = modifierId;
    }

    @PrePersist
    public void onCreate() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (regId == null) {
            regId = id;
        }
        if (modifyId == null) {
            modifyId = regId;
        }
        if (regDt == null) {
            regDt = LocalDateTime.now();
        }
        if (modifyDt == null) {
            modifyDt = regDt;
        }
        if (status == null) {
            status = "ACTIVE";
        }
        if (stock == null) {
            stock = 0;
        }
    }

    @PreUpdate
    public void onUpdate() {
        modifyDt = LocalDateTime.now();
        if (modifyId == null) {
            modifyId = id;
        }
    }
}