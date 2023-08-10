package com.example.bitcamptiger.Review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
<<<<<<< HEAD
import com.querydsl.core.types.dsl.PathInits;
=======
>>>>>>> eea8acc3377211c712ecefd7dd3a7e769b9257b4


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 425298329L;

<<<<<<< HEAD
    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final ListPath<ReviewFile, QReviewFile> images = this.<ReviewFile, QReviewFile>createList("images", ReviewFile.class, QReviewFile.class, PathInits.DIRECT2);

    public final com.example.bitcamptiger.member.entity.QMember member;

    public final NumberPath<Long> orderNum = createNumber("orderNum", Long.class);
=======
    public static final QReview review = new QReview("review");

    public final StringPath orderNum = createString("orderNum");
>>>>>>> eea8acc3377211c712ecefd7dd3a7e769b9257b4

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final StringPath reviewContent = createString("reviewContent");

    public final NumberPath<Long> reviewNum = createNumber("reviewNum", Long.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final NumberPath<Long> storeId = createNumber("storeId", Long.class);

<<<<<<< HEAD
    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.example.bitcamptiger.member.entity.QMember(forProperty("member")) : null;
=======
    public final StringPath userId = createString("userId");

    public QReview(String variable) {
        super(Review.class, forVariable(variable));
    }

    public QReview(Path<? extends Review> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReview(PathMetadata metadata) {
        super(Review.class, metadata);
>>>>>>> eea8acc3377211c712ecefd7dd3a7e769b9257b4
    }

}

