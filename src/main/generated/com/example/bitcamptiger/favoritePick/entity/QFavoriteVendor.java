package com.example.bitcamptiger.favoritePick.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavoriteVendor is a Querydsl query type for FavoriteVendor
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavoriteVendor extends EntityPathBase<FavoriteVendor> {

    private static final long serialVersionUID = 1979230442L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavoriteVendor favoriteVendor = new QFavoriteVendor("favoriteVendor");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.bitcamptiger.member.entity.QMember member;

    public final com.example.bitcamptiger.vendor.entity.QVendor vendor;

    public QFavoriteVendor(String variable) {
        this(FavoriteVendor.class, forVariable(variable), INITS);
    }

    public QFavoriteVendor(Path<? extends FavoriteVendor> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavoriteVendor(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavoriteVendor(PathMetadata metadata, PathInits inits) {
        this(FavoriteVendor.class, metadata, inits);
    }

    public QFavoriteVendor(Class<? extends FavoriteVendor> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.example.bitcamptiger.member.entity.QMember(forProperty("member")) : null;
        this.vendor = inits.isInitialized("vendor") ? new com.example.bitcamptiger.vendor.entity.QVendor(forProperty("vendor"), inits.get("vendor")) : null;
    }

}

