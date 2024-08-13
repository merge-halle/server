package dev.changuii.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor @AllArgsConstructor
public class OpenAiRequestDTO {
    //{"model" : "model명", "prompt" : "데이터", "max_tokens" : 100, "temparature" : 1}

    private String model;
    private List<LinkedHashMap> messages;
}
