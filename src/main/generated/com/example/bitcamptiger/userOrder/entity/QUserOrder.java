package com.example.bitcamptiger.userOrder.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserOrder is a Querydsl query type for UserOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserOrder extends EntityPathBase<UserOrder> {

    private static final long serialVersionUID = 981616335L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserOrder userOrder = new QUserOrder("userOrder");

    public final com.example.bitcamptiger.cart.entity.QCart cart;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.bitcamptiger.member.entity.QMember member;

    public final SetPath<OrderedMenu, QOrderedMenu> orderedMenus = this.<OrderedMenu, QOrderedMenu>createSet("orderedMenus", OrderedMenu.class, QOrderedMenu.class, PathInits.DIRECT2);

    public final NumberPath<Integer> totalAmount = createNumber("totalAmount", Integer.class);

    public final NumberPath<Integer> totalQuantity = createNumber("totalQuantity", Integer.class);

    public final com.example.bitcamptiger.vendor.entity.QVendor vendor;

    public QUserOrder(String variable) {
        this(UserOrder.class, forVariable(variable), INITS);
    }

    public QUserOrder(Path<? extends UserOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserOrder(PathMetadata metadata, PathInits inits) {
        this(UserOrder.class, metadata, inits);
    }

    public QUserOrder(Class<? extends UserOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cart = inits.isInitialized("cart") ? new com.example.bitcamptiger.cart.entity.QCart(forProperty("cart"), inits.get("cart")) : null;
        this.member = inits.isInitialized("member") ? new com.example.bitcamptiger.member.entity.QMember(forProperty("member")) : null;
        this.vendor = inits.isInitialized("vendor") ? new com.example.bitcamptiger.vendor.entity.QVendor(forProperty("vendor")) : null;
    }

}

