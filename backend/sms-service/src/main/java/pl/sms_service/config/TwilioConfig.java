package pl.sms_service.config;

import com.twilio.Twilio;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;

@Configuration
public class TwilioConfig {

    public void initTwilio() {
        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));
    }
}

