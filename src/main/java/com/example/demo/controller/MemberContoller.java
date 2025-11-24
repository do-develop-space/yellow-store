//package com.example.demo.controller;
//
//import com.example.demo.common.ResponseEntity;
//import com.example.demo.member.Member;
//import com.example.demo.member.MemberRepository;
//import com.example.demo.member.MemberRequest;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@RestController
//@RequestMapping("/api/v1/members")
//public class MemberContoller {
//    @Autowired
//    private MemberRepository memberRepository;
//
//    /*
//    * 아래 코드는 @Autowired로 대체된다.
//    *
//    * private final MemberRepository memberRepository;
//    * public MemberContoller(MemberRepository memberRepository) {
//            this.memberRepository = memberRepository;
//      }
//    */
//
//
//    @Operation(
//            summary = "회원 목록 조회",
//            description = "public.member 테이블에 저장된 모든 회원을 조회한다."
//    )
//    @GetMapping
////    public List<Member> findAll(){
////        return memberRepository.findAll();
////    }
//    public ResponseEntity<List<Member>> findAll(){
//        return new ResponseEntity<>(HttpStatus.OK.value(), memberRepository.findAll(), )
//    }
//
//    @Operation(
//            summary = "회원 등록",
//            description = "요청으로 받은 회원 정보를 public.member 테이블에 등록한다."
//    )
//    @PostMapping
//    public Member create(@RequestBody MemberRequest request){
//        Member member = new Member(
//                UUID.randomUUID(),
//                request.email(),
//                request.name(),
//                request.password(),
//                request.phone(),
//                request.saltKey(),
//                request.flag()
//        );
//
////        return memberRepository.save(member);
//
//        Member member1 = memberRepository.save(member);
//        AtomicInteger cnt = new AtomicInteger();
//        if(member1 instanceof List) {
//            cnt.set(((List<?>) member1).size());
//        } else {
//            cnt.set(1);
//        }
//
//        return new ResponseEntity<>(HttpStatus.OK.value(), member1, cnt.get());
//
//    }
//
//    @Operation(
//            summary = "회원 수정",
//            description = "요청으로 받은 회원 정보를 public.member 테이블에 수정한다."
//    )
//    @PutMapping("{id}")
//    public Member update(@RequestBody MemberRequest request, @PathVariable String id){
//        Member member = new Member(
//                id,
//                request.email(),
//                request.name(),
//                request.password(),
//                request.phone(),
//                request.saltKey(),
//                request.flag()
//        );
//
//        return memberRepository.save(member);
//    }
//
//    @Operation(
//            summary = "회원 정보 삭제",
//            description = "요청으로 받은 회원 정보를 public.member 테이블에서 삭제한다."
//    )
//    @DeleteMapping("{id}")
//    public void delete(@PathVariable String id){
//        memberRepository.deleteById(UUID.fromString(id));
//    }
//
//}
