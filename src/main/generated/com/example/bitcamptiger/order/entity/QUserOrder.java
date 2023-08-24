package com.example.bitcamptiger.order.entity;

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
public class QUserOrder extends EntityPathBase<Orders> {

    private static final long serialVersionUID = 981616335L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserOrder userOrder = new QUserOrder("userOrder");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.bitcamptiger.member.entity.QMember member;

    public final ListPath<OrderMenu, QOrderedMenu> orderedMenuList = this.<OrderMenu, QOrderedMenu>createList("orderedMenuList", OrderMenu.class, QOrderedMenu.class, PathInits.DIRECT2);

    public final NumberPath<Integer> totalAmount = createNumber("totalAmount", Integer.class);

    public final NumberPath<Integer> totalQuantity = createNumber("totalQuantity", Integer.class);

    public final com.example.bitcamptiger.vendor.entity.QVendor vendor;

    public QUserOrder(String variable) {
        this(Orders.class, forVariable(variable), INITS);
    }

    public QUserOrder(Path<? extends Orders> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserOrder(PathMetadata metadata, PathInits inits) {
        this(Orders.class, metadata, inits);
    }

    public QUserOrder(Class<? extends Orders> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.example.bitcamptiger.member.entity.QMember(forProperty("member")) : null;
        this.vendor = inits.isInitialized("vendor") ? new com.example.bitcamptiger.vendor.entity.QVendor(forProperty("vendor")) : null;
    }

}

