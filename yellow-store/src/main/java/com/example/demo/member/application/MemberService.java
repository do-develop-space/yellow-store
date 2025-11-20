package com.example.demo.member.application;

import com.example.demo.common.ResponseEntity;
import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberRepository;
import com.example.demo.member.application.dto.MemberCommand;
import com.example.demo.member.application.dto.MemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MemberService {
    
    @Autowired
    private MemberRepository memberRepository;

    public ResponseEntity<List<MemberInfo>> findAll(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        List<MemberInfo> memberInfos = page.stream()
                .map(MemberInfo::from)
                .toList();
        return new ResponseEntity<>(HttpStatus.OK.value(), memberInfos, page.getTotalElements());
    }

    public ResponseEntity<MemberInfo> create(MemberCommand command) {
        Member member = Member.create(
                command.email(),
                command.name(),
                command.phone(),
                command.saltKey(),
                command.flag()
        );
        Member member1 = memberRepository.save(member);
        int cnt = 0;
        if(member1 instanceof List){
            cnt = ((List<?>) member1).size();
        }else{
            cnt = 1;
        }

        MemberInfo memberInfo = MemberInfo.from(member1);
        return new ResponseEntity<>(HttpStatus.OK.value(), memberInfo, cnt);
    }

    public ResponseEntity<MemberInfo> update(MemberCommand command, String id) {
        // 기존 회원 정보 조회
        Member existingMember = memberRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다: " + id));
        
        existingMember.setEmail(command.email());
        existingMember.setName(command.name());
        existingMember.setPassword(command.password());
        existingMember.setPhone(command.phone());
        existingMember.setSaltKey(command.saltKey());
        existingMember.setFlag(command.flag());
        // reg_id, reg_dt는 유지
        // modify_id, modify_dt는 @PreUpdate에서 처리
        
        Member updatedMember = memberRepository.save(existingMember);
        MemberInfo memberInfo = MemberInfo.from(updatedMember);
        return new ResponseEntity<>(HttpStatus.OK.value(), memberInfo, 1);
    }

    public ResponseEntity<Void> delete(String id) {
        memberRepository.deleteById(UUID.fromString(id));
        return new ResponseEntity<>(HttpStatus.OK.value(), null, 1);
    }
}

