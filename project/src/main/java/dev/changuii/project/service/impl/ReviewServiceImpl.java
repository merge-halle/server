package dev.changuii.project.service.impl;

import dev.changuii.project.dto.OpenAiRequestDTO;
import dev.changuii.project.dto.ReviewDTO;
import dev.changuii.project.dto.response.ReviewResponseDTO;
import dev.changuii.project.entity.QuestionEntity;
import dev.changuii.project.entity.ReviewEntity;
import dev.changuii.project.enums.ErrorCode;
import dev.changuii.project.exception.CustomException;
import dev.changuii.project.repository.QuestionRepository;
import dev.changuii.project.repository.ReviewRepository;
import dev.changuii.project.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;


@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final QuestionRepository questionRepository;

    private final RestTemplate restTemplate;
    private final String url = "https://api.openai.com/v1/chat/completions";

    @Value("${openai.api-key}")
    private String apiKey;

    public ReviewServiceImpl(
            @Autowired QuestionRepository questionRepository,
            @Autowired ReviewRepository reviewRepository,
            @Autowired RestTemplate restTemplate
    ){
        this.reviewRepository=reviewRepository;
        this.restTemplate=restTemplate;
        this.questionRepository=questionRepository;
    }

    @Override
    public ReviewResponseDTO generateReview(ReviewDTO reviewDTO, Long id) {

        String productDescription = this.questionRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND)).getProductDescription();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OpenAiRequestDTO> request = new HttpEntity<>(reviewDTO.toOpenAiRequestDTO(productDescription), headers);

        LinkedHashMap response = restTemplate.exchange(url, HttpMethod.POST, request, LinkedHashMap.class).getBody();
        List<LinkedHashMap> data = (List<LinkedHashMap>) response.get("choices");
        LinkedHashMap message = (LinkedHashMap) data.get(0).get("message");
        String contents = message.get("content").toString();

        System.out.println(contents);
        ReviewEntity review = ReviewEntity.builder()
                .review(contents)
                .build();

        return this.reviewRepository.save(review)
                .toResponseDTO();
    }
}
