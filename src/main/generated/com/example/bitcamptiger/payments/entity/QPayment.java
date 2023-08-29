package com.example.bitcamptiger.payments.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payments> {

    private static final long serialVersionUID = 768218735L;

    public static final QPayment payment = new QPayment("payment");

    public final StringPath applyNum = createString("applyNum");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath impUid = createString("impUid");

    public final StringPath merchantUid = createString("merchantUid");

    public final NumberPath<Long> paidAmount = createNumber("paidAmount", Long.class);

    public final StringPath payMethod = createString("payMethod");

    public QPayment(String variable) {
        super(Payments.class, forVariable(variable));
    }

    public QPayment(Path<? extends Payments> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPayment(PathMetadata metadata) {
        super(Payments.class, metadata);
    }

}

