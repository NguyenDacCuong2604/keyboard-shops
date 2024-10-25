package com.example.keyboardshops.data;

import com.example.keyboardshops.model.User;
import com.example.keyboardshops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExits();
    }

    private void createDefaultUserIfNotExits() {
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) continue;
            User user = new User();
            user.setEmail(defaultEmail);
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setPassword("123456");
            userRepository.save(user);
            System.out.println("User " + i + " created");
        }
    }


}
