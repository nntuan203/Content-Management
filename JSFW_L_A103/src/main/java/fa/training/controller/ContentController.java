package fa.training.controller;

import fa.training.models.dtos.ContentDTO;
import fa.training.models.entities.Content;
import fa.training.services.ContentService;
import fa.training.services.MemberService;
import fa.training.security.UserDetailServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class ContentController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ContentService contentService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @PostMapping("/add-content")
    public String addContent(@ModelAttribute("content") @Valid ContentDTO contentDTO,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "add-content";
        } else {

            Content content = modelMapper.map(contentDTO, Content.class);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            content.setMember(memberService.findMemberByEmail(authentication.getName()));

            contentService.saveContent(content);
            model.addAttribute("messSuccess", "Add Content Success");
            model.addAttribute("content", new ContentDTO());

            //Update authorize
            userDetailsService.updateAuthorize(authentication);

            return "add-content";
        }
    }

    @PreAuthorize("hasRole(('ROLE_AUTHOR' + #contentId))")
    @GetMapping("/update")
    public String updateContent(Model model, @RequestParam("contentId") Integer contentId) {
        model.addAttribute("content", contentService.getContent(contentId));
        return "edit-content";
    }

    @PreAuthorize("hasRole(('ROLE_AUTHOR' + #contentId))")
    @PostMapping("/update")
    public String updateContent(@ModelAttribute("content") @Valid ContentDTO contentDTO,
                                BindingResult bindingResult, Model model,
                                @RequestParam("contentId") Integer contentId) {

        if (bindingResult.hasErrors()) {
            return "edit-content";
        } else {

            Content content = modelMapper.map(contentDTO, Content.class);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            content.setContentId(contentId);
            content.setCreatedDate(contentService.getContent(contentId).getCreatedDate());
            content.setMember(memberService.findMemberByEmail(auth.getName()));

            contentService.saveContent(content);

            model.addAttribute("messSuccess", "Update Content Success");
            model.addAttribute("content", contentService.getContent(contentId));
            return "edit-content";
        }
    }
    @PreAuthorize("hasRole(('ROLE_AUTHOR' + #contentId))")
    @GetMapping("/delete-content")
    public String deleteContent(@RequestParam("contentId") Integer contentId,RedirectAttributes redirectAttributes) {

        contentService.deleteContent(contentId);
        redirectAttributes.addFlashAttribute("messSuccess", "Delete Content Success");

        return "redirect:/view/list?pageNumber=1";
    }
}
