package org.itxyq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.itxyq.reggie.entity.OrderDetail;
import org.itxyq.reggie.mapper.OrderDetailMapper;
import org.itxyq.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author xyq 13127
 * @version 1.0.0
 * @date 2023/9/3
 * @description 订单明细服务层
 **/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
