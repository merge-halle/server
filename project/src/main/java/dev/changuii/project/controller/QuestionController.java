package dev.changuii.project.controller;


import dev.changuii.project.dto.QuestionDTO;
import dev.changuii.project.dto.response.QuestionResponseDTO;
import dev.changuii.project.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question")
public class QuestionController {


    private final QuestionService questionService;

    public QuestionController(
            @Autowired QuestionService questionService
    ){
        this.questionService=questionService;
    }


    @PostMapping()
    public ResponseEntity<QuestionResponseDTO> createQuestion(
            @RequestBody QuestionDTO questionDTO
            ){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.questionService.generateQuestion(questionDTO));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponseDTO> readQuestion(
            @PathVariable Long questionId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.questionService.readQuestion(questionId));
    }

}
