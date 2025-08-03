package myappuserservice.springmyapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import myappuserservice.springmyapp.model.UserNotification;
import myappuserservice.springmyapp.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notification-Service", description = "Отправка Email при пользователям")
public class NotificationController {

    private final EmailService emailService;

    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public void sendNotification(@RequestBody UserNotification notification) {
        String message;
        if ("CREATE".equals(notification.getOperation())) {
            message = "Здравствуйте! Ваш аккаунт на сайте был успешно создан.";
        } else if ("DELETE".equals(notification.getOperation())) {
            message = "Здравствуйте! Ваш аккаунт был удалён.";
        } else {
            throw new IllegalArgumentException("Неизвестная операция");
        }

        emailService.sendEmail(notification.getEmail(), message);
    }
}
