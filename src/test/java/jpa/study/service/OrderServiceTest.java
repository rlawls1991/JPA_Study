package jpa.study.service;

import jpa.study.domain.Address;
import jpa.study.domain.Member;
import jpa.study.domain.Order;
import jpa.study.domain.OrderStatus;
import jpa.study.domain.item.Book;
import jpa.study.domain.item.Item;
import jpa.study.exception.NotEnoughtStockException;
import jpa.study.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    private Member memberTest = new Member();
    private Item itemTest = new Book();

    @Test
    public void 상품주문() throws Exception {
        // given
        // 테스트_데이터_설정 로 설정

        // when
        Long orderId = orderService.order(memberTest.getId(), itemTest.getId(), 80);

        // then
        Order order = orderRepository.findOne(orderId);
        assertThat(OrderStatus.ORDER).isEqualTo(order.getStatus());

        // 주문한 상품 종류수가 맞는지
        assertThat(order.getOrderItems().size()).isEqualTo(1);

        // 주문한 가격은 * 수량이다.
        assertThat(order.getTotalPrice()).isEqualTo(10000 * 80);

        // 주문 후 남은 재고가 맞는지
        assertThat(itemTest.getStockQuantity()).isEqualTo(20);
    }

    @Test(expected = NotEnoughtStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        // when
        Long orderId = orderService.order(memberTest.getId(), itemTest.getId(), 120);

        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Long orderId = orderService.order(memberTest.getId(), itemTest.getId(), 80);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order order = orderRepository.findOne(orderId);
        // 주문 취소상태가 맞는지
        assertThat(OrderStatus.CANCEL).isEqualTo(order.getStatus());

        // 상품의 수가 다시 원상복귀가 되었는지
        assertThat(itemTest.getStockQuantity()).isEqualTo(100);
    }


    @Before
    public void 테스트_데이터_설정() {
        // given
        memberTest.setName("진석스");
        memberTest.setAddress(new Address("경기", "안양시", "123,123"));
        em.persist(memberTest);

        itemTest.setName("진석 에세이");
        itemTest.setPrice(10000);
        itemTest.setStockQuantity(100);
        em.persist(itemTest);
    }

}