package myappuserservice.springmyapp.kafka;

import myappuserservice.springmyapp.model.UserNotification;
import myappuserservice.springmyapp.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private final EmailService emailService;

    public NotificationListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "user-notifications", groupId = "notification-group")
    public void listen(UserNotification notification) {
        String message;
        if ("CREATE".equals(notification.getOperation())) {
            message = "Здравствуйте! Ваш аккаунт на сайте был успешно создан.";
        } else if ("DELETE".equals(notification.getOperation())) {
            message = "Здравствуйте! Ваш аккаунт был удалён.";
        } else {
            return;
        }

        emailService.sendEmail(notification.getEmail(), message);
    }
}
