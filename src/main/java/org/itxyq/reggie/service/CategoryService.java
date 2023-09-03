package org.itxyq.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.itxyq.reggie.entity.Category;

public interface CategoryService extends IService<Category> {
    /**
     * @param id 分类id
     * @description 自定义删除方法 当目录关联菜品或者套餐时不删除
     **/
    void remove(Long id);
}
