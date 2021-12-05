package com.example.datn.service;

import java.util.List;

import com.example.datn.models.Review;

public interface ReviewService {
	List<Review> findAll();
	
	Review create(Review review);
}
