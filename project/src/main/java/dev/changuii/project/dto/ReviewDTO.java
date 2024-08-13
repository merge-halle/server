package dev.changuii.project.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {


    private List<String> questions;
    private List<String> answers;

    public OpenAiRequestDTO toOpenAiRequestDTO(String productDescription){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<questions.size(); i++){
            sb.append(questions.get(i) + answers.get(i)).append(", ");
        }
        String request = productDescription+"\n"
                 + "\n";
        LinkedHashMap map = new LinkedHashMap();
        map.put("role", "user");
        map.put("content",request+sb);
        return OpenAiRequestDTO.builder()
                .model("gpt-4o")
                .messages(Arrays.asList(map))
                .build();
    }

}
