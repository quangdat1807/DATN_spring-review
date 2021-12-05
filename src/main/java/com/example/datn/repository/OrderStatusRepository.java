package com.example.datn.repository;

import com.example.datn.models.TinhTrangDonHang;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<TinhTrangDonHang, Integer> {
    
}
