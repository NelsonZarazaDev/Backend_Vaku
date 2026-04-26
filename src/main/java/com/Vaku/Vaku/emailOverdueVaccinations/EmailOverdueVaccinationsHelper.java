package com.Vaku.Vaku.emailOverdueVaccinations;

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
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class EmailOverdueVaccinationsHelper {
    private final JavaMailSender javaMailSender;
    private final JavaMailSenderImpl mailSender;

    public void sendEmail(List<String> recipients) {
        String htmlContent = this.readHtmlTemplate();

        for (String to : recipients) {
            try {
                MimeMessage message = mailSender.createMimeMessage(); // mover aquí
                message.setRecipients(MimeMessage.RecipientType.TO, to);
                message.setSubject("Vacunas atrasadas – Revisión de tu esquema de vacunación");
                message.setContent(htmlContent, MediaType.TEXT_HTML_VALUE);
                mailSender.send(message);
            } catch (MessagingException e) {
                log.error("Error al enviar correo a: {}", to, e);
            }
        }
    }

    private String readHtmlTemplate() {
        try {
            Path TEMPLATE_PATH = Paths.get("D:\\Vaku_Backend\\Vaku\\src\\main\\java\\com\\Vaku\\Vaku\\emailOverdueVaccinations\\email_template.html");
            return Files.readString(TEMPLATE_PATH);
        } catch (IOException e) {
            log.error("Cant read html template", e);
            throw new RuntimeException();
        }
    }

}
