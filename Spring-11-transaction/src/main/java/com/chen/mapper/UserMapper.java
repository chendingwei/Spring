package com.chen.mapper;

import com.chen.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    public List<User> selectUser();

    public int addUser(@Param("user") User user);

    public int delUser(@Param("id") int id);
}
