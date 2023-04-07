package fa.training.repositories;

import fa.training.models.entities.Content;
import fa.training.models.entities.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content,Integer> {
    List<Content> findAllByMemberIs(Member member);
    Page<Content> findAllByTitleContainsOrBriefContains(String title,String brief, Pageable pageable);


}
