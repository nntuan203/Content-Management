package fa.training.repositories;

import fa.training.models.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer> {
    Member findMemberByEmail(String email);

    Member findMemberByUserName(String userName);

}
