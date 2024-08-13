package dev.changuii.project.controller;


import dev.changuii.project.dto.ReviewDTO;
import dev.changuii.project.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {


    private final ReviewService reviewService;

    public ReviewController(
            @Autowired ReviewService reviewService
    ){
        this.reviewService=reviewService;
    }

    @PostMapping("/{id}")
    public ResponseEntity generateReview(
            @RequestBody ReviewDTO reviewDTO,
            @PathVariable("id") Long id
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.reviewService.generateReview(reviewDTO, id));
    }


}
