package com.company.lims.authuserservice.security;

import com.company.lims.authuserservice.dao.entity.RoleEntity;
import com.company.lims.authuserservice.dao.entity.UserEntity;
import com.company.lims.authuserservice.dao.repository.RoleRepository;
import com.company.lims.authuserservice.dao.repository.UserRepository;
import com.company.lims.authuserservice.enums.RoleName;
import com.company.lims.authuserservice.exception.RoleNotFounException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByUsername("testuser")) {
            UserEntity user = new UserEntity();
            user.setUsername("testuser");
            user.setPassword(passwordEncoder.encode("12345"));

            RoleEntity roleEntity = roleRepository.findByName(RoleName.SUPER_ADMIN).orElseThrow(() -> {
                log.warn("qeyd olunan{} rol bazada movcud deyil", RoleName.SUPER_ADMIN);
                return new RoleNotFounException("Rol movcud deyil");
            });
            user.getRoles().add(roleEntity);
            userRepository.save(user);
        }
    }
}
