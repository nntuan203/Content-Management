package fa.training.services.impl;

import fa.training.models.entities.Content;
import fa.training.models.entities.Member;
import fa.training.repositories.ContentRepository;
import fa.training.services.ContentService;
import fa.training.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentRepository contentRepository;

    @Override
    @Transactional
    public List<Content> getContents() {
        return contentRepository.findAll();
    }

    @Override
    @Transactional
    public Content saveContent(Content content) {
        return contentRepository.save(content);
    }

    @Override
    @Transactional
    public Content getContent(int theId) {
        return contentRepository.findById(theId).orElseThrow(() -> new EntityNotFoundException("Content not found with id " + theId));
    }

    @Override
    @Transactional
    public void deleteContent(int theId) {
        contentRepository.deleteById(theId);
    }

    @Override
    @Transactional
    public Page<Content> paging(int pageNumber){
        return contentRepository.findAll(PageRequest.of(pageNumber-1, Constants.PAGE_SIZE));
    }

    @Override
    public List<Content> findAllByMemberIs(Member member) {
        return contentRepository.findAllByMemberIs(member);
    }

    @Override
    @Transactional
    public Page<Content> search(String value, int pageNumber) {

        return contentRepository.findAllByTitleContainsOrBriefContains(value,value,PageRequest.of(pageNumber-1, Constants.PAGE_SIZE));
    }


}
