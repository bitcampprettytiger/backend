package com.example.bitcamptiger.seoulApi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGangNamVenders is a Querydsl query type for GangNamVenders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGangNamVenders extends EntityPathBase<GangNamVenders> {

    private static final long serialVersionUID = 824527529L;

    public static final QGangNamVenders gangNamVenders = new QGangNamVenders("gangNamVenders");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath 관리부서명 = createString("관리부서명");

    public final StringPath 관리부서전화번호 = createString("관리부서전화번호");

    public final StringPath 구분 = createString("구분");

    public final StringPath 데이터기준일자 = createString("데이터기준일자");

    public final StringPath 소재지도로명주소 = createString("소재지도로명주소");

    public final StringPath 소재지지번주소 = createString("소재지지번주소");

    public final StringPath 연번 = createString("연번");

    public final StringPath 취급물품 = createString("취급물품");

    public QGangNamVenders(String variable) {
        super(GangNamVenders.class, forVariable(variable));
    }

    public QGangNamVenders(Path<? extends GangNamVenders> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGangNamVenders(PathMetadata metadata) {
        super(GangNamVenders.class, metadata);
    }

}

