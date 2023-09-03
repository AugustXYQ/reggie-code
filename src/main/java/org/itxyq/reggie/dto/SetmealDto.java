package org.itxyq.reggie.dto;

import lombok.Data;
import org.itxyq.reggie.entity.Setmeal;
import org.itxyq.reggie.entity.SetmealDish;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
