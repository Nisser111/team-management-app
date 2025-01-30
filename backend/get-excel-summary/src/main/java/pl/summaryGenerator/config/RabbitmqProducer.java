package pl.summaryGenerator.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.core.Queue;

/**
 * RabbitmqProducer is responsible for sending messages to a RabbitMQ queue.
 * It uses RabbitTemplate to facilitate the messaging process.
 */
public class RabbitmqProducer {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    /**
     * Sends a message to the RabbitMQ queue indicating that the Excel summary file
     * is available.
     */
    public void sender() {
        String message = "Excel summary file is available on link.";
        this.template.convertAndSend(queue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
