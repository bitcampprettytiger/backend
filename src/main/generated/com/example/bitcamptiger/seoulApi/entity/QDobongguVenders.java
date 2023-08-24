package com.example.bitcamptiger.seoulApi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDobongguVenders is a Querydsl query type for DobongguVenders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDobongguVenders extends EntityPathBase<DobongguVenders> {

    private static final long serialVersionUID = -1742228749L;

    public static final QDobongguVenders dobongguVenders = new QDobongguVenders("dobongguVenders");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath 도로주소 = createString("도로주소");

    public final StringPath 연번 = createString("연번");

    public final StringPath 운영품목 = createString("운영품목");

    public final StringPath 점용목적 = createString("점용목적");

    public final StringPath 점용장소 = createString("점용장소");

    public final StringPath 지정번호 = createString("지정번호");

    public QDobongguVenders(String variable) {
        super(DobongguVenders.class, forVariable(variable));
    }

    public QDobongguVenders(Path<? extends DobongguVenders> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDobongguVenders(PathMetadata metadata) {
        super(DobongguVenders.class, metadata);
    }

}

