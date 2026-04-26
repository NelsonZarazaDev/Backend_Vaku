package com.Vaku.Vaku.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@AllArgsConstructor
@Slf4j
public class EmailHelper {
    private final JavaMailSender javaMailSender;
    private final JavaMailSenderImpl mailSender;

    public void sendEmail(String to) {
        MimeMessage message = mailSender.createMimeMessage();
        String htmlContent = this.readHtmlTemplate();

        try {
            message.setRecipients(MimeMessage.RecipientType.TO, to);
            message.setSubject("Nueva vacuna aplicada");
            message.setContent(htmlContent, MediaType.TEXT_HTML_VALUE);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error to send mail ", e);
        }
    }

    private String readHtmlTemplate() {
        try {
            Path TEMPLATE_PATH = Paths.get("D:\\Vaku_Backend\\Vaku\\src\\main\\java\\com\\Vaku\\Vaku\\email\\email_template.html");
            return Files.readString(TEMPLATE_PATH);
        } catch (IOException e) {
            log.error("Cant read html template", e);
            throw new RuntimeException();
        }
    }

}
