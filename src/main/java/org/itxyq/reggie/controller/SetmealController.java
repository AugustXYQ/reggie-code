package org.itxyq.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.itxyq.reggie.common.R;
import org.itxyq.reggie.dto.SetmealDto;
import org.itxyq.reggie.entity.Category;
import org.itxyq.reggie.entity.Setmeal;
import org.itxyq.reggie.service.CategoryService;
import org.itxyq.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xyq 13127
 * @version 1.0.0
 * @date 2023/9/3
 * @description 套餐管理
 **/
@Slf4j
@RestController
@Api(value = "/setmeal", tags = "测试SetmealController相关api")
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealServicel;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "新增套餐", notes = "新增套餐")
    @ApiImplicitParam(name = "setmealDto", value = "SetmealDto", required = true, dataType = "SetmealDto")
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealServicel.saveWithDish(setmealDto);
        return R.success("新增菜品成功");
    }

    @ApiOperation(value = "获取套餐信息分页", notes = "获取套餐信息分页")
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //构造分页构造器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        //执行查询
        setmealServicel.page(pageInfo, queryWrapper);

        //对象拷贝 可以指定忽略属性
        BeanUtils.copyProperties(pageInfo, setmealDtoPage, "records");

        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据id查询到分类对象
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            setmealDto.setCategoryName(categoryName);
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(list);

        return R.success(setmealDtoPage);
    }

    @ApiOperation(value = "删除套餐", notes = "删除套餐")
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        setmealServicel.removeWithDish(ids);
        return R.success("套餐信息删除成功");
    }

    //修改前端口味不显示规格bug
    @ApiOperation(value = "套餐数据", notes = "根据条件查询套餐数据")
    @ApiImplicitParam(name = "setmeal", value = "Setmeal", required = true, dataType = "Setmeal")
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal) {
        //条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        //查询状态为1  1表示启售
        queryWrapper.eq(Setmeal::getStatus, 1);
        //添加排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        return R.success(setmealServicel.list(queryWrapper));
    }
}
