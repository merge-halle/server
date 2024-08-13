package dev.changuii.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.LinkedHashMap;

@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class QuestionDTO {

    private String productDescription;


    public OpenAiRequestDTO toOpenAiRequestDTO(){
        String requestMessage = "당신은 다목적 제품 평가 전문가 AI입니다. 사용자가 제공한 제품 설명을 분석하여 해당 제품의 유형을 파악하고, 그에 맞는 평가 요소들을 질문 형식으로 '질문1\\n질문2\\n질문3\\n질문4\\n'형식으로 나열하세요. 제품의 유형에 따라 주요 평가 요소는 달라질 수 있습니다. 예를 들어, 음식이라면 '이 제품의 맛은 만족스러웠습니까?', '신선도는 어떻습니까?'와 같은 질문을, 가전제품이라면 '이 제품의 성능은 기대에 부응합니까?', '에너지 효율성은 만족스럽습니까?'와 같은 질문을 생성합니다. 제품의 종류와 특성을 고려하여 적절한 평가 요소에 대한 질문을 \n를 사이에둔 형식으로 작성하세요." +
                "\n 예시로는 '저희 기업의 제품은 '신선한 국내산 감자탕 밀키트'입니다. 신선한 재료로 구성된 2인분 분량의 감자탕입니다.'라는 질문이 오면 제품의 맛은 어떠셨나요? 제품의 배송 기간은 적절하였나요? 재료의 신선도는 만족스러우셨나요? 조리 과정은 간편하였나요? 제품의 포장 상태는 어땠나요? 양은 적당했나요? 밀키트의 재료들이 균형잡혀 있었나요? 국물의 깊은 맛은 만족스러웠나요? 제품의 가격 대비 가치는 어떻게 생각하시나요? 다시 구매할 의향이 있으신가요? 와 같은 대답을 반환합니다. 번호 없이 만들어줘 \n 요청 제품 정보 : ";
        LinkedHashMap map = new LinkedHashMap();
        map.put("role", "user");
        map.put("content",requestMessage+ this.productDescription);
        return OpenAiRequestDTO.builder()
                .model("gpt-4o")
                .messages(Arrays.asList(map))
                .build();
    }


}
