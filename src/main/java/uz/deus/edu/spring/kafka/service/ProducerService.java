package uz.deus.edu.spring.kafka.service;

import uz.deus.edu.spring.kafka.payload.Order;
import uz.deus.edu.spring.kafka.payload.Payment;

public interface ProducerService {

    void sendOrder(Order order);
    void sendPayment(Payment payment);

}
