package pl.email_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MailServiceListener {
    @RabbitListener(queues = "employee-updates-queue")
    public void receiveMessage(Map<String, String> mailData) {
        System.out.println("Sending email to " + mailData.get("email") + " about update of " + mailData.get("name") + ". New team: " + mailData.get("team"));
        sendEmailNotification(mailData);
    }

    private void sendEmailNotification(Map<String, String> mailData) {
        System.out.println("Sending email to " + mailData.get("email") + " about the new belonging to the team (" + mailData.get("team") + ") of " + mailData.get("email"));
    }
}
