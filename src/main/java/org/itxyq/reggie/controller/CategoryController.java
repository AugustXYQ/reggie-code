package org.itxyq.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.itxyq.reggie.common.R;
import org.itxyq.reggie.entity.Category;
import org.itxyq.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xyq 13127
 * @version 1.0.0
 * @date 2023/9/2
 * @description 分类表现层
 **/
@Slf4j
@RestController
@Api(value = "/category", tags = "测试CategoryController相关api")
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "新增分类", notes = "新增分类")
    @ApiImplicitParam(name = "category", value = "Category", required = true, dataType = "Category")
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("新增分类");
    }

    @ApiOperation(value = "获取分类信息分页", notes = "获取分类信息分页")
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        //分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件, 根据sort进行排序
        queryWrapper.orderByAsc(Category::getSort);
        //执行查询
        categoryService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    @ApiOperation(value = "删除分类", notes = "删除分类")
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        categoryService.removeByIds(ids);
        return R.success("分类信息删除成功");
    }

    @ApiOperation(value = "修改分类", notes = "根据id修改分类")
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return R.success("分类信息修改成功");
    }

    @ApiOperation(value = "分类数据", notes = "根据条件查询分类数据")
    @ApiImplicitParam(name = "category", value = "Category", required = true, dataType = "Category")
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        return R.success(categoryService.list(queryWrapper));
    }
}
