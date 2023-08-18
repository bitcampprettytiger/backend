package com.example.bitcamptiger.vendor.repository.Impl;

import com.example.bitcamptiger.vendor.entity.QRandmark;
import com.example.bitcamptiger.vendor.entity.Randmark;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.NowLocationRepositoryCustom;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.example.bitcamptiger.vendor.entity.QRandmark.randmark;

public class NowLocationRepositoryCustomImpl implements NowLocationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public NowLocationRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    double maxDistance = 3000.0;

    @Override
    public List<Randmark> findRandmarkBydistinct(Vendor vendor) {
        QRandmark qRandmark = QRandmark.randmark;

        double vendorLatitude = Double.parseDouble(vendor.getY()); // 위도를 가져옴
        double vendorLongitude = Double.parseDouble(vendor.getX()); // 경도를 가져옴
        double maxDistance = 3000.0; // 3 키로미터
        System.out.println("vendorLatitude"+vendorLatitude+"vendorLongitude"+vendorLongitude+"3");
        NumberExpression<Double> distanceExpression = Expressions.numberTemplate(Double.class,
                "ST_Distance_Sphere(POINT({0}, {1}), POINT({2}, {3}))",
                 qRandmark.Hardness,qRandmark.Latitude, vendorLongitude, vendorLatitude
        );

        return queryFactory.selectFrom(qRandmark)
                .where(distanceExpression.loe(maxDistance))
                .orderBy(distanceExpression.asc())
                .fetch();
    }
}
