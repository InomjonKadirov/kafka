package uz.deus.edu.spring.kafka.payload;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Order(@JsonProperty("id")UUID id, @JsonProperty("status") String status) {

}
