package com.example.order.ms.dao.repository;

import com.example.order.ms.dao.entity.OrderTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTestRepository extends JpaRepository<OrderTestEntity,Long> {
}
