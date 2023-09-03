package org.itxyq.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.itxyq.reggie.entity.Orders;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
