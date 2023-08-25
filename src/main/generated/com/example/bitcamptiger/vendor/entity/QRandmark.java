package com.example.bitcamptiger.vendor.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRandmark is a Querydsl query type for Randmark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRandmark extends EntityPathBase<Randmark> {

    private static final long serialVersionUID = 191356995L;

    public static final QRandmark randmark = new QRandmark("randmark");

    public final StringPath Hardness = createString("Hardness");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath Latitude = createString("Latitude");

    public final StringPath location = createString("location");

    public final StringPath mapLocation = createString("mapLocation");

    public QRandmark(String variable) {
        super(Randmark.class, forVariable(variable));
    }

    public QRandmark(Path<? extends Randmark> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRandmark(PathMetadata metadata) {
        super(Randmark.class, metadata);
    }

}

