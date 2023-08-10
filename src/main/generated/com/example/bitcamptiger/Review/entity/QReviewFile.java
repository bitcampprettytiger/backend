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

<<<<<<< HEAD
    public final StringPath filePath = createString("filePath");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);
=======
    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);
>>>>>>> eea8acc3377211c712ecefd7dd3a7e769b9257b4

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath originName = createString("originName");

    public final QReview review;

<<<<<<< HEAD
    public final StringPath savedName = createString("savedName");
=======
    public final StringPath saveName = createString("saveName");

    public final NumberPath<Long> size = createNumber("size", Long.class);
>>>>>>> eea8acc3377211c712ecefd7dd3a7e769b9257b4

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
<<<<<<< HEAD
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
=======
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review")) : null;
>>>>>>> eea8acc3377211c712ecefd7dd3a7e769b9257b4
    }

}

