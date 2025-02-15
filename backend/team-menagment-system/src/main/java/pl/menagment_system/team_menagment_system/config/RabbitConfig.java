package pl.menagment_system.team_menagment_system.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    // For email service

    @Bean
    public DirectExchange employeeMailExchange() {
        return new DirectExchange("employee-mail-exchange");
    }

    @Bean
    public Queue employeeUpdatesMailQueue() {
        return new Queue("employee-updates-mail-queue");
    }

    @Bean
    public Queue emailStatusQueue() {
        return new Queue("email-status-queue");
    }

    @Bean
    public Binding employeeUpdatesMailBinding(Queue employeeUpdatesMailQueue, DirectExchange employeeMailExchange) {
        return BindingBuilder.bind(employeeUpdatesMailQueue).to(employeeMailExchange).with("employee.mail.update");
    }

    @Bean
    public Binding emailStatusBinding(Queue emailStatusQueue, DirectExchange employeeMailExchange) {
        return BindingBuilder.bind(emailStatusQueue).to(employeeMailExchange).with("email.status");
    }

    // For sms service

    @Bean
    public DirectExchange employeeSmsExchange() {
        return new DirectExchange("employee-sms-exchange");
    }

    @Bean
    public Queue employeeUpdatesSmsQueue() {
        return new Queue("employee-updates-sms-queue");
    }

    @Bean
    public Queue smsStatusQueue() {
        return new Queue("sms-status-queue");
    }

    @Bean
    public Binding employeeUpdatesSmsBinding(Queue employeeUpdatesSmsQueue, DirectExchange employeeSmsExchange) {
        return BindingBuilder.bind(employeeUpdatesSmsQueue).to(employeeSmsExchange).with("employee.sms.update");
    }

    @Bean
    public Binding smsStatusBinding(Queue smsStatusQueue, DirectExchange employeeSmsExchange) {
        return BindingBuilder.bind(smsStatusQueue).to(employeeSmsExchange).with("sms.status");
    }
}
