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

    @Bean
    public DirectExchange employeeExchange() {
        return new DirectExchange("employee-exchange");
    }

    @Bean
    public Queue employeeUpdatesQueue() {
        return new Queue("employee-updates-queue");
    }

    @Bean
    public Queue emailStatusQueue() {
        return new Queue("email-status-queue");
    }

    @Bean
    public Binding employeeUpdatesBinding(Queue employeeUpdatesQueue, DirectExchange employeeExchange) {
        return BindingBuilder.bind(employeeUpdatesQueue).to(employeeExchange).with("employee.update");
    }

    @Bean
    public Binding emailStatusBinding(Queue emailStatusQueue, DirectExchange employeeExchange) {
        return BindingBuilder.bind(emailStatusQueue).to(employeeExchange).with("email.status");
    }
}
