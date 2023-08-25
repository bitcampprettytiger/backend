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

    public static final QVendor vendor = new QVendor("vendor");

    public final StringPath accountNumber = createString("accountNumber");

    public final StringPath accountOwner = createString("accountOwner");

    public final StringPath address = createString("address");

    public final StringPath b_no = createString("b_no");

    public final StringPath bank = createString("bank");

    public final StringPath businessDay = createString("businessDay");

    public final StringPath close = createString("close");

    public final StringPath coolerInfo = createString("coolerInfo");

    public final ListPath<com.example.bitcamptiger.favoritePick.entity.FavoriteVendor, com.example.bitcamptiger.favoritePick.entity.QFavoriteVendor> favoriteVendors = this.<com.example.bitcamptiger.favoritePick.entity.FavoriteVendor, com.example.bitcamptiger.favoritePick.entity.QFavoriteVendor>createList("favoriteVendors", com.example.bitcamptiger.favoritePick.entity.FavoriteVendor.class, com.example.bitcamptiger.favoritePick.entity.QFavoriteVendor.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath location = createString("location");

    public final ListPath<com.example.bitcamptiger.menu.entity.Menu, com.example.bitcamptiger.menu.entity.QMenu> menuList = this.<com.example.bitcamptiger.menu.entity.Menu, com.example.bitcamptiger.menu.entity.QMenu>createList("menuList", com.example.bitcamptiger.menu.entity.Menu.class, com.example.bitcamptiger.menu.entity.QMenu.class, PathInits.DIRECT2);

    public final StringPath open = createString("open");

    public final StringPath perNo = createString("perNo");

    public final NumberPath<Long> reviewCount = createNumber("reviewCount", Long.class);

    public final StringPath rlAppiNm = createString("rlAppiNm");

    public final StringPath tel = createString("tel");

    public final StringPath toiletDistance = createString("toiletDistance");

    public final StringPath toiletInfo = createString("toiletInfo");

    public final NumberPath<Double> totalReviewScore = createNumber("totalReviewScore", Double.class);

    public final StringPath vendorInfo = createString("vendorInfo");

    public final StringPath vendorName = createString("vendorName");

    public final StringPath vendorOpenStatus = createString("vendorOpenStatus");

    public final StringPath vendorType = createString("vendorType");

    public final NumberPath<Double> weightedAverageScore = createNumber("weightedAverageScore", Double.class);

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

