package com.example.bitcamptiger.Review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserReviewAction is a Querydsl query type for UserReviewAction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserReviewAction extends EntityPathBase<UserReviewAction> {

    private static final long serialVersionUID = -1222648806L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserReviewAction userReviewAction = new QUserReviewAction("userReviewAction");

    public final BooleanPath disliked = createBoolean("disliked");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath liked = createBoolean("liked");

    public final com.example.bitcamptiger.member.entity.QMember member;

    public final QReview review;

    public QUserReviewAction(String variable) {
        this(UserReviewAction.class, forVariable(variable), INITS);
    }

    public QUserReviewAction(Path<? extends UserReviewAction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserReviewAction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserReviewAction(PathMetadata metadata, PathInits inits) {
        this(UserReviewAction.class, metadata, inits);
    }

    public QUserReviewAction(Class<? extends UserReviewAction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.example.bitcamptiger.member.entity.QMember(forProperty("member")) : null;
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

