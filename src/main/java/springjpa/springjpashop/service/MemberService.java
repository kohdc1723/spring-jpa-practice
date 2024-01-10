package springjpa.springjpashop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpa.springjpashop.domain.Member;
import springjpa.springjpashop.repository.MemberRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long register(Member member) {
        validateDuplicateName(member);

        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateName(Member member) {
        List<Member> foundMembers = memberRepository.findByName(member.getName());

        if (!foundMembers.isEmpty()) {
            throw new IllegalStateException("User name already exists.");
        }
    }

    @Transactional(readOnly = true)
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
