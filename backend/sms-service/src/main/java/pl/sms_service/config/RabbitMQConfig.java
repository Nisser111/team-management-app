package pl.sms_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {
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
