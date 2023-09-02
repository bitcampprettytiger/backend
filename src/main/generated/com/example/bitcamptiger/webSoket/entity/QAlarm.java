package com.example.bitcamptiger.webSoket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAlarm is a Querydsl query type for Alarm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlarm extends EntityPathBase<Alarm> {

    private static final long serialVersionUID = -1309643746L;

    public static final QAlarm alarm = new QAlarm("alarm");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memberId = createString("memberId");

    public final BooleanPath uncheck = createBoolean("uncheck");

    public QAlarm(String variable) {
        super(Alarm.class, forVariable(variable));
    }

    public QAlarm(Path<? extends Alarm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAlarm(PathMetadata metadata) {
        super(Alarm.class, metadata);
    }

}

