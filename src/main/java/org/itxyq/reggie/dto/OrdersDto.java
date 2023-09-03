package org.itxyq.reggie.dto;

import lombok.Data;
import org.itxyq.reggie.entity.OrderDetail;
import org.itxyq.reggie.entity.Orders;

import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;

}
