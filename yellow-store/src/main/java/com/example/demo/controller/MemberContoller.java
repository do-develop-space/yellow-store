package com.example.demo.controller;

import com.example.demo.common.ResponseEntity;
import com.example.demo.member.MemberRequest;
import com.example.demo.member.MemberResponse;
import com.example.demo.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
public class MemberContoller {

    @Autowired
    private MemberService memberService;


    @Operation(
            summary = "회원 목록 조회",
            description = "public.member 테이블에 저장된 모든 회원을 조회한다."
    )
    @GetMapping
    public ResponseEntity<List<MemberResponse>> findAll(){
        return memberService.findAll();
    }

    @Operation(
            summary = "회원 등록",
            description = "요청으로 받은 회원 정보를 public.member 테이블에 등록한다."
    )
    @PostMapping
    public ResponseEntity<MemberResponse> create(@RequestBody MemberRequest request){
        return memberService.create(request);
    }

    @Operation(
            summary = "회원 수정",
            description = "요청으로 받은 회원 정보를 public.member 테이블에 수정한다."
    )
    @PutMapping("{id}")
    public ResponseEntity<MemberResponse> update(@RequestBody MemberRequest request, @PathVariable String id){
        return memberService.update(request, id);
    }

    @Operation(
            summary = "회원 정보 삭제",
            description = "요청으로 받은 회원 정보를 public.member 테이블에서 삭제한다."
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        return memberService.delete(id);
    }

}
