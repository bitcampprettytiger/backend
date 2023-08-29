package com.example.bitcamptiger.payments.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayments is a Querydsl query type for Payments
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayments extends EntityPathBase<Payments> {

    private static final long serialVersionUID = -747029533L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayments payments = new QPayments("payments");

    public final StringPath applyNum = createString("applyNum");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath impUid = createString("impUid");

    public final com.example.bitcamptiger.member.entity.QMember member;

    public final StringPath merchantUid = createString("merchantUid");

    public final com.example.bitcamptiger.order.entity.QOrders orders;

    public final NumberPath<Long> paidAmount = createNumber("paidAmount", Long.class);

    public final DateTimePath<java.time.LocalDateTime> payDate = createDateTime("payDate", java.time.LocalDateTime.class);

    public final StringPath payMethod = createString("payMethod");

    public QPayments(String variable) {
        this(Payments.class, forVariable(variable), INITS);
    }

    public QPayments(Path<? extends Payments> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayments(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayments(PathMetadata metadata, PathInits inits) {
        this(Payments.class, metadata, inits);
    }

    public QPayments(Class<? extends Payments> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.example.bitcamptiger.member.entity.QMember(forProperty("member")) : null;
        this.orders = inits.isInitialized("orders") ? new com.example.bitcamptiger.order.entity.QOrders(forProperty("orders"), inits.get("orders")) : null;
    }

}

