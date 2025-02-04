package pl.email_service.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Service for sending emails using SendGrid.
 */
@Service
public class SendGridEmailService {
    private final String sendGridApiKey = System.getenv("SENDGRID_API_KEY");

    /**
     * Sends a dynamic email using the specified parameters.
     *
     * @param fromEmail   the sender's email address
     * @param toEmail     the recipient's email address
     * @param dynamicData a map containing dynamic data to be included in the email
     *                    template
     * @return true if the email was sent successfully, false otherwise
     * @throws IOException if there is an error during the email sending process
     */
    public Boolean sendDynamicEmail(String fromEmail, String toEmail, Map<String, String> dynamicData)
            throws IOException {
        Mail mail = getMail(fromEmail, toEmail, dynamicData);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            return response.getStatusCode() == 202;
        } catch (IOException ex) {
            throw new RuntimeException("Error sending email", ex);
        }
    }

    /**
     * Constructs a Mail object with the specified parameters.
     *
     * @param fromEmail   the sender's email address
     * @param toEmail     the recipient's email address
     * @param dynamicData a map containing dynamic data to be included in the email
     *                    template
     * @return a Mail object ready to be sent
     */
    private static Mail getMail(String fromEmail, String toEmail, Map<String, String> dynamicData) {
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setTemplateId("d-ead48a5831e5448aa420d6b3889b55d8");

        Personalization personalization = new Personalization();
        personalization.addTo(to);

        for (Map.Entry<String, String> entry : dynamicData.entrySet()) {
            personalization.addDynamicTemplateData(entry.getKey(), entry.getValue());
        }

        mail.addPersonalization(personalization);
        return mail;
    }
}
