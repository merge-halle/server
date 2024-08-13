package dev.changuii.project.service;

import dev.changuii.project.dto.ReviewDTO;
import dev.changuii.project.dto.response.ReviewResponseDTO;

public interface ReviewService {

    public ReviewResponseDTO generateReview(ReviewDTO reviewDTO, Long id);
}
