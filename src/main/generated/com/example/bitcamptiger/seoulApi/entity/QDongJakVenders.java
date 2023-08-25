package com.example.bitcamptiger.seoulApi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDongJakVenders is a Querydsl query type for DongJakVenders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDongJakVenders extends EntityPathBase<DongJakVenders> {

    private static final long serialVersionUID = -171038048L;

    public static final QDongJakVenders dongJakVenders = new QDongJakVenders("dongJakVenders");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath 거리가게명 = createString("거리가게명");

    public final StringPath 구분 = createString("구분");

    public final StringPath 데이터기준일자 = createString("데이터기준일자");

    public final StringPath 업종_판매품목 = createString("업종_판매품목");

    public final NumberPath<Integer> 연번 = createNumber("연번", Integer.class);

    public final StringPath 위치 = createString("위치");

    public QDongJakVenders(String variable) {
        super(DongJakVenders.class, forVariable(variable));
    }

    public QDongJakVenders(Path<? extends DongJakVenders> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDongJakVenders(PathMetadata metadata) {
        super(DongJakVenders.class, metadata);
    }

}

