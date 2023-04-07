package fa.training.controller;

import fa.training.models.dtos.UserDTO;
import fa.training.models.entities.Member;
import fa.training.services.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
@Controller
public class RegisterController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/process_register")
    public String registerUser(@ModelAttribute("user") @Valid UserDTO userDTO,
                               BindingResult bindingResult, HttpServletRequest request, Model model) {

        if (memberService.findMemberByUserName(userDTO.getUserName()) != null) {
            bindingResult.rejectValue("userName", "error.user", "User Name is already exists");
            return "register";
        } else if (memberService.findMemberByEmail(userDTO.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.user", "Email is already exists");
            return "register";
        } else if (!userDTO.getPassword().equals(request.getParameter("rePassword"))) {
            bindingResult.rejectValue("password", "error.user", "Passwords do NOT match");
            return "register";
        } else if (bindingResult.hasErrors()) {
            return "register";
        } else {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            Member member = modelMapper.map(userDTO, Member.class);
            memberService.saveMember(member);
            model.addAttribute("messSuccess", "Register Success");
            return "/login";
        }
    }
}
