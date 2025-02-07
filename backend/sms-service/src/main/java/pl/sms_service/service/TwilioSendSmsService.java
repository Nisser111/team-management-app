package pl.sms_service.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TwilioSendSmsService {

    private final String messagingServiceId = System.getenv("TWILIO_MSG_SERVICE_ID");

    public boolean sendSms(String to, String templateSid, Map<String, String> variables) {
        try {
            if (messagingServiceId == null || messagingServiceId.isEmpty()) {
                throw new IllegalStateException("Twilio Messaging Service ID is not set in environment variables.");
            }

            if (templateSid == null || templateSid.isEmpty()) {
                throw new IllegalArgumentException("Template SID must not be null or empty.");
            }

            Message.creator(
                    new PhoneNumber(to),
                    messagingServiceId,
                            "Witaj " + variables.get("employee_name") + ",\n" +
                                    "\n" +
                                    "Z przyjemnością informujemy, że zostałeś/aś dodany/a do zespołu " + variables.get("team_name") + ".\n" +
                                    "\n" +
                                    "Współpracuj, twórz innowacje i wywieraj wpływ razem ze swoimi nowymi kolegami.\n" +
                                    "\n" +
                                    "Zespół Systemu Zarządzania Zespołami"
                    ).setMessagingServiceSid(messagingServiceId)
                    .setSmartEncoded(true)
                    .create();


            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
