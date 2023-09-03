package org.itxyq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.itxyq.reggie.entity.DishFlavor;
import org.itxyq.reggie.mapper.DishFlavorMapper;
import org.itxyq.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author xyq 13127
 * @version 1.0.0
 * @date 2023/9/3
 * @description 菜品口味服务层
 **/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
