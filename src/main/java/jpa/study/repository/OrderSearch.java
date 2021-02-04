package jpa.study.repository;

import jpa.study.domain.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
