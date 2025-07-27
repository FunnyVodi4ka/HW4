package myappuserservice.springmyapp.controller;

import lombok.RequiredArgsConstructor;
import myappuserservice.springmyapp.model.User;
import myappuserservice.springmyapp.repository.UserDTO;
import myappuserservice.springmyapp.service.EmailService;
import myappuserservice.springmyapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private Long update_id;

    private final UserService userService;

    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/users")
    public String findAllUsers(Model model) {
        List<UserDTO> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/user-create")
    public String createUserForm(User user) {
        return "user-create";
    }

    @PostMapping("/user-create")
    public String createUser(UserDTO user) {
        userService.saveUser(user);
        this.sendMessageAdd(user);
        return "redirect:/users";
    }

    private void sendMessageAdd(UserDTO user) {
        if(user != null)
            emailService.sendEmail(user.getEmail(), "Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
    }

    @GetMapping("user-delete/{user_id}")
    public String deleteUser(@PathVariable("user_id") Long user_id) {
        UserDTO user = userService.findUserById(user_id);
        userService.deleteById(user_id);
        this.sendMessageDelete(user);
        return "redirect:/users";
    }

    private void sendMessageDelete(UserDTO user) {
        if(user != null)
            emailService.sendEmail(user.getEmail(), "Здравствуйте! Ваш аккаунт был удалён.");
    }

    @GetMapping("/user-update/{user_id}")
    public String updateUserForm(@PathVariable("user_id") Long user_id, Model model) {
        UserDTO user = userService.findUserById(user_id);
        update_id = user.getUser_id();
        model.addAttribute("user", user);
        return "/user-update";
    }

    @PostMapping("/user-update")
    public String updateUser(UserDTO user) {
        user.setUser_id(update_id);
        userService.saveUser(user);
        return "redirect:/users";
    }
}
