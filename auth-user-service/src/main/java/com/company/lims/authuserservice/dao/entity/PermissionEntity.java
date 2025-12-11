//package com.company.lims.authuserservice.dao.entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToMany;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDateTime;
//import java.util.HashSet;
//import java.util.Set;
//
//import static jakarta.persistence.GenerationType.IDENTITY;
//import static lombok.AccessLevel.PRIVATE;
//
//@Entity
//@Table(name = "users")
//@FieldDefaults(level = PRIVATE)
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//public class PermissionEntity {
//    @Id
//    @GeneratedValue(strategy = IDENTITY)
//    Long id;
//
//    String code;
//
//    String description;
//
//    @CreationTimestamp
//    LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    LocalDateTime updateAt;
//
//    @ManyToMany(mappedBy = "permissions")
//    Set<UserEntity> users = new HashSet<>();
//}
