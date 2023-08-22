package com.example.bitcamptiger.userOrder.entity;

import com.example.bitcamptiger.cart.entity.Cart;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.vendor.entity.Vendor;
import jakarta.persistence.*;
import jakarta.persistence.metamodel.IdentifiableType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 주문 고유 번호 값

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 주문한 회원

    @OneToMany(mappedBy = "userOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderedMenu> orderedMenus = new HashSet<>(); // 주문한 메뉴들의 정보

    private int totalAmount; // 총 결제 금액

    private int totalQuantity; // 주문 메뉴 수량을 합산한 총 주문 메뉴 수량

    @ManyToOne
    @JoinColumn(name = "vendor_id") // 가게 정보를 참조하는 외래키
    private Vendor vendor; // 해당 메뉴를 제공하는 가게 정보

    // 총 금액 계산 및 저장
    public void calculateTotalAmount() {
        totalAmount = orderedMenus.stream().mapToInt(OrderedMenu::getTotalAmount).sum();
    }

    // 총 수량 계산 및 저장
    public void calculateTotalQuantity() {
        totalQuantity = orderedMenus.stream().mapToInt(OrderedMenu::getQuantity).sum();
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // 수정된 부분: OrderedMenu에 있는 메뉴 정보 가져오기
    public Menu getMenuFromOrderedMenu() {
        if (!orderedMenus.isEmpty()) {
            return orderedMenus.iterator().next().getMenu();
        }
        return null;
    }


}
