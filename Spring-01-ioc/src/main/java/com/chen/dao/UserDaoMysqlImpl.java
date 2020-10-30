package com.chen.dao;

public class UserDaoMysqlImpl implements UserDao{
    @Override
    public void getUser() {
        System.out.println("通过mysql获取数据");
    }
}
