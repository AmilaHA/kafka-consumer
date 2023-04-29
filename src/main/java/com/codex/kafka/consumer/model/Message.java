package com.codex.kafka.consumer.model;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class Message {

    String name;
    Double amount;

}
