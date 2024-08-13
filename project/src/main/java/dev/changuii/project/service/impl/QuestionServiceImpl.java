package dev.changuii.project.service.impl;

import dev.changuii.project.dto.QuestionDTO;
import dev.changuii.project.dto.response.QuestionResponseDTO;
import dev.changuii.project.dto.OpenAiRequestDTO;
import dev.changuii.project.entity.QuestionEntity;
import dev.changuii.project.repository.QuestionRepository;
import dev.changuii.project.service.QuestionService;
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
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final RestTemplate restTemplate;
    private final String url = "https://api.openai.com/v1/chat/completions";

    @Value("${openai.api-key}")
    private String apiKey;

    public QuestionServiceImpl(
            @Autowired QuestionRepository questionRepository,
            @Autowired RestTemplate restTemplate
    ){
        this.questionRepository=questionRepository;
        this.restTemplate=restTemplate;
    }


    @Override
    public QuestionResponseDTO generateQuestion(QuestionDTO questionDTO) {
        // post 요청
        // https://api.openai.com/v1/completions
        // Authorization : Bearer {API KEY}
        // {"model" : "model명", "prompt" : "데이터", "max_tokens" : 100, "temparature" : 1}

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OpenAiRequestDTO> request = new HttpEntity<>(questionDTO.toOpenAiRequestDTO(), headers);

        LinkedHashMap response = restTemplate.exchange(url, HttpMethod.POST, request, LinkedHashMap.class).getBody();
        List<LinkedHashMap> data = (List<LinkedHashMap>) response.get("choices");
        LinkedHashMap message = (LinkedHashMap) data.get(0).get("message");
        String[] contents = message.get("content").toString().split("\n");

        QuestionEntity question = QuestionEntity.builder()
                .questionList(Arrays.asList(contents)).build();

        return this.questionRepository
                .save(question)
                .toResponseDTO();
    }
}
