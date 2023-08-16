package com.example.bitcamptiger.vendor.repository.Impl;

import com.example.bitcamptiger.vendor.entity.QVendor;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.VendorRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class VendorRepositoryCustomImpl implements VendorRepositoryCustom {

    // querydsl 사용하기 위한 준비
    private final JPAQueryFactory queryFactory;

    //생성자 만들기
    //query를 던질 준비 완료
    public VendorRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Vendor> findVendorByAddressCategory(String address) {

        //selectFrom은 select와 from을 한 번에 같이 불러올 수 있음.
        List<Vendor> content = queryFactory.selectFrom(QVendor.vendor)
                // where절에서 , 로 이어진 부분은 and의 의미.
                // 조건을 여러개를 걸 수 있다. 그러나 모든 조건을 써야하는 것은 아니고 null값으로 생략도 가능하다.
                // 즉, 갖고 있는 값에 따라 조건을 다양하게 걸 수 있다. (동적 쿼리. 쿼리dsl)
                .where(searchAddressEq(address))
                .orderBy(QVendor.vendor.vendorName.asc())
                .fetch();

        // Wildcard.count는 count(*)와 동일. 갯수 세는 집합함수
        // where절의 조건을 부합하는 데이터의 전체 갯수를 센다. 쿼리 결과의 총 레코드 수를 알 수 있음.
//        long total = queryFactory.select(Wildcard.count).from(QVendor.vendor)
//                .where(searchOpenStatusEq(vendorOpenStatus), searchAddressEq(address))
//                .fetchOne();


        return content;
    }

    //Query dsl의 메소드
    //null이거나 null이 아닌 값을 같은지 확인해서 boolean 비교
    //VendorOpenStatus가 OPEN인 vendor만 검색
//    private BooleanExpression searchOpenStatusEq(VendorOpenStatus vendorOpenStatus){
//        return vendorOpenStatus == null ? null : QVendor.vendor.vendorOpenStatus.eq(vendorOpenStatus);
//    }


    //Query dsl의 메소드
    //주소별 검색
    private BooleanExpression searchAddressEq(String address){
        return QVendor.vendor.address.contains(address);
    }









}