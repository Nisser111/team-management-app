package pl.menagment_system.team_menagment_system.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service for publishing employee-related events to a message broker.
 * This service is responsible for sending messages related to employee updates
 * to the RabbitMQ exchange.
 */
@Service
public class EmployeeEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    /**
     * Constructor for EmployeeEventPublisher.
     *
     * @param rabbitTemplate The RabbitTemplate used for sending messages to
     *                       RabbitMQ.
     */
    public EmployeeEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Sends an employee update message to the RabbitMQ exchange.
     *
     * @param mailData A map containing the data related to the employee update.
     * @return A response from the message broker, typically indicating the result
     *         of the operation.
     */
    public String sendEmployeeUpdateMail(Map<String, String> mailData) {
        return (String) rabbitTemplate.convertSendAndReceive("employee-mail-exchange", "employee.mail.update", mailData);
    }

    public String sendEmployeeUpdateSms(Map<String, String> mailData) {
        return (String) rabbitTemplate.convertSendAndReceive("employee-sms-exchange", "employee.sms.update", mailData);
    }
}
