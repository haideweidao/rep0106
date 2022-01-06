package com.sibd.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sibd.mp.pojo.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {
    User findById(Long id);
}
