package az.company.onlinelibrarysystem.service;


public interface EmailService {
    void sendEmail(String to, String subject, String text);
}

