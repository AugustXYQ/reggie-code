package org.itxyq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.itxyq.reggie.entity.User;
import org.itxyq.reggie.mapper.UserMapper;
import org.itxyq.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author xyq 13127
 * @version 1.0.0
 * @date 2023/9/3
 * @description 用户服务层
 **/

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
