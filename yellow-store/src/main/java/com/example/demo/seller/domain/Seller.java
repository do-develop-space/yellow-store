package com.example.demo.seller.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "판매자 정보")
@Data
@Entity
@Table(name = "\"seller\"", schema = "public")
public class Seller {
    @Schema(description = "판매자의 UUID")
    @Id
    private UUID id;

    @Schema(description = "회사명")
    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Schema(description = "대표 이름")
    @Column(name = "representative_name", nullable = false, length = 50)
    private String representativeName;

    @Schema(description = "판매자의 이메일")
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Schema(description = "판매자의 연락처")
    @Column(nullable = false, unique = true, length = 20)
    private String phone;

    @Schema(description = "사업자등록번호")
    @Column(name = "business_number", nullable = false, unique = true, length = 20)
    private String businessNumber;

    @Schema(description = "판매자의 주소")
    @Column(length = 200)
    private String address;

    @Schema(description = "판매자 상태")
    @Column(nullable = false, length = 20)
    private String status;

    @Schema(description = "판매자 생성 날짜")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Schema(description = "판매자 수정 날짜")
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected Seller() {
    }

    private Seller(String companyName,
                   String representativeName,
                   String email,
                   String phone,
                   String businessNumber,
                   String address,
                   String status) {
        this.id = UUID.randomUUID();
        this.companyName = companyName;
        this.representativeName = representativeName;
        this.email = email;
        this.phone = phone;
        this.businessNumber = businessNumber;
        this.address = address;
        this.status = status;
    }

    public static Seller register(String companyName,
                                  String representativeName,
                                  String email,
                                  String phone,
                                  String businessNumber,
                                  String address,
                                  String status) {
        return new Seller(companyName, representativeName, email, phone, businessNumber, address, status);
    }

    public void update(String companyName,
                       String representativeName,
                       String email,
                       String phone,
                       String businessNumber,
                       String address,
                       String status) {
        this.companyName = companyName;
        this.representativeName = representativeName;
        this.email = email;
        this.phone = phone;
        this.businessNumber = businessNumber;
        this.address = address;
        this.status = status;
    }

    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (id == null) {
            id = UUID.randomUUID();
        }
        createdAt = now;
        updatedAt = now;
        if (status == null) {
            status = "ACTIVE";
        }
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
