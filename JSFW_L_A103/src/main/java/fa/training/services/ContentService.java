package fa.training.services;

import fa.training.models.entities.Content;
import fa.training.models.entities.Member;
import org.springframework.data.domain.Page;


import java.util.List;

public interface ContentService {
    List<Content> getContents();
    Content saveContent(Content content);
    Content getContent(int theId);
    void deleteContent(int theId);

    Page<Content> paging(int pageNumber);

    List<Content> findAllByMemberIs(Member member);

    Page<Content> search(String value, int pageNumber);
}
