package fa.training.controller;

import fa.training.models.dtos.ContentDTO;
import fa.training.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/")
    public String goHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userName",memberService.findMemberByEmail(auth.getName()).getUserName());
        model.addAttribute("mess","Welcome to home page ");
        return "home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("userName",memberService.findMemberByEmail(auth.getName()).getUserName());
        model.addAttribute("mess","Welcome to home page ");
        return "home";
    }

    @GetMapping("/edit-profile")
    public String profile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("profile",memberService.findMemberByEmail(auth.getName()));
        return "edit-profile";
    }

    @GetMapping("/view-contents")
    public String view() {
        return "redirect:/view/list?pageNumber=1";
    }

    @GetMapping("/add-content")
    public String add(Model model) {
        model.addAttribute("content", new ContentDTO());
        return "add-content";
    }

    @RequestMapping("/403")
    public String errorHandling() {
        return "403";
    }
}
