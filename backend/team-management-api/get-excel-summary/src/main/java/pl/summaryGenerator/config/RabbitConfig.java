package pl.summaryGenerator.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ settings.
 * This class defines the necessary beans for RabbitMQ messaging.
 */
@Configuration
public class RabbitConfig {

    /**
     * Creates a Queue bean for the RabbitMQ messaging system.
     * The queue is named "get-employees-excel-summary".
     *
     * @return a new instance of Queue
     */
    @Bean
    public Queue queue() {
        return new Queue("get-employees-excel-summary");
    }

    /**
     * Creates a RabbitmqProducer bean for sending messages to RabbitMQ.
     *
     * @return a new instance of RabbitmqProducer
     */
    @Bean
    public RabbitmqProducer sender() {
        return new RabbitmqProducer();
    }

}
