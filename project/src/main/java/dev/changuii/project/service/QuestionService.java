package dev.changuii.project.service;

import dev.changuii.project.dto.QuestionDTO;
import dev.changuii.project.dto.response.QuestionResponseDTO;

public interface QuestionService {

    public QuestionResponseDTO generateQuestion(QuestionDTO questionDTO);


}
