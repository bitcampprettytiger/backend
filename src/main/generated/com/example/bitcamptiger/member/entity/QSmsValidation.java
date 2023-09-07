package com.example.bitcamptiger.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSmsValidation is a Querydsl query type for SmsValidation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSmsValidation extends EntityPathBase<SmsValidation> {

    private static final long serialVersionUID = -1088365713L;

    public static final QSmsValidation smsValidation = new QSmsValidation("smsValidation");

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final StringPath tel = createString("tel");

    public QSmsValidation(String variable) {
        super(SmsValidation.class, forVariable(variable));
    }

    public QSmsValidation(Path<? extends SmsValidation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSmsValidation(PathMetadata metadata) {
        super(SmsValidation.class, metadata);
    }

}

