package pl.menagment_system.team_menagment_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

/**
 * Configuration class for RabbitMQ.
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
     * Creates a RabbitmqConsumer bean for processing messages from the queue.
     *
     * @return a new instance of RabbitmqConsumer
     */
    @Bean
    public RabbitmqConsumer reciver() {
        return new RabbitmqConsumer();
    }

}
