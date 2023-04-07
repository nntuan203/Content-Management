package fa.training.services.impl;

import fa.training.models.entities.Member;
import fa.training.repositories.MemberRepository;
import fa.training.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;


    @Override
    @Transactional
    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    @Override
    @Transactional
    public Member saveMember(Member Member) {
        return memberRepository.save(Member);
    }

    @Override
    @Transactional
    public Member getMember(int theId) {
        return memberRepository.findById(theId).orElseThrow(() -> new EntityNotFoundException("Member not found with id " + theId));
    }

    @Override
    @Transactional
    public void deleteMember(int theId) {
        memberRepository.deleteById(theId);
    }

    @Override
    public Member findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    @Override
    public Member findMemberByUserName(String userName) {
        return memberRepository.findMemberByUserName(userName);
    }
}
