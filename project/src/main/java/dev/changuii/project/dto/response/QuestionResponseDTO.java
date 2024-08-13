package dev.changuii.project.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class QuestionResponseDTO {

    private Long id;
    private List<String> questionList;

}
