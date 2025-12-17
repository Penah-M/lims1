package com.example.order.ms.dao.repository;

import com.example.order.ms.dao.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {


}
