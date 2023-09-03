package org.itxyq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.itxyq.reggie.entity.AddressBook;
import org.itxyq.reggie.mapper.AddressBookMapper;
import org.itxyq.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author xyq 13127
 * @version 1.0.0
 * @date 2023/9/3
 * @description 用户地址簿服务层
 **/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
