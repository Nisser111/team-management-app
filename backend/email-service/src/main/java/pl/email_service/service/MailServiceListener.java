package pl.email_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    private final RabbitTemplate rabbitTemplate;

    /**
     * Constructor for MailServiceListener.
     *
     * @param sendGridEmailService The service used to send emails via SendGrid.
     * @param rabbitTemplate       The RabbitTemplate used for sending messages to
     *                             RabbitMQ.
     */
    public MailServiceListener(SendGridEmailService sendGridEmailService, RabbitTemplate rabbitTemplate) {
        this.sendGridEmailService = sendGridEmailService;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Listens for messages from the "employee-updates-queue".
     * When a message is received, it extracts the email data and sends
     * an email notification to the specified recipient.
     *
     * @param mailData A map containing email data, including the recipient's email,
     *                 employee's first name, and the new team.
     * @return A string indicating the result of the email sending operation
     *         ("SUCCESS" or "FAILED").
     * @throws IOException If an error occurs while sending the email.
     */
    @RabbitListener(queues = "employee-updates-queue")
    public String receiveMessage(Map<String, String> mailData) throws IOException {
        boolean emailSent = sendEmailNotification(mailData);
        return emailSent ? "SUCCESS" : "FAILED";
    }

    /**
     * Sends an email notification using the SendGridEmailService.
     * It prepares the dynamic data for the email and calls the service to send it.
     *
     * @param mailData A map containing email data, including the recipient's email,
     *                 employee's first name, and the new team.
     * @return A boolean indicating whether the email was sent successfully.
     * @throws IOException If an error occurs while sending the email.
     */
    private boolean sendEmailNotification(Map<String, String> mailData) throws IOException {
        try {
            Map<String, String> dynamicData = new HashMap<>();
            dynamicData.put("employee_name", mailData.get("firstName"));
            dynamicData.put("team_name", mailData.get("newTeam"));

            return sendGridEmailService.sendDynamicEmail(
                    System.getenv("SENDER_EMAIL"),
                    mailData.get("email"),
                    dynamicData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
