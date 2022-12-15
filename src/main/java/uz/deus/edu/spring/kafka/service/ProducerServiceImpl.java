package uz.deus.edu.spring.kafka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import uz.deus.edu.spring.kafka.payload.Order;
import uz.deus.edu.spring.kafka.payload.Payment;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Override
    public void sendOrder(Order order) {
        kafkaTemplate.send("order", order);
    }

    @Override
    public void sendPayment(Payment payment) {
        kafkaTemplate.send("payment", payment);
    }
}
