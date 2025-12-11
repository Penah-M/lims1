package com.company.lims.authuserservice.service;

import com.company.lims.authuserservice.dao.entity.RoleEntity;
import com.company.lims.authuserservice.dao.entity.UserEntity;
import com.company.lims.authuserservice.dao.repository.RoleRepository;
import com.company.lims.authuserservice.dao.repository.UserRepository;
import com.company.lims.authuserservice.dto.request.RegisterRequest;
import com.company.lims.authuserservice.dto.response.RegisterResponse;
import com.company.lims.authuserservice.dto.response.UserResponse;
import com.company.lims.authuserservice.exception.RoleNotFounException;
import com.company.lims.authuserservice.exception.UserAlreadyExistsException;
import com.company.lims.authuserservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.company.lims.authuserservice.enums.UserStatus.ACTIVE;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Transactional
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;
    UserMapper userMapper;



//    @Transactional
//    public RegisterResponse createUser(RegisterRequest request){
//        // Username və email check
//        if(userRepository.existsByUsername(request.getUserName())) {
//            throw new UserAlreadyExistsException("Bu username artıq mövcuddur");
//        }
//        if(userRepository.existsByEmail(request.getEmail())) {
//            throw new UserAlreadyExistsException("Bu email artıq mövcuddur");
//        }
//
//        // Mapper ilə user entity yarat
//        UserEntity userEntity = userMapper.entity(request);
//        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
//
//        // Rol tap
//        Optional<RoleEntity> optionalRole = roleRepository.findByName(request.getRoleName());
//        if(optionalRole.isEmpty()) {
//            log.error("Qeyd olunan {} ROL bazada movcud deyil", request.getRoleName());
//            throw new RoleNotFounException("Qeyd olunan rol bazada movcud deyil");
//        }
//
//        RoleEntity role = optionalRole.get();
//        log.info("Role tapildi: {}", role.getName());
//
//        // Set null yoxlanisi (mapperde artıq HashSet var, amma check əlavə edək)
//        if(userEntity.getRoles() == null) {
//            log.warn("UserEntity roles null geldi, yeni HashSet yaradildi");
//            userEntity.setRoles(new HashSet<>());
//        }
//
//        userEntity.getRoles().add(role);
//        userEntity.setStatus(ACTIVE);
//
//        UserEntity savedUser = userRepository.save(userEntity);
//        log.info("User yaradildi: {}", savedUser.getUsername());
//
//        return userMapper.response(savedUser);
//    }


    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }



    @Transactional
    public RegisterResponse createUser (RegisterRequest request){

        if(userRepository.existsByUsername(request.getUserName())) {
            throw new UserAlreadyExistsException("Bu username artıq mövcuddur");
        }
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Bu email artıq mövcuddur");
        }
        UserEntity userEntity = userMapper.entity(request);
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));

        RoleEntity role = roleRepository.findByName(request.getRoleName()).orElseThrow(() -> {
            log.error("Qeyd olunan {} ROL bazada movcud deyil", request.getRoleName());
            return new RoleNotFounException("Qeyd olunan rol bazada movcud deyil");
        });

        userEntity.getRoles().add(role);
        userEntity.setStatus(ACTIVE);
        userRepository.save(userEntity);
        return userMapper.response(userEntity);
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
