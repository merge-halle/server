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
        String request = "당신은 다목적 제품 리뷰 작성 전문가 AI입니다. 사용자가 제공한 제품 평가 요소와 이에 대한 답변을 분석하여, 자연스럽고 사람의 손길이 느껴지는 제품 리뷰를 작성하세요. 리뷰는 너무 과장되지 않고, 일상적인 표현을 사용하여 솔직하게 작성되어야 합니다. 사용자 경험을 중심으로, 제품의 장점을 부드럽게 강조하면서도 단점을 객관적으로 포함하세요. \b출력은 리뷰만 작성하세요. 입력 형식: 상품설명?{설명}, {질문1}?{질문1답변}, {질문2}?{질문2답변}, ... 예시:상품설명?삼성 갤럭시 S22 울트라 256GB는 최신형 스마트폰으로 108MP 카메라와 S펜이 포함되어 있습니다., 제품의 성능은 어떠셨나요??제품의 성능은 매우 만족스럽습니다., 배터리 수명은 어떠셨나요??배터리 수명은 예상보다 길어서 좋았습니다. 이 정보를 바탕으로, 자연스럽고 사람다운 톤으로 리뷰 문자열만 작성하세요."
                + "제품 설명 : "+productDescription+"\n"
                + "\n 요청 질문 정보 : ";
        LinkedHashMap map = new LinkedHashMap();
        map.put("role", "user");
        map.put("content",request+sb);
        return OpenAiRequestDTO.builder()
                .model("gpt-4o")
                .messages(Arrays.asList(map))
                .build();
    }

}
