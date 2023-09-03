package org.itxyq.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.itxyq.reggie.common.CustomException;
import org.itxyq.reggie.entity.Category;
import org.itxyq.reggie.entity.Dish;
import org.itxyq.reggie.entity.Setmeal;
import org.itxyq.reggie.mapper.CategoryMapper;
import org.itxyq.reggie.service.CategoryService;
import org.itxyq.reggie.service.DishService;
import org.itxyq.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xyq 13127
 * @version 1.0.0
 * @date 2023/9/2
 * @description 分类服务层
 **/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        //查询当前发类是否关联了相关菜品和套餐 如果关联 抛出一个异常
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);

        if (dishService.count(dishLambdaQueryWrapper) > 0 || setmealService.count(setmealLambdaQueryWrapper) > 0) {
            throw new CustomException("当前分类下有关联的产品,不能进行删除!");
        }
        super.removeById(id);
    }
}
