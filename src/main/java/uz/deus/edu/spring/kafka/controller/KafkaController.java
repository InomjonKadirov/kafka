package uz.deus.edu.spring.kafka.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.deus.edu.spring.kafka.payload.Order;
import uz.deus.edu.spring.kafka.payload.Payment;
import uz.deus.edu.spring.kafka.service.ProducerService;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kafka")
public class KafkaController {

    private final ProducerService producerService;

    @PostMapping("/order")
    public ResponseEntity<String> sendOrder(Order order) {
        log.info("Order {}", order);
        producerService.sendOrder(order);
        return ResponseEntity.ok("Send");
    }

    @PostMapping("/payment")
    public ResponseEntity<String> sendPayment(Payment payment) {
        log.info("Payment {}", payment);
        producerService.sendPayment(payment);
        return ResponseEntity.ok("Send");
    }

}
