package com.codex.kafka.consumer.service;

import com.codex.kafka.consumer.entity.Item;
import com.codex.kafka.consumer.model.Message;
import com.codex.kafka.consumer.repository.ItemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @Value("${topic.name.consumer")
    private String topicName;

    @Autowired
    private ItemRepository itemRepository;

    private ObjectMapper objectMapper;
    private ModelMapper modelMapper;

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
    public void messageConsumer(String message) {
        try {
            Message messageDTO = objectMapper.readValue(message, Message.class);
            saveItem(messageDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String saveItem(Message message) {
        Item item = modelMapper.map(message, Item.class);
        Item save = itemRepository.save(item);
        return "save success: " + save;
    }

}
