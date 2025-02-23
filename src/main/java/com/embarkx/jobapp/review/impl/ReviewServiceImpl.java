package com.embarkx.jobapp.review.impl;

import com.embarkx.jobapp.company.Company;
import com.embarkx.jobapp.company.CompanyService;
import com.embarkx.jobapp.review.Review;
import com.embarkx.jobapp.review.ReviewRepository;
import com.embarkx.jobapp.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    ReviewRepository reviewRepository;
    private final CompanyService companyService;
    private Long reviewID = 1L;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {

        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean addReview(Long companyId, Review review){

        Company company = companyService.getCompanyById(companyId);

        if(company!=null)
        {
            review.setCompany(company);
            review.setId(reviewID++);
            reviewRepository.save(review);
            return true;
        }

        return false;
    }

    @Override
    public Review getCompanyReviewByReviewId(Long companyId, Long reviewId) {

        List<Review> reviews = reviewRepository.findByCompanyId(companyId);

        return reviews.stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review review) {

        List<Review> reviews = reviewRepository.findByCompanyId(companyId);

        for(Review reviewExisting : reviews)
        {
            if(reviewExisting.getId().equals(reviewId)){

                reviewExisting.setRating(review.getRating());
                reviewExisting.setDescription(review.getDescription());
                reviewExisting.setTitle(review.getTitle());
                reviewExisting.setCompany(companyService.getCompanyById(companyId));

                reviewRepository.save(reviewExisting);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {

        if(companyService.getCompanyById(companyId) != null && reviewRepository.existsById(reviewId))
        {
            Review review = reviewRepository.findById(reviewId).orElse(null);
            Company company = review.getCompany();
            company.getReviews().remove(review);
            review.setCompany(null);
            companyService.updateCompany(companyId, company);
            reviewRepository.deleteById(reviewId);

            return true;
        }

        return false;
    }
}
