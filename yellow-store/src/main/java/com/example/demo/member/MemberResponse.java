package com.example.demo.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "회원 응답 정보")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    
    @Schema(description = "유저의 UUID")
    private UUID id;

    @Schema(description = "유저의 이메일")
    private String email;

    @Schema(description = "유저의 이름")
    private String name;

    @Schema(description = "유저의 연락처")
    private String phone;

    @Schema(description = "유저의 등록 id")
    private UUID regId;

    @Schema(description = "등록 일시")
    private LocalDateTime regDt;

    @Schema(description = "유저의 수정 id")
    private UUID modifyId;

    @Schema(description = "수정 일시")
    private LocalDateTime modifyDt;

    @Schema(description = "플래그")
    private String flag;

    // Member 엔티티에서 MemberResponse로 변환하는 메서드
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPhone(),
                member.getRegId(),
                member.getRegDt(),
                member.getModifyId(),
                member.getModifyDt(),
                member.getFlag()
        );
    }
}

