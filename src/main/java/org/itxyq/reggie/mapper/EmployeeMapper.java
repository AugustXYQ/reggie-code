package org.itxyq.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.itxyq.reggie.entity.Employee;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
