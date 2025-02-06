package pl.email_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Service that listens for email notifications related to employee updates.
 * It receives messages from a RabbitMQ queue and sends email notifications
 * using the SendGridEmailService.
 */
@Service
public class MailServiceListener {

    private final SendGridEmailService sendGridEmailService;

    /**
     * Constructor for MailServiceListener.
     *
     * @param sendGridEmailService The service used to send emails via SendGrid.
     */
    public MailServiceListener(SendGridEmailService sendGridEmailService) {
        this.sendGridEmailService = sendGridEmailService;
    }

    /**
     * Listens for messages from the "employee-updates-queue".
     * When a message is received, it extracts the email data and sends
     * an email notification to the specified recipient.
     *
     * @param mailData A map containing email data, including the recipient's email,
     *                 employee's first name, and the new team.
     * @throws IOException If an error occurs while sending the email.
     */
    @RabbitListener(queues = "employee-updates-queue")
    public void receiveMessage(Map<String, String> mailData) throws IOException {
        System.out.println("Sending email to " + mailData.get("email") + " about update of " + mailData.get("firstName")
                + ". New team: " + mailData.get("newTeam"));
        sendEmailNotification(mailData);
    }

    /**
     * Sends an email notification using the SendGridEmailService.
     * It prepares the dynamic data for the email and calls the service to send it.
     *
     * @param mailData A map containing email data, including the recipient's email,
     *                 employee's first name, and the new team.
     * @throws IOException If an error occurs while sending the email.
     */
    private void sendEmailNotification(Map<String, String> mailData) throws IOException {
        try {
            Map<String, String> dynamicData = new HashMap<>();
            dynamicData.put("employee_name", mailData.get("firstName"));
            dynamicData.put("team_name", mailData.get("newTeam"));

            boolean result = sendGridEmailService.sendDynamicEmail(
                    System.getenv("SENDER_EMAIL"),
                    mailData.get("email"),
                    dynamicData);

            if (result) {
                System.out.println("Email sent to " + mailData.get("email"));
            } else {
                System.out.println("Email not sent to " + mailData.get("email"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
