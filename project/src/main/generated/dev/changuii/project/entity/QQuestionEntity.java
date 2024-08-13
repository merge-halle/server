package dev.changuii.project.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestionEntity is a Querydsl query type for QuestionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestionEntity extends EntityPathBase<QuestionEntity> {

    private static final long serialVersionUID = 1207786314L;

    public static final QQuestionEntity questionEntity = new QQuestionEntity("questionEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath productDescription = createString("productDescription");

    public final ListPath<String, StringPath> questionList = this.<String, StringPath>createList("questionList", String.class, StringPath.class, PathInits.DIRECT2);

    public QQuestionEntity(String variable) {
        super(QuestionEntity.class, forVariable(variable));
    }

    public QQuestionEntity(Path<? extends QuestionEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuestionEntity(PathMetadata metadata) {
        super(QuestionEntity.class, metadata);
    }

}

