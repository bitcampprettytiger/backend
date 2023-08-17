package com.example.bitcamptiger.Review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewFile is a Querydsl query type for ReviewFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewFile extends EntityPathBase<ReviewFile> {

    private static final long serialVersionUID = 1476034229L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewFile reviewFile = new QReviewFile("reviewFile");

    public final QReview review;

    public final StringPath reviewFileCate = createString("reviewFileCate");

    public final StringPath reviewFileName = createString("reviewFileName");

    public final NumberPath<Long> reviewFileNo = createNumber("reviewFileNo", Long.class);

    public final StringPath reviewFileOrigin = createString("reviewFileOrigin");

    public final StringPath reviewFilePath = createString("reviewFilePath");

    public QReviewFile(String variable) {
        this(ReviewFile.class, forVariable(variable), INITS);
    }

    public QReviewFile(Path<? extends ReviewFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewFile(PathMetadata metadata, PathInits inits) {
        this(ReviewFile.class, metadata, inits);
    }

    public QReviewFile(Class<? extends ReviewFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}

