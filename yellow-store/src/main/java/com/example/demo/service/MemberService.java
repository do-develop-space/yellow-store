package com.example.demo.service;

import com.example.demo.common.ResponseEntity;
import com.example.demo.member.Member;
import com.example.demo.member.MemberRepository;
import com.example.demo.member.MemberRequest;
import com.example.demo.member.MemberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MemberService {
    
    @Autowired
    private MemberRepository memberRepository;

    public ResponseEntity<List<MemberResponse>> findAll() {
        List<Member> members = memberRepository.findAll();
        List<MemberResponse> memberResponses = members.stream()
                .map(MemberResponse::from)
                .toList();
        return new ResponseEntity<>(HttpStatus.OK.value(), memberResponses, memberRepository.count());
    }

    public ResponseEntity<MemberResponse> create(MemberRequest request) {
        Member member = new Member(
                UUID.randomUUID(),
                request.email(),
                request.name(),
                request.password(),
                request.phone(),
                request.saltKey(),
                request.flag()
        );
        Member member1 = memberRepository.save(member);
        int cnt = 0;
        if(member1 instanceof List){
            cnt = ((List<?>) member1).size();
        }else{
            cnt = 1;
        }

        MemberResponse memberResponse = MemberResponse.from(member1);
        return new ResponseEntity<>(HttpStatus.OK.value(), memberResponse, cnt);
    }

    public ResponseEntity<MemberResponse> update(MemberRequest request, String id) {
        // 기존 회원 정보 조회
        Member existingMember = memberRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다: " + id));
        
        existingMember.setEmail(request.email());
        existingMember.setName(request.name());
        existingMember.setPassword(request.password());
        existingMember.setPhone(request.phone());
        existingMember.setSaltKey(request.saltKey());
        existingMember.setFlag(request.flag());
        // reg_id, reg_dt는 유지
        // modify_id, modify_dt는 @PreUpdate에서 처리
        
        Member updatedMember = memberRepository.save(existingMember);
        MemberResponse memberResponse = MemberResponse.from(updatedMember);
        return new ResponseEntity<>(HttpStatus.OK.value(), memberResponse, 1);
    }

    public ResponseEntity<Void> delete(String id) {
        memberRepository.deleteById(UUID.fromString(id));
        return new ResponseEntity<>(HttpStatus.OK.value(), null, 1);
    }
}

