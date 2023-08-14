package com.example.bitcamptiger.board.repository;

import com.example.bitcamptiger.board.entity.Board;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.bitcamptiger.board.entity.QBoard.board;
//import static com.example.wantedpreonboardingbackend.board.entity.QBoard.board;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{



    private JPAQueryFactory jpaQueryFactory;

    public BoardRepositoryCustomImpl(EntityManager em){
//        ItemRepositoryCustomImpl 여기서 호출되면 엔티티 매니져 객체를 받아서 jpaQueryFactory에 객체를 주입시킨다
        jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Board> getBoardPage(Pageable pageable) {

        List<Board> fetch = jpaQueryFactory.selectFrom(board).orderBy(board.id.desc()).offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        long total = jpaQueryFactory.select(Wildcard.count).from(board).fetchOne();


        return new PageImpl<>(fetch,pageable,total);
    }
}
