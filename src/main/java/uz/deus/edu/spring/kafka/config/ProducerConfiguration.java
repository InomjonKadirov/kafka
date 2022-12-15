package uz.deus.edu.spring.kafka.config;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import static org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BATCH_SIZE_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.COMPRESSION_TYPE_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.LINGER_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION;
import static org.apache.kafka.clients.producer.ProducerConfig.RETRIES_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@RequiredArgsConstructor
public class ProducerConfiguration {

    private final  KafkaProperties kafkaProperties;

    @Bean
    public Map<String, Object> configs() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());

        // most of the configuration can be set in application.yml file
        props.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        //  safe producer configs where the writes are acknowledged
        props.put(ENABLE_IDEMPOTENCE_CONFIG, "true");  // to avoid message duplication
        props.put(ACKS_CONFIG, "all"); // to ensure that the data is not lost in any circumstances
        props.put(RETRIES_CONFIG, Integer.toString(10));
        props.put(MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");

        // high throughput producer configs
        props.put(COMPRESSION_TYPE_CONFIG, "snappy"); // faster data transfer and less latency
        props.put(LINGER_MS_CONFIG, "20");  // to ensure we use batching
        props.put(BATCH_SIZE_CONFIG, Integer.toString(32*1024)); //32KB
        return props;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(configs());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
