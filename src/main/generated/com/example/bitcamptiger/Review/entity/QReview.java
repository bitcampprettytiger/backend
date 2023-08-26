package com.example.bitcamptiger.Review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 425298329L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final NumberPath<Integer> disLikeCount = createNumber("disLikeCount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final com.example.bitcamptiger.member.entity.QMember member;

    public final com.example.bitcamptiger.order.entity.QOrders orders;

    public final StringPath reviewContent = createString("reviewContent");

    public final DateTimePath<java.time.LocalDateTime> reviewRegDate = createDateTime("reviewRegDate", java.time.LocalDateTime.class);

    public final NumberPath<Double> reviewScore = createNumber("reviewScore", Double.class);

    public final com.example.bitcamptiger.vendor.entity.QVendor vendor;

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
        this.orders = inits.isInitialized("orders") ? new com.example.bitcamptiger.order.entity.QOrders(forProperty("orders"), inits.get("orders")) : null;
        this.vendor = inits.isInitialized("vendor") ? new com.example.bitcamptiger.vendor.entity.QVendor(forProperty("vendor")) : null;
    }

}

