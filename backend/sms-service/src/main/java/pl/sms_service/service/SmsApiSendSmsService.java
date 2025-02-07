package pl.sms_service.service;

import org.springframework.stereotype.Service;
import pl.smsapi.OAuthClient;
import pl.smsapi.api.SmsFactory;
import pl.smsapi.api.action.sms.SMSGet;
import pl.smsapi.api.action.sms.SMSSend;
import pl.smsapi.api.response.MessageResponse;
import pl.smsapi.api.response.StatusResponse;
import pl.smsapi.exception.SmsapiException;
import pl.smsapi.proxy.ProxyNative;

import java.util.Map;

@Service
public class SmsApiSendSmsService {

    private final String oauthToken = System.getenv("SMSAPI_OAUTH_TOKEN");

    public boolean sendSms(Map<String, String> variables) {
        try {
            if (oauthToken == null || oauthToken.isEmpty()) {
                throw new IllegalStateException("Sms API OAuth token is not set in environment variables.");
            }

            // Ensure variables map is valid
            if (variables == null || !variables.containsKey("phone") || variables.get("phone") == null) {
                throw new IllegalArgumentException("Phone number is missing or null in variables.");
            }

            String phoneNumber = variables.get("phone").trim();
            if (phoneNumber.isEmpty()) {
                throw new IllegalArgumentException("Phone number is empty.");
            }

            // Extract and validate employee_name and team_name
            String employeeName = variables.getOrDefault("employee_name", "Użytkownik");
            String teamName = variables.getOrDefault("team_name", "Twój nowy zespół");

            // Construct SMS body with placeholders replaced
            String messageBody = String.format(
                    "Witaj %s,\n\nZ przyjemnością informujemy, że zostałeś/aś dodany/a do zespołu %s.\n\n" +
                            "Współpracuj, twórz innowacje i wywieraj wpływ razem ze swoimi nowymi kolegami.\n\n" +
                            "Zespół Systemu Zarządzania Zespołami",
                    employeeName, teamName
            );

            OAuthClient client = new OAuthClient(oauthToken);
            ProxyNative proxy = new ProxyNative("https://api.smsapi.pl/");
            SmsFactory smsApi = new SmsFactory(client, proxy);

            SMSSend action = smsApi.actionSend(phoneNumber, messageBody);

            StatusResponse result = action.execute();

            if (result.getList() == null || result.getList().isEmpty()) {
                throw new RuntimeException("SMS API returned an empty response.");
            }

            String shipmentId = result.getList().get(0).getId();
            SMSGet getAction = smsApi.actionGet(shipmentId);

            StatusResponse shipmentStatus = getAction.execute();
            MessageResponse status = shipmentStatus.getList().get(0);


            return status.getStatus().equals("SENT");
        } catch (SmsapiException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
