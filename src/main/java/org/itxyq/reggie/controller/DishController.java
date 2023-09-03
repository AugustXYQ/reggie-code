package org.itxyq.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.itxyq.reggie.common.R;
import org.itxyq.reggie.dto.DishDto;
import org.itxyq.reggie.entity.Category;
import org.itxyq.reggie.entity.Dish;
import org.itxyq.reggie.entity.DishFlavor;
import org.itxyq.reggie.service.CategoryService;
import org.itxyq.reggie.service.DishFlavorService;
import org.itxyq.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xyq 13127
 * @version 1.0.0
 * @date 2023/9/3
 * @description 菜品表现层
 **/

@Slf4j
@RestController
@Api(value = "/dish", tags = "测试DishController相关api")
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @ApiOperation(value = "新增菜品", notes = "新增菜品")
    @ApiImplicitParam(name = "dishDto", value = "DishDto", required = true, dataType = "DishDto")
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    @ApiOperation(value = "获取菜品信息分页", notes = "获取菜品信息分页")
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //构造分页构造器
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Dish::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        //执行查询
        dishService.page(pageInfo, queryWrapper);

        //对象拷贝 可以指定忽略属性
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据id查询到分类对象
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            dishDto.setCategoryName(categoryName);
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    @ApiOperation(value = "查询菜品信息", notes = "根据id查询菜品信息")
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        return R.success(dishService.getByIdWithFlavor(id));
    }

    @ApiOperation(value = "修改菜品", notes = "修改菜品")
    @ApiImplicitParam(name = "dishDto", value = "DishDto", required = true, dataType = "DishDto")
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /*@ApiOperation(value = "菜品数据", notes = "根据条件查询菜品数据")
    @ApiImplicitParam(name = "dish", value = "Dish", required = true, dataType = "Dish")
    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish) {
        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        //查询状态为1  1表示启售
        queryWrapper.eq(Dish::getStatus, 1);
        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        return R.success(dishService.list(queryWrapper));
    }*/

    //修改前端口味不显示规格bug
    @ApiOperation(value = "菜品数据", notes = "根据条件查询菜品数据")
    @ApiImplicitParam(name = "dish", value = "Dish", required = true, dataType = "Dish")
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        //查询状态为1  1表示启售
        queryWrapper.eq(Dish::getStatus, 1);
        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);
        List<DishDto> dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据id查询到分类对象
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            dishDto.setCategoryName(categoryName);
            //当前菜品id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
            List<DishFlavor> dishFlavors = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavors);
            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDtoList);
    }
}
