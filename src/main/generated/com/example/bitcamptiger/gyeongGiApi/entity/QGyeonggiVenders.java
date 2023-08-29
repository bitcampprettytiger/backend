package com.example.bitcamptiger.gyeongGiApi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGyeonggiVenders is a Querydsl query type for GyeonggiVenders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGyeonggiVenders extends EntityPathBase<GyeonggiVenders> {

    private static final long serialVersionUID = -508673290L;

    public static final QGyeonggiVenders gyeonggiVenders = new QGyeonggiVenders("gyeonggiVenders");

    public final StringPath clsbizYn = createString("clsbizYn");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath induTypeNm = createString("induTypeNm");

    public final StringPath licensgDe = createString("licensgDe");

    public final NumberPath<Double> occupTnAr = createNumber("occupTnAr", Double.class);

    public final StringPath prmsnNm = createString("prmsnNm");

    public final StringPath refineLotnoAddr = createString("refineLotnoAddr");

    public final StringPath refineRoadnmAddr = createString("refineRoadnmAddr");

    public final StringPath refineWgs84Lat = createString("refineWgs84Lat");

    public final StringPath refineWgs84Logt = createString("refineWgs84Logt");

    public final StringPath refineZipno = createString("refineZipno");

    public final StringPath sigunNm = createString("sigunNm");

    public final StringPath storeNm = createString("storeNm");

    public QGyeonggiVenders(String variable) {
        super(GyeonggiVenders.class, forVariable(variable));
    }

    public QGyeonggiVenders(Path<? extends GyeonggiVenders> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGyeonggiVenders(PathMetadata metadata) {
        super(GyeonggiVenders.class, metadata);
    }

}

