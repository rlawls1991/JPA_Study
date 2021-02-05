package jpa.study.repository;

import jpa.study.domain.OrderStatus;
import lombok.Getter;

@Getter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
