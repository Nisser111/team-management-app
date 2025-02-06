package pl.menagment_system.team_menagment_system.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmployeeEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public EmployeeEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmployeeUpdate(Map<String, String> mailData) {
        rabbitTemplate.convertAndSend("employee-exchange", "employee.update", mailData);
        System.out.println("Sent message: " + mailData);
    }
}
