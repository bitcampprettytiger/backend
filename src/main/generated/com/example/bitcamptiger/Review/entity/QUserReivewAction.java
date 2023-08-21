package com.example.bitcamptiger.Review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserReivewAction is a Querydsl query type for UserReivewAction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserReivewAction extends EntityPathBase<UserReivewAction> {

    private static final long serialVersionUID = -690044780L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserReivewAction userReivewAction = new QUserReivewAction("userReivewAction");

    public final BooleanPath disliked = createBoolean("disliked");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath liked = createBoolean("liked");

    public final com.example.bitcamptiger.member.entity.QMember member;

    public final QReview review;

    public QUserReivewAction(String variable) {
        this(UserReivewAction.class, forVariable(variable), INITS);
    }

    public QUserReivewAction(Path<? extends UserReivewAction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserReivewAction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserReivewAction(PathMetadata metadata, PathInits inits) {
        this(UserReivewAction.class, metadata, inits);
    }

    public QUserReivewAction(Class<? extends UserReivewAction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.example.bitcamptiger.member.entity.QMember(forProperty("member")) : null;
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

