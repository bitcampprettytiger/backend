package com.example.bitcamptiger.menu.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
<<<<<<< HEAD
=======
import com.querydsl.core.types.dsl.PathInits;
>>>>>>> 8fad26b2edd55a3abc95e73c6b26f7996886e569


/**
 * QMenu is a Querydsl query type for Menu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenu extends EntityPathBase<Menu> {

    private static final long serialVersionUID = 781848135L;

<<<<<<< HEAD
=======
    private static final PathInits INITS = PathInits.DIRECT2;

>>>>>>> 8fad26b2edd55a3abc95e73c6b26f7996886e569
    public static final QMenu menu = new QMenu("menu");

    public final NumberPath<Long> id = createNumber("id", Long.class);

<<<<<<< HEAD
    public final StringPath menuName = createString("menuName");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QMenu(String variable) {
        super(Menu.class, forVariable(variable));
    }

    public QMenu(Path<? extends Menu> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMenu(PathMetadata metadata) {
        super(Menu.class, metadata);
=======
    public final StringPath menuContent = createString("menuContent");

    public final StringPath menuName = createString("menuName");

    public final EnumPath<MenuSellStatus> menuSellStatus = createEnum("menuSellStatus", MenuSellStatus.class);

    public final StringPath MenuType = createString("MenuType");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final com.example.bitcamptiger.vendor.entity.QVendor vendor;

    public QMenu(String variable) {
        this(Menu.class, forVariable(variable), INITS);
    }

    public QMenu(Path<? extends Menu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenu(PathMetadata metadata, PathInits inits) {
        this(Menu.class, metadata, inits);
    }

    public QMenu(Class<? extends Menu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vendor = inits.isInitialized("vendor") ? new com.example.bitcamptiger.vendor.entity.QVendor(forProperty("vendor")) : null;
>>>>>>> 8fad26b2edd55a3abc95e73c6b26f7996886e569
    }

}

