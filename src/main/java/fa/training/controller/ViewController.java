package fa.training.controller;


import fa.training.models.entities.Content;
import fa.training.services.ContentService;
import fa.training.services.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MemberService memberService;

    @GetMapping("/list")
    public String getContents(ModelMap model, @RequestParam("pageNumber") Integer pageNumber) {

        Page<Content> contentList = contentService.paging(pageNumber);

        model.addAttribute("contentList", contentList);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", contentList.getTotalPages());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        model.addAttribute("email", email);

        return "view";
    }

    @GetMapping("/search-content")
    public String deleteContent(@RequestParam("value") String value, @RequestParam("pageNumber") Integer pageNumber, ModelMap model) {

        Page<Content> contentList = contentService.search(value, pageNumber);

        if (contentList.isEmpty()) {
            contentList = contentService.paging(pageNumber);

            model.addAttribute("contentList", contentList);
            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("totalPages", contentList.getTotalPages());
            model.addAttribute("value", value);
            model.addAttribute("messError", "Couldn't find any matches for your search.");
        } else {

            model.addAttribute("contentList", contentList);
            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("totalPages", contentList.getTotalPages());
            model.addAttribute("value", value);

        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        model.addAttribute("email", email);

        return "view-search";
    }
}
