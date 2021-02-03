package jpa.study.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //주문 회원

    /*
    cascade = CascadeType.ALL는
    orderItem이 여러개 저장이되면 아래처럼 행위를 해야되는데
    아래처럼 할 필요 없이 한꺼번에 해주는 기능을 가짐
    persist(orderItemA)
    persist(orderItemB)
    persist(orderItemC)
    persist(order)

    즉 persist(order) 이 행위만 해주면 됨
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; //배송정보
    private LocalDateTime orderDate; //주문시간
    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==연관관계 메서드== 컨틀로하는쪽이 가지는게 좋다. //
    /*
    setMember 메소드 풀어쓰면 아래와 같다.
    public static void main(){
        Member member = new member;
        Order ordr = new Order();

        member.getOrders().add((order);
        order.setMember(member);
    }
     */
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}