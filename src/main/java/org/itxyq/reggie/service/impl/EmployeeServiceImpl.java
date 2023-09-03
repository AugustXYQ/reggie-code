package org.itxyq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.itxyq.reggie.entity.Employee;
import org.itxyq.reggie.mapper.EmployeeMapper;
import org.itxyq.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author xyq 13127
 * @version 1.0.0
 * @date 2023/9/2
 * @description 管理员服务层
 **/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
