package myappuserservice.springmyapp;

import myappuserservice.springmyapp.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NotificationServiceIntegrationTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;

    private String testTopic = "notification-topic";

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testKafkaEmailIntegration() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("AlexZuevOfficial@yandex.ru");
        mailMessage.setSubject("Уведомление");
        mailMessage.setText("Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
        mailMessage.setFrom("vodi4ka.note@yandex.ru");
        mailSender.send(mailMessage);

        assertThat(mailMessage).isNotNull();
        assertThat(mailMessage.getTo()).isEqualTo(new String[]{"AlexZuevOfficial@yandex.ru"});
        assertThat(mailMessage.getSubject()).isEqualTo("Уведомление");
        assertThat(mailMessage.getText()).isEqualTo("Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
        assertThat(mailMessage.getFrom()).isEqualTo("vodi4ka.note@yandex.ru");
    }
}
