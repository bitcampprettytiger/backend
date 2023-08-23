package com.example.bitcamptiger.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderedMenu is a Querydsl query type for OrderedMenu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderedMenu extends EntityPathBase<OrderMenu> {

    private static final long serialVersionUID = 2079491320L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderedMenu orderedMenu = new QOrderedMenu("orderedMenu");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.bitcamptiger.menu.entity.QMenu menu;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final QUserOrder userOrder;

    public QOrderedMenu(String variable) {
        this(OrderMenu.class, forVariable(variable), INITS);
    }

    public QOrderedMenu(Path<? extends OrderMenu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderedMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderedMenu(PathMetadata metadata, PathInits inits) {
        this(OrderMenu.class, metadata, inits);
    }

    public QOrderedMenu(Class<? extends OrderMenu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.menu = inits.isInitialized("menu") ? new com.example.bitcamptiger.menu.entity.QMenu(forProperty("menu"), inits.get("menu")) : null;
        this.userOrder = inits.isInitialized("userOrder") ? new QUserOrder(forProperty("userOrder"), inits.get("userOrder")) : null;
    }

}

