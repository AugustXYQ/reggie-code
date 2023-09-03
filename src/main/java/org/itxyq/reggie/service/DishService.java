package org.itxyq.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.itxyq.reggie.dto.DishDto;
import org.itxyq.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    /**
     * @param dishDto 数据转换层 当前端传入的数据没和表对应时 可以用这个封装
     * @description 新增菜品 同时插入菜品对应的口味数据 需要操作两张表
     **/
    void saveWithFlavor(DishDto dishDto);

    /**
     * @param id 菜品id
     * @return DishDto
     * @description 根据id查询菜品信息和对应的口味信息
     **/
    DishDto getByIdWithFlavor(Long id);

    /**
     * @param dishDto 新信息
     * @description 更新菜品信息和口味信息
     **/
    void updateWithFlavor(DishDto dishDto);
}
