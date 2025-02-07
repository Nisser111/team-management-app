package pl.sms_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class SmsServiceListener {

    private final TwilioSendSmsService twilioSendSmsService;

    private final RabbitTemplate rabbitTemplate;

    public SmsServiceListener(TwilioSendSmsService twilioSendSmsService, RabbitTemplate rabbitTemplate) {
        this.twilioSendSmsService = twilioSendSmsService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "employee-updates-sms-queue")
    public String receiveMessage(Map<String, String> smsData) throws IOException {
        boolean emailSent = sendSmsNotification(smsData);
        return emailSent ? "SUCCESS" : "FAILED";
    }

    private boolean sendSmsNotification(Map<String, String> smsData) throws IOException {
        try {
            Map<String, String> dynamicData = new HashMap<>();
            dynamicData.put("employee_name", smsData.get("firstName"));
            dynamicData.put("team_name", smsData.get("newTeam"));

            return twilioSendSmsService.sendSms(smsData.get("phone"), System.getenv("TWILIO_MSG_TEMPLATE_ID"), dynamicData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
