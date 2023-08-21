package com.example.bitcamptiger.vendor.repository.Impl;

import com.example.bitcamptiger.menu.entity.QMenu;
import com.example.bitcamptiger.vendor.entity.QVendor;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.VendorRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
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


    //검색어에 해당하는 모든 가게 정보 조회
    @Override
    public List<Vendor> findVendorByCategory(String address, String menuName, String vendorName) {


        // where절에서 or조건절을 사용하기 위해서 BooleanBuilder를 사용
        BooleanBuilder builder = new BooleanBuilder();

        if(address != null && !address.isEmpty()){
            builder.or(QVendor.vendor.address.contains(address));
        }

        if(menuName != null && !menuName.isEmpty()){
            builder.or(QMenu.menu.menuName.contains(menuName));
        }

        if(vendorName != null && !vendorName.isEmpty()){
            builder.or(QVendor.vendor.vendorName.contains(vendorName));
        }

        //selectFrom은 select와 from을 한 번에 같이 불러올 수 있음.
        List<Vendor> content = queryFactory.selectFrom(QVendor.vendor)
                // where절에서 ,로 이어진 부분은 and의 의미.
                //  or 조건을 사용하려면 BooleanBuilder 클래스를 사용하여 동적 쿼리 생성하는 것이 좋음.
                // 조건을 여러개를 걸 수 있다. 그러나 모든 조건을 써야하는 것은 아니고 null값으로 생략도 가능하다.
                // 즉, 갖고 있는 값에 따라 조건을 다양하게 걸 수 있다. (동적 쿼리. 쿼리dsl)
                .leftJoin(QVendor.vendor.menuList, QMenu.menu)
                .where(builder)
                .orderBy(QVendor.vendor.vendorName.asc())
                .fetch();

        // Wildcard.count는 count(*)와 동일. 갯수 세는 집합함수
        // where절의 조건을 부합하는 데이터의 전체 갯수를 센다. 쿼리 결과의 총 레코드 수를 알 수 있음.
//        long total = queryFactory.select(Wildcard.count).from(QVendor.vendor)
//                .where(searchOpenStatusEq(vendorOpenStatus), searchAddressEq(address))
//                .fetchOne();

        return content;
    }

    //길거리 음식, 포장마차 타입 분류
    //해당 타입에 포함되는 가게 조회하기
    @Override
    public List<Vendor> findVendorByvendorType(String vendorType) {

        BooleanBuilder builder = new BooleanBuilder();

        if(vendorType != null && !vendorType.isEmpty()){
            builder.and(QVendor.vendor.vendorType.contains(vendorType));
        }

        List<Vendor> content = queryFactory.selectFrom(QVendor.vendor)
                .where(builder)
                .orderBy(QVendor.vendor.vendorName.asc())
                .fetch();

        return content;
    }

    //메뉴 타입별 가게 정보 조회
    @Override
    public List<Vendor> findMenuByCategory(String menuType) {
        BooleanBuilder builder = new BooleanBuilder();

        if(menuType != null && !menuType.isEmpty()){
            builder.and(QMenu.menu.menuType.contains(menuType));
        }

        List<Vendor> content = queryFactory.selectFrom(QVendor.vendor)
                .leftJoin(QVendor.vendor.menuList, QMenu.menu)
                .where(builder)
                .orderBy(QVendor.vendor.vendorName.asc())
                .fetch();


        return content;
    }

    @Override
    public List<Vendor> findByReview(Double weightedAverageScore) {
        BooleanBuilder builder = new BooleanBuilder();

        
        return null;
    }


    //Query dsl의 메소드
    //vendorType을 기준으로 분류
//    private BooleanExpression searchVendorType(String vendorType){
//        return QVendor.vendor.vendorType.contains(vendorType);
//    }


    //Query dsl의 메소드
    //null이거나 null이 아닌 값을 같은지 확인해서 boolean 비교
    //VendorOpenStatus가 OPEN인 vendor만 검색
//    private BooleanExpression searchOpenStatusEq(VendorOpenStatus vendorOpenStatus){
//        return vendorOpenStatus == null ? null : QVendor.vendor.vendorOpenStatus.eq(vendorOpenStatus);
//    }








}
