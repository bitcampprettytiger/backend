package com.example.bitcamptiger.vendor.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVendor is a Querydsl query type for Vendor
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVendor extends EntityPathBase<Vendor> {

    private static final long serialVersionUID = 158633305L;

    public static final QVendor vendor = new QVendor("vendor");

    public final StringPath address = createString("address");

    public final StringPath businessDay = createString("businessDay");

    public final TimePath<java.time.LocalTime> close = createTime("close", java.time.LocalTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath menu = createString("menu");

    public final TimePath<java.time.LocalTime> open = createTime("open", java.time.LocalTime.class);

    public final StringPath tel = createString("tel");

    public final StringPath vendorName = createString("vendorName");

    public final EnumPath<VendorOpenStatus> vendorOpenStatus = createEnum("vendorOpenStatus", VendorOpenStatus.class);

    public final StringPath vendorType = createString("vendorType");

    public final StringPath x = createString("x");

    public final StringPath y = createString("y");

    public QVendor(String variable) {
        super(Vendor.class, forVariable(variable));
    }

    public QVendor(Path<? extends Vendor> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVendor(PathMetadata metadata) {
        super(Vendor.class, metadata);
    }

}

