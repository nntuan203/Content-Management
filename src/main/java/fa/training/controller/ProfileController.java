package fa.training.controller;

import fa.training.models.dtos.ProfileDTO;
import fa.training.models.entities.Member;
import fa.training.services.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ProfileController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MemberService memberService;

    @PostMapping("/edit-profile")
    public String addContent(@ModelAttribute("profile") @Valid ProfileDTO profileDTO,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/edit-profile";
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            Member member = modelMapper.map(profileDTO, Member.class);
            member.setMemberId(memberService.findMemberByEmail(auth.getName()).getMemberId());
            member.setPassword(memberService.findMemberByEmail(auth.getName()).getPassword());
            member.setUserName(memberService.findMemberByEmail(auth.getName()).getUserName());
            member.setCreatedDate(memberService.findMemberByEmail(auth.getName()).getCreatedDate());
            member.setContents(memberService.findMemberByEmail(auth.getName()).getContents());

            memberService.saveMember(member);

            model.addAttribute("messSuccess", "Update Profile Success");

            model.addAttribute("profile",memberService.findMemberByEmail(auth.getName()));
            return "/edit-profile";
        }
    }
}
