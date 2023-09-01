package com.example.bitcamptiger.vendor.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVendor is a Querydsl query type for Vendor
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVendor extends EntityPathBase<Vendor> {

    private static final long serialVersionUID = 158633305L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVendor vendor = new QVendor("vendor");

    public final StringPath address = createString("address");

    public final NumberPath<Double> averageReviewScore = createNumber("averageReviewScore", Double.class);

    public final StringPath b_no = createString("b_no");

    public final StringPath businessDay = createString("businessDay");

    public final StringPath close = createString("close");

    public final ListPath<com.example.bitcamptiger.favoritePick.entity.FavoriteVendor, com.example.bitcamptiger.favoritePick.entity.QFavoriteVendor> favoriteVendors = this.<com.example.bitcamptiger.favoritePick.entity.FavoriteVendor, com.example.bitcamptiger.favoritePick.entity.QFavoriteVendor>createList("favoriteVendors", com.example.bitcamptiger.favoritePick.entity.FavoriteVendor.class, com.example.bitcamptiger.favoritePick.entity.QFavoriteVendor.class, PathInits.DIRECT2);

    public final StringPath helpCheck = createString("helpCheck");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath location = createString("location");

    public final com.example.bitcamptiger.member.entity.QMember member;

    public final ListPath<com.example.bitcamptiger.menu.entity.Menu, com.example.bitcamptiger.menu.entity.QMenu> menuList = this.<com.example.bitcamptiger.menu.entity.Menu, com.example.bitcamptiger.menu.entity.QMenu>createList("menuList", com.example.bitcamptiger.menu.entity.Menu.class, com.example.bitcamptiger.menu.entity.QMenu.class, PathInits.DIRECT2);

    public final StringPath open = createString("open");

    public final ListPath<com.example.bitcamptiger.payments.entity.Payments, com.example.bitcamptiger.payments.entity.QPayments> paymentsList = this.<com.example.bitcamptiger.payments.entity.Payments, com.example.bitcamptiger.payments.entity.QPayments>createList("paymentsList", com.example.bitcamptiger.payments.entity.Payments.class, com.example.bitcamptiger.payments.entity.QPayments.class, PathInits.DIRECT2);

    public final StringPath perNo = createString("perNo");

    public final NumberPath<Integer> reviewCount = createNumber("reviewCount", Integer.class);

    public final ListPath<com.example.bitcamptiger.Review.entity.Review, com.example.bitcamptiger.Review.entity.QReview> reviewList = this.<com.example.bitcamptiger.Review.entity.Review, com.example.bitcamptiger.Review.entity.QReview>createList("reviewList", com.example.bitcamptiger.Review.entity.Review.class, com.example.bitcamptiger.Review.entity.QReview.class, PathInits.DIRECT2);

    public final StringPath rlAppiNm = createString("rlAppiNm");

    public final StringPath SIGMenu = createString("SIGMenu");

    public final StringPath tel = createString("tel");

    public final NumberPath<Double> totalReviewScore = createNumber("totalReviewScore", Double.class);

    public final StringPath vendorInfo = createString("vendorInfo");

    public final StringPath vendorName = createString("vendorName");

    public final StringPath vendorOpenStatus = createString("vendorOpenStatus");

    public final StringPath vendorType = createString("vendorType");

    public final StringPath x = createString("x");

    public final StringPath y = createString("y");

    public QVendor(String variable) {
        this(Vendor.class, forVariable(variable), INITS);
    }

    public QVendor(Path<? extends Vendor> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVendor(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVendor(PathMetadata metadata, PathInits inits) {
        this(Vendor.class, metadata, inits);
    }

    public QVendor(Class<? extends Vendor> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.example.bitcamptiger.member.entity.QMember(forProperty("member")) : null;
    }

}

