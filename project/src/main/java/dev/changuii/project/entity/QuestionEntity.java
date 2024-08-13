package dev.changuii.project.entity;


import dev.changuii.project.dto.response.QuestionResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class QuestionEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ElementCollection
    private List<String> questionList = new ArrayList<>();
    private String productDescription;


    public QuestionResponseDTO toResponseDTO(){
        return QuestionResponseDTO.builder()
                .id(this.id)
                .questionList(this.questionList)
                .build();
    }

}
