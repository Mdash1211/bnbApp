package com.airbnb.service;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Review;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ReviewService {

    public Review addReview(Review review,AppUser user, Long propertyId);

    public List<Review> getAllReviews(AppUser user);
}
