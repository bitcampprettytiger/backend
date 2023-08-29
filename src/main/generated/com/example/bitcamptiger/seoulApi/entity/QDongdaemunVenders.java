package com.example.bitcamptiger.seoulApi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDongdaemunVenders is a Querydsl query type for DongdaemunVenders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDongdaemunVenders extends EntityPathBase<DongdaemunVenders> {

    private static final long serialVersionUID = -1328545776L;

    public static final QDongdaemunVenders dongdaemunVenders = new QDongdaemunVenders("dongdaemunVenders");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath 거리가게명 = createString("거리가게명");

    public final StringPath 데이터수집일자 = createString("데이터수집일자");

    public final StringPath 업종 = createString("업종");

    public final StringPath 연번 = createString("연번");

    public final StringPath 주소 = createString("주소");

    public QDongdaemunVenders(String variable) {
        super(DongdaemunVenders.class, forVariable(variable));
    }

    public QDongdaemunVenders(Path<? extends DongdaemunVenders> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDongdaemunVenders(PathMetadata metadata) {
        super(DongdaemunVenders.class, metadata);
    }

}

