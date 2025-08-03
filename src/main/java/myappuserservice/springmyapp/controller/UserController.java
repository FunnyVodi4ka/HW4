package myappuserservice.springmyapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import myappuserservice.springmyapp.model.User;
import myappuserservice.springmyapp.repository.UserDTO;
import myappuserservice.springmyapp.service.EmailService;
import myappuserservice.springmyapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Tag(name = "User-Service", description = "CRUD-операции с пользователями")
public class UserController {

    private EntityModel<UserDTO> toEntityModel(UserDTO user) {
        EntityModel<UserDTO> resource = EntityModel.of(user);

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).updateUserForm(user.getUser_id(), null)).withSelfRel();
        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).deleteUser(user.getUser_id())).withRel("delete");

        resource.add(selfLink, deleteLink);

        return resource;
    }

    private Long update_id;

    private final UserService userService;

    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/users")
    @Operation(summary = "Получение всех пользователей", description = "Выводит список всех пользователей")
    public String findAllUsers(Model model) {

        List<EntityModel<UserDTO>> users = userService.findAllUsers().stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        model.addAttribute("users", users);
        return "user-list";
    }


    @GetMapping("/user-create")
    @Operation(summary = "Показать форму создания пользователя", description = "Отображает форму для создания нового пользователя")
    public String createUserForm(User user) {
        return "user-create";
    }

    @PostMapping("/user-create")
    @Operation(summary = "Создайте нового пользователя", description = "Сохраняет нового пользователя и отправляет электронное письмо с подтверждением")
    public String createUser(@Parameter(description = "Пользовательские данные, которые будут созданы") UserDTO user) {
        userService.saveUser(user);
        this.sendMessageAdd(user);
        return "redirect:/users";
    }

    private void sendMessageAdd(UserDTO user) {
        if(user != null)
            emailService.sendEmail(user.getEmail(), "Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
    }

    @GetMapping("user-delete/{user_id}")
    @Operation(summary = "Удаляет пользователя", description = "Удаляет пользователя по идентификатору и отправляет уведомление по электронной почте")
    public String deleteUser(@Parameter(description = "Идентификатор пользователя, подлежащего удалению") @PathVariable("user_id") Long user_id) {
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
    @Operation(summary = "Показать форму обновления пользователя", description = "Отображает форму для обновления существующего пользователя по идентификатору")
    public String updateUserForm(@Parameter(description = "Идентификатор пользователя, который будет обновлен") @PathVariable("user_id") Long user_id, Model model) {
        UserDTO user = userService.findUserById(user_id);
        update_id = user.getUser_id();
        model.addAttribute("user", user);
        return "/user-update";
    }

    @PostMapping("/user-update")
    @Operation(summary = "Обновляет существующего пользователя", description = "Обновляет сведения о существующем пользователе")
    public String updateUser(@Parameter(description = "Обновленные пользовательские данные") UserDTO user) {
        user.setUser_id(update_id);
        userService.saveUser(user);
        return "redirect:/users";
    }
}