package pl.menagment_system.team_menagment_system.config;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * RabbitmqConsumer is a listener class that processes messages from the
 * RabbitMQ queue.
 * It listens to the queue named "get-employees-excel-summary" and handles
 * incoming messages.
 */
@RabbitListener(queues = "get-employees-excel-summary")
public class RabbitmqConsumer {

    /**
     * This method is invoked when a message is received from the RabbitMQ queue.
     * It logs the received message to the console.
     *
     * @param in the message received from the queue
     */
    @RabbitHandler
    public void receive(String in) {
        System.out.println(" [x] Received '" + in + "'");
    }
}
