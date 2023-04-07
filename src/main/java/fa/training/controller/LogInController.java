package fa.training.controller;

import fa.training.models.dtos.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogInController {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "login";
    }

    @GetMapping("/login-fail")
    public String loginFail(Model model){
        model.addAttribute("messLoginFail","Email or Password is not correct");
        return "login";
    }

    @GetMapping("/logout-success")
    public String logout(Model model){
        model.addAttribute("messSuccess","Logout Success");
        return "login";
    }
}
