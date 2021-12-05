package com.example.datn.repository;

import com.example.datn.models.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "Select * from orders where CAST(order_id AS varchar) iLike %:id%", nativeQuery = true)
    Page<Order>  findByOrderId(String id, Pageable pager);

    Order findByOrderId(Long orderId);

    @Query(value = "Select * from orders where email iLike %:email% or customer_id = :customer_id", nativeQuery = true)
    Page<Order> findOrderCustomer(@Param(value = "email") String email, @Param(value = "customer_id") int customer_id, Pageable pager );
}
