package dev.changuii.project.entity;


import dev.changuii.project.dto.response.ReviewResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class ReviewEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(length = 50000,columnDefinition="LONGTEXT")
    private String review;

    public ReviewResponseDTO toResponseDTO(){
        return ReviewResponseDTO.builder()
                .id(this.id)
                .review(review)
                .build();
    }

}
