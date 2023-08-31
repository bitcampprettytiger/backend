package com.example.bitcamptiger.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrders is a Querydsl query type for Orders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrders extends EntityPathBase<Orders> {

    private static final long serialVersionUID = 445720996L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrders orders = new QOrders("orders");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.bitcamptiger.member.entity.QMember member;

    public final DateTimePath<java.time.LocalDateTime> orderDate = createDateTime("orderDate", java.time.LocalDateTime.class);

    public final ListPath<OrderMenu, QOrderMenu> orderMenuList = this.<OrderMenu, QOrderMenu>createList("orderMenuList", OrderMenu.class, QOrderMenu.class, PathInits.DIRECT2);

    public final EnumPath<OrderStatus> orderStatus = createEnum("orderStatus", OrderStatus.class);

    public final com.example.bitcamptiger.payments.entity.QPayments payments;

    public final com.example.bitcamptiger.vendor.entity.QVendor vendor;

    public QOrders(String variable) {
        this(Orders.class, forVariable(variable), INITS);
    }

    public QOrders(Path<? extends Orders> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrders(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrders(PathMetadata metadata, PathInits inits) {
        this(Orders.class, metadata, inits);
    }

    public QOrders(Class<? extends Orders> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.example.bitcamptiger.member.entity.QMember(forProperty("member")) : null;
        this.payments = inits.isInitialized("payments") ? new com.example.bitcamptiger.payments.entity.QPayments(forProperty("payments"), inits.get("payments")) : null;
        this.vendor = inits.isInitialized("vendor") ? new com.example.bitcamptiger.vendor.entity.QVendor(forProperty("vendor"), inits.get("vendor")) : null;
    }

}

