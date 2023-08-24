package com.example.bitcamptiger.vendor.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVendorImage is a Querydsl query type for VendorImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVendorImage extends EntityPathBase<VendorImage> {

    private static final long serialVersionUID = -160261246L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVendorImage vendorImage = new QVendorImage("vendorImage");

    public final StringPath fileCate = createString("fileCate");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath originName = createString("originName");

    public final StringPath S3url = createString("S3url");

    public final StringPath url = createString("url");

    public final QVendor vendor;

    public QVendorImage(String variable) {
        this(VendorImage.class, forVariable(variable), INITS);
    }

    public QVendorImage(Path<? extends VendorImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVendorImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVendorImage(PathMetadata metadata, PathInits inits) {
        this(VendorImage.class, metadata, inits);
    }

    public QVendorImage(Class<? extends VendorImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vendor = inits.isInitialized("vendor") ? new QVendor(forProperty("vendor")) : null;
    }

}

