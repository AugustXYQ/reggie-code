package org.itxyq.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.itxyq.reggie.entity.Orders;

public interface OrdersService extends IService<Orders> {
    /**
     * @param orders 用户订单
     * @description 用户下单
     **/
    void submit(Orders orders);
}
