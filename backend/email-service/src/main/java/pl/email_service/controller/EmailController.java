package pl.email_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.email_service.service.SendGridEmailService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for handling email-related requests.
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    private final SendGridEmailService sendGridEmailService;

    /**
     * Constructs an EmailController with the specified SendGridEmailService.
     *
     * @param sendGridEmailService the service used to send emails
     */
    public EmailController(SendGridEmailService sendGridEmailService) {
        this.sendGridEmailService = sendGridEmailService;
    }

    /**
     * Sends a notification email when an employee is added to a new team.
     *
     * @param toEmail       the recipient's email address
     * @param employee_name the name of the employee
     * @param team_name     the name of the team
     * @return a ResponseEntity containing the result of the email sending operation
     */
    @PostMapping("/send")
    public ResponseEntity<Object> sendAddToNewTeamNotification(
            @RequestParam String toEmail,
            @RequestParam String employee_name,
            @RequestParam String team_name) {

        Map<String, String> response = new HashMap<>();

        try {
            Map<String, String> dynamicData = new HashMap<>();
            dynamicData.put("employee_name", employee_name);
            dynamicData.put("team_name", team_name);

            boolean result = sendGridEmailService.sendDynamicEmail(
                    System.getenv("SENDER_EMAIL"),
                    toEmail,
                    dynamicData);

            if (result) {
                response.put("message", "The message has been sent successfully.");
                return ResponseEntity.ok().body(response);
            } else {
                response.put("error", "Error during sending an email.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (IOException e) {
            response.put("error", "Error sending email.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
