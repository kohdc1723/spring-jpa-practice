package springjpa.springjpashop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springjpa.springjpashop.domain.Member;
import springjpa.springjpashop.repository.MemberRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void testRegister() {
        Member member = new Member();
        member.setName("member1");

        Long registeredId = memberService.register(member);

        assertThat(memberRepository.findOne(registeredId)).isEqualTo(member);
    }

    @Test
    void testDuplicateValidation() {
        Member member1 = new Member();
        member1.setName("member");
        Member member2 = new Member();
        member2.setName("member");

        memberService.register(member1);

        assertThrows(IllegalStateException.class, () -> memberService.register(member2));
    }
}