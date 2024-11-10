package com.demo.service;

import com.demo.event.UserEvent;
import com.demo.model.User;
import com.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public User save(User user){
        UserEvent userEvent = UserEvent.builder()
                .eventName(user.getUsername())
                .build();
        kafkaTemplate.send("event-topic", userEvent);
        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}