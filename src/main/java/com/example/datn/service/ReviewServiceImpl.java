package com.example.datn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.datn.models.Review;
import com.example.datn.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewRepository rvRep;
	
	@Override
	public List<Review> findAll() {
		// TODO Auto-generated method stub
		return rvRep.findAll();
	}

	@Override
	public Review create(Review review) {
		// TODO Auto-generated method stub
		return rvRep.save(review);
	}

}
