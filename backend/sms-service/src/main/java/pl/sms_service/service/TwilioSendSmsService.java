package pl.sms_service.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TwilioSendSmsService {

    private final String messagingServiceId = System.getenv("TWILIO_MSG_SERVICE_ID");

    public void sendSms(String to, String templateSid, Map<String, String> variables) {
        if (messagingServiceId == null || messagingServiceId.isEmpty()) {
            throw new IllegalStateException("Twilio Messaging Service ID is not set in environment variables.");
        }

        if (templateSid == null || templateSid.isEmpty()) {
            throw new IllegalArgumentException("Template SID must not be null or empty.");
        }

        Message message = Message.creator(
                        new PhoneNumber(to),
                        messagingServiceId,
                        ""
                ).setMessagingServiceSid(messagingServiceId)
                .setSmartEncoded(true)
                .setApplicationSid(templateSid) // This ensures the correct dynamic template is used
                .create();

        System.out.println("Message Sent: " + message.getSid());
    }
}
