package com.example.keyboardshops.data;

import com.example.keyboardshops.model.Role;
import com.example.keyboardshops.model.User;
import com.example.keyboardshops.repository.RoleRepository;
import com.example.keyboardshops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_USER", "ROLE_ADMIN");
        createDefaultUserIfNotExits(defaultRoles);
        createDefaultUserIfNotExits();
        createDefaultAdminIfNotExits();
    }

    private void createDefaultUserIfNotExits() {
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) continue;
            User user = new User();
            user.setEmail(defaultEmail);
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setRoles(Set.of(userRole));
            user.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(user);
            System.out.println("User " + i + " created");
        }
    }

    private void createDefaultAdminIfNotExits() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) continue;
            User user = new User();
            user.setEmail(defaultEmail);
            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setRoles(Set.of(adminRole));
            user.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(user);
            System.out.println("Admin " + i + " created");
        }
    }

    private void createDefaultUserIfNotExits(Set<String> roles){
        roles.stream().filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role :: new).forEach(roleRepository :: save);
    }


}
