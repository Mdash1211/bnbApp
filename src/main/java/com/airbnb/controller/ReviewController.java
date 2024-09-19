package com.airbnb.controller;


import com.airbnb.entity.AppUser;
import com.airbnb.entity.Review;
import com.airbnb.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping("/createReview")
    public ResponseEntity<Review> createReview(@RequestBody Review review,
        @AuthenticationPrincipal AppUser user, @RequestParam Long propertyId) {
        return new ResponseEntity<>(reviewService.addReview(review,user,propertyId),HttpStatus.CREATED);

    }

    @GetMapping("/userReviews")
    public ResponseEntity<List<Review>> listReviewOfUser(@AuthenticationPrincipal AppUser user) {
        return new ResponseEntity<>(reviewService.getAllReviews(user), HttpStatus.OK);
    }
}
