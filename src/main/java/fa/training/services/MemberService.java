package fa.training.services;

import fa.training.models.entities.Member;

import java.util.List;

public interface MemberService {
    List<Member> getMembers();
    Member saveMember(Member member);
    Member getMember(int theId);
    void deleteMember(int theId);

    Member findMemberByEmail(String email);

    Member findMemberByUserName(String userName);

}
