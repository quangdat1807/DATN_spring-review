package com.example.datn.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.datn.models.Product;
import com.example.datn.models.Review;
import com.example.datn.payload.request.RatingRequest;
import com.example.datn.payload.response.MessageResponse;
import com.example.datn.repository.ReviewRepository;


@RestController
@RequestMapping("api/review")
public class ReviewRestController {
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@GetMapping("/{id}")
	public List<Review> getAll(@PathVariable("id") int id) {
		
	    return reviewRepository.findByProductId(id);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid RatingRequest ratingRequest) {
		
		String phoneNumber = ratingRequest.getPhoneNumber();
		int productId = ratingRequest.getProductId();
		
		Optional<Review> reviewOptional = Optional
				.ofNullable(reviewRepository.findByReview(phoneNumber, productId));
		
		if (!reviewOptional.isPresent()) {
			Product pr = new Product();
			pr.setProductId(ratingRequest.getProductId());
			
			System.out.println(ratingRequest.getProductId());
			Review rw = new Review();
			rw.setName(ratingRequest.getName());
			rw.setProduct(pr);
			rw.setDescription(ratingRequest.getDescription());
			rw.setGmail(ratingRequest.getGmail());
			rw.setPhoneNumber(ratingRequest.getPhoneNumber());
			rw.setScore(ratingRequest.getScore());
			rw.setImage(ratingRequest.getImage());
			reviewRepository.save(rw);
			
			return ResponseEntity.ok(new MessageResponse("successfully!"));
		}
		
		
		
		return ResponseEntity.ok(new MessageResponse("spam kia!"));
	}
	
	
}
