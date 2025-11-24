package com.example.demo.member.application;

import com.example.demo.common.ResponseEntity;
import com.example.demo.member.application.dto.MemberCommand;
import com.example.demo.member.application.dto.MemberInfo;
import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberRepository;
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
        UUID uuid = UUID.fromString(id);
        Member member = memberRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + id));

        member.updateInformation(
                command.email(),
                command.name(),
                command.password(),
                command.phone(),
                command.saltKey(),
                command.flag()
        );

        Member updated = memberRepository.save(member);
        return new ResponseEntity<>(HttpStatus.OK.value(), MemberInfo.from(updated), 1);
    }

    public ResponseEntity<Void> delete(String id) {
        memberRepository.deleteById(UUID.fromString(id));
        return new ResponseEntity<>(HttpStatus.OK.value(), null, 1);
    }
}

