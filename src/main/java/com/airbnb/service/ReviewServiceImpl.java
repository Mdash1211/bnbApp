package com.airbnb.service;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Property;
import com.airbnb.entity.Review;
import com.airbnb.exception.PropertyNotFoundException;
import com.airbnb.exception.ReviewAlreadyExistsException;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;
import com.airbnb.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }


    @Override
    public Review addReview(Review review, AppUser user, Long propertyId) {
        Property property=propertyRepository.findById(propertyId).orElseThrow(
                ()->new PropertyNotFoundException("Property Not Found")
        );
        Review byUserAndProperty = reviewRepository.findByUserAndProperty(user, property);
        if(byUserAndProperty!=null){
            throw new ReviewAlreadyExistsException("Review already exists for this property by the user");
        }
            review.setAppUser(user);
            review.setProperty(property);
            return reviewRepository.save(review);

    }

    @Override
    public List<Review> getAllReviews(AppUser user) {
        List<Review> reviewsByUser = reviewRepository.findReviewsByUser(user);
        return reviewsByUser;
    }
}
