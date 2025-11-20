package com.example.demo.member.presentation.dto;

import com.example.demo.member.application.dto.MemberCommand;

public record MemberRequest(
        String email,
        String name,
        String password,
        String phone,
        String saltKey,
        String flag
) {

    public MemberCommand toCommand() {
        return new MemberCommand(email, name, password, phone, saltKey, flag);
    }
}
