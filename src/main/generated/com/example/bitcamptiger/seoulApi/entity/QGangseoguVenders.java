package com.example.bitcamptiger.seoulApi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGangseoguVenders is a Querydsl query type for GangseoguVenders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGangseoguVenders extends EntityPathBase<GangseoguVenders> {

    private static final long serialVersionUID = 956669944L;

    public static final QGangseoguVenders gangseoguVenders = new QGangseoguVenders("gangseoguVenders");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath 거리가게_유형 = createString("거리가게_유형");

    public final StringPath 데이터_기준일자 = createString("데이터_기준일자");

    public final StringPath 비고 = createString("비고");

    public final StringPath 시군구명 = createString("시군구명");

    public final StringPath 위치 = createString("위치");

    public final StringPath 판매품목 = createString("판매품목");

    public final StringPath 허가기간 = createString("허가기간");

    public QGangseoguVenders(String variable) {
        super(GangseoguVenders.class, forVariable(variable));
    }

    public QGangseoguVenders(Path<? extends GangseoguVenders> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGangseoguVenders(PathMetadata metadata) {
        super(GangseoguVenders.class, metadata);
    }

}

