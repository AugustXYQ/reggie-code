package org.itxyq.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.itxyq.reggie.dto.SetmealDto;
import org.itxyq.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * @param setmealDto 套餐信息
     * @description 新增套餐 同时需要保存套餐和菜品的关联关系
     **/
    void saveWithDish(SetmealDto setmealDto);

    /**
     * @param ids 批量删除ids
     * @description 批量删除套餐 菜品及套餐和菜品的关联数据
     **/
    void removeWithDish(List<Long> ids);
}
