//package com.company.lims.authuserservice;
//
//import com.company.lims.authuserservice.dao.entity.RoleEntity;
//import com.company.lims.authuserservice.dao.entity.UserEntity;
//import com.company.lims.authuserservice.dao.repository.RoleRepository;
//import com.company.lims.authuserservice.dao.repository.UserRepository;
//import com.company.lims.authuserservice.enums.RoleName;
//import com.company.lims.authuserservice.enums.UserStatus;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//@RequiredArgsConstructor
//public class DataLoader implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Value("${superadmin.username}")
//    private String superUsername;
//
//    @Value("${superadmin.password}")
//    private String superPassword;
//
//
//    @Transactional
//    @Override
//    public void run(String... args) throws Exception {
//        if (!userRepository.existsByUsername(superUsername)) {
//            RoleEntity superAdminRole = roleRepository.findByName(RoleName.SUPER_ADMIN)
//                    .orElseThrow(() -> new RuntimeException("SUPER_ADMIN role missing"));
//            UserEntity superAdmin = new UserEntity();
//            superAdmin.setUsername(superUsername);
//            superAdmin.setPassword(passwordEncoder.encode(superPassword));
//            superAdmin.setStatus(UserStatus.ACTIVE);
//            superAdmin.getRoles().add(superAdminRole);
//            userRepository.save(superAdmin);
//        }
//    }
//}

